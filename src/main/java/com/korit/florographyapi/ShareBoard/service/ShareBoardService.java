package com.korit.florographyapi.ShareBoard.service;

import com.korit.florographyapi.ShareBoard.dto.ShareBoardCreateRequest;
import com.korit.florographyapi.ShareBoard.dto.ShareBoardModifyRequest;
import com.korit.florographyapi.ShareBoard.dto.ShareBoardResponse;
import com.korit.florographyapi.ShareBoard.mapper.ShareBoardMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.ShareBoard;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareBoardService {
    private final ShareBoardMapper shareBoardMapper;

    //게시글 입력
    public CreateResponse create(ShareBoardCreateRequest dto) {
        ShareBoard shareBoard = dto.toShareBoard();
        shareBoardMapper.insert(shareBoard);
        return CreateResponse.builder()
                .domainName("shareBoard")
                .createdIds(List.of(shareBoard.getId()))
                .build();
    }
    //게시글 전체 출력
    public List<ShareBoardResponse> getAll(Long id) {
        return shareBoardMapper.selectAll().stream().map(ShareBoard::toResponse).toList();
    }
//    userId 로 가져오는 리스트
//    public List<ShareBoardResponse> getSelectByUserId(Long userId) {
//        return shareBoardMapper.selectByUserId(userId).stream().map(ShareBoard::toResponse).toList();
//    }
    // 인기순위
    public List<ShareBoardResponse> getRank(Long userId) {
        return shareBoardMapper.selectRank(userId).stream().map(ShareBoard::toResponse).toList();
    }

    public void modify(ShareBoardModifyRequest dto) {
        shareBoardMapper.update(dto.toShareBoard());
    }

    public void delete(Long id, Long userId) {shareBoardMapper.delete(id,userId);}
}
