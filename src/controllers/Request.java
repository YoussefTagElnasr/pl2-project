package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Request {

    private StringProperty requestID;
    private StringProperty customer;
    private StringProperty description;

    public Request(String id, String cust, String desc) {
        this.requestID = new SimpleStringProperty(id);
        this.customer = new SimpleStringProperty(cust);
        this.description = new SimpleStringProperty(desc);
    }

    public StringProperty requestIDProperty() { return requestID; }
    public StringProperty customerProperty() { return customer; }
    public StringProperty descriptionProperty() { return description; }

    public String getRequestID() { return requestID.get(); }
}
