package com.revature;

import java.util.Scanner;

public class CreateCustomer {
    Scanner in;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

    CreateCustomer(){
        this.in = new Scanner(System.in);
        this.username = getUsername();
        this.password = getPassword();
        this.email = getEmailAddress();
        this.phone = getPhone();
        this.address = getAddress();
    }
    private String getUsername(){
        System.out.println("------------");
        System.out.println("What would you like your username to be?");
        String user = in.nextLine();

        return user;
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
    private String getEmailAddress(){
        System.out.println("------------");
        System.out.println("What is your email address?");
        String email = in.nextLine();

        return email;
    }
    private boolean verifyPassword(){
        System.out.println("-----------");
        System.out.println("Please confirm your password to complete your registration");

        return new PasswordEncoder().confirmPass(in.nextLine(),password);
    }
    public void deployToDB(){
        DatabaseConnection db = new DatabaseConnection();
        String SQL = "INSERT INTO Users.Customer\n" +
                "        (Username, Password, Email, Phone, Address) VALUES (?,?,?,?,?)";
        if(verifyPassword()){
            db.submitSQL(SQL,getStrArray());
        }else{
            System.out.println("Sorry, that password was not correct, or something else failed.");
        }

    }
    private String[] getStrArray(){
        return new String[]{username, password, email, phone, address};
    }
}
