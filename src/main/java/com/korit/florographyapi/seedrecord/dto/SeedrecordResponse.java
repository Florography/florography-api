package com.korit.florographyapi.seedrecord.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SeedrecordResponse {
    private String userId;
    private String sentence;
    private int moodIdx;
    private String aiComment;
    private LocalDate createdDate;
}
