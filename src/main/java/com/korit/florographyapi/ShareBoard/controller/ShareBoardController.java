package com.korit.florographyapi.ShareBoard.controller;

import com.korit.florographyapi.ShareBoard.dto.ShareBoardResponse;
import com.korit.florographyapi.ShareBoard.service.ShareBoardService;
import com.korit.florographyapi.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shareboard")
@RequiredArgsConstructor
public class ShareBoardController {
    private final ShareBoardService shareBoardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getAll(userId)));
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getSelectByUserId( @PathVariable Long userId) {
//        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getSelectByUserId(userId)));
//    }

    @GetMapping("/rank")
    public ResponseEntity<ApiResponse<List<ShareBoardResponse>>> getRank(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(shareBoardService.getRank(userId)));
    }


}
