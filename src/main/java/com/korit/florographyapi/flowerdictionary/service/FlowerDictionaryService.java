package com.korit.florographyapi.flowerdictionary.service;

import com.korit.florographyapi.entity.FlowerDictionary;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.flowerdictionary.mapper.FlowerDictionaryMapper;
import com.korit.florographyapi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerDictionaryService {
    private final FlowerDictionaryMapper flowerDictionaryMapper;
    private final UserMapper userMapper;

    public List<FlowerDictionary> getAll(Authentication authentication) {
        // authentication 에는 현재 DB의 PK인 id(Long) 값이 저장되어 있습니다.
        Long id = (Long) authentication.getPrincipal();
        
        // UserMapper를 통해 해당 유저 정보를 조회하여 String 형태의 userID(uid)를 가져옵니다.
        User user = userMapper.selectById(id);
        String userId = user.getUid();

        System.out.println("문자열 userID (uid): " + userId);
        System.out.println("데이터베이스 PK id: " + id);
        
        // 주의: UserFlower 테이블의 user_id는 Long 타입(PK)으로 되어있으므로 mapper에는 id를 넘깁니다.
        System.out.println(flowerDictionaryMapper.getFlower(userId));
        List<FlowerDictionary> flowers = flowerDictionaryMapper.getAll(userId);
        System.out.println(flowers);
        
        return flowers;
    }
}
