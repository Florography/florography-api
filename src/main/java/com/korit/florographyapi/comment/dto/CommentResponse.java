package com.korit.florographyapi.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long userId;
    private String body;
    private LocalDateTime createdAt;
}
