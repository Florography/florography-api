package com.korit.florographyapi.shareboard.dto;

import com.korit.florographyapi.entity.BoardLike;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardLikeCreateRequest {
    private Long boardId;
    private String userId;

    public BoardLike toBoardLike() {
        return BoardLike.builder()
                .boardId(boardId)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
