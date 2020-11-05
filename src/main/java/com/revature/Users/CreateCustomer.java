package com.revature.Users;


import com.revature.Utils.PasswordEncoder;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateCustomer implements UsersMethods {
    Scanner in;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

    CreateCustomer() {
        this.in = new Scanner(System.in);
            this.username = getUsername();
            this.password = getPassword();
            this.email = getEmailAddress();
            this.phone = getPhone();
            this.address = getAddress();

    }
    private String getUsername() {
        System.out.println("------------");
        System.out.println("What would you like your username to be?");
        String user = in.nextLine();
        int count = 0;
        try {
            count = getCount("Username",user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if( count == 0){
            return user;
        }else{
            System.out.println("Sorry, that username is taken. Please try another");
            return getUsername();
        }
    }
    private String getPassword(){
        System.out.println("------------");
        System.out.println("What would you like your password to be?");
        String pass = new PasswordEncoder().hashPassword(in.nextLine());

        return pass;
    }
    private String getPhone(){
        System.out.println("------------");
        System.out.println("What is your phone number?");
        String PhoneNo = in.nextLine();

        return PhoneNo;
    }
    private String getAddress(){
        System.out.println("------------");
        System.out.println("What is your address?");
        String address = in.nextLine();

        return address;
    }
    private String getEmailAddress() {
        System.out.println("------------");
        System.out.println("What is your email address?");
        String email = in.nextLine();
        int count = 0;
        try {
            count = getCount("Email",email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if( count == 0){
            return email;
        }else{
            System.out.println("Sorry, that email is taken. Please try another");
            return getEmailAddress();
        }
    }
    private boolean verifyPassword(){
        System.out.println("-----------");
        System.out.println("Please confirm your password to complete your registration");
        return new PasswordEncoder().confirmPass(in.nextLine(),password);
    }
    public void deployToDB(){
        String SQL = "INSERT INTO Users.Customer\n" +
                "        (Username, Password, Email, Phone, Address) VALUES (?,?,?,?,?)";
        if(verifyPassword()){
            db.submitSQL(SQL,username, password, email, phone, address);
        }else{
            System.out.println("Sorry, that password was not correct, or something else failed.");
        }
    }

}
