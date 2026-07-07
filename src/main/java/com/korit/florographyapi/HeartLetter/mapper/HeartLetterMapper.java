package com.korit.florographyapi.HeartLetter.mapper;

import com.korit.florographyapi.entity.HeartLetter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HeartLetterMapper {
    int insert(HeartLetter heartLetter);
    List<HeartLetter> selectAllUserId(String userId);
    int update(HeartLetter heartLetter);

}
