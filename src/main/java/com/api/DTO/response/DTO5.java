package com.api.DTO.response;

import java.io.Serializable;

public class DTO5 implements Serializable {

    private static final long serialVersionUID =1L;

    private float senderBalance;
    private float receiverBalance;

    public float getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(float senderBalance) {
        this.senderBalance = senderBalance;
    }

    public float getReceiverBalance() {
        return receiverBalance;
    }

    public void setReceiverBalance(float receiverBalance) {
        this.receiverBalance = receiverBalance;
    }
    public DTO5(float sb,float rb){
        this.senderBalance=sb;
        this.receiverBalance=rb;


    }
    public DTO5(){

    }
}
