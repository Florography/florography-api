package com.korit.florographyapi.ShareBoard.controller;

import com.korit.florographyapi.ShareBoard.dto.CommentCreateRequest;
import com.korit.florographyapi.ShareBoard.dto.CommentModifyRequest;
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

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long userId, @RequestBody CommentModifyRequest dto) {
        commentService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정완료"));
    }

    @DeleteMapping("/{boardId}/{userId}/{id}")
    public ResponseEntity<ApiResponse<?>> delelte(@PathVariable Long boardId,@PathVariable Long userId  ,@PathVariable Long id ) {
        commentService.delete(boardId,userId,id);
        return ResponseEntity.ok(ApiResponse.success("삭제 완료"));
    }
}
