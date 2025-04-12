package com.glacier.glacierdiary.common.exception;

import com.glacier.glacierdiary.common.result.Result;
import com.glacier.glacierdiary.common.result.ResultCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 全局异常处理
 * @since 2025/4/13 1:12
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @apiNote IllegalArgumentException 异常处理
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(Result.failed("非法参数: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * @apiNote Exception 异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(Result.failed("服务器内部错误:" + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @apiNote @Valid 或 @Validated 参数校验失败异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String errorMessage = String.join("; ", errorMessages);
        return new ResponseEntity<>(Result.failed(errorMessage), HttpStatus.BAD_REQUEST);
    }

}
