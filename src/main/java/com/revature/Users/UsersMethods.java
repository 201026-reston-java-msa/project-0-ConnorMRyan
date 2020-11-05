package com.revature.Users;

import com.revature.Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UsersMethods {
    DatabaseConnection db = new DatabaseConnection();
     default int getCount(String tableName, String value) throws SQLException {
        System.out.println();
        ResultSet rs = db.getResult(String.format("SELECT COUNT(*) FROM Users.Customer WHERE %s = ?;",tableName),value);
        rs.next();
        return rs.getInt("");
    }
}
