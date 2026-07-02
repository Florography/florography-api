package com.korit.florographyapi.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserReqCreate {
    private String nickname;
    private String email;
    private String profileImage;
    private LocalDate createdAt;
    private Long daysConnected;
}
