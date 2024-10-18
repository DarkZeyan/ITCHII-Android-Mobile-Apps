package com.example.listadecontactos;

import androidx.annotation.NonNull;

public class Contact {

    int id;
    String name;
    String last_name;
    String phoneNumber;

    public Contact(int id, String name, String last_name, String phoneNumber){
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return this.name;
    }

    public String getLastName(){
        return this.last_name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;

    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLastName(String last_name){
        this.last_name = last_name;
    }
    public int getId(){
        return this.id;
    }

    @NonNull
    public String toString(){
        return this.name + " " + this.last_name + " " + this.phoneNumber;
    }

    public String getFullName(){
        return getName() + " " + getLastName();
    }



}
