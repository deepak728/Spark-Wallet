package com.Exception;


import com.api.DTO.ErrorResponse.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex){
        ErrorDTO error=new ErrorDTO();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setErrorMessage("Please contact developer");
        return new ResponseEntity<ErrorDTO>(error,HttpStatus.OK);
    }
}
