package com.revature.Users;

import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.PasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class User {
    DatabaseConnection db = DatabaseConnection.getConnection();
    Scanner in = new Scanner(System.in);
    int ID;
    String userName;
    String table;
    boolean isLoggedIn = false;

    public boolean logIn() {
        getUsername();
        checkUserExists();
        if (checkUserExists()) {
            if (checkPassword(getPassword())) {
                if (fetchIDFromDB()) {
                    System.out.println("You have successfully logged in.");
                    isLoggedIn = true;
                    return true;
                }
            }
        }
        System.out.println("Sorry, there was an error with your login request.");
        return false;
    }

    protected boolean checkUserExists() {
        String sqlCommand = String.format("SELECT Username FROM %s WHERE Username = ?;", table);
        ResultSet rs = db.getResult(sqlCommand, userName);
        try {
            rs.next();
            rs.getString("Username");
            return true;
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            return false;
        }
    }

    protected boolean fetchIDFromDB() {
        String sqlCommand = String.format("SELECT ID FROM %s Where Username = ?;", table);
        ResultSet rs = db.getResult(sqlCommand, userName);
        try {
            rs.next();
            ID = rs.getInt("ID");
            return true;
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            return false;
        }
    }

    protected String getPassword() {
        System.out.println("------------");
        System.out.println("What is your password?");
        System.out.println("------------");
        return in.nextLine();
    }

    protected void getUsername() {
        System.out.println("------------");
        System.out.println("What is your username?");
        userName = in.nextLine();
        System.out.println("-----------");

    }

    boolean checkPassword(String enteredPass) {
        String sqlCommand = String.format("SELECT Password FROM %s Where Username = ?;", table);
        ResultSet rs = db.getResult(sqlCommand, userName);
        try {
            rs.next();
            String passFromDB = rs.getString("Password");
            return new PasswordEncoder().confirmPass(enteredPass, passFromDB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public int getID() {
        return ID;
    }

    public User getUser() {
        {
            if (logIn()) {
                return this;
            } else {
                return null;
            }
        }
    }

}
