package com.api.Controller;


import com.Service.SparkService;
import com.api.DTO.request.DTO1;
import com.api.DTO.response.DTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
@RestController
@RequestMapping("spark")
public class SparkController {

    @Autowired(required = true)
    private SparkService sparkService;

    @GetMapping(value = "/{ph}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO2 getUserDetails(@PathVariable("ph") String ph){
        System.out.println("calling getUserDetails Method");
        return sparkService.getUserDetails(ph);
    }

@RequestMapping(value = "/add",method = RequestMethod.POST)
    public DTO2 addUser(@RequestBody DTO1 dto1){
        System.out.println("Adding User to the database"+dto1.getPhone());
        DTO2 dto2=sparkService.addUser(dto1);
        return dto2;
    }
    @RequestMapping(value = "/{ph}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("ph") String ph ){
        System.out.println("deleting User "+ph);
        sparkService.deleteUser(ph);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO2 updateUser(@RequestBody DTO1 dto1){

        DTO2 dto2=sparkService.updateUser(dto1);
        return dto2;
    }
}

