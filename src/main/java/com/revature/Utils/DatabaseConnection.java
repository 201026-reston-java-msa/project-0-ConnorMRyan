package com.revature.Utils;

import java.sql.*;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        String URL = System.getenv("AZ_DATABASE_NAME");
        String USER = System.getenv("AZ_SQL_SERVER_USERNAME");
        String PASS = System.getenv("AZ_SQL_SERVER_PASSWORD");
        int PORT = 1433;
        String connectionUrl = "jdbc:sqlserver://" + URL + ":" + PORT + ";database=bankstuff;user=" + USER + ";password=" + PASS + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
        System.out.print("Connecting to SQL Server ... ");
        try {
            this.connection = DriverManager.getConnection(connectionUrl);

            System.out.println("Done.");

        } catch (Exception e) {
            System.err.println("Could not connect");
            e.printStackTrace();
        }
    }

    public Boolean submitSQL(String sqlString, String... Values) {
        try {
            connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            int val = 1;
            for (String str : Values) {
                preparedStatement.setString(val, str);
                val++;
            }
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getResult(String sqlString, String... Values) {
        try {
            connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            int val = 1;
            for (String str : Values) {
                preparedStatement.setString(val, str);
                val++;
            }
            return preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public int getCount(String tableName,String columnName, String value) throws SQLException {
        ResultSet rs = this.getResult(String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;",tableName,columnName),value);
        rs.next();
        return rs.getInt("");
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("The connection could not be closed");
            e.printStackTrace();
        }
    }


}
