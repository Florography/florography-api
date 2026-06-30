package com.korit.florographyapi.User.mapper;

import com.korit.florographyapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insert(User user);
    User selectByProvider(String providerId);
    User selectById(Long userId);
}
