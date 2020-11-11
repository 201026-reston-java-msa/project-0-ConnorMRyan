package com.revature.Accounts;

import com.revature.Utils.DatabaseConnection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

public class AccountServiceImp implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);


    @Override
    public void withdraw(BankAccount ba, int amount) {
        try {
            int initialAmount;
            String SQLinitAmt = "SELECT Balance FROM Accounts.Checking WHERE CheckingID = ?";
            ResultSet rs = DatabaseConnection.getConnection().getResult(SQLinitAmt, "" + ba.accountID);
            rs.next();
            initialAmount = rs.getInt("Balance");
            if (initialAmount - amount > 0) {
                String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ?";
                int newAmount = initialAmount - amount;
                DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, "" + newAmount);
            }

        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
    }

    @Override
    public void deposit(BankAccount ba, int amount) {
        try {
            int initialAmount;
            String SQLinitAmt = "SELECT Balance FROM Accounts.Checking WHERE CheckingID = ?";
            ResultSet rs = DatabaseConnection.getConnection().getResult(SQLinitAmt, "" + ba.accountID);
            rs.next();
            initialAmount = rs.getInt("Balance");

            String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ?";
            int newAmount = initialAmount + amount;
            DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, "" + newAmount);

        } catch (Exception e) {
            logger.trace(e.getMessage());
        }

    }

    @Override
    public void transfer(BankAccount transferFrom, BankAccount transferTo, int amount) {
        try {
            Savepoint savepoint = DatabaseConnection.setSavePoint("savePoint");
            deposit(transferTo, amount);
            withdraw(transferFrom, amount);
            try {
                DatabaseConnection.rollback(savepoint);
            } catch (SQLException f) {
                System.err.println("Something BAD went wrong.");
                logger.trace(f.getMessage());
            }
        }catch (SQLException e){
            System.out.println("Something BAD went wrong.");
            logger.trace(e.getMessage());
        }
    }
}
