package com.korit.florographyapi.mood.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoodResponse {
    private Long id;
    private String mood;
}
