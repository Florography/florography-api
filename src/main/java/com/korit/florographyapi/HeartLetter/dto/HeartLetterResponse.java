package com.korit.florographyapi.HeartLetter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartLetterResponse {
    private String userId;
    private String title;
    private String recipient;
    private String body;
    private int paperTheme;
    private int fontSize;
    private LocalDate createdAt;
}
