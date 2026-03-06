-- =============================================================
-- Clinic Microservices - MySQL Initialization Script
-- Runs once on first container startup
-- Creates databases and grants permissions to clinic_user
-- =============================================================

-- Appointment Service Database
CREATE DATABASE IF NOT EXISTS clinic_appointment_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Doctor Service Database
CREATE DATABASE IF NOT EXISTS clinic_doctor_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Patient Service Database
CREATE DATABASE IF NOT EXISTS clinic_patient_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Grant full privileges to clinic_user on all 3 schemas
GRANT ALL PRIVILEGES ON clinic_appointment_db.* TO 'clinic_user'@'%';
GRANT ALL PRIVILEGES ON clinic_doctor_db.*       TO 'clinic_user'@'%';
GRANT ALL PRIVILEGES ON clinic_patient_db.*      TO 'clinic_user'@'%';

FLUSH PRIVILEGES;
