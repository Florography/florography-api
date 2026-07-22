package com.korit.florographyapi.shareboard.service;

import com.korit.florographyapi.shareboard.dto.BoardLikeCreateRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardCreateRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardModifyRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardResponse;
import com.korit.florographyapi.shareboard.mapper.ShareBoardMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.BoardLike;
import com.korit.florographyapi.entity.ShareBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<ShareBoardResponse> getRank(String userId) {
        return shareBoardMapper.selectRank(userId).stream().map(ShareBoard::toResponse).toList();
    }

    public void modify(ShareBoardModifyRequest dto) {
        shareBoardMapper.update(dto.toShareBoard());
    }

    public void delete(Long id, String userId) {shareBoardMapper.delete(id,userId);}


    //좋아요클릭시  board_like 테이블에 추가 및 share_board like 1증가
    public CreateResponse boardLikeCreate (BoardLikeCreateRequest dto) {
        BoardLike existingLike = shareBoardMapper.selectBoardLike(dto.getBoardId(), dto.getUserId());

        if (existingLike != null) {
            return CreateResponse.builder()
                    .domainName("boardlike")
                    .createdIds(List.of(existingLike.getId()))
                    .build();
        }

        BoardLike boardLike = BoardLike.builder()
                .boardId(dto.getBoardId())
                .userId(dto.getUserId())
                .createdAt(LocalDateTime.now())
                .build();

        shareBoardMapper.insertBoardLike(boardLike);

        return CreateResponse.builder()
                .domainName("boardLike")
                .createdIds(List.of(dto.getBoardId()))
                .build();
    }
    //좋아요 취소시 삭제 및 share_board like 1감소
    public void deleteBoardLike(Long boardId, String userId) {
        shareBoardMapper.deleteBoardLike(boardId, userId);
    }

    // 좋아요 상태확인용 get
    public BoardLike getBoardLike (Long boardId, String userId) {
        return shareBoardMapper.selectBoardLike(boardId, userId);
    }

    // board_like board_id 별 좋아요 카운트
    public int getLikeCount(Long boardId) {
        return shareBoardMapper.selectLikeCountByBoardId(boardId);
    }




}
