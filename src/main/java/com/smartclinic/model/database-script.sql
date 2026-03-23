CREATE DATABASE smart_clinic;
USE smart_clinic;

CREATE TABLE doctor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    speciality VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE patient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE prescription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medication VARCHAR(255) NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    notes TEXT NOT NULL,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    CONSTRAINT fk_prescription_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_prescription_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE doctor_available_times (
    doctor_id BIGINT NOT NULL,
    available_time VARCHAR(50) NOT NULL,
    CONSTRAINT fk_available_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

INSERT INTO doctor (name, speciality, email, phone_number, password) VALUES
('Dr. John Smith', 'Cardiology', 'john@clinic.com', '0711111111', 'pass123'),
('Dr. Alice Brown', 'Dermatology', 'alice@clinic.com', '0722222222', 'pass123');

INSERT INTO patient (name, email, phone_number, password) VALUES
('Patient One', 'p1@mail.com', '0700000001', 'pass123'),
('Patient Two', 'p2@mail.com', '0700000002', 'pass123'),
('Patient Three', 'p3@mail.com', '0700000003', 'pass123'),
('Patient Four', 'p4@mail.com', '0700000004', 'pass123'),
('Patient Five', 'p5@mail.com', '0700000005', 'pass123'),
('Patient Six', 'p6@mail.com', '0700000006', 'pass123');

INSERT INTO appointment (doctor_id, patient_id, appointment_time, status) VALUES
(1, 1, '2026-03-23 09:00:00', 'BOOKED'),
(1, 2, '2026-03-23 10:00:00', 'BOOKED'),
(1, 3, '2026-03-23 11:00:00', 'BOOKED'),
(2, 4, '2026-03-24 09:00:00', 'BOOKED'),
(2, 5, '2026-03-24 10:00:00', 'BOOKED');

INSERT INTO doctor_available_times (doctor_id, available_time) VALUES
(1, '09:00'),
(1, '10:00'),
(1, '11:00'),
(2, '09:00'),
(2, '10:00');

DELIMITER $$

CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN p_doctor_id BIGINT, IN p_date DATE)
BEGIN
    SELECT d.name AS doctor_name,
           a.appointment_time,
           p.name AS patient_name,
           a.status
    FROM appointment a
    JOIN doctor d ON a.doctor_id = d.id
    JOIN patient p ON a.patient_id = p.id
    WHERE a.doctor_id = p_doctor_id
      AND DATE(a.appointment_time) = p_date
    ORDER BY a.appointment_time;
END $$

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN p_month INT, IN p_year INT)
BEGIN
    SELECT d.id, d.name, COUNT(a.patient_id) AS total_patients
    FROM appointment a
    JOIN doctor d ON a.doctor_id = d.id
    WHERE MONTH(a.appointment_time) = p_month
      AND YEAR(a.appointment_time) = p_year
    GROUP BY d.id, d.name
    ORDER BY total_patients DESC
    LIMIT 1;
END $$

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN p_year INT)
BEGIN
    SELECT d.id, d.name, COUNT(a.patient_id) AS total_patients
    FROM appointment a
    JOIN doctor d ON a.doctor_id = d.id
    WHERE YEAR(a.appointment_time) = p_year
    GROUP BY d.id, d.name
    ORDER BY total_patients DESC
    LIMIT 1;
END $$

DELIMITER ;
