package com.api.DTO.request;

import java.io.Serializable;

public class DTO6 implements Serializable {
    private static final long serialVersionUID =1L;

    private String passWord;
    private String ph;

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }



    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
