package com.Service;

import com.DAO.SparkDAO;
import com.DAO.SparkDAOImpl;
import com.DO.LoginDO;
import com.DO.SparkEntity2;
import com.DO.spark_entity;
import com.api.DTO.request.DTO1;
import com.api.DTO.request.DTO6;
import com.api.DTO.response.DTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.Date;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class SparkServiceImpl implements SparkService {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    @Autowired(required = true)
    private SparkDAO sparkDAO;
    @Autowired(required = true)
    private SparkDAOImpl sparkDAOImpl;

    @Override
    public DTO2 getUserDetails(String token) {
        String ph=authenticateUser(token);
        if(ph!=null){
            spark_entity se=sparkDAO.getUserDetails(ph);
            DTO2 dto2=new DTO2(se);
            return dto2;
        }else{
            System.out.print("Please LogIn again ");
            DTO2 dto2=new DTO2();
            return dto2;
        }

    }

    @Override
    public DTO2 addUser(DTO1 dto1) {
          SparkEntity2 se2=new SparkEntity2(dto1);
          spark_entity se=sparkDAO.addUser(se2);
          DTO2 dto2=new DTO2(se);
   return dto2;
    }

    @Override
    public void deleteUser(String token) {
        String ph=authenticateUser(token);
        if(ph!=null){
            sparkDAO.deleteUser(ph);

        }else{
            System.out.println("please LogIn again ");
        }

    }

    @Override
    public DTO2 updateUser(DTO1 dto1) {
        SparkEntity2 se2=new SparkEntity2(dto1);
        spark_entity se =sparkDAO.updateUser(se2);
        DTO2 dto2=new DTO2(se);
        return dto2;
    }

    @Override
    public String logInUser(DTO6 dto6) {

        String ph=dto6.getPh();
        System.out.println(ph);
        spark_entity se=sparkDAO.getUserDetails(ph);
        int id =se.getId();
        System.out.println(id);
       String userName=se.getUserName();
        String temp_data=userName+dto6.getPassWord();
        if(id>0) {
            String hashedPassword = sparkDAO.getPass(ph);
            try {
                String tempHashedPass = sparkDAOImpl.getHashedPassword(temp_data);
                if (tempHashedPass.equals(hashedPassword)) {

                    String accessKey = sparkDAOImpl.randomGenerator(temp_data);
                    String token = generateToken(temp_data, accessKey);
                    Date current_time = getCurrentTime();
                    Date expiry_time = getExpiryTime(current_time);
                    System.out.println(token);
                    System.out.println(expiry_time);
                    LoginDO loginDO = new LoginDO();
                    loginDO.setToken(token);
                    loginDO.setAccessKey(accessKey);
                    loginDO.setExpire_time(expiry_time);
                    loginDO.setId(id);
                    System.out.println("Setting token to User");
                    String userToken = sparkDAO.login(loginDO);

                    return userToken;

                } else {
                    System.out.println("Username or Password Incorrect ");
                    return null;
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            } catch (SignatureException e) {
                e.printStackTrace();
                return null;
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            System.out.println("User Doesn't exist");
            return null;
        }
    }

    private Date getExpiryTime(Date current_time) {
        java.sql.Date tomorrow= new java.sql.Date( current_time.getTime() + 24*60*60*1000);
        return tomorrow;
    }

    private Date getCurrentTime() {
        java.sql.Date now = new java.sql.Date( new java.util.Date().getTime() );
        return now;
    }



    private String generateToken(String temp_data, String Key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(Key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(temp_data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }


   public String authenticateUser(String token){
        int  id=sparkDAO.getUserFromToken(token);
        if(id>0){
            Date expiry_time=sparkDAO.getUserExpiry(id);
            Date current_time=getCurrentTime();
            if(current_time.before(expiry_time)){
                String ph=sparkDAO.getPhoneFromId(id);
                return ph;

            }else{
                System.out.println("Session expired, Please Login again!!");
                return null;
            }


        }else{
            System.out.println("User doesn't exist ");
            return null;
        }
   }

}
