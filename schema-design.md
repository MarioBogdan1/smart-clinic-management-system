# Smart Clinic Management System - MySQL Schema Design

## Tables

### 1. doctor
- id BIGINT PRIMARY KEY AUTO_INCREMENT
- name VARCHAR(255) NOT NULL
- speciality VARCHAR(255) NOT NULL
- email VARCHAR(255) UNIQUE NOT NULL
- phone_number VARCHAR(50) UNIQUE NOT NULL
- password VARCHAR(255) NOT NULL

### 2. patient
- id BIGINT PRIMARY KEY AUTO_INCREMENT
- name VARCHAR(255) NOT NULL
- email VARCHAR(255) UNIQUE NOT NULL
- phone_number VARCHAR(50) UNIQUE NOT NULL
- password VARCHAR(255) NOT NULL

### 3. appointment
- id BIGINT PRIMARY KEY AUTO_INCREMENT
- doctor_id BIGINT NOT NULL
- patient_id BIGINT NOT NULL
- appointment_time DATETIME NOT NULL
- status VARCHAR(50)

Foreign Keys:
- doctor_id REFERENCES doctor(id)
- patient_id REFERENCES patient(id)

### 4. prescription
- id BIGINT PRIMARY KEY AUTO_INCREMENT
- medication VARCHAR(255) NOT NULL
- dosage VARCHAR(255) NOT NULL
- notes TEXT NOT NULL
- doctor_id BIGINT NOT NULL
- patient_id BIGINT NOT NULL

Foreign Keys:
- doctor_id REFERENCES doctor(id)
- patient_id REFERENCES patient(id)

### 5. doctor_available_times
- doctor_id BIGINT NOT NULL
- available_time VARCHAR(50) NOT NULL

Foreign Key:
- doctor_id REFERENCES doctor(id)
