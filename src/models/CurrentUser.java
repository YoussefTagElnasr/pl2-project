package models;

public class CurrentUser {
    private static CurrentUser instance;

    private String name;
    private String email;
    private String role;

    private CurrentUser(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static void setInstance(String name, String email, String role) {
        instance = new CurrentUser(name, email, role);
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
