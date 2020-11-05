package com.revature.Accounts;

import com.revature.Utils.DatabaseConnection;
import com.revature.Users.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateBankAccount {
    DatabaseConnection db = new DatabaseConnection();
    boolean jointAccount = false;
    Scanner in = new Scanner(System.in);
    int startingBalance = 0;
    Customer primaryCustomer;
    Customer secondaryCustomer;
    String[] ownerIDs;
    int OwnershipID;
    final int ROUTING_NO = 1231234567;

    public CreateBankAccount(){
        getOwners();
        getStartingBalance();
        deployToOwnershipDB();
        OwnershipID = getOwnershipID();
        deployToAccountDB();
    }

    private void printExitStatement(){
        System.out.println("Your account is pending while waiting for verification from a banker");
    }
    void getOwners(){
        System.out.println("Will you be applying with a joint owner? y/N");
        if(in.nextLine().charAt(0) == 'Y'){
            jointAccount = true;
        }
        System.out.println("Please log in");
        primaryCustomer = new Customer().getCustomer();

        if(jointAccount){
            secondaryCustomer = new Customer().getCustomer();
        }

        ownerIDs = new String[]{Integer.toString(primaryCustomer.getID()), (secondaryCustomer != null) ? Integer.toString(secondaryCustomer.getID()) : "-1"};
    }


    private void getStartingBalance(){
        System.out.println("How much would you like to make as an initial deposit");
        startingBalance = in.nextInt();
    }

    private void deployToOwnershipDB(){
        String SQL = "INSERT INTO Various.AccountOwners\n" +
                "(PrimaryAccount,SecondaryAccount) " +
                "VALUES (?,?)";
        db.submitSQL(SQL, ownerIDs[0],ownerIDs[1]);
    }
    private int getOwnershipID(){
        try {
            String SQL = "SELECT OwnershipID FROM Various.AccountOwners WHERE PrimaryAccount = ? AND SecondaryAccount = ?;";
            ResultSet rs = db.getResult(SQL, ownerIDs[0], ownerIDs[1]);
            rs.next();
            return rs.getInt("OwnershipID");
        }catch (SQLException e){
            return 0;
        }
    }

    private void deployToAccountDB(){
        String SQL = "Insert INTO Accounts.Checking"+
                "(OwnersID,Balance,RoutingNo) "+
                "VALUES (?,?,?)";
        db.submitSQL(SQL,""+OwnershipID,""+startingBalance,""+ROUTING_NO);


    }


}
