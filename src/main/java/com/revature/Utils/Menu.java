package com.revature.Utils;

import com.revature.Users.Customer;

import java.sql.Savepoint;

public class Menu {
    void getAccountType(){
        System.out.println("What type of account are you trying to log in to?");
        System.out.println("1: Personal Account ");
        System.out.println("2: Bank Employee Account");
        System.out.println("3: Quit");

    }

    void personalAccount(){
        Customer customer = new Customer();
        if(customer.logIn()){
            customer.getAccounts();
        }
    }

    void printPersonalMenu(){
        System.out.println("What would you like to do?");
        System.out.println("1: Withdraw");
        System.out.println("2: Deposit");
        System.out.println("3: Transfer");
    }

    void quit(){
        System.out.println("Thank you for using ConnorBank, have a wonderful day");
    }
}
