package com.korit.florographyapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowerDictionary {
    private Long id;
    private String flowerImg;
    private String flowerName;
    private String flowerMeaning;
    private String kindWords;

    private UserFlower userFlower;      // 이게 null일 경우 오픈하지 않음.
}
