package ru.amerain.models;

/**
 * Created by User on 30.03.2017.
 */
public class Order {
    private int id;
    private String full_name;
    private String client_place;
    private String date;

    public Order(int id, String full_name, String client_place , String date){
        this.id=id;
        this.full_name = full_name;//user
        this.client_place = client_place;
        this.date = date;
        //list
    }
    public Order(){
        this.full_name = "full_name";
        this.client_place = "my_adress";
        this.date = "date";
    }
    public  int getId(){
        return id;
    }

    public void setFull_name(String full_name){
        this.full_name=full_name;
    }
    public  String getFull_name(){
        return full_name;
    }
    public void setAdress(String client_place){
        this.client_place=client_place;
    }
    public String getAdress(){
        return this.client_place;
    }
    public  String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
}
