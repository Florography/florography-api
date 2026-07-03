package com.korit.florographyapi.seedrecord.mapper;

import com.korit.florographyapi.entity.Seedrecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeedrecordMapper {
    int insert(Seedrecord comment);
    List<Seedrecord> selectAllSeedrecord(Long userId);
    int update(Seedrecord comment);
}
