package com.korit.florographyapi.comment.controller;

import com.korit.florographyapi.comment.dto.CommentModifyRequest;
import com.korit.florographyapi.comment.service.CommentService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.comment.dto.CommentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody CommentCreateRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(commentService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.getAll(userId)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> modify(@RequestBody CommentModifyRequest dto) {
        commentService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정 완료"));
    }
}
