package com.korit.florographyapi.plantStatus.service;

import com.korit.florographyapi.plantStatus.mapper.PlantStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantStatusService {

    private final PlantStatusMapper plantStatusMapper;


}
