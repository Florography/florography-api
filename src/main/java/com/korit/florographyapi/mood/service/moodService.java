package com.korit.florographyapi.mood.service;

import com.korit.florographyapi.entity.Mood;
import com.korit.florographyapi.mood.dto.MoodResponse;
import com.korit.florographyapi.mood.mapper.MoodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class moodService {
    private final MoodMapper moodMapper;

    public List<MoodResponse> getAll() {
        return moodMapper.selectAll()
                .stream()
                .map(Mood::toResponse)
                .toList();
    }
}
