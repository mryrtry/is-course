-- 1. Функция проверки допуска пользователя для брони
CREATE OR REPLACE FUNCTION fab.check_user_has_active_admission(p_user_id BIGINT, p_resource_id BIGINT)
    RETURNS BOOLEAN
    LANGUAGE plpgsql AS
$$
DECLARE
    cnt INT;
BEGIN
    SELECT COUNT(*)
    INTO cnt
    FROM admissions
    WHERE user_id = p_user_id
      AND resource_id = p_resource_id
      AND status = 'active'
      AND (expires_at IS NULL OR expires_at > now());
    RETURN cnt > 0;
END;
$$;

-- 2. Триггер BEFORE INSERT на bookings: проверка допуска и пересечения тайм-слотов
CREATE OR REPLACE FUNCTION fab.fn_before_insert_booking()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
DECLARE
    has_admission BOOLEAN;
    overlap_count INT;
BEGIN
    -- 1) Проверка допуска
    has_admission := fab.check_user_has_active_admission(NEW.user_id, NEW.resource_id);
    IF NOT has_admission THEN
        RAISE EXCEPTION 'User % has no active admission for resource %', NEW.user_id, NEW.resource_id;
    END IF;

    -- 2) Проверка пересечения по ресурсу: не разрешаем накладки в одно и то же время (простая логика)
    SELECT COUNT(*)
    INTO overlap_count
    FROM bookings
    WHERE resource_id = NEW.resource_id
      AND status IN ('booked', 'confirmed')
      AND NOT (NEW.end_time <= start_time OR NEW.start_time >= end_time);

    IF overlap_count > 0 THEN
        RAISE EXCEPTION 'Time slot conflict for resource % (overlaps with % existing bookings)', NEW.resource_id, overlap_count;
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_before_insert_booking
    BEFORE INSERT
    ON bookings
    FOR EACH ROW
EXECUTE FUNCTION fab.fn_before_insert_booking();

-- 3. Триггер BEFORE INSERT на incidents: при новом инциденте с type=damage переводим ресурс в out_of_service
CREATE OR REPLACE FUNCTION fab.fn_before_insert_incident()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NEW.resource_id IS NOT NULL AND LOWER(NEW.type) = 'damage' THEN
        UPDATE resources SET status = 'out_of_service' WHERE resource_id = NEW.resource_id;
    END IF;
    RETURN NEW;
END;
$$;

-- 4. Триггер AFTER INSERT на maintenance_records: обновляем статус ресурса в operational
CREATE TRIGGER trg_before_insert_incident
    BEFORE INSERT
    ON incidents
    FOR EACH ROW
EXECUTE FUNCTION fab.fn_before_insert_incident();

CREATE OR REPLACE FUNCTION fab.fn_after_insert_maintenance()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE resources SET status = 'operational' WHERE resource_id = NEW.resource_id;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_after_insert_maintenance
    AFTER INSERT
    ON maintenance_records
    FOR EACH ROW
EXECUTE FUNCTION fab.fn_after_insert_maintenance();