package com.korit.florographyapi.ShareBoard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long boardId;               // 게시글 id
    private Long userId;                // 유저 id
    private String body;                // 댓글 내용
    private LocalDateTime createdAt;    // 댓글 시간
}
