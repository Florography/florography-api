package com.korit.florographyapi.ShareBoard.dto;

import com.korit.florographyapi.entity.ShareBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShareBoardModifyRequest {
    private Long id;
    private String userId;
    private short typeId;
    private String body;
    private Long like;

    public ShareBoard toShareBoard() {
        return ShareBoard.builder()
                .id(id)
                .userId(userId)
                .body(body)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
