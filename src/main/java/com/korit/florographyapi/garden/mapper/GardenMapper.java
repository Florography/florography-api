package com.korit.florographyapi.garden.mapper;

import com.korit.florographyapi.garden.dto.GardenCreateRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GardenMapper {


    void insertGardenData(GardenCreateRequest request);
}



