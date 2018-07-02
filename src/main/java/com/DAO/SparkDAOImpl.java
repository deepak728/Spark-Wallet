package com.DAO;

import com.DO.BankEntity;
import com.DO.SparkEntity;
import com.DO.SparkEntity2;
import com.api.DTO.request.DTO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.sql.Types;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class SparkDAOImpl implements SparkDAO{


   @Autowired
    JdbcTemplate template;
   public void setTemplate(JdbcTemplate template){
       this.template=template;
   }


    @Override
    public SparkEntity getUserDetails(String ph) {
           String sql="select id from User where phone=?";
           SparkEntity se=template.queryForObject(sql,new Object[]{ph},new BeanPropertyRowMapper<SparkEntity>(SparkEntity.class ));
           System.out.println(se.getUserId());
           return se;
    }
    @Override
    public int getUserId(String ph) {
        String sql="select id from User where phone=?";
        int id=template.queryForObject(sql,new Object[]{ph},Integer.class);
        return id;
    }

    @Override
    public SparkEntity addUser (SparkEntity2 se2) {
         String ph=se2.getPhone();
        String sql2="select id from User where phone=?";
        int rows=-1;
        try{
           rows =template.queryForObject(sql2,new Object[]{ph},Integer.class);

        }catch (Exception e){

        }
        if(rows>0){
             System.out.println("User Already exist!!");
             return  null;
        }else {

            String sql =
                    "INSERT INTO User (firstName, " +
                            "    lastName, " +
                            "    userName, " +
                            " email," +
                            "phone," +
                            "password," +
                            "    wallet) " +
                            "VALUES ( ?, ?, ?, ?,?,?,?)";
            String firstName = se2.getFirstName();
            String lastName = se2.getLastName();
            String userName = se2.getUserName();
            String email = se2.getEmail();
            String phone = se2.getPhone();
            String password = se2.getPassword();
            String temp_pass = userName + password;
            try {
                String sha256_pass = getHashedPassword(temp_pass);

            float wallet = 100;

            Object[] params = new Object[]{
                    firstName, lastName, userName, email, phone, sha256_pass, wallet
            };
            int[] types = new int[]{
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.FLOAT
            };
            int row = template.update(sql, params, types);

            String sql5 = "select id from User where phone=?";
            int id = template.queryForObject(sql5, new Object[]{ph}, Integer.class);


            String bankAccount = ("SBI" + String.valueOf(id));
            float bankBalance = 1000;

            String sql3 = "INSERT INTO UserAccount (id," +
                    "account_id," +
                    "balance)" +
                    "VALUES(?,?,?)";

            Object[] params2 = new Object[]{id, bankAccount, bankBalance};
            int[] types2 = new int[]{Types.INTEGER, Types.VARCHAR, Types.FLOAT};

            template.update(sql3, params2, types2);
            String sql4 = "select account_id from UserAccount where id=?";
            BankEntity be = template.queryForObject(sql4, new Object[]{id}, new BeanPropertyRowMapper<BankEntity>(BankEntity.class));
            System.out.print(ph + "'s Bank Account Created Account No: " + be.getBankAccount() + " Current Bank Balance = " + (int)be.getBalance());

            return this.getUserDetails(phone);
                 }
               catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }


    }

    @Override
    public void deleteUser(String ph) {
        String sql="delete from User where phone=?";
        int id=this.getUserId(ph);
        String sql2="delete from UserAccount where id=?";

           Object [] params={ph};
           Object [] params2={id};
           int [] type2={Types.INTEGER};
           int [] type={Types.VARCHAR};

               int rows=template.update(sql,params,type);
               int row2=template.update(sql2,params2,type2);
               if(rows*row2>0){
                   System.out.println(rows+"  deleted successfully");
               }else{
                   System.out.println("User does not exist");
               }


    }

    @Override
    public SparkEntity updateUser(SparkEntity2 se2) {
        String ph=se2.getPhone();
        String sql="select id from User where phone=?";
        String sql3="select * from User where phone=?";



        int row=template.queryForObject(sql,new Object[]{ph},Integer.class);
        if(row>0){


            SparkEntity2 temp=template.queryForObject(sql3,new Object[]{ph},new BeanPropertyRowMapper<SparkEntity2>(SparkEntity2.class ));
            String firstName=temp.getFirstName();
            String lastName=temp.getLastName();
            String email=temp.getEmail();
            String userName=temp.getUserName();
            String password=temp.getPassword();

                if (se2.getFirstName() != null) {
                    firstName = se2.getFirstName();
                }
                if (se2.getLastName() != null) {
                    lastName = se2.getLastName();
                }
                if (se2.getEmail() != null) {
                    email = se2.getEmail();
                }
                if (se2.getPassword() != null) {
                    password = se2.getPassword();
                }
                if (se2.getUserName() != null) {
                    userName = se2.getUserName();
                }

            String temp_pass=userName+password;
            try {
                String sha256_pass = getHashedPassword(temp_pass);


                String sql2 =
                        "update User SET firstName=?,lastName=?,userName=?,email=?,password=? where phone=" + ph;


                Object[] params2 = new Object[]{
                        firstName, lastName, userName, email, sha256_pass
                };
                int[] types2 = new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,

                };

                template.update(sql2, params2, types2);
                SparkEntity se = getUserDetails(ph);
                return se;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        else{
            System.out.print("User does not exist!! ");
            return null;
        }

    }

    public String getHashedPassword(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
