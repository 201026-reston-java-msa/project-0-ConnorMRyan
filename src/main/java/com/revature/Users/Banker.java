package com.revature.Users;

import com.revature.Accounts.BankAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Banker extends User implements UserService {
  public Banker() {
    table = "Users.Banker";
  }

  public void verifyAccounts(List<BankAccount> accountList) {
    if (accountList.size() == 0) {
      System.out.println("There are no accounts to verify.");
    } else {
      for (BankAccount account : accountList) {
        account.getAccountDetails();
        if (approveAccount()) {
          String SQL = "UPDATE Accounts.Checking SET Banker = ? WHERE CheckingID = ?";
          db.submitSQL(SQL, "" + ID, "" + account.getAccountID());
        }
      }
    }
  }

  public List<BankAccount> getAccountsToVerify() {
    ArrayList accountsList = new ArrayList<BankAccount>();
    String SQL = "SELECT * FROM Accounts.Checking where Banker = -1;";
    ResultSet rs = db.getResult(SQL);
    try {
      while (rs.next()) {
        BankAccount ba = new BankAccount(rs.getInt("CheckingID"));
        accountsList.add(ba);
      }
    } catch (SQLException e) {
      System.out.println("Error");
    }
    return accountsList;
  }

  public List<BankAccount> getManagedAccounts() {
    ArrayList accountsList = new ArrayList<BankAccount>();
    String SQL = "SELECT * FROM Accounts.Checking where Banker = ?;";
    ResultSet rs = db.getResult(SQL, "" + ID);
    try {
      while (rs.next()) {
        BankAccount ba = new BankAccount(rs.getInt("CheckingID"));
        accountsList.add(ba);
      }
    } catch (SQLException e) {
      System.out.println("Error");
    }
    return accountsList;
  }

  boolean approveAccount() {
    System.out.println("Would you like to approve the account? [Y/n]");
    return in.nextLine().toUpperCase().charAt(0) == 'Y';
  }

  @Override
  public List<BankAccount> getAccounts() {
    return null;
  }
}
