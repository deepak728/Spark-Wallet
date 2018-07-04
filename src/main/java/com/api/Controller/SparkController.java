package com.api.Controller;


import com.Exception.CustomException;
import com.Service.SparkService;
import com.api.DTO.ErrorResponse.ErrorDTO;
import com.api.DTO.request.DTO1;
import com.api.DTO.request.DTO6;
import com.api.DTO.response.DTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
@RestController
@RequestMapping("spark")
public class SparkController {

    @Autowired(required = true)
    private SparkService sparkService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO2 getUserDetails(@RequestHeader("token") String token) throws CustomException{
        System.out.println("calling getUserDetails Method");
        return sparkService.getUserDetails(token);
    }

@RequestMapping(value = "/add",method = RequestMethod.POST)
    public DTO2 addUser(@RequestBody DTO1 dto1) throws CustomException{
        System.out.println("Adding User to the database"+dto1.getPhone());
        DTO2 dto2=sparkService.addUser(dto1);
        return dto2;
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader String token )throws CustomException{
        System.out.println("deleting User "+token);
        sparkService.deleteUser(token);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO2 updateUser(@RequestBody DTO1 dto1) throws CustomException{

        DTO2 dto2=sparkService.updateUser(dto1);
        return dto2;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody DTO6 dto6) throws CustomException,Exception{
        String token =sparkService.logInUser(dto6);
        HttpHeaders responseHeader=new HttpHeaders();
        responseHeader.set("token",token);
        ResponseEntity re=new ResponseEntity("logedIn",responseHeader,HttpStatus.OK);
        return re;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(CustomException ex){
        ErrorDTO error=new ErrorDTO();
        error.setErrorCode(ex.getHs().value());
        error.setErrorMessage(ex.getErrorMessage());
        return new ResponseEntity<ErrorDTO>(error,ex.getHs());
    }

}

