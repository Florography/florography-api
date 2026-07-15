package com.korit.florographyapi.garden.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GardenSaveRequest {
    private Long id;
    private String userId;
    private String gardenName;
    private String gardenData;
    private String gardenImage;
    private LocalDateTime createdAt;

}
