package com.smartclinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELATIE CU DOCTOR (IMPORTANT pentru punctaj)
    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // RELATIE CU PATIENT (IMPORTANT pentru punctaj)
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // IMPORTANT pentru punctaj
    @NotNull
    @Future
    private LocalDateTime appointmentTime;

    private String status;

    public Appointment() {
    }

    public Appointment(Long id, Doctor doctor, Patient patient, LocalDateTime appointmentTime, String status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
