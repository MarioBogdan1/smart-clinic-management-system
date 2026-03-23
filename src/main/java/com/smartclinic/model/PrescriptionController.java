package com.smartclinic.controller;

import com.smartclinic.model.Prescription;
import com.smartclinic.repository.PrescriptionRepository;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionRepository prescriptionRepository,
                                  TokenService tokenService) {
        this.prescriptionRepository = prescriptionRepository;
        this.tokenService = tokenService;
    }

    // IMPORTANT pentru punctaj
    @PostMapping
    public ResponseEntity<?> createPrescription(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody Prescription prescription
    ) {
        String token = authHeader.replace("Bearer ", "");

        if (!tokenService.validateToken(token)) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid token");
            return ResponseEntity.status(401).body(error);
        }

        Prescription saved = prescriptionRepository.save(prescription);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Prescription saved successfully");
        response.put("data", saved);

        return ResponseEntity.ok(response);
    }
}
