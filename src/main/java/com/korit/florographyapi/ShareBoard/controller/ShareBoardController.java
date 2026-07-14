package com.korit.florographyapi.ShareBoard.controller;

import com.korit.florographyapi.ShareBoard.dto.ShareBoardCreateRequest;
import com.korit.florographyapi.ShareBoard.dto.ShareBoardModifyRequest;
import com.korit.florographyapi.ShareBoard.dto.ShareBoardResponse;
import com.korit.florographyapi.ShareBoard.service.ShareBoardService;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.CreateResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> modify(@PathVariable Long userId, @RequestBody ShareBoardModifyRequest dto) {
        shareBoardService.modify(dto);
        return ResponseEntity.ok(ApiResponse.success("수정완료"));
    }
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id, @PathVariable String userId) {
        shareBoardService.delete(id, userId);
        return ResponseEntity.ok(ApiResponse.success("삭제 완료"));
    }

    @PutMapping("/{id}/up")
    public ResponseEntity<ApiResponse<?>> modifyLikeUp(@PathVariable Long id, @RequestBody ShareBoardModifyRequest dto) {
        shareBoardService.modifyLikeUP(dto);
        return ResponseEntity.ok(ApiResponse.success("좋아요 증가"));
    }

    @PutMapping("/{id}/down")
    public ResponseEntity<ApiResponse<?>> modifyLikeDown(@PathVariable Long id, @RequestBody ShareBoardModifyRequest dto) {
        shareBoardService.modifyLikeDown(dto);
        System.out.println(dto);
        return ResponseEntity.ok(ApiResponse.success("좋아요 감소"));
    }



}
