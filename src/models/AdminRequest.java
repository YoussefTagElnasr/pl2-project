package models;

public class AdminRequest {
    private String customer;
    private String email;
    private String detail;
    private String status;
    private int price;
    private String readyDate;

    public AdminRequest(String customer , String email , String detail , String status , int price , String readyDate){
        this.customer = customer;
        this.email = email;
        this.detail = detail;
        this.status = status;
        this.price = price;
        this.readyDate = readyDate;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmail() {
        return email;
    }

    public String getDetail() {
        return detail;
    }

    public int getPrice() {
        return price;
    }

    public String getReadyDate() {
        return readyDate;
    }
    
    public String getStatus() {
        return status;
    }
}