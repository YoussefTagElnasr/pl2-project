package models;

public class CurrentUser {
    private static CurrentUser instance;

    private String username;
    private String role;

    private CurrentUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public static void setInstance(String username, String role) {
        instance = new CurrentUser(username, role);
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
}
