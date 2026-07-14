package com.korit.florographyapi.shareboard.dto;

import com.korit.florographyapi.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentCreateRequest {
    private Long boardId;
    private String userId;
    private String body;


    public Comment toComment() {
        return Comment.builder()
                .boardId(boardId)
                .userId(userId)
                .body(body)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
