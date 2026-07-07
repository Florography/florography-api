package com.korit.florographyapi.seedrecord.controller;

import com.korit.florographyapi.entity.Seedrecord;
import com.korit.florographyapi.seedrecord.dto.SeedrecordModifyRequest;
import com.korit.florographyapi.seedrecord.service.SeedrecordService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.seedrecord.dto.SeedrecordCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seedrecord")
@RequiredArgsConstructor
public class SeedrecordController {
    private final SeedrecordService seedrecordService;


    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody SeedrecordCreateRequest dto) {

        return ResponseEntity.ok(ApiResponse.success(seedrecordService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll(@AuthenticationPrincipal Long userId) {
        System.out.println("seedRecord: getAll Start");
        return ResponseEntity.ok(ApiResponse.success(seedrecordService.getAll(userId)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> modify(@RequestBody SeedrecordModifyRequest dto) {
        seedrecordService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정 완료"));
    }
}
