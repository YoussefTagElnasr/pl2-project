package models;

public class CurrentUser {
    private String username;
    private String role;

    public CurrentUser(String username , String role){
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}