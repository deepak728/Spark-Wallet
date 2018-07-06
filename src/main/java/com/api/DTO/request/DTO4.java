package com.api.DTO.request;

import java.io.Serializable;

public class DTO4 implements Serializable {
    private static final long serialVersionUID =1L;

    private String receiverPh;
    private float amount;

    public String getReceiverPh() {
        return receiverPh;
    }

    public void setReceiverPh(String receiverPh) {
        this.receiverPh = receiverPh;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
