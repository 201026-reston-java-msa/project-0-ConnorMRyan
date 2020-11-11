package com.revature.Accounts;

import com.revature.Utils.Exceptions.BalanceException;
import com.revature.Utils.Exceptions.WithdrawException;

import java.sql.SQLException;

public interface AccountService {

    int getBalance(BankAccount ba) throws BalanceException;

    boolean withdraw(BankAccount ba, int amount) throws WithdrawException;

    boolean deposit(BankAccount ba, int amount);

    boolean transfer(BankAccount transferFrom, BankAccount transferTo, int amount) throws SQLException, WithdrawException;


}
