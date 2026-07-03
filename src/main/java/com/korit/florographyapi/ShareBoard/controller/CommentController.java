package com.korit.florographyapi.ShareBoard.controller;

import com.korit.florographyapi.ShareBoard.dto.CommentCreateRequest;
import com.korit.florographyapi.ShareBoard.dto.CommentResponse;
import com.korit.florographyapi.ShareBoard.service.CommentService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shareboard/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody CommentCreateRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(commentService.create(dto)));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getSelectByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.getSelectByBoardId(boardId)));
    }
}
