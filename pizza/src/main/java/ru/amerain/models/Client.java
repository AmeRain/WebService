package ru.amerain.models;

import javax.persistence.Id;

/**
 * Created by User on 05.04.2017.
 */
public class Client {
    private int ID;
    private String full_name;
    private String phone_number;
    public Client(String full_name,String phone_number){

        this.full_name=full_name;
        this.phone_number=phone_number;
    }
    public Client(int ID,String full_name,String phone_number){
        this.ID=ID;
        this.full_name=full_name;
        this.phone_number=phone_number;
    }

    public int getID(){
        return  ID;
    }
    public void setID(int ID){
        this.ID=ID;
    }

    public String getFull_name(){
        return  full_name;
    }
    public String getPhone_number(){
        return  phone_number;
    }
}
