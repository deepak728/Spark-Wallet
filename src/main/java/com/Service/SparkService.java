package com.Service;


import com.api.DTO.request.DTO1;
import com.api.DTO.request.DTO6;
import com.api.DTO.response.DTO2;
import org.springframework.stereotype.Service;



public interface SparkService {
    public DTO2 getUserDetails(String token);
    public DTO2 addUser(DTO1 dto1);
    public void deleteUser(String ph);
    public DTO2 updateUser(DTO1 dto1);
    public String logInUser(DTO6 dto6);
}
