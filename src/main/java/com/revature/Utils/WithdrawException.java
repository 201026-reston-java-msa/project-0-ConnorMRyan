package com.revature.Utils;

public class WithdrawException extends Exception {
    public WithdrawException(){
        super("Sorry, there was an error withdrawing that amount, may have exceeded limit.");
    }
}
