package com.nilesh.whatsappclone.expection;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetial> UserExceptionHandler(UserException e, WebRequest req) {

        ErrorDetial errorDetial = new ErrorDetial();
        errorDetial.setError(e.getMessage());
        errorDetial.setDetail(req.getDescription(false));
        errorDetial.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetial>(errorDetial, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetial> MessageExceptionHandler(MessageException e, WebRequest req) {

        ErrorDetial errorDetial = new ErrorDetial();
        errorDetial.setError(e.getMessage());
        errorDetial.setDetail(req.getDescription(false));
        errorDetial.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetial>(errorDetial, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetial> ChatExceptionHandler(ChatException e, WebRequest req) {

        ErrorDetial errorDetial = new ErrorDetial();
        errorDetial.setError(e.getMessage());
        errorDetial.setDetail(req.getDescription(false));
        errorDetial.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetial>(errorDetial, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetial> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,
            WebRequest req) {

        String error = e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorDetial errorDetial = new ErrorDetial();
        errorDetial.setError("Validation error");
        errorDetial.setDetail(error);
        errorDetial.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetial>(errorDetial, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetial> otherExceptionHandler(Exception e, WebRequest req) {

        ErrorDetial errorDetial = new ErrorDetial();
        errorDetial.setError(e.getMessage());
        errorDetial.setDetail(req.getDescription(false));
        errorDetial.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetial>(errorDetial, HttpStatus.BAD_REQUEST);

    }

}
