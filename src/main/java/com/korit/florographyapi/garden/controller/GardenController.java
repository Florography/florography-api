package com.korit.florographyapi.garden.controller;

import com.korit.florographyapi.garden.service.GardenService;
import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import com.korit.florographyapi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garden")
public class GardenController {
    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createGarden(@RequestBody GardenCreateRequest gardenCreateRequest) {
        System.out.println("createGarden: controller ==" + gardenCreateRequest);
        return ResponseEntity.ok(gardenService.createGarden(gardenCreateRequest));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllGardens(@RequestParam String userId) {
        return ResponseEntity.ok(gardenService.getAllGardens(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getGardenById(@PathVariable Long id) {
        return ResponseEntity.ok(gardenService.getGardenById(id));
    }
}
