package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.exception.BaseException;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.model.enums.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonRes<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("{}", HttpMessageNotReadableException.class.getSimpleName(), ex);
        BaseException exception = new BaseException(ResultCode.INVALID_JSON_FORMAT.getCode(), ResultCode.INVALID_JSON_FORMAT.getMessage());
        return ApiHandler.badRequest(new CommonRes<>(exception.getErrCode(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonRes<?>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("{}", MethodArgumentNotValidException.class.getSimpleName(), ex);
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .findFirst()
                .orElse(ResultCode.VALIDATION_ERROR.getMessage());
        BaseException exception = new BaseException(ResultCode.VALIDATION_ERROR.getCode(), message);
        return ApiHandler.badRequest(new CommonRes<>(exception.getErrCode(), exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonRes<?>> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("{}", ConstraintViolationException.class.getSimpleName(), ex);
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .findFirst()
                .orElse(ResultCode.VALIDATION_ERROR.getMessage());
        BaseException exception = new BaseException(ResultCode.VALIDATION_ERROR.getCode(), message);
        return ApiHandler.badRequest(new CommonRes<>(exception.getErrCode(), exception.getErrCode()));
    }

    @ExceptionHandler(DelegationServiceException.class)
    public ResponseEntity<CommonRes<?>> handleBusinessException(DelegationServiceException ex, HttpServletRequest request) {
        log.error("{}", DelegationServiceException.class.getSimpleName(), ex);
        return ApiHandler.badRequest(new CommonRes<>(ex.getErrCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonRes<?>> handleException(Exception ex, HttpServletRequest request) {
        log.error("{}", Exception.class.getSimpleName(), ex);
        BaseException exception = new BaseException(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage());
        return ApiHandler.badRequest(new CommonRes<>(exception.getErrCode(), exception.getMessage()));
    }
}

