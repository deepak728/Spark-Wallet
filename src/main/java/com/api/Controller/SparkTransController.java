package com.api.Controller;


import com.Service.SparkTransService;
import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spark")
public class SparkTransController {
     @Autowired(required = true)
    private  SparkTransService sparkTransService;

     @RequestMapping(value = "/addMoney/{ph}",method = RequestMethod.POST,consumes ={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public DTO3 addMoney(@RequestBody DTO3 dto3, @PathVariable("ph") String ph){
         DTO3 updatedBalance=sparkTransService.addMoney(ph,dto3.getAmount());
         return updatedBalance;
     }
     @RequestMapping(value = "/sendMoney/{ph}",method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
       public DTO5 sendMoney(@RequestBody DTO4 dto4, @PathVariable("ph") String ph){
         DTO5 updatedBalance=sparkTransService.sendMoney(ph,dto4);
         return updatedBalance;
     }


}
