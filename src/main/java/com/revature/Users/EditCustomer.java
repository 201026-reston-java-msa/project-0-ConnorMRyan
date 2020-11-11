package com.revature.Users;

import com.revature.Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EditCustomer {
    DatabaseConnection db = DatabaseConnection.getConnection();
    Scanner in = new Scanner(System.in);
    private String username;
    private int ID;
    private String email;
    private String address; // Should replace this with an address table.
    private String phone;

    EditCustomer(Customer customer) throws SQLException {
        if (customer.logIn()) {
            this.ID = customer.getID();
            getCustomerInfo();
            printCustomerInfo();
            System.out.println("-----------------");
            updateInfo(getInfoToUpdate());
            deployCustomerInfoUpdate();
            System.out.println("------------");

            getCustomerInfo();
            printCustomerInfo();


        }
    }

    private void getCustomerInfo() {
        String sqlCommand = "SELECT Email, Phone, Address FROM Users.Customer WHERE ID = ?;";
        ResultSet rs = db.getResult(sqlCommand, "" + ID);
        try {
            rs.next();
            email = rs.getString("Email");
            phone = rs.getString("Phone");
            address = rs.getString("Address");
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
        }
    }

    private void deployCustomerInfoUpdate() {
        String sqlCommand = String.format("Update Users.Customer SET Email = ?, Phone = ?, Address = ? WHERE ID = %s;", ID);
        db.submitSQL(sqlCommand, email, phone, address);


    }

    private void printCustomerInfo() {
        System.out.println("Your current info!");
        System.out.println("-------------------");
        System.out.printf("Email: %s\n", email);
        System.out.printf("Phone: %s\n", phone);
        System.out.printf("Address: %s\n", address);
        System.out.println("-------------------");
    }

    private int getInfoToUpdate() {
        System.out.println("------------------");
        System.out.println("What would you like to update?");
        System.out.println("1: Email");
        System.out.println("2: Phone");
        System.out.println("3: Address");
        System.out.println("4: All");
        System.out.println("5: None");
        return new Scanner(System.in).nextInt();
    }

    private void updateEmail() {
        System.out.println("------------");
        System.out.println("What is your email address?");
        String updateEmail = in.nextLine();
        int count = 0;
        try {
            count = db.getCount("Users.Customer", "Email", updateEmail);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (count == 0) {
            this.email = updateEmail;
        } else {
            System.out.println("Sorry, that email is taken. Please try another");
            updateEmail();

        }
    }

    private void updatePhone() {
        System.out.println("What is your phone number?");
        phone = in.nextLine();

    }

    private void updateAddress() {
        System.out.println("What is your address?");
        address = in.nextLine();

    }

    private void updateInfo(int choice) {
        switch (choice) {
            case 1:
                updateEmail();
                break;
            case 2:
                updatePhone();
                break;
            case 3:
                updateAddress();
                break;
            case 4:
                updateEmail();
                updatePhone();
                updateAddress();
                break;
            default:
                System.out.println("Sorry, something went wrong.");
        }
    }
}
