package com.korit.florographyapi.comment.mapper;

import com.korit.florographyapi.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insert(Comment comment);
    List<Comment> selectAllComment(Long userId);
    int update(Comment comment);
}
