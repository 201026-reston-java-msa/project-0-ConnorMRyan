package com.revature;


import com.revature.Accounts.*;
import com.revature.Users.*;
import com.revature.Utils.Address;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CreateBankAccount.CreateAccount();
        new Banker().verifyAccounts(new Banker().getAccountsToVerify());
        Customer customer = new Customer();
        customer.logIn();
        customer.displayAccounts(customer.getAccounts());

    }
}
