package com.korit.florographyapi.User.mapper;

import com.korit.florographyapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertProviderUser(User user);
    void insertUser(User user);
    User selectByEmail(String providerId);
}
