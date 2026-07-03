package com.korit.florographyapi.seedrecord.dto;

import com.korit.florographyapi.entity.Seedrecord;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SeedrecordCreateRequest {
    private String userId;
    private String sentence;
    private int moodIdx;
    private String aiComment;
    private LocalDate createdDate;

    public Seedrecord toSeedrecord() {
        return Seedrecord.builder()
                .userId(userId)
                .sentence(sentence)
                .moodIdx(moodIdx)
                .aiComment(aiComment)
                .createdDate(LocalDate.now())
                .build();
    }
}
