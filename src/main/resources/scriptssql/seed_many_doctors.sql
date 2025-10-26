-- Seed many doctors (users + user_roles + doctors)
-- Run this against the clinic_db database after creating it (or include it in your setup scripts)

USE clinic_db;

-- Ensure ROLE_DOCTOR exists
INSERT IGNORE INTO roles (name) VALUES ('ROLE_DOCTOR');

-- Common bcrypt password for seeded doctors: password123
-- Hash used in existing scripts: $2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2

-- Helper pattern (idempotent):
-- 1) INSERT IGNORE INTO users (...) VALUES (...);
-- 2) SET @uid = (SELECT id FROM users WHERE username = 'username');
-- 3) INSERT INTO user_roles SELECT @uid, role_id FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
-- 4) INSERT INTO doctors SELECT @uid, specialization, experience_years, available FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id=@uid);


-- Doctor 1
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drallen', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Allen Carter', 'allen.carter@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drallen');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Cardiology', 12, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 2
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drjane', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Jane Roberts', 'jane.roberts@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drjane');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Dermatology', 8, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 3
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drlee', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Samuel Lee', 'samuel.lee@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drlee');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Neurology', 15, FALSE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 4
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drpatel', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Priya Patel', 'priya.patel@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drpatel');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Pediatrics', 7, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 5
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drwang', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Ming Wang', 'ming.wang@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drwang');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Orthopedics', 10, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 6
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drgarcia', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Maria Garcia', 'maria.garcia@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drgarcia');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Gynecology', 11, FALSE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 7
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drchen', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Li Chen', 'li.chen@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drchen');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Ophthalmology', 9, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 8
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drkhan', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Ahmed Khan', 'ahmed.khan@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drkhan');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'ENT', 6, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 9
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drsantos', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Carlos Santos', 'carlos.santos@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drsantos');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Psychiatry', 14, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 10
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drmiller', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Susan Miller', 'susan.miller@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drmiller');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Oncology', 13, FALSE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 11
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drnguyen', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Thanh Nguyen', 'thanh.nguyen@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drnguyen');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Endocrinology', 8, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 12
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drbrown', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. William Brown', 'william.brown@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drbrown');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Urology', 10, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 13
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drsingh', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Rajesh Singh', 'rajesh.singh@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drsingh');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Nephrology', 9, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 14
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drmartinez', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Elena Martinez', 'elena.martinez@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drmartinez');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Gastroenterology', 12, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 15
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drwilson', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Mark Wilson', 'mark.wilson@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drwilson');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Pulmonology', 11, FALSE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 16
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drrodriguez', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Ana Rodriguez', 'ana.rodriguez@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drrodriguez');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Rheumatology', 7, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 17
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drcooper', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Emily Cooper', 'emily.cooper@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drcooper');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Hematology', 6, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 18
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drkhanna', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Vikram Khanna', 'vikram.khanna@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drkhanna');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Infectious Disease', 14, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 19
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drwhite', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Nancy White', 'nancy.white@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drwhite');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'General Practice', 5, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 20
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('dradams', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Peter Adams', 'peter.adams@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'dradams');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Family Medicine', 9, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 21
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drdental', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Olivia Dent', 'olivia.dent@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drdental');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Dentistry', 4, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 22
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drradiology', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Kevin Park', 'kevin.park@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drradiology');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Radiology', 13, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 23
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('drpath', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Laura Park', 'laura.park@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'drpath');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Pathology', 16, FALSE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 24
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('dranesth', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Robert Young', 'robert.young@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'dranesth');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Anesthesiology', 20, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- Doctor 25
INSERT IGNORE INTO users (username, password, full_name, email, role, enabled)
VALUES ('dremergency', '$2a$10$NvJfFDCbfKVxAqvmZLZFNu3D3GCAX3QnZcCvLGsGkSbqn4hD7x4A2', 'Dr. Victor Stone', 'victor.stone@clinic.com', 'DOCTOR', TRUE);
SET @uid = (SELECT id FROM users WHERE username = 'dremergency');
INSERT INTO user_roles (user_id, role_id)
SELECT @uid, (SELECT id FROM roles WHERE name = 'ROLE_DOCTOR') FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM user_roles WHERE user_id=@uid AND role_id=(SELECT id FROM roles WHERE name='ROLE_DOCTOR'));
INSERT INTO doctors (user_id, specialization, experience_years, available)
SELECT @uid, 'Emergency Medicine', 18, TRUE FROM DUAL
WHERE NOT EXISTS(SELECT 1 FROM doctors WHERE user_id = @uid);

-- End of seed
SELECT 'Seed completed' as status;
