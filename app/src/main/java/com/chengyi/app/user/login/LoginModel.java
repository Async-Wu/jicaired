package com.chengyi.app.user.login;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  LoginModel  {

    /**
     * balance : 56018.27994
     * clientUserSession : 1402857_5efb82dfc61592b4760026338c162d6f
     * score : 155510
     * capitalHandsel : 0.0
     */

    private int flag;
    private  String errorMessage;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private double balance;
    private String clientUserSession;
    private int score;
    private double capitalHandsel;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getClientUserSession() {
        return clientUserSession;
    }

    public void setClientUserSession(String clientUserSession) {
        this.clientUserSession = clientUserSession;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getCapitalHandsel() {
        return capitalHandsel;
    }

    public void setCapitalHandsel(double capitalHandsel) {
        this.capitalHandsel = capitalHandsel;
    }




}
