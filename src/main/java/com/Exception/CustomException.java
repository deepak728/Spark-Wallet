package com.Exception;

import com.api.DTO.ErrorResponse.ErrorDTO;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    private static final long serialVersionUID=1L;
    private String errorMessage;
  private HttpStatus hs;
    public String getErrorMessage(){
        return errorMessage;
    }

    public HttpStatus getHs() {
        return hs;
    }

    public CustomException(String errorMessage, HttpStatus hs){
        super();
        this.hs=hs;
        this.errorMessage=errorMessage;
    }

    public CustomException(){
        super();
    }
}
