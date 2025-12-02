package models;

public class Request {
    private String name;
    private String email;
    private String details;
    private String status;
    private String price;
    private String readyDate;

    public Request(String name, String email, String details, String status, String price, String readyDate) {
        this.name = name;
        this.email = email;
        this.details = details;
        this.status = status;
        this.price = price;
        this.readyDate = readyDate;
    }
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getDetails(){return details;}
    public String getStatus(){return status;}
    public String getPrice(){return price;}
    public String getReadyDate(){return readyDate;}

}


