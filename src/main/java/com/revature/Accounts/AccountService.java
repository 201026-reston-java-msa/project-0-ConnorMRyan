package com.revature.Accounts;

import java.sql.SQLException;

public interface AccountService {

    void withdraw(BankAccount ba ,int amount);

    void deposit(BankAccount ba ,int amount);

    void transfer(BankAccount transferFrom,BankAccount transferTo,int amount) throws SQLException;


}
