package com.korit.florographyapi.shareboard.mapper;

import com.korit.florographyapi.entity.BoardLike;
import com.korit.florographyapi.entity.ShareBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShareBoardMapper {
    int insert(ShareBoard shareBoard);
    List<ShareBoard> selectByUserId(String userId);
    List<ShareBoard> selectAll();
    List<ShareBoard> selectRank(String userId);
    int update(ShareBoard shareBoard);
    int delete(Long id, String userId);

    // ShareBoard 테이블의 like 컬럼 증감
//    void increaseLikeCount(Long boardId);
//    void decreaseLikeCount(Long boardId);

    int insertBoardLike (BoardLike boardLike);
    int deleteBoardLike (Long boardId, String userId);
    BoardLike selectBoardLike (Long boardId, String userId);
    int selectLikeCountByBoardId(Long boardId);

}
