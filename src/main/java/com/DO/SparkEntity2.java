package com.DO;


import com.api.DTO.request.DTO1;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class SparkEntity2 {

//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int userId;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "userName")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "wallet")
    private float wallet;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public SparkEntity2(DTO1 dto){
//        this.userId=dto.getId();
        this.firstName=dto.getFirstName();
        this.lastName=dto.getLastName();
        this.email=dto.getEmail();
        this.phone=dto.getPhone();
        this.userName=dto.getUserName();
        this.password=dto.getPassword();
    }
    public SparkEntity2(){

    }
}
