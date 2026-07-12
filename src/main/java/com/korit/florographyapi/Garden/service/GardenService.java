package com.korit.florographyapi.Garden.service;

import com.korit.florographyapi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GardenService {

    public ApiResponse<?> saveGarden() {
        System.out.println("saveGarden: Service");

        return ApiResponse.success();
    }

}
