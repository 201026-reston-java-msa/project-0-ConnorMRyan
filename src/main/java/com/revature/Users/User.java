package com.revature.Users;

public abstract class User {
    private int ID;
    private String userName;

    abstract boolean logIn();

    abstract boolean checkPassword(String passToCheck);
}
