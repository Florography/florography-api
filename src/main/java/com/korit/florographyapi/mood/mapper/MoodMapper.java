package com.korit.florographyapi.mood.mapper;

import com.korit.florographyapi.entity.Mood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoodMapper {
    int insert (Mood mood);
    List<Mood> selectAll();
}
