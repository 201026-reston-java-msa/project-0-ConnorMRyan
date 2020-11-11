package com.revature.Users;

import com.revature.Accounts.BankAccount;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements UserService {
    public Customer(){
        table = "Users.Customer";
    }
    @Override
    public List<BankAccount> getAccounts(){
        try {
            List<BankAccount> baList = new ArrayList<>();
            ArrayList<Integer> ownershipIDs = new ArrayList<>();
            String SQL = "SELECT * FROM Various.AccountOwners WHERE SecondaryAccount = ? OR PrimaryAccount = ?";
            ResultSet rs = db.getResult(SQL, "" + ID, "" + ID);

            while (rs.next()) {
                ownershipIDs.add(rs.getInt("OwnershipID"));
            }

            for (Integer inty :
                    ownershipIDs) {
                String accountSQL = "SELECT CheckingID FROM Accounts.Checking WHERE OwnersID = ? AND Banker IS NOT NULL";
                ResultSet qs = db.getResult(accountSQL, "" + inty);
                while (qs.next()) {
                    baList.add(new BankAccount(qs.getInt("CheckingID")));
                }
                return baList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
