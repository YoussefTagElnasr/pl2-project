package controllers;

import models.Customer;
import services.RegisterServices;

public class RegisterController {

    public static void handleRegister(Customer customer) throws IllegalArgumentException {

        if (customer.getEmail().isEmpty() || 
            customer.getPassword().isEmpty() || 
            customer.getName().isEmpty()) 
        {
            throw new IllegalArgumentException("All fields are required.");
        }

        if (!customer.getEmail().contains("@") || !customer.getEmail().contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (customer.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }

        boolean exists = RegisterServices.emailExists(customer.getEmail());
        if (exists) {
            throw new IllegalArgumentException("Email already exists.");
        }

        RegisterServices.register(customer);
    }
}