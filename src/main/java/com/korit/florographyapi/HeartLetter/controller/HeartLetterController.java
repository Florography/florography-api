package com.korit.florographyapi.HeartLetter.controller;

import com.korit.florographyapi.HeartLetter.dto.HeartLetterCreateRequest;
import com.korit.florographyapi.HeartLetter.dto.HeartLetterModifyRequest;
import com.korit.florographyapi.HeartLetter.dto.HeartLetterResponse;
import com.korit.florographyapi.HeartLetter.service.HeartLetterService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heartletter")
@RequiredArgsConstructor
public class HeartLetterController {
    private final HeartLetterService heartLetterService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody HeartLetterCreateRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(heartLetterService.create(dto)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getAllUserId(@PathVariable String userId) {
        return  ResponseEntity.ok(ApiResponse.success(heartLetterService.getAllUserId(userId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long id, @RequestBody HeartLetterModifyRequest dto) {
        dto.setId(id);
        heartLetterService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정 완료"));
    }
}
