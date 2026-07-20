package com.korit.florographyapi.entity;

import com.korit.florographyapi.HeartLetter.dto.HeartLetterResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartLetter {
    private Long id;
    private String userId;
    private String title;
    private String recipient;
    private String body;
    private int paperTheme;
    private int fontSize;
    private LocalDate createdAt;

    public HeartLetterResponse toResponse() {
        return HeartLetterResponse.builder()
                .userId(userId)
                .title(title)
                .recipient(recipient)
                .body(body)
                .paperTheme(paperTheme)
                .fontSize(fontSize)
                .createdAt(createdAt)
                .build();
    }
}
