package com.korit.florographyapi.flowerdictionary.mapper;

import com.korit.florographyapi.entity.FlowerDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlowerDictionaryMapper {
    // insert는 없을 예정
    List<FlowerDictionary> getAll(Long userId);
}
