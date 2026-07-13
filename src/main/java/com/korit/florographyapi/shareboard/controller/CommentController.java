package com.korit.florographyapi.shareboard.controller;

import com.korit.florographyapi.shareboard.dto.CommentCreateRequest;
import com.korit.florographyapi.shareboard.dto.CommentModifyRequest;
import com.korit.florographyapi.shareboard.dto.CommentResponse;
import com.korit.florographyapi.shareboard.service.CommentService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shareboard/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody CommentCreateRequest dto, @AuthenticationPrincipal Object principal) {

        System.out.println("토큰에서 자동으로 매핑된 진짜 로그인 유저 ID: " + dto.getUserId());
        return ResponseEntity.ok(ApiResponse.success(commentService.create(dto)));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getSelectByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.getSelectByBoardId(boardId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long userId, @RequestBody CommentModifyRequest dto) {
        commentService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정완료"));
    }

    @DeleteMapping("/{boardId}/{userId}/{id}")
    public ResponseEntity<ApiResponse<?>> delelte(@PathVariable Long boardId,@PathVariable String userId  ,@PathVariable Long id ) {
        commentService.delete(boardId,userId,id);
        return ResponseEntity.ok(ApiResponse.success("삭제 완료"));
    }
}
