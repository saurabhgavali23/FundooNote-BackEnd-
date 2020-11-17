package com.bridgelabz.fundoonote.exception;

import com.bridgelabz.fundoonote.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(FundooUserException.class)
    public ResponseEntity<Response> handleUserException(
            FundooUserException ex) {

        Response response = new Response(ex.httpStatus, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FundooNoteException.class)
    public ResponseEntity<Response> handleInvalidData(FundooNoteException ex) {

        Response response = new Response(ex.httpStatus, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
