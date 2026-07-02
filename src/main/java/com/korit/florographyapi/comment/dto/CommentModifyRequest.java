package com.korit.florographyapi.comment.dto;

import com.korit.florographyapi.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentModifyRequest {
    private Long userId;
    private String body;
    private LocalDateTime createdAt;

    public Comment toComment() {
        return Comment.builder()
                .userId(userId)
                .body(body)
                .createdAt(createdAt)
                .build();
    }
}
