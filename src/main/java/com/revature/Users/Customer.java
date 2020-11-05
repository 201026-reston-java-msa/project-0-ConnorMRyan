package com.revature.Users;

import com.revature.Utils.PasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer extends User implements UsersMethods {
    Scanner in = new Scanner(System.in);
    private String username;
    private int ID;
    public Customer(){

    }

    @Override
    boolean logIn() {
        try {
            getUsername();
            checkUserExists(username);
            if (checkUserExists(username)) {
                if (checkPassword(getPassword())) {
                    if (fetchIDFromDB()) {
                        System.out.println("You have successfully logged in.");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error");


        }
        return false;
    }

    public Customer getCustomer(){
        if(logIn()){
            return this;
        }else{

            return null;
        }
    }

    private void getUsername() {
        System.out.println("------------");
        System.out.println("What is your username?");
        username = in.nextLine();
        System.out.println("-----------");
    }

    private String getPassword() {
        System.out.println("------------");
        System.out.println("What is your password?");
        System.out.println("------------");
        return in.nextLine();
    }

    boolean checkPassword(String enteredPass) {
        String sqlCommand = "SELECT Password FROM Users.Customer Where Username = ?;";
        ResultSet rs = db.getResult(sqlCommand, username);
        try {
            rs.next();
            String passFromDB = rs.getString("Password");
            if (new PasswordEncoder().confirmPass(enteredPass, passFromDB)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    private boolean checkUserExists(String userToCheck) throws SQLException {
        String sqlCommand = "SELECT Username FROM Users.Customer WHERE Username = ?;";
        ResultSet rs = db.getResult(sqlCommand, userToCheck);
        try {
            rs.next();
            rs.getString("Username");
            return true;
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            return false;
        }
    }

    private boolean fetchIDFromDB() {
        String sqlCommand = "SELECT ID FROM Users.Customer Where Username = ?;";
        ResultSet rs = db.getResult(sqlCommand, username);
        try {
            rs.next();
            ID = rs.getInt("ID");
            return true;
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            return false;
        }
    }

    public int getID(){
        return this.ID;
    }
}
