package com.api.DTO.response;


import com.DO.spark_entity;

import java.io.Serializable;

public class DTO2 implements Serializable {
    private static final long serialVersionUID=1L;
    private String ph;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private float wallet;

    public DTO2() {

    }

    public String getph(){
        return ph;
    }
    public void setph(String ph){
        this.ph=ph;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public String getuserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public float getWallet(){
        return wallet;
    }
    public void setWallet(float wallet){
        this.wallet=wallet;
    }


   public DTO2(spark_entity se){
        this.ph=se.getPhone();
        this.firstName=se.getFirstName();
        this.lastName=se.getLastName();
        this.userName=se.getUserName();
        this.email=se.getEmail();
        this.wallet=se.getWallet();
   }

}
