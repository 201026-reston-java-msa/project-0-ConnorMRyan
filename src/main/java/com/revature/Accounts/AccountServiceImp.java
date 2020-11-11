package com.revature.Accounts;

import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.Exceptions.BalanceException;
import com.revature.Utils.Exceptions.WithdrawException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

public class AccountServiceImp implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);


    @Override
    public int getBalance(BankAccount ba) throws BalanceException {
        try {
            String SQLinitAmt = "SELECT Balance FROM Accounts.Checking WHERE CheckingID = ?";
            ResultSet rs = DatabaseConnection.getConnection().getResult(SQLinitAmt, "" + ba.accountID);
            rs.next();
            logger.trace("Account " + ba.getAccountID() + " had it's balance checked.");
            return rs.getInt("Balance");
        } catch (SQLException e) {
            throw new BalanceException();
        }
    }

    @Override
    public boolean withdraw(BankAccount ba, int amount) {
        try {
            int initialAmount = getBalance(ba);
            if (initialAmount - amount >= 0) {
                String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ? WHERE CheckingID = ?";
                int newAmount = initialAmount - amount;
                DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, "" + newAmount, "" + ba.accountID);
                logger.trace("Account " + ba.getAccountID() + " had " + amount + " withdrawn");

                return true;
            } else {
                throw new WithdrawException();
            }

        } catch (Exception e) {
            logger.trace(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deposit(BankAccount ba, int amount) {
        try {
            int initialAmount = getBalance(ba);
            String SQLupdateAmt = "UPDATE Accounts.Checking SET Balance = ? WHERE CheckingID = ?";
            int newAmount = initialAmount + amount;
            DatabaseConnection.getConnection().submitSQL(SQLupdateAmt, "" + newAmount, "" + ba.getAccountID());
            logger.trace("Account " + ba.getAccountID() + " had " + amount + " deposited");
            return true;

        } catch (Exception e) {
            logger.trace(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean transfer(BankAccount transferFrom, BankAccount transferTo, int amount) {
        try {
            boolean failed = false;
            Savepoint savepoint = DatabaseConnection.setSavePoint("savePoint");
            if (withdraw(transferFrom, amount)) {
                if (deposit(transferTo, amount)) {
                    return true;
                } else {
                    failed = true;
                }
            } else {
                failed = true;
            }
            if (failed) {
                try {
                    DatabaseConnection.rollback(savepoint);
                } catch (SQLException f) {

                    logger.trace(f.getMessage());
                }
            }
        } catch (SQLException e) {
            logger.trace(e.getMessage());
        }
        return false;
    }
}
