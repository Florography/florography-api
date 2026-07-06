package com.korit.florographyapi.mood.controller;

import com.korit.florographyapi.ShareBoard.service.CommentService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.mood.service.moodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/mood")
@RequiredArgsConstructor
public class moodController {
    private final moodService moodService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(moodService.getAll()));
    }
}
