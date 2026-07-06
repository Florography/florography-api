package com.korit.florographyapi.ShareBoard.dto;

import com.korit.florographyapi.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentModifyRequest {
        private Long id;
        private Long boardId;
        private Long userId;
        private String body;


        public Comment toComment() {
            return Comment.builder()
                    .id(id)
                    .boardId(boardId)
                    .userId(userId)
                    .body(body)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
}
