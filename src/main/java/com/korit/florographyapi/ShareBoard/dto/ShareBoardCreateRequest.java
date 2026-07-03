package com.korit.florographyapi.ShareBoard.dto;

import com.korit.florographyapi.entity.ShareBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShareBoardCreateRequest {
    private long userId;
    private short typeId;
    private String body;
    private Long like;

    public ShareBoard toShareBoard() {
        return ShareBoard.builder()
                .userId(userId)
                .typeId(typeId)
                .body(body)
                .like(like)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
