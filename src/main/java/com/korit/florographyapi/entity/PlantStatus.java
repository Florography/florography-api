package com.korit.florographyapi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantStatus {
    private Long id;
    private String userId;
    private Long daysCount;
    private Integer stage;
    private Integer health;
}
