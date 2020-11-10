package com.revature.Users;

import com.revature.Accounts.BankAccount;
import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.PasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User {
    Scanner in = new Scanner(System.in);
    DatabaseConnection db;
    private String username;
    private int ID;
    public Customer(){
        db = new DatabaseConnection();
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

    List<BankAccount> getAccounts(){
        try {
            List<BankAccount> baList = new ArrayList<>();
            ArrayList<Integer> ownershipIDs = new ArrayList<>();
            String SQL = "SELECT * FROM Various.AccountOwners WHERE SecondaryAccount = ? OR PrimaryAccount = ?";
            ResultSet rs = db.getResult(SQL, "" + ID, "" + ID);

            while (rs.next()) {
                ownershipIDs.add(rs.getInt("OwnershipID"));
            }

            for (Integer inty :
                    ownershipIDs) {
                String accountSQL = "SELECT CheckingID FROM Accounts.Checking WHERE OwnersID = ? AND Banker IS NOT NULL";
                ResultSet qs = db.getResult(accountSQL, "" + inty);
                while (qs.next()) {
                    baList.add(new BankAccount(qs.getInt("CheckingID")));
                }
                return baList;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
