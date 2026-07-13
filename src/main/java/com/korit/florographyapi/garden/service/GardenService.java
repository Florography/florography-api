package com.korit.florographyapi.garden.service;

import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.entity.Garden;
import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import com.korit.florographyapi.garden.mapper.GardenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final GardenMapper gardenMapper;

    public ApiResponse<?> createGarden(GardenCreateRequest dto) {
        System.out.println("createGarden: Service - " + dto);

        // 저장 구현
        gardenMapper.insertGardenData(dto);
//        System.out.println("Hello");
        return ApiResponse.success(gardenMapper);
    }

//    public ApiResponse<?> createGarden

}
