package com.korit.florographyapi.seedrecord.service;

import com.korit.florographyapi.seedrecord.dto.SeedrecordCreateRequest;
import com.korit.florographyapi.seedrecord.dto.SeedrecordModifyRequest;
import com.korit.florographyapi.seedrecord.dto.SeedrecordResponse;
import com.korit.florographyapi.seedrecord.mapper.SeedrecordMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.Seedrecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedrecordService {
    private final SeedrecordMapper seedrecordMapper;
    
    public CreateResponse create(SeedrecordCreateRequest dto) {
        Seedrecord seedrecord = Seedrecord.builder()
                .userId(dto.getUserId())
                .sentence(dto.getSentence())
                .moodIdx(dto.getMoodIdx())
                .aiComment("가나다라")
                .createdDate(LocalDate.now())
                .build();
        System.out.println(dto);
        seedrecordMapper.insert(seedrecord);
        
        return CreateResponse.builder()
                .domainName("comment")
                .createdIds(null)
                .build();
    }

    public List<SeedrecordResponse> getAll(Long userId) {
        List<SeedrecordResponse> comments = seedrecordMapper.selectAllSeedrecord(userId)
                .stream()
                .map(Seedrecord::toResponse)
                .toList();
        System.out.println(comments);
        return comments;
    }

    public void modify(SeedrecordModifyRequest dto) {
        System.out.println(dto);
        seedrecordMapper.update(dto.toSeedrecord());
    }

    public List<SeedrecordResponse> getByUserId(String userId) {
        List<SeedrecordResponse> seedrecordResponseList = seedrecordMapper.selectByUserId(userId)
                .stream()
                .map(Seedrecord::toResponse)
                .toList();
        System.out.println(seedrecordResponseList);
        return seedrecordResponseList;
    }
}
