package com.revature.Users;

import com.revature.Accounts.BankAccount;
import com.revature.Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Banker {
    DatabaseConnection db = new DatabaseConnection();
    public Banker(){

    }

    public List<BankAccount> getAccountsToVerify(){
        ArrayList accountsList = new ArrayList<BankAccount>();
        String SQL = "SELECT * FROM Accounts.Checking where Banker IS NULL;";
        ResultSet rs = db.getResult(SQL);
        try {
            while(rs.next()){
                BankAccount ba = new BankAccount(rs.getInt("CheckingID"));
                accountsList.add(ba);

            }
        }catch (SQLException e) {
            System.out.println("Error");
        }
        return accountsList;
    }
    void getAccountDetails(BankAccount ba){
        System.out.println("---------------------");
        System.out.println("Primary Owner");

    }
}
