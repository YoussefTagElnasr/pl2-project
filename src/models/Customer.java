package models;

public class Customer {
    private String email;
    private String password;
    private String name;
    private String role;

    public Customer(String email , String password , String name , String role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
