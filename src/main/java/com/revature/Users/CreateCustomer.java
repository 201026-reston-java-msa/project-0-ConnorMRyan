package com.revature.Users;


import com.revature.Utils.Address;
import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.PasswordEncoder;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateCustomer {
    static DatabaseConnection db;
    static Scanner in;
    static private String username;
    static private String password;
    static private String email;
    static private String phone;
    static private int address;
    static private String name;
    static private final String TABLE = "Users.Customer";

    public static void createCustomer(){
         db = DatabaseConnection.getConnection();
         in = new Scanner(System.in);
         username = getUsername();
         password = getPassword();
         email = getEmailAddress();
         name = getName();
         phone = getPhone();
         address = getAddress();
         deployToDB();

     }

    private static String getUsername() {
        System.out.println("------------");
        System.out.println("What would you like your username to be?");
        String user = in.nextLine();
        int count = 0;
        try {
            count = db.getCount(TABLE, username, user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (count == 0) {
            return user;
        } else {
            System.out.println("Sorry, that username is taken. Please try another");
            return getUsername();
        }
    }

    private static String getPassword() {
        System.out.println("------------");
        System.out.println("What would you like your password to be?");
        return new PasswordEncoder().hashPassword(in.nextLine());
    }

    private static String getPhone() {
        System.out.println("------------");
        System.out.println("What is your phone number?");
        String PhoneNo = in.nextLine();

        return PhoneNo;
    }

    private static int getAddress() {
        System.out.println("------------");
        return new Address().getID();
     }

    private static String getName() {
        System.out.println("------------");
        System.out.println("What is your name?");
        return in.nextLine();
    }

    private static String getEmailAddress() {
        System.out.println("------------");
        System.out.println("What is your email address?");
        String email = in.nextLine();
        int count = 0;
        try {
            count = db.getCount(TABLE, "Email", email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (count == 0) {
            return email;
        } else {
            System.out.println("Sorry, that email is taken. Please try another");
            return getEmailAddress();
        }
    }

    private static boolean verifyPassword() {
        System.out.println("-----------");
        System.out.println("Please confirm your password to complete your registration");
        return new PasswordEncoder().confirmPass(in.nextLine(), password);
    }

    private static void deployToDB() {
        String SQL = "INSERT INTO Users.Customer\n" +
                "        (Username, Password, Email, Name, Phone, Address) VALUES (?,?,?,?,?,?)";
        if (verifyPassword()) {
            db.submitSQL(SQL, username, password, email, name, phone, ""+address);
        } else {
            System.out.println("Sorry, that password was not correct, or something else failed.");
        }
    }

}
