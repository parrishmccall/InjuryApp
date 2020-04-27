package com.example.injury3;

public class Users {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String practice;

    public Users(){}


    public Users(String name, String email, String phone, String address){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Users(String name, String email, String phone, String address, String practice){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.practice = practice;
    }




    public String getName(){
        return name;
    }

    private void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    private void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    private void setPhone(String phone){
        this.phone = phone;
    }

    public String getpracticeName(){
        return practice;
    }

    private void setpracticeName(String practice){
        this.practice = practice;
    }

    public String getAddress(){
        return address;
    }

    private void setAddress(String address){
        this.address = address;
    }
}
