package com.revature.Accounts;

import com.revature.Utils.BalanceException;
import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.WithdrawException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

public class AccountServiceImp implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);


    @Override
    public  int getBalance(BankAccount ba)throws BalanceException {
        try{
        String SQLinitAmt = "SELECT Balance FROM Accounts.Checking WHERE CheckingID = ?";
        ResultSet rs = DatabaseConnection.getConnection().getResult(SQLinitAmt, "" + ba.accountID);
        rs.next();
        return rs.getInt("Balance");
        }catch (SQLException e){
            throw new BalanceException();
        }
    }

    @Override
    public  void withdraw(BankAccount ba, int amount) {
        try {
            int initialAmount = getBalance(ba);

            if (initialAmount - amount >= 0) {
                String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ? WHERE CheckingID = ?";
                int newAmount = initialAmount - amount;
                DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, ""+newAmount,""+ba.accountID);
            }else{
                throw new WithdrawException();
            }

        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
    }

    @Override
    public  void deposit(BankAccount ba, int amount) {
        try {
            int initialAmount= getBalance(ba);

            String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ? WHERE CheckingID = ?";
            int newAmount = initialAmount + amount;
            DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, "" + newAmount,""+ba.getAccountID());

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
