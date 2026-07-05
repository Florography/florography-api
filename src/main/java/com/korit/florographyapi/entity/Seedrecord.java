package com.korit.florographyapi.entity;


import com.korit.florographyapi.seedrecord.dto.SeedrecordResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seedrecord {
    private Long id;
    private String userId;
    private String sentence;
    private int moodIdx;
    private String aiComment;
    private LocalDate createdDate;

    public Mood mood;

    public SeedrecordResponse toResponse() {
        return SeedrecordResponse.builder()
                .userId(userId)
                .sentence(sentence)
                .moodIdx(moodIdx)
                .aiComment(aiComment)
                .createdDate(createdDate)
                .mood(mood)
                .build();
    }
}
