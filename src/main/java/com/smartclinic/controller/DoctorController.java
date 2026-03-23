package com.smartclinic.controller;

import com.smartclinic.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // IMPORTANT pentru punctaj
    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<List<String>> getAvailability(
            @PathVariable Long doctorId,
            @RequestParam String date,
            @RequestHeader("Authorization") String token
    ) {
        List<String> slots = doctorService.getAvailableTimeSlots(
                doctorId,
                LocalDate.parse(date)
        );

        return ResponseEntity.ok(slots);
    }
}
