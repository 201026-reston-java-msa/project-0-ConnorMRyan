package com.revature.Users;

import com.revature.Accounts.BankAccount;

import java.util.List;

public interface UserService {

    List<BankAccount> getAccounts();

    default void displayAccounts( List<BankAccount> accountList){
        for (BankAccount account:
                accountList) {
            account.getAccountDetails();
        }
    }


}
