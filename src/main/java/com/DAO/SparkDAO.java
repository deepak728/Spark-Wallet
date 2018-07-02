package com.DAO;

import com.DO.SparkEntity;
import com.DO.SparkEntity2;
import com.api.DTO.request.DTO1;
import org.omg.CORBA.PUBLIC_MEMBER;

public interface SparkDAO {
    public SparkEntity getUserDetails(String ph);
    public SparkEntity addUser(SparkEntity2 se2);
    public void deleteUser(String ph);
    public SparkEntity updateUser(SparkEntity2 se2);
    public int getUserId(String ph);
}
