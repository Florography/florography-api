package com.korit.florographyapi.garden.mapper;

import com.korit.florographyapi.entity.Garden;
import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GardenMapper {

    void insertGardenData(GardenCreateRequest request);

    List<Garden> selectAllGardens(String userId);

    Garden selectGardenById(Long id);
}



