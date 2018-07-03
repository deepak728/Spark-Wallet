package com.DAO;

import com.DO.LoginDO;
import com.DO.SparkEntity2;
import com.DO.spark_entity;

import java.util.Date;

public interface SparkDAO {
    public spark_entity getUserDetails(String ph);
    public spark_entity addUser(SparkEntity2 se2);
    public void deleteUser(String ph);
    public spark_entity updateUser(SparkEntity2 se2);
    public int getUserId(String ph);
    public String getPass(String ph);
    public String getAccessKey(String ph);
    public String login(LoginDO loginDO);
    public int getUserFromToken(String ph);
    public String getPhoneFromId(int id);
    public java.sql.Date getUserExpiry(int id);

}
