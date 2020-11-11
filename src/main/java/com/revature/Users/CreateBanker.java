package com.revature.Users;

import com.revature.Utils.Address;
import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.PasswordEncoder;

import java.util.Scanner;

public class CreateBanker {
    static DatabaseConnection db;
    static Scanner in = new Scanner(System.in);
    static String password;
    static String username;
    static double salary;
    static String name;
    static int address;
    static boolean isManager = false;

    static private void setName() {
        System.out.println("What is your name?" );
        name = in.nextLine();
    }

    static private void setPassword() {
        System.out.println("------------");
        System.out.println("What would you like your password to be?");
        password = new PasswordEncoder().hashPassword(in.nextLine());
    }

    static private void setAddress() {
        address = new Address().getID();
    }

    static private void setSalary() {
        System.out.println("What is the banker's salary?");
        salary = in.nextFloat();
        in.nextLine();
    }

    static private void setManager() {
        System.out.println("Is the user a manager?");
        isManager = in.nextLine().toUpperCase().charAt(0) == 'Y';
    }
    private static void setUsername() {
        if(isManager){
            username = "BankManager"+name;
        }else{
            username = "BankUser"+name;
        }
    }
    static private void deployToDB(){
        String SQL = "INSERT INTO Users.Banker (Username,Password,Salary,Name,Address,isManager)"+
                "VALUES (?,?,?,?,?,?)";
        if (verifyPassword()) {
            db.submitSQL(SQL, username, password, Double.toString(salary), name, ""+address,""+isManager);
        } else {
            System.out.println("Sorry, that password was not correct, or something else failed.");
        }
    }
    private static boolean verifyPassword() {
        System.out.println("-----------");
        System.out.println("Please confirm your password to complete the registration");
        return new PasswordEncoder().confirmPass(in.nextLine(), password);
    }

    private static void printUsername(){
        System.out.println("Your username is "+username);
    }


    public static void createBanker(){
        db = DatabaseConnection.getConnection();
        in = new Scanner(System.in);
        setName();
        setAddress();
        setManager();
        setSalary();
        setUsername();
        printUsername();
        setPassword();
        deployToDB();
    }
}
