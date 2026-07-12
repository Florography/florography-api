package com.korit.florographyapi.Garden.controller;

import com.korit.florographyapi.Garden.service.GardenService;
import com.korit.florographyapi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garden")
public class GardenController {
    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> saveGarden(@RequestBody String params) {
        System.out.println("SaveGarden: controller ==" + params);
        return ResponseEntity.ok(gardenService.saveGarden());
    }
}
