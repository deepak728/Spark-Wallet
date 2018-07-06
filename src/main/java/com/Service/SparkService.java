package com.Service;


import com.Exception.CustomException;
import com.api.DTO.request.DTO1;
import com.api.DTO.request.DTO6;
import com.api.DTO.response.DTO2;
import org.springframework.stereotype.Service;



public interface SparkService {
    public DTO2 getUserDetails(String token) throws CustomException;
    public DTO2 addUser(DTO1 dto1) throws CustomException;
    public void deleteUser(String ph) throws CustomException;
    public DTO2 updateUser(DTO1 dto1) throws CustomException;
    public String logInUser(DTO6 dto6) throws CustomException, Exception;
}
