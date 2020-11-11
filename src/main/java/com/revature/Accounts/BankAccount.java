package com.revature.Accounts;

import com.revature.Utils.DatabaseConnection;

import java.sql.ResultSet;

public class BankAccount {
    int accountID;
    int ownersID;
    int primaryAccountID;
    int secondaryAccountID;
    int banker;
    int balance;
    int routingNo;
    public BankAccount(int ID){
        try {
            String AccountSQL = "SELECT * FROM Accounts.Checking WHERE CheckingID = ?";
            DatabaseConnection db = DatabaseConnection.getConnection();
            ResultSet rs = db.getResult(AccountSQL, Integer.toString(ID));
            rs.next();
            accountID = rs.getInt("CheckingID");
            banker = rs.getInt("Banker");
            balance = rs.getInt("Balance");
            routingNo = rs.getInt("RoutingNo");
            ownersID = rs.getInt("OwnersID");
            rs.close();
            String OwnerSQL = "SELECT * FROM Various.AccountOwners WHERE OwnershipID = ?";
            ResultSet qs = db.getResult(OwnerSQL,Integer.toString(ownersID));
            qs.next();
            primaryAccountID = qs.getInt("PrimaryAccount");
            secondaryAccountID = qs.getInt("SecondaryAccount");
            qs.close();
        }catch (Exception e){

        }

    }

    public int getOwnersID() {
        return ownersID;
    }

    public int getPrimaryAccountID() {
        return primaryAccountID;
    }

    public int getSecondaryAccountID() {
        return secondaryAccountID;
    }

    public int getBanker() {
        return banker;
    }

    public int getBalance() {
        return balance;
    }

    public int getRoutingNo() {
        return routingNo;
    }

    public int getAccountID() {
        return accountID;
    }

    public void getAccountDetails(){
        System.out.println("---------------------");
        System.out.println("Account ID : " + this.getAccountID());
        System.out.println("Primary Owner ID : " +this.getPrimaryAccountID());
        if(this.getSecondaryAccountID() != -1){
            System.out.println("Secondary Owner ID: " + this.getSecondaryAccountID());
        }
        System.out.printf("Balance : %.2f $\n", (double) this.getBalance()/100);
        if(banker == -1){
            System.out.println("This account has not yet been verified");
        }else{
            System.out.println("BankerID : " + banker);
        }
        System.out.println("---------------------\n");


    }
}
