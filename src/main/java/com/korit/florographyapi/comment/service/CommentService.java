package com.korit.florographyapi.comment.service;

import com.korit.florographyapi.comment.dto.CommentCreateRequest;
import com.korit.florographyapi.comment.dto.CommentModifyRequest;
import com.korit.florographyapi.comment.dto.CommentResponse;
import com.korit.florographyapi.comment.mapper.CommentMapper;
import com.korit.florographyapi.dto.CreateResponse;
import com.korit.florographyapi.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    
    public CreateResponse create(CommentCreateRequest dto) {
        Comment comment = dto.toComment();
        commentMapper.insert(comment);
        
        return CreateResponse.builder()
                .domainName("comment")
                .createdIds(List.of(comment.getId()))
                .build();
    }

    public List<CommentResponse> getAll(Long userId) {
        return commentMapper.selectAllComment(userId)
                .stream()
                .map(Comment::toResponse)
                .toList();
    }

    public void modify(CommentModifyRequest dto) {
        System.out.println(dto);
        commentMapper.update(dto.toComment());
    }
}
