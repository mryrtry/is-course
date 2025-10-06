CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_resources_type ON resources (type);
CREATE INDEX idx_materials_name ON materials (name);
CREATE INDEX idx_bookings_times ON bookings (resource_id, start_time, end_time);
CREATE INDEX idx_admissions_user_resource ON admissions (user_id, resource_id);
CREATE INDEX idx_induction_attempts_user ON induction_attempts (user_id);
CREATE INDEX idx_incidents_resource_status ON incidents (resource_id, status);