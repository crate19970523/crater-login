package com.crater.craterlogin.exception;

import com.crater.craterlogin.bean.response.BasicResponse;
import com.crater.craterlogin.bean.response.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<BasicResponse> handleAccountException(AccountException e) {
        return new ResponseEntity<>(new BasicResponse(new Status(false, e.getMessage(),
                ExceptionUtils.getStackTrace(e))), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DbException.class)
    public ResponseEntity<BasicResponse> handleDBException(DbException e) {
        return new ResponseEntity<>(new BasicResponse(new Status(false, "DB error",
                ExceptionUtils.getStackTrace(e))), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
