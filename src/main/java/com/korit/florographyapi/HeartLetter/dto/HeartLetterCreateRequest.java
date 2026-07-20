package com.korit.florographyapi.HeartLetter.dto;

import com.korit.florographyapi.entity.HeartLetter;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HeartLetterCreateRequest {
    private String userId;
    private String title;
    private String recipient;
    private String body;
    private int paperTheme;
    private int fontSize;
    private LocalDate createdAt;

    public HeartLetter toHeartLetter() {
        return HeartLetter.builder()
                .userId(userId)
                .title(title)
                .recipient(recipient)
                .body(body)
                .paperTheme(paperTheme)
                .fontSize(fontSize)
                .createdAt(LocalDate.now())
                .build();

    }
}
