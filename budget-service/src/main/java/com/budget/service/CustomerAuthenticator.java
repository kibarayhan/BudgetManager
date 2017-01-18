package com.budget.service;

/**
 * Created by a579295 on 18.09.2016.
 * Simple interface for authentication and authorization checks.
 */
public interface CustomerAuthenticator {
    boolean login(String username, String password);
    boolean register(String username, String password, String passwordAgain, String eMailAddress);
}
