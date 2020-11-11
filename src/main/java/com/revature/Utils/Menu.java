package com.revature.Utils;

import com.revature.Accounts.AccountServiceImp;
import com.revature.Accounts.BankAccount;
import com.revature.Users.Customer;

import java.util.Scanner;

public class Menu {
  Scanner in = new Scanner(System.in);

  void getAccountType() {
    System.out.println("What type of account are you trying to log in to?");
    System.out.println("1: Personal Account ");
    System.out.println("2: Bank Employee Account");
    System.out.println("3: Quit");
  }

  void personalAccount() {
    Customer customer = new Customer();
    if (customer.logIn()) {
      customer.getAccounts();
      boolean quit = false;
      while (true) {
        printPersonalMenu();
        switch (getChoice()) {
          case 1:
            withdraw(getAccount(customer), getAmount());
            break;
          case 2:
            deposit(getAccount(customer), getAmount());
            break;

          case 3:
            transfer(getAccount(customer), getAmount());
            break;
          case 4:
            customer.displayAccounts(customer.getAccounts());
            break;
          default:
            quit();
        }
      }
    }
  }

  void printPersonalMenu() {
    System.out.println("------------------");
    System.out.println("What would you like to do?");
    System.out.println("1: Withdraw");
    System.out.println("2: Deposit");
    System.out.println("3: Transfer");
    System.out.println("4: View Balances");
    System.out.println("5: Quit");
    System.out.println("------------------");
  }

  void withdraw(BankAccount ba, int amount) {
    AccountServiceImp asi = new AccountServiceImp();
    asi.withdraw(ba, amount);
  }

  void deposit(BankAccount ba, int amount) {
    AccountServiceImp asi = new AccountServiceImp();
    asi.deposit(ba, amount);
  }

  void transfer(BankAccount ba, int amount) {
    AccountServiceImp asi = new AccountServiceImp();
    asi.transfer(ba, getSecondaryAccount(), amount);
  }

  BankAccount getSecondaryAccount() {
    System.out.println("What is the ID of the account you'd like to transfer to?");
    return new BankAccount(in.nextInt());
  }

  int getAmount() {
    System.out.println("How much would you like to attempt?");
    int pending = in.nextInt();
    if (pending > 0) {
      return pending;
    } else {
      System.out.println("Sorry, you can't use an amount less than or equal to zero");
      return 0;
    }
  }

  BankAccount getAccount(Customer user) {
    user.displayAccounts(user.getAccounts());
    System.out.println("\n Which account would you like to use?");
    int choice = in.nextInt();
    return user.getAccounts().get(choice - 1);
  }

  int getChoice() {
    return in.nextInt();
  }

  void quit() {
    System.out.println("Thank you for using ConnorBank, have a wonderful day");
    System.exit(0);
  }
}
