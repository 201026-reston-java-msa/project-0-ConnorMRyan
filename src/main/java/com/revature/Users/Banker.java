package com.revature.Users;

import com.revature.Accounts.BankAccount;
import com.revature.Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banker {
    DatabaseConnection db = DatabaseConnection.getConnection();
    Scanner in = new Scanner(System.in);
    int ID;
    public Banker(){
    }

    public void verifyAccounts(List<BankAccount> accountList){
        for (BankAccount account:
             accountList) {
            getAccountDetails(account);
            if(approveAccount()){
                String SQL = "UPDATE Accounts.Checking SET Banker = ?";
                db.submitSQL(SQL,""+ID);
            }
        }
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
        System.out.println("Primary Owner ID : " +ba.getPrimaryAccountID());
        if(ba.getSecondaryAccountID() != -1){
            System.out.println("Secondary Owner ID: " + ba.getSecondaryAccountID());
        }
        System.out.println("Balance : " + ba.getBalance());
    }
    boolean approveAccount(){
        System.out.println("Would you like to approve the account? [Y/n]");
        return in.nextLine().toUpperCase().charAt(0) == 'Y';
    }
}
