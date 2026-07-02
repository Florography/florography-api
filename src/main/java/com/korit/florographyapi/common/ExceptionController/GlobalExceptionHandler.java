package com.korit.florographyapi.common.ExceptionController;

import com.korit.florographyapi.common.Exception.DuplicatedException;
import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.dto.NotValidResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ApiResponse<DuplicatedException>> duplicated(DuplicatedException e) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(e));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<AuthenticationException>> authentication(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(e));   //FORBIDDEN - 403
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> notValid(MethodArgumentNotValidException e) {
        List<NotValidResponse.ErrorField> errorFields = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new NotValidResponse.ErrorField(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage())
                ).toList();
        return  ResponseEntity.badRequest().body(ApiResponse.fail(new NotValidResponse("유효성 검사 오류", errorFields)));
    }
}
