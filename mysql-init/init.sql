-- =============================================================
-- MySQL Init Script
-- Grants clinic_user access to all 3 service schemas.
-- Runs once on first container startup.
-- =============================================================

CREATE DATABASE IF NOT EXISTS clinic_appointment_db;
CREATE DATABASE IF NOT EXISTS clinic_doctor_db;
CREATE DATABASE IF NOT EXISTS clinic_patient_db;

GRANT ALL PRIVILEGES ON clinic_appointment_db.* TO 'clinic_user'@'%';
GRANT ALL PRIVILEGES ON clinic_doctor_db.*        TO 'clinic_user'@'%';
GRANT ALL PRIVILEGES ON clinic_patient_db.*       TO 'clinic_user'@'%';

FLUSH PRIVILEGES;
