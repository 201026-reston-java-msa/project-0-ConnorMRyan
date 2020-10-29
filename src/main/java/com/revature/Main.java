package com.revature;


import java.beans.Encoder;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CreateCustomer connor = new CreateCustomer();
        connor.deployToDB();
    }
}
