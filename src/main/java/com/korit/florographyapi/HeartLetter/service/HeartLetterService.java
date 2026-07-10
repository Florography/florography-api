package com.korit.florographyapi.HeartLetter.service;

import com.korit.florographyapi.HeartLetter.dto.HeartLetterCreateRequest;
import com.korit.florographyapi.HeartLetter.dto.HeartLetterModifyRequest;
import com.korit.florographyapi.HeartLetter.dto.HeartLetterResponse;
import com.korit.florographyapi.HeartLetter.mapper.HeartLetterMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.HeartLetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartLetterService {
    private final HeartLetterMapper heartLetterMapper;

    public CreateResponse create(HeartLetterCreateRequest dto) {
        HeartLetter heartLetter = dto.toHeartLetter();
        heartLetterMapper.insert(heartLetter);

        return CreateResponse.builder()
                .domainName("heartletter")
                .createdIds(List.of(heartLetter.getId()))
                .build();
    }

    public List<HeartLetterResponse> getAllUserId(String userId) {
        List<HeartLetterResponse> heartLetters = heartLetterMapper.selectAllUserId(userId)
                .stream()
                .map(HeartLetter::toResponse)
                .toList();
        System.out.println(userId);
        return heartLetters;
    }

    public void modify(HeartLetterModifyRequest dto) {
        System.out.println("수정 요청 ID: " + dto.getId() + ", USER ID: " + dto.getUserId());
        int result = heartLetterMapper.update(dto.toHeartLetter());
        System.out.println("수정된 행 개수: " + result);
        if (result == 0) {
            throw new RuntimeException("수정할 수 없습니다. (편지 ID가 존재하지 않거나, 작성자가 일치하지 않습니다)");
        }
    }
}
