package com.korit.florographyapi.ShareBoard.mapper;

import com.korit.florographyapi.entity.ShareBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShareBoardMapper {
    int insert(ShareBoard shareBoard);
    List<ShareBoard> selectByUserId(Long userId);
    List<ShareBoard> selectAll();
    List<ShareBoard> selectRank(Long userId);


}
