package com.api.Controller;


import com.Exception.CustomException;
import com.Service.SparkTransService;
import com.api.DTO.ErrorResponse.ErrorDTO;
import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spark")
public class SparkTransController {
     @Autowired(required = true)
    private  SparkTransService sparkTransService;

     @RequestMapping(value = "/addMoney",method = RequestMethod.POST,consumes ={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO3 addMoney(@RequestBody DTO3 dto3, @RequestHeader  String token) throws CustomException {
         DTO3 updatedBalance=sparkTransService.addMoney(token,dto3.getAmount());
         return updatedBalance;
     }
     @RequestMapping(value = "/sendMoney",method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
       public DTO5 sendMoney(@RequestBody DTO4 dto4, @RequestHeader  String token) throws CustomException{
         DTO5 updatedBalance=sparkTransService.sendMoney(token,dto4);
         return updatedBalance;
     }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(CustomException ex){
        ErrorDTO error=new ErrorDTO();
        error.setErrorCode(ex.getHs().value());
        error.setErrorMessage(ex.getErrorMessage());
        return new ResponseEntity<ErrorDTO>(error,ex.getHs());
    }

}
