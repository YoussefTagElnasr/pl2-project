package models;

public class Message {
    private final String name;
    private final String email;
    private final String content;

    public Message(String name, String email, String content) {
        this.name = name;
        this.email = email;
        this.content = content;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getContent() { return content; }
}
