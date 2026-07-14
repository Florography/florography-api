package com.korit.florographyapi.shareboard.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShareBoardResponse {
    private Long id;                 //게시글 id
    private String userId;             // 유저 id
    private short typeId;            // 한마디, 정원 분리
    private String body;             // 내용
    private Long like;               // 좋아요 수
    private LocalDateTime createdAt;  // 생성 날짜
//    private String gardenImage;      // 정원 이미지
}
