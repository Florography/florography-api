package com.korit.florographyapi.entity;


import com.korit.florographyapi.comment.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long userId;
    private String body;
    private LocalDateTime createdAt;

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .userId(userId)
                .body(body)
                .createdAt(createdAt)
                .build();
    }
}
