package com.revature.Utils.Exceptions;

public class AccountException extends Exception {
    AccountException() {
        super("Sorry, that account does not exist.");
    }
}
