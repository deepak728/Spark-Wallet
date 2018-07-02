package com.api.DTO.request;


import java.io.Serializable;

public class DTO3 implements Serializable {
    private static final long serialVersionUID =1L;

    private float amount ;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public DTO3(float amount){
        this.amount=amount;
    }
    public  DTO3(){

    }
}
