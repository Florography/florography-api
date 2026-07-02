package com.korit.florographyapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String uid;
    private String nickname;
    private String email;
    private String profileImage;
    private LocalDateTime createdAt;
    private Long daysConnected;

}
