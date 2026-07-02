package com.korit.florographyapi.user.mapper;

import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertProviderUser(ProviderUser user);
    void insertUser(User user);
    ProviderUser selectByProviderId(String providerId);
    User selectByUserId(String uid);
    User selectById(Long id);

    // 소셜 계정 연동 관련
    List<ProviderUser> selectProvidersByUid(String uid);
    ProviderUser selectByProviderAndUid(@Param("provider") String provider, @Param("uid") String uid);
    void deleteProviderByProviderAndUid(@Param("provider") String provider, @Param("uid") String uid);
    int countProvidersByUid(String uid);
}
