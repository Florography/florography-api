package com.korit.florographyapi.plantStatus.mapper;

import com.korit.florographyapi.entity.PlantStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlantStatusMapper {

    PlantStatus[] SelectByUserId(String userId);

    void insertPlants(String userId);

}
