package com.korit.florographyapi.ShareBoard.service;

import com.korit.florographyapi.ShareBoard.dto.ShareBoardResponse;
import com.korit.florographyapi.ShareBoard.mapper.ShareBoardMapper;
import com.korit.florographyapi.entity.ShareBoard;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareBoardService {
    private final ShareBoardMapper shareBoardMapper;

    public List<ShareBoardResponse> getAll(Long id) {
        return shareBoardMapper.selectAll().stream().map(ShareBoard::toResponse).toList();
    }
//    userId 로 가져오는 리스트
//    public List<ShareBoardResponse> getSelectByUserId(Long userId) {
//        return shareBoardMapper.selectByUserId(userId).stream().map(ShareBoard::toResponse).toList();
//    }

    public List<ShareBoardResponse> getRank(Long userId) {
        return shareBoardMapper.selectRank(userId).stream().map(ShareBoard::toResponse).toList();
    }
}
