CREATE SCHEMA IF NOT EXISTS fab;
SET search_path TO fab, public;

-- -----------------------
-- Основные сущности
-- -----------------------

-- Пользователи
CREATE TABLE users
(
    user_id       BIGSERIAL PRIMARY KEY,
    username      TEXT NOT NULL UNIQUE,
    email         TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    full_name     TEXT,
    registered_at TIMESTAMPTZ   DEFAULT now(),
    last_login    TIMESTAMPTZ,
    status        TEXT NOT NULL DEFAULT 'active', -- active / blocked
    rating        NUMERIC(3, 2) DEFAULT 0,
    access_level  INTEGER       DEFAULT 1,
    profile       JSONB
);

-- Роли
CREATE TABLE roles
(
    role_id     BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT
);

-- (M:N) Пользователей с ролями
CREATE TABLE user_roles
(
    user_id     BIGINT NOT NULL,
    role_id     BIGINT NOT NULL,
    assigned_by BIGINT,
    assigned_at TIMESTAMPTZ DEFAULT now(),
    expires_at  TIMESTAMPTZ,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_assigned_by FOREIGN KEY (assigned_by) REFERENCES users (user_id)
);

-- Ресурсы
CREATE TABLE resources
(
    resource_id         BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    type                TEXT NOT NULL, -- machine/tool/area/other
    serial_number       TEXT,
    model               TEXT,
    technical_specs     JSONB,
    status              TEXT NOT NULL  DEFAULT 'operational',
    usage_cost_per_hour NUMERIC(10, 2) DEFAULT 0,
    created_at          TIMESTAMPTZ    DEFAULT now()
);

-- Материалы
CREATE TABLE materials
(
    material_id         BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    category            TEXT,
    specifications      JSONB,
    unit                TEXT           DEFAULT 'pcs',
    unit_cost           NUMERIC(12, 2) DEFAULT 0,
    stock_qty           NUMERIC(12, 3) DEFAULT 0,
    min_stock_threshold NUMERIC(12, 3) DEFAULT 0,
    created_at          TIMESTAMPTZ    DEFAULT now()
);

-- (M:N) Ресурсы с материалами
CREATE TABLE resource_materials
(
    resource_id        BIGINT NOT NULL,
    material_id        BIGINT NOT NULL,
    recommended_params JSONB,
    is_recommended     BOOLEAN DEFAULT true,
    notes              TEXT,
    PRIMARY KEY (resource_id, material_id),
    CONSTRAINT fk_resource_materials_resource FOREIGN KEY (resource_id) REFERENCES resources (resource_id) ON DELETE CASCADE,
    CONSTRAINT fk_resource_materials_material FOREIGN KEY (material_id) REFERENCES materials (material_id) ON DELETE CASCADE
);

-- Курсы
CREATE TABLE inductions
(
    induction_id        BIGSERIAL PRIMARY KEY,
    title               TEXT NOT NULL,
    description         TEXT,
    version             TEXT,
    theory_content_link TEXT,
    created_by          BIGINT,
    created_at          TIMESTAMPTZ DEFAULT now(),
    duration_hours      NUMERIC(5, 2),
    CONSTRAINT fk_inductions_created_by FOREIGN KEY (created_by) REFERENCES users (user_id)
);

-- Попытки прохождения курса
CREATE TABLE induction_attempts
(
    attempt_id          BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL,
    induction_id        BIGINT NOT NULL,
    status              TEXT   NOT NULL DEFAULT 'in_progress',
    score               NUMERIC(5, 2),
    practical_signed_by BIGINT,
    passed_at           TIMESTAMPTZ,
    expires_at          TIMESTAMPTZ,
    created_at          TIMESTAMPTZ     DEFAULT now(),
    CONSTRAINT fk_attempts_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_attempts_induction FOREIGN KEY (induction_id) REFERENCES inductions (induction_id) ON DELETE CASCADE,
    CONSTRAINT fk_attempts_practical_by FOREIGN KEY (practical_signed_by) REFERENCES users (user_id)
);

-- Доступы
CREATE TABLE admissions
(
    admission_id BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    resource_id  BIGINT NOT NULL,
    level        TEXT   NOT NULL DEFAULT 'trial',
    granted_by   BIGINT,
    granted_at   TIMESTAMPTZ     DEFAULT now(),
    expires_at   TIMESTAMPTZ,
    status       TEXT   NOT NULL DEFAULT 'active',
    notes        TEXT,
    UNIQUE (user_id, resource_id),
    CONSTRAINT fk_admissions_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_admissions_resource FOREIGN KEY (resource_id) REFERENCES resources (resource_id) ON DELETE CASCADE,
    CONSTRAINT fk_admissions_granted_by FOREIGN KEY (granted_by) REFERENCES users (user_id)
);

-- Бронирования
CREATE TABLE bookings
(
    booking_id        BIGSERIAL PRIMARY KEY,
    user_id           BIGINT      NOT NULL,
    resource_id       BIGINT      NOT NULL,
    start_time        TIMESTAMPTZ NOT NULL,
    end_time          TIMESTAMPTZ NOT NULL,
    purpose           TEXT,
    planned_materials JSONB,
    status            TEXT        DEFAULT 'booked',
    created_at        TIMESTAMPTZ DEFAULT now(),
    approved_by       BIGINT,
    CHECK (start_time < end_time),
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_bookings_resource FOREIGN KEY (resource_id) REFERENCES resources (resource_id) ON DELETE CASCADE,
    CONSTRAINT fk_bookings_approved_by FOREIGN KEY (approved_by) REFERENCES users (user_id)
);

-- Бронирование материала
CREATE TABLE booking_materials
(
    booking_id  BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    planned_qty NUMERIC(12, 3) DEFAULT 0,
    actual_qty  NUMERIC(12, 3),
    issued_by   BIGINT,
    issued_at   TIMESTAMPTZ,
    cost        NUMERIC(12, 2),
    PRIMARY KEY (booking_id, material_id),
    CONSTRAINT fk_booking_materials_booking FOREIGN KEY (booking_id) REFERENCES bookings (booking_id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_materials_material FOREIGN KEY (material_id) REFERENCES materials (material_id) ON DELETE RESTRICT,
    CONSTRAINT fk_booking_materials_issued_by FOREIGN KEY (issued_by) REFERENCES users (user_id)
);

-- Топики
CREATE TABLE topics
(
    topic_id   BIGSERIAL PRIMARY KEY,
    title      TEXT NOT NULL,
    body       TEXT,
    author_id  BIGINT,
    created_at TIMESTAMPTZ   DEFAULT now(),
    status     TEXT          DEFAULT 'draft',
    rating     NUMERIC(3, 2) DEFAULT 0,
    CONSTRAINT fk_topics_author FOREIGN KEY (author_id) REFERENCES users (user_id)
);

-- Версии топика
CREATE TABLE topic_versions
(
    version_id     BIGSERIAL PRIMARY KEY,
    topic_id       BIGINT NOT NULL,
    version_number INT    NOT NULL DEFAULT 1,
    content        TEXT,
    created_at     TIMESTAMPTZ     DEFAULT now(),
    created_by     BIGINT,
    CONSTRAINT fk_topic_versions_topic FOREIGN KEY (topic_id) REFERENCES topics (topic_id) ON DELETE CASCADE,
    CONSTRAINT fk_topic_versions_created_by FOREIGN KEY (created_by) REFERENCES users (user_id)
);

-- Инциденты
CREATE TABLE incidents
(
    incident_id      BIGSERIAL PRIMARY KEY,
    reported_by      BIGINT,
    resource_id      BIGINT,
    assigned_to      BIGINT,
    type             TEXT,
    description      TEXT,
    status           TEXT        DEFAULT 'new',
    severity         TEXT        DEFAULT 'normal',
    created_at       TIMESTAMPTZ DEFAULT now(),
    resolution_notes TEXT,
    CONSTRAINT fk_incidents_reported_by FOREIGN KEY (reported_by) REFERENCES users (user_id),
    CONSTRAINT fk_incidents_resource FOREIGN KEY (resource_id) REFERENCES resources (resource_id),
    CONSTRAINT fk_incidents_assigned_to FOREIGN KEY (assigned_to) REFERENCES users (user_id)
);

-- Запись о ремонте / техническом обслуживании оборудования
CREATE TABLE maintenance_records
(
    maintenance_id   BIGSERIAL PRIMARY KEY,
    resource_id      BIGINT NOT NULL,
    performed_by     BIGINT,
    performed_at     TIMESTAMPTZ DEFAULT now(),
    work_description TEXT,
    hours_spent      NUMERIC(6, 2),
    cost             NUMERIC(12, 2),
    CONSTRAINT fk_maintenance_resource FOREIGN KEY (resource_id) REFERENCES resources (resource_id) ON DELETE CASCADE,
    CONSTRAINT fk_maintenance_performed_by FOREIGN KEY (performed_by) REFERENCES users (user_id)
);

-- Затраченные на обслуживание ресурсы
CREATE TABLE maintenance_used_materials
(
    maintenance_id BIGINT NOT NULL,
    material_id    BIGINT NOT NULL,
    qty            NUMERIC(12, 3) DEFAULT 0,
    unit_cost      NUMERIC(12, 2),
    PRIMARY KEY (maintenance_id, material_id),
    CONSTRAINT fk_mum_maintenance FOREIGN KEY (maintenance_id) REFERENCES maintenance_records (maintenance_id) ON DELETE CASCADE,
    CONSTRAINT fk_mum_material FOREIGN KEY (material_id) REFERENCES materials (material_id) ON DELETE RESTRICT
);

-- Поставщики
CREATE TABLE suppliers
(
    supplier_id    BIGSERIAL PRIMARY KEY,
    name           TEXT NOT NULL,
    contact_info   JSONB,
    lead_time_days INT,
    created_at     TIMESTAMPTZ DEFAULT now()
);

-- Заявки на закупку материалов, комплектующих
CREATE TABLE purchase_requests
(
    pr_id             BIGSERIAL PRIMARY KEY,
    created_by        BIGINT,
    supplier_id       BIGINT,
    status            TEXT        DEFAULT 'requested',
    created_at        TIMESTAMPTZ DEFAULT now(),
    expected_delivery TIMESTAMPTZ,
    notes             TEXT,
    CONSTRAINT fk_pr_created_by FOREIGN KEY (created_by) REFERENCES users (user_id),
    CONSTRAINT fk_pr_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

-- Материал, комплектующие в закупке
CREATE TABLE purchase_request_items
(
    pr_id         BIGINT NOT NULL,
    material_id   BIGINT NOT NULL,
    requested_qty NUMERIC(12, 3) DEFAULT 0,
    approved_qty  NUMERIC(12, 3),
    price         NUMERIC(12, 2),
    PRIMARY KEY (pr_id, material_id),
    CONSTRAINT fk_pri_pr FOREIGN KEY (pr_id) REFERENCES purchase_requests (pr_id) ON DELETE CASCADE,
    CONSTRAINT fk_pri_material FOREIGN KEY (material_id) REFERENCES materials (material_id)
);
-- -----------------------
-- Примеры представлений
-- -----------------------

-- Просмотр активных бронирований для ресурса
CREATE OR REPLACE VIEW vw_active_bookings AS
SELECT b.booking_id,
       b.resource_id,
       r.name as resource_name,
       b.user_id,
       u.full_name,
       b.start_time,
       b.end_time,
       b.status
FROM bookings b
         JOIN resources r ON b.resource_id = r.resource_id
         JOIN users u ON b.user_id = u.user_id
WHERE b.status IN ('booked', 'confirmed');

-- Просмотр материалов ниже порога
CREATE OR REPLACE VIEW vw_materials_below_threshold AS
SELECT material_id, name, stock_qty, min_stock_threshold
FROM materials
WHERE stock_qty < COALESCE(min_stock_threshold, 0);