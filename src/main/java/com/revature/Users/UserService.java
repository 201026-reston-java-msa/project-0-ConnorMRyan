package com.revature.Users;

import com.revature.Accounts.BankAccount;

import java.util.List;

public interface UserService {

    List<BankAccount> getAccounts();

    default void displayAccounts(List<BankAccount> accountList) {
        int i = 1;
        for (BankAccount account :
                accountList) {
            System.out.println("Account Number : " + i);
            account.getAccountDetails();
            i++;
        }
    }


}
