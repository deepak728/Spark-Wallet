package com.Service;

import com.Exception.CustomException;
import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;

public interface SparkTransService {
    public DTO3 addMoney(String token, float amount) throws CustomException;
    public DTO5 sendMoney(String token, DTO4 dto4) throws CustomException;

}
