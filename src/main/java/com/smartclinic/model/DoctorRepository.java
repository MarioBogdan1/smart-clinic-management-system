package com.smartclinic.repository;

import com.smartclinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByEmailAndPassword(String email, String password);

    List<Doctor> findBySpecialityIgnoreCase(String speciality);

    List<Doctor> findByNameContainingIgnoreCase(String name);
}
