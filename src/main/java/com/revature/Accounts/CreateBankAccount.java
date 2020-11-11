package com.revature.Accounts;

import com.revature.Utils.DatabaseConnection;
import com.revature.Users.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

 public class CreateBankAccount {
    static DatabaseConnection db =  DatabaseConnection.getConnection();
    static boolean jointAccount = false;
    static Scanner in = new Scanner(System.in);
    static int startingBalance = 0;
    static Customer primaryCustomer;
    static Customer secondaryCustomer = null;
    static String[] ownerIDs;
    static int OwnershipID;
    static final int ROUTING_NO = 1231234567;

    static public void CreateAccount(){
        getOwners();
        getStartingBalance();
        deployToOwnershipDB();
        OwnershipID = getOwnershipID();
        deployToAccountDB();
        printExitStatement();
    }

    static private void printExitStatement(){
        System.out.println("Your account is pending while waiting for verification from a banker");
    }
    static void getOwners(){
        System.out.println("Will you be applying with a joint owner? y/N");
        if(in.nextLine().charAt(0) == 'Y'){
            jointAccount = true;
        }
        System.out.println("Please log in");
        primaryCustomer = (Customer) new Customer().getUser();

        if(jointAccount){
            do {
                secondaryCustomer = (Customer) new Customer().getUser();
                if(secondaryCustomer.getID() == primaryCustomer.getID()){
                    System.out.println("Sorry, you seem to have logged in twice, please try again.");
                }
            }while (secondaryCustomer.getID() == primaryCustomer.getID());
        }

        ownerIDs = new String[]{Integer.toString(primaryCustomer.getID()), (secondaryCustomer != null) ? Integer.toString(secondaryCustomer.getID()) : "-1"};
    }


    static private void getStartingBalance(){
        System.out.println("How much would you like to make as an initial deposit");
        startingBalance = in.nextInt();
    }

    static private void deployToOwnershipDB(){
        String SQL = "INSERT INTO Various.AccountOwners\n" +
                "(PrimaryAccount,SecondaryAccount) " +
                "VALUES (?,?)";
        if(secondaryCustomer == null){
            ownerIDs[1] = "-1";
        }
        db.submitSQL(SQL, ownerIDs[0],ownerIDs[1]);
    }
    static private int getOwnershipID(){
        try {
            String SQL = "SELECT OwnershipID FROM Various.AccountOwners WHERE PrimaryAccount = ? AND SecondaryAccount = ?;";
            if(secondaryCustomer == null){
                ownerIDs[1] = "-1";
            }
            ResultSet rs = db.getResult(SQL, ownerIDs[0], ownerIDs[1]);
            rs.next();
            return rs.getInt("OwnershipID");
        }catch (SQLException e){
            return 0;
        }
    }

    static private void deployToAccountDB(){
        String SQL = "Insert INTO Accounts.Checking"+
                "(OwnersID,Balance,RoutingNo) "+
                "VALUES (?,?,?)";
        db.submitSQL(SQL,""+OwnershipID,""+startingBalance,""+ROUTING_NO);


    }


}
