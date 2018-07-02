package com.Service;

import com.DAO.SparkDAO;
import com.DO.SparkEntity;
import com.DO.SparkEntity2;
import com.api.DTO.request.DTO1;
import com.api.DTO.response.DTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SparkServiceImpl implements SparkService {

    @Autowired(required = true)
    private SparkDAO sparkDAO;

    @Override
    public DTO2 getUserDetails(String ph) {
        SparkEntity se=sparkDAO.getUserDetails(ph);
        DTO2 dto2=new DTO2(se);
        return dto2;
    }

    @Override
    public DTO2 addUser(DTO1 dto1) {
          SparkEntity2 se2=new SparkEntity2(dto1);
          SparkEntity se=sparkDAO.addUser(se2);
          DTO2 dto2=new DTO2(se);
   return dto2;
    }

    @Override
    public void deleteUser(String ph) {
       sparkDAO.deleteUser(ph);

    }

    @Override
    public DTO2 updateUser(DTO1 dto1) {
        SparkEntity2 se2=new SparkEntity2(dto1);
        SparkEntity se =sparkDAO.updateUser(se2);
        DTO2 dto2=new DTO2(se);
        return dto2;
    }


}
