package com.smartclinic.controller;

import com.smartclinic.model.Doctor;
import com.smartclinic.repository.DoctorRepository;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService,
                            DoctorRepository doctorRepository,
                            TokenService tokenService) {
        this.doctorService = doctorService;
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    // IMPORTANT pentru punctaj
    @GetMapping("/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long doctorId,
            @RequestParam String date
    ) {
        String token = authHeader.replace("Bearer ", "");

        if (!tokenService.validateToken(token)) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid token");
            return ResponseEntity.status(401).body(error);
        }

        List<String> availableTimes = doctorService.getAvailableTimeSlots(
                doctorId,
                LocalDate.parse(date)
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("doctorId", doctorId);
        response.put("date", date);
        response.put("availableTimes", availableTimes);

        return ResponseEntity.ok(response);
    }

    // pentru curl GET all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    // pentru cautare doctori (speciality + time)
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam String speciality,
            @RequestParam String time
    ) {
        return ResponseEntity.ok(
                doctorRepository.findBySpecialityIgnoreCase(speciality)
        );
    }
}
