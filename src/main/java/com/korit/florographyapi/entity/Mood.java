package com.korit.florographyapi.entity;

import com.korit.florographyapi.mood.dto.MoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mood {
    private Long id;
    private String mood;

    public MoodResponse toResponse() {
        return MoodResponse.builder()
                .id(id)
                .mood(mood)
                .build();
    }
}
