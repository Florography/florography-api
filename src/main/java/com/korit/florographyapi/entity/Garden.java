package com.korit.florographyapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Garden {
    private Long id;
    private String userId;
    private String name;
    private String gardenData;
    private String gardenImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
