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
    private boolean isDelete; //"true"면 취소(-1), "false"면 좋아요(+1)로 쓸 변수 추가

    public ShareBoard toShareBoard() {
        return ShareBoard.builder()
                .id(id)
                .userId(userId)
                .body(body)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ShareBoard toShareBoardLike() {
        return ShareBoard.builder()
                .id(id)
                .like(like)
                .build();
    }
}
