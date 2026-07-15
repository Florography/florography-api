package com.korit.florographyapi.garden.controller;

import com.korit.florographyapi.garden.dto.GardenSaveRequest;
import com.korit.florographyapi.garden.service.GardenService;
import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import com.korit.florographyapi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garden")
public class GardenController {
    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createGarden(@RequestBody GardenCreateRequest dto) {
        System.out.println("createGarden: controller ==" + dto);
        return ResponseEntity.ok(gardenService.createGarden(dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllGardens(Authentication authentication) {
        System.out.println("getAllGarden: authentication == " + authentication );
        return ResponseEntity.ok(gardenService.getAllGardens(authentication));
    }

    // 저장 가능
    @PutMapping
    public ResponseEntity<ApiResponse<?>> saveGarden(@RequestBody GardenSaveRequest dto){
        System.out.println("saveGarden: controller == " + dto);

        return ResponseEntity.ok(gardenService.saveGarden(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getGardenById(@PathVariable Long id) {
        System.out.println("getAllGardenId");
        return ResponseEntity.ok(gardenService.getGardenById(id));
    }
}
