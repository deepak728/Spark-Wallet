package com.Service;

import com.DAO.SparkDAO;
import com.DAO.SparkDAOImpl;
import com.DO.LoginDO;
import com.DO.SparkEntity2;
import com.DO.spark_entity;
import com.Exception.CustomException;
import com.api.DTO.request.DTO1;
import com.api.DTO.request.DTO6;
import com.api.DTO.response.DTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class SparkServiceImpl implements SparkService{
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    @Autowired(required = true)
    private SparkDAO sparkDAO;
    @Autowired(required = true)
    private SparkDAOImpl sparkDAOImpl;

    @Override
    public DTO2 getUserDetails(String token) throws CustomException {
        String ph=authenticateUser(token);
            spark_entity se=sparkDAO.getUserDetails(ph);
            DTO2 dto2=new DTO2(se);
            return dto2;
    }

    @Override
    public DTO2 addUser(DTO1 dto1) throws CustomException{

          SparkEntity2 se2=new SparkEntity2(dto1);
          try{
              spark_entity se=sparkDAO.addUser(se2);
              DTO2 dto2=new DTO2(se);
              return dto2;
          }catch (Exception e){
              throw new CustomException("Failed to add User",HttpStatus.NOT_ACCEPTABLE);
          }

    }

    @Override
    public void deleteUser(String token) throws CustomException{
        String ph=authenticateUser(token);
        try{
            sparkDAO.deleteUser(ph);

        }catch (Exception e){
            throw new CustomException("Unable to delete User ",HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public DTO2 updateUser(DTO1 dto1) throws CustomException {
        SparkEntity2 se2;
        try{
            se2=new SparkEntity2(dto1);

            spark_entity se =sparkDAO.updateUser(se2);
            DTO2 dto2=new DTO2(se);
            return dto2;
        }catch (Exception e){
            throw new CustomException("Unable to Update User ",HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public String logInUser(DTO6 dto6) throws CustomException,Exception {
        String ph;
        spark_entity se;
        int id;
        String userName;
        String temp_data;
        String hashedPassword;
        String tempHashedPass;


        try {
            ph = dto6.getPh();
        se = sparkDAO.getUserDetails(ph);

        id = se.getId();
            userName = se.getUserName();
            temp_data = userName + dto6.getPassWord();
            hashedPassword = sparkDAO.getPass(ph);
            tempHashedPass = sparkDAOImpl.getHashedPassword(temp_data);

        } catch (Exception e) {
            throw new CustomException("User does Not exist", HttpStatus.NOT_FOUND);
        }

        try {
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

            }else{
                throw new CustomException("",HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("Internal Error",HttpStatus.FAILED_DEPENDENCY);
        } catch (SignatureException e) {
            throw new CustomException("Internal Error",HttpStatus.BAD_REQUEST);
        } catch (InvalidKeyException e) {
            throw new CustomException("Internal Error",HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw new CustomException("UserName or Password incorrect" ,HttpStatus.UNAUTHORIZED);
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



    private String generateToken(String temp_data, String Key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, CustomException {
        try{

        SecretKeySpec signingKey = new SecretKeySpec(Key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(temp_data.getBytes()));
    }catch (Exception e){
        throw new CustomException("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }


   public String authenticateUser(String token) throws CustomException {
             int id ;
             String ph;
             Date expiry_time;
             Date current_time;
       try {
            id = sparkDAO.getUserFromToken(token);
            ph = sparkDAO.getPhoneFromId(id);
            expiry_time = sparkDAO.getUserExpiry(id);
            current_time = getCurrentTime();
           } catch (Exception e) {
              throw new CustomException("User not found",HttpStatus.NOT_FOUND);
       }
       if (current_time.before(expiry_time)) {
           return ph;
       } else {
           throw new CustomException("Session Expired, Please Login again", HttpStatus.REQUEST_TIMEOUT);
       }
   }
}
