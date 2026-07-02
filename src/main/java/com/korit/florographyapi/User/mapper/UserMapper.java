package com.korit.florographyapi.User.mapper;

import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertProviderUser(ProviderUser user);
    void insertUser(User user);
    ProviderUser selectByProviderId(String providerId);
}
