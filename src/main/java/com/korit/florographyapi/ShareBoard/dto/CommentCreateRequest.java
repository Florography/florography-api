package com.korit.florographyapi.ShareBoard.dto;

import com.korit.florographyapi.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentCreateRequest {
    private Long boardId;
    private Long userId;
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
