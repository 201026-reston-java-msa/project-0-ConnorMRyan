package com.revature.Accounts;

import com.revature.Users.Customer;
import com.revature.Utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankAccount {
    int OwnersID;
    int PrimaryAccountID;
    int SecondaryAccountID;
    int Banker;
    int Balance;
    int RoutingNo;
    public BankAccount(int ID){
        try {
            String AccountSQL = "SELECT * FROM Accounts.Checking WHERE CheckingID = ?";
            DatabaseConnection db = DatabaseConnection.getConnection();
            ResultSet rs = db.getResult(AccountSQL, Integer.toString(ID));
            rs.next();
            Banker = rs.getInt("Banker");
            Balance = rs.getInt("Balance");
            RoutingNo = rs.getInt("RoutingNo");
            OwnersID = rs.getInt("OwnersID");
            rs.close();
            String OwnerSQL = "SELECT * FROM Various.AccountOwners WHERE OwnershipID = ?";
            ResultSet qs = db.getResult(OwnerSQL,Integer.toString(OwnersID));
            qs.next();
            PrimaryAccountID = qs.getInt("PrimaryAccount");
            SecondaryAccountID = qs.getInt("SecondaryAccount");
            qs.close();
        }catch (Exception e){

        }

    }

    public int getOwnersID() {
        return OwnersID;
    }

    public int getPrimaryAccountID() {
        return PrimaryAccountID;
    }

    public int getSecondaryAccountID() {
        return SecondaryAccountID;
    }

    public int getBanker() {
        return Banker;
    }

    public int getBalance() {
        return Balance;
    }

    public int getRoutingNo() {
        return RoutingNo;
    }
}
