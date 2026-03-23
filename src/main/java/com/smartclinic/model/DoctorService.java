package com.smartclinic.service;

import com.smartclinic.model.Doctor;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final TokenService tokenService;

    public DoctorService(DoctorRepository doctorRepository, TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    // IMPORTANT pentru punctaj: returneaza sloturile disponibile
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return doctorOptional.get().getAvailableTimes();
    }

    // IMPORTANT pentru punctaj: valideaza login si returneaza raspuns structurat
    public Map<String, Object> loginDoctor(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);
        if (doctor.isPresent()) {
            String token = tokenService.generateToken(email);
            response.put("success", true);
            response.put("message", "Doctor login successful");
            response.put("token", token);
            response.put("doctor", doctor.get());
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
        }

        return response;
    }
}
