package com.korit.florographyapi.ShareBoard.mapper;

import com.korit.florographyapi.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insert (Comment comment);
    List<Comment> selectByBoardId(Long boardId);
    int update (Comment comment);
    int delete (Long boardId,Long userId, Long id);
}
