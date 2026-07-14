package com.korit.florographyapi.garden.service;

import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.entity.Garden;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import com.korit.florographyapi.garden.mapper.GardenMapper;
import com.korit.florographyapi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final GardenMapper gardenMapper;
    private final UserMapper userMapper;

    public ApiResponse<?> createGarden(GardenCreateRequest dto) {
        System.out.println("createGarden: Service - " + dto);
        gardenMapper.insertGardenData(dto);
        return ApiResponse.success(null);
    }

    public ApiResponse<?> getAllGardens(Authentication authentication) {
        System.out.println("======================================");
        Long userId = (Long)authentication.getPrincipal();
        User user = userMapper.selectById(userId);
        List<Garden> list = gardenMapper.selectAllGardens(user.getUid());
        System.out.println(list);
        System.out.println("======================================");   
        return ApiResponse.success(list);
    }

    public ApiResponse<?> getGardenById(Long id) {
        return ApiResponse.success(gardenMapper.selectGardenById(id));
    }
}
