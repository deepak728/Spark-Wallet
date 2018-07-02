package com.DAO;


import com.DO.BankEntity;
import com.DO.SparkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.sql.Types;

@Repository
public class SparkTransImpl implements  SparkTrans{



    @Autowired
    JdbcTemplate template;
    public void setTemplate(JdbcTemplate template){
        this.template=template;
    }
    @Autowired
    SparkDAO sparkDAO;
    @Override
    public BankEntity getBankDetail(String ph) {
        String sql ="select id from User where phone=?";
        int id=template.queryForObject(sql,new Object[]{ph},Integer.class);
        String sql2="select * from UserAccount where id=?";

        BankEntity be=template.queryForObject(sql2,new Object[]{id},new BeanPropertyRowMapper<BankEntity>(BankEntity.class ));
        return be;
    }

    @Override
    public float updateBalance(String ph, float BA, float WA) {
             String sql="update User SET wallet=? where phone="+ph;
           String sql3="select id from User Where phone=?";
            int id=template.queryForObject(sql3,new Object[]{ph},Integer.class);
             String sql2="update UserAccount SET balance=? where id="+id;


             Object[] param=new Object[]{WA};
             int[] type=new int[]{Types.FLOAT};
        Object[] param2=new Object[]{BA};
        int[] type2=new int[]{Types.FLOAT};

        template.update(sql,param,type);
        template.update(sql2,param2,type2);

        SparkEntity se =sparkDAO.getUserDetails(ph);
        return se.getWallet();
    }
}
