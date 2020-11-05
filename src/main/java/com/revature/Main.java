package com.revature;


import com.revature.Accounts.BankAccount;
import com.revature.Users.Banker;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //CreateCustomer testUser = new CreateCustomer();
        //testUser.deployToDB();
        //EditCustomer TestEdit = new EditCustomer(new Customer());

        Banker banker = new Banker();
        for (BankAccount ba : banker.getAccountsToVerify()) {
            System.out.println(ba.getBalance());
        }


    }
}
