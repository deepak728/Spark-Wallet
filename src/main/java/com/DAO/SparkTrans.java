package com.DAO;

import com.DO.BankEntity;

public interface SparkTrans {

    public BankEntity getBankDetail(String ph);
    public float updateBalance(String ph,float BA,float WA);

}
