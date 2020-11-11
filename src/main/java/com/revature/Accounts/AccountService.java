package com.revature.Accounts;

import com.revature.Utils.BalanceException;
import com.revature.Utils.WithdrawException;

import java.sql.SQLException;

public interface AccountService {

     int getBalance(BankAccount ba) throws BalanceException;

     void withdraw(BankAccount ba, int amount) throws WithdrawException;

     void deposit(BankAccount ba ,int amount);

    void transfer(BankAccount transferFrom,BankAccount transferTo,int amount) throws SQLException,WithdrawException;


}
