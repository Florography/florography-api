package com.korit.florographyapi.flowerdictionary.service;

import com.korit.florographyapi.entity.FlowerDictionary;
import com.korit.florographyapi.flowerdictionary.mapper.FlowerDictionaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerDictionaryService {
    private final FlowerDictionaryMapper flowerDictionaryMapper;

    public List<FlowerDictionary> getAll(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<FlowerDictionary> flowers = flowerDictionaryMapper.getAll(userId);
        System.out.println(flowers);
        return flowers;
    }
}
