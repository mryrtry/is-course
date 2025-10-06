-- USERS
INSERT INTO fab.users (username, email, password_hash, full_name, status)
VALUES ('alice', 'alice@example.com', 'hash1', 'Alice Ivanova', 'active'),
       ('bob', 'bob@example.com', 'hash2', 'Bob Petrov', 'active'),
       ('kate', 'kate@example.com', 'hash3', 'Ekaterina Sidorova', 'active'),
       ('mike', 'mike@example.com', 'hash4', 'Mikhail Smirnov', 'blocked');

-- ROLES
INSERT INTO fab.roles (name, description)
VALUES ('Novice', 'Базовый пользователь'),
       ('Master', 'Опытный пользователь'),
       ('Moderator', 'Модератор ресурса'),
       ('Admin', 'Администратор системы'),
       ('Technician', 'Технический специалист');

-- USER_ROLES
INSERT INTO fab.user_roles (user_id, role_id, assigned_by)
SELECT u.user_id, r.role_id, 1
FROM fab.users u
         JOIN fab.roles r ON r.name = 'Novice'
WHERE u.username IN ('alice', 'bob', 'kate');

INSERT INTO fab.user_roles (user_id, role_id, assigned_by)
SELECT u.user_id, r.role_id, 1
FROM fab.users u
         JOIN fab.roles r ON r.name = 'Admin'
WHERE u.username = 'mike';

-- RESOURCES
INSERT INTO fab.resources (name, type, serial_number, model, status, usage_cost_per_hour)
VALUES ('3D Printer Prusa', 'machine', 'SN-3D-001', 'Prusa i3', 'operational', 200),
       ('Laser Cutter', 'machine', 'SN-LAS-002', 'Epilog Zing', 'operational', 500),
       ('CNC Mill', 'machine', 'SN-CNC-003', 'Roland MDX', 'maintenance', 800);

-- MATERIALS
INSERT INTO fab.materials (name, category, specifications, unit, unit_cost, stock_qty, min_stock_threshold)
VALUES ('PLA Filament Red', 'plastic', '{
  "diameter": "1.75mm"
}', 'kg', 1200, 15, 5),
       ('PLA Filament Black', 'plastic', '{
         "diameter": "1.75mm"
       }', 'kg', 1250, 10, 3),
       ('Plywood 4mm', 'wood', '{
         "size": "600x400mm"
       }', 'sheet', 300, 50, 20),
       ('Acrylic 3mm', 'plastic', '{
         "size": "600x400mm"
       }', 'sheet', 400, 30, 10);

-- RESOURCE ↔ MATERIALS (recommended)
INSERT INTO fab.resource_materials (resource_id, material_id, is_recommended, notes)
SELECT r.resource_id, m.material_id, true, 'Рекомендованный материал'
FROM fab.resources r,
     fab.materials m
WHERE r.name = '3D Printer Prusa'
  AND m.name LIKE 'PLA%';

INSERT INTO fab.resource_materials (resource_id, material_id, is_recommended, notes)
SELECT r.resource_id, m.material_id, true, 'Подходит для лазерной резки'
FROM fab.resources r,
     fab.materials m
WHERE r.name = 'Laser Cutter'
  AND m.name IN ('Plywood 4mm', 'Acrylic 3mm');

-- INDUCTIONS
INSERT INTO fab.inductions (title, description, version, created_by, duration_hours)
VALUES ('3D Printing Basics', 'Введение в 3D-печать', 'v1.0', 1, 2),
       ('Laser Cutter Safety', 'Безопасность работы с лазером', 'v1.0', 2, 1.5);

-- INDUCTION ATTEMPTS
INSERT INTO fab.induction_attempts (user_id, induction_id, status, score, practical_signed_by, passed_at, expires_at)
VALUES (1, 1, 'passed', 95, 2, now() - interval '10 days', now() + interval '1 year'),
       (2, 2, 'passed', 87, 2, now() - interval '5 days', now() + interval '1 year');

-- ADMISSIONS (user -> resource)
INSERT INTO fab.admissions (user_id, resource_id, level, granted_by, granted_at, status)
VALUES (1, 1, 'full', 2, now() - interval '7 days', 'active'),
       (2, 2, 'trial', 2, now() - interval '3 days', 'active');

-- BOOKINGS
INSERT INTO fab.bookings (user_id, resource_id, start_time, end_time, purpose, status, approved_by)
VALUES (1, 1, now() + interval '1 day', now() + interval '1 day 2 hours', 'Учебный проект', 'booked', 2),
       (2, 2, now() + interval '2 days', now() + interval '2 days 3 hours', 'Коммерческий заказ', 'confirmed', 2);

-- BOOKING_MATERIALS
INSERT INTO fab.booking_materials (booking_id, material_id, planned_qty, actual_qty, issued_by, issued_at, cost)
VALUES (1, 1, 0.5, 0.5, 2, now() + interval '1 day', 600),
       (2, 3, 2, NULL, NULL, NULL, NULL);

-- TOPICS
INSERT INTO fab.topics (title, body, author_id, status)
VALUES ('Как калибровать 3D-принтер', 'Пошаговое руководство...', 1, 'published'),
       ('Лазерная резка фанеры', 'Советы и лайфхаки...', 2, 'published');

-- TOPIC_VERSIONS
INSERT INTO fab.topic_versions (topic_id, version_number, content, created_by)
VALUES (1, 1, 'Версия 1 инструкции по 3D-принтеру', 1),
       (2, 1, 'Версия 1 инструкции по лазерной резке', 2);

-- INCIDENTS
INSERT INTO fab.incidents (reported_by, resource_id, type, description, status, severity)
VALUES (1, 3, 'damage', 'Поломка шпинделя на ЧПУ', 'new', 'critical');

-- MAINTENANCE_RECORDS
INSERT INTO fab.maintenance_records (resource_id, performed_by, work_description, hours_spent, cost)
VALUES (3, 4, 'Замена шпинделя и настройка', 3.5, 5000);

-- MAINTENANCE_USED_MATERIALS
INSERT INTO fab.maintenance_used_materials (maintenance_id, material_id, qty, unit_cost)
VALUES (1, 4, 1, 400);

-- SUPPLIERS
INSERT INTO fab.suppliers (name, contact_info, lead_time_days)
VALUES ('ООО "ФабМатериалы"', '{
  "phone": "+7 999 123-45-67",
  "email": "sales@fabmat.ru"
}', 7),
       ('ИП "ПластикТорг"', '{
         "phone": "+7 999 987-65-43",
         "email": "info@plastictorg.ru"
       }', 5);

-- PURCHASE REQUESTS
INSERT INTO fab.purchase_requests (created_by, supplier_id, status, expected_delivery, notes)
VALUES (2, 1, 'requested', now() + interval '14 days', 'Закупка пластика');

-- PURCHASE REQUEST ITEMS
INSERT INTO fab.purchase_request_items (pr_id, material_id, requested_qty, price)
VALUES (1, 2, 20, 1250);
