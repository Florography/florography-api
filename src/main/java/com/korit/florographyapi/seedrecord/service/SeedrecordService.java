package com.korit.florographyapi.seedrecord.service;

import com.korit.florographyapi.seedrecord.dto.SeedrecordCreateRequest;
import com.korit.florographyapi.seedrecord.dto.SeedrecordModifyRequest;
import com.korit.florographyapi.seedrecord.dto.SeedrecordResponse;
import com.korit.florographyapi.seedrecord.mapper.SeedrecordMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.Seedrecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedrecordService {
    private final SeedrecordMapper commentMapper;
    
    public CreateResponse create(SeedrecordCreateRequest dto) {
        Seedrecord seedrecord = dto.toSeedrecord();
        System.out.println(seedrecord);
        commentMapper.insert(seedrecord);
        
        return CreateResponse.builder()
                .domainName("comment")
                .createdIds(List.of(seedrecord.getId()))
                .build();
    }

    public List<SeedrecordResponse> getAll(Long userId) {
        List<SeedrecordResponse> comments = commentMapper.selectAllSeedrecord(userId)
                .stream()
                .map(Seedrecord::toResponse)
                .toList();
        System.out.println(comments);
        return comments;
    }

    public void modify(SeedrecordModifyRequest dto) {
        System.out.println(dto);
        commentMapper.update(dto.toSeedrecord());
    }
}
