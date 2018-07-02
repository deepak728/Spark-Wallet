package com.Service;

import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;

public interface SparkTransService {
    public DTO3 addMoney(String ph, float amount);
    public DTO5 sendMoney(String ph, DTO4 dto4);

}
