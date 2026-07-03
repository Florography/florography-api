package com.korit.florographyapi.entity;


import com.korit.florographyapi.ShareBoard.dto.ShareBoardResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareBoard {
    private Long id;                 //게시글 id
    private Long userId;             // 유저 id
    private short typeId;            // 한마디, 정원 분리
    private String body;             // 내용
    private Long like;               // 좋아요 수
    private LocalDateTime createdAt;  // 생성 날짜

    public ShareBoardResponse toResponse() {
        return ShareBoardResponse.builder()
                .userId(userId)
                .typeId(typeId)
                .body(body)
                .like(like)
                .createdAt(createdAt)
                .build();
    }
}
