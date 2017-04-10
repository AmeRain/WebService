package ru.amerain.models;

/**
 * Created by User on 30.03.2017.
 */
public class Order {
    private int id;
    private Client client;
    private String client_place;
    private String date;
    private String notes;
    private String[] products;
    private String[] countProducts;

    public Order(int id, Client client, String client_place , String date, String notes){
        this.id=id;
        this.client = client;//user
        this.client_place = client_place;
        this.date = date;
        this.notes=notes;
        //list
    }
    public Order(int id, Client client, String client_place , String date){
        this.id=id;
        this.client = client;//user
        this.client_place = client_place;
        this.date = date;
        //list
    }
    public Order(Client client, String client_place , String date , String[] products, String[] countProducts){
        this.products = products;
        this.countProducts = countProducts;
        this.client = client;//user
        this.client_place = client_place;
        this.date = date;
        //list
    }
    public String[] getProducts(){
        return products;
    }
    public String[] getCountProducts(){
        return countProducts;
    }
    public  int getId(){
        return id;
    }
    public void setID(int ID){
        this.id=ID;
    }
    public void setClient(Client client){
        this.client=client;
    }
    public Client getClient(){
        return client;
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
    public String getNotes(){
        return this.notes;
    }
    public  void setNotes(String notes){
        this.notes=notes;
    }
}
