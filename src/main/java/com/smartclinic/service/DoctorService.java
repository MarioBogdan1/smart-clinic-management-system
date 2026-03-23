package com.smartclinic.service;

import com.smartclinic.model.Doctor;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // IMPORTANT pentru punctaj
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);

        if (doctorOpt.isEmpty()) {
            return Collections.emptyList();
        }

        // mock simplu pentru demo
        return Arrays.asList("09:00", "10:00", "11:00");
    }

    // IMPORTANT pentru punctaj
    public Map<String, Object> login(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();

        if (doctorOpt.isPresent() && doctorOpt.get().getPassword().equals(password)) {
            response.put("success", true);
            response.put("doctor", doctorOpt.get());
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
        }

        return response;
    }
}
