package com.revature.Users;

import com.revature.Accounts.BankAccount;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements UserService {
    Logger logger = LogManager.getLogger(Customer.class);

    public Customer() {
        table = "Users.Customer";
    }

    public Customer(int ID) {
        this.ID = ID;
        table = "Users.Customer";
    }

    @Override
    public List<BankAccount> getAccounts() {
        List<BankAccount> baList = new ArrayList<>();
        ArrayList<Integer> ownershipIDs = new ArrayList<>();
        try {


            String SQL = "SELECT * FROM Various.AccountOwners WHERE SecondaryAccount = ? OR PrimaryAccount = ?";
            ResultSet rs = db.getResult(SQL, "" + ID, "" + ID);

            while (rs.next()) {
                ownershipIDs.add(rs.getInt("OwnershipID"));
            }

            for (Integer ownerID :
                    ownershipIDs) {
                String accountSQL = "SELECT CheckingID FROM Accounts.Checking WHERE OwnersID = ? AND Banker != -1";
                ResultSet qs = db.getResult(accountSQL, "" + ownerID);
                while (qs.next()) {
                    baList.add(new BankAccount(qs.getInt("CheckingID")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baList;
    }

}
