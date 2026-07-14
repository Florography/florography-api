package com.korit.florographyapi.shareboard.controller;

import com.korit.florographyapi.entity.BoardLike;
import com.korit.florographyapi.shareboard.dto.BoardLikeCreateRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardCreateRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardModifyRequest;
import com.korit.florographyapi.shareboard.dto.ShareBoardResponse;
import com.korit.florographyapi.shareboard.service.ShareBoardService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shareboard")
@RequiredArgsConstructor
public class ShareBoardController {
    private final ShareBoardService shareBoardService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody ShareBoardCreateRequest dto) {

        System.out.println("프론트에서 넘어온 user_id: " + dto.getUserId());
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getAll(userId)));
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getSelectByUserId( @PathVariable Long userId) {
//        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getSelectByUserId(userId)));
//    }

    @GetMapping("/rank")
    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getRank(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getRank(userId)));
    }

    //게시글 수정
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long userId, @RequestBody ShareBoardModifyRequest dto) {
        shareBoardService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정완료"));
    }
    //게시글 삭제
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id, @PathVariable String userId) {
        shareBoardService.delete(id, userId);
        return ResponseEntity.ok(ApiResponse.success("삭제 완료"));
    }

//    //좋아요 증가
//    @PutMapping("/{boardId}/up")
//    public ResponseEntity<ApiResponse<?>> modifyLikeUp(@PathVariable Long boardId, @RequestBody ShareBoardModifyRequest dto) {
//        shareBoardService.boardLikeCreate(dto);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 증가"));
//    }
//
//    //좋아요 감소
//    @PutMapping("/{boardId}/down")
//    public ResponseEntity<ApiResponse<?>> modifyLikeDown(@PathVariable Long boardId, @RequestBody ShareBoardModifyRequest dto) {
//        shareBoardService.deleteBoardLike(dto);
//        return ResponseEntity.ok(ApiResponse.success("좋아요 감소"));
//    }

    // 좋아요 저장
    @PostMapping("/boardlike")
    public ResponseEntity<ApiResponse<CreateResponse>> createBoardLike (@RequestBody BoardLikeCreateRequest dto) {
        System.out.println("넘어온 DTO 값 확인" + dto.toString());
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.boardLikeCreate(dto)));
    }

    //좋아요 삭제
    @DeleteMapping("/boardlike/{boardId}/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteBoardLike (@PathVariable Long boardId, @PathVariable String userId) {
        shareBoardService.deleteBoardLike(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success("좋아요 삭제완료"));
    }

    //좋아요 여부 호출
    @GetMapping("/boardlike/{boardId}")
    public ResponseEntity<ApiResponse<BoardLike>> getBoardLike (@PathVariable Long boardId,@RequestParam String userId) {
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getBoardLike(boardId,userId)));
    }


}
