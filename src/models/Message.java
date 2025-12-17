package models;

public class Message {
    private String name;
    private String email;
    private String content;
    private String status;

    public Message(String name, String email, String content, String status) {
        this.name = name;
        this.email = email;
        this.content = content;
        this.status = status;
    }

    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public String getContent(){ return content; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

}
