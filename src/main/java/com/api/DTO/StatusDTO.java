package com.api.DTO;

public class StatusDTO {
    private long status;
    private String message;
    public long getStatus(){
        return status;
    }
    public void setStatus(long status){
        this.status=status;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
