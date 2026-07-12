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
    private String name;
    private int themeIdx;
    private String slots;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String gardenImage;     // null 일수도 있고
}
