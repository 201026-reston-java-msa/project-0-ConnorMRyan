package com.revature.Utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.Inet4Address;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    private static DatabaseConnection db = null;
    private static Connection connection;
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private DatabaseConnection() {
        String URL = System.getenv("AZ_DATABASE_NAME");
        String USER = System.getenv("AZ_SQL_SERVER_USERNAME");
        String PASS = System.getenv("AZ_SQL_SERVER_PASSWORD");
        int PORT = 1433;
        String connectionUrl = "jdbc:sqlserver://" + URL + ":" + PORT + ";database=bankstuff;user=" + USER + ";password=" + PASS + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
        System.out.print("Connecting to SQL Server ... ");
        try {
            connection = DriverManager.getConnection(connectionUrl);

            System.out.println("Done.");

        } catch (Exception e) {
            System.err.println("Could not connect");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getConnection(){
        if(db == null){
            db = new DatabaseConnection();
            logger.trace("A new database connection was created ");
        }
        return db;
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
        db = null;
        try {
            connection.close();

        } catch (SQLException e) {
            System.out.println("The connection could not be closed");
            e.printStackTrace();
        }
    }

    public static Savepoint setSavePoint(String savepoint) throws SQLException {
        connection.setAutoCommit(false);
        Savepoint s1 = connection.setSavepoint("savepoint");
        connection.setAutoCommit(true);
    return s1;
    }

    public static void rollback(Savepoint savepoint) throws SQLException{
        connection.setAutoCommit(false);
        connection.rollback(savepoint);
        connection.setAutoCommit(true);
    }
}
