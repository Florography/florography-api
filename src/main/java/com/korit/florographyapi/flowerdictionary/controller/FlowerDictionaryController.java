package com.korit.florographyapi.flowerdictionary.controller;

import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.flowerdictionary.service.FlowerDictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flowerdictionary")
public class FlowerDictionaryController {
    private final FlowerDictionaryService flowerDictionaryService;
    // 모든 도감을 불러옴
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll(Authentication authentication){
        System.out.println("getFlowerDirectories Start");
        return ResponseEntity.ok(ApiResponse.success(flowerDictionaryService.getAll(authentication)));
    }
}
