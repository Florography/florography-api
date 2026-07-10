package com.korit.florographyapi.ShareBoard.service;

import com.korit.florographyapi.ShareBoard.dto.CommentCreateRequest;
import com.korit.florographyapi.ShareBoard.dto.CommentModifyRequest;
import com.korit.florographyapi.ShareBoard.dto.CommentResponse;
import com.korit.florographyapi.ShareBoard.mapper.CommentMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    @Transactional
    public CreateResponse create(CommentCreateRequest dto) {
        Comment comment = dto.toComment();
        commentMapper.insert(comment);
        return CreateResponse.builder()
                .domainName("comment")
                .createdIds(List.of(comment.getId()))
                .build();
    }

    public List<CommentResponse> getSelectByBoardId(Long boardId) {
        return commentMapper.selectByBoardId(boardId).stream().map(Comment::toResponse).toList();
    }

    public void modify(CommentModifyRequest dto) {
        commentMapper.update(dto.toComment());
    }

    public void delete(Long boardId, String userId, Long id ) {commentMapper.delete(boardId,userId, id);}
}
