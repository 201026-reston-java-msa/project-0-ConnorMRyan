package com.revature.Utils;

import com.revature.Accounts.AccountService;

public class AccountException extends Exception {
    AccountException(){
        super("Sorry, that account does not exist.");
    }
}
