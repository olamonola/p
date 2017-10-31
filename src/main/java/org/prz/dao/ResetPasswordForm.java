/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

/**
 *
 * @author Ola
 */
public class ResetPasswordForm {
    private String password;
    private String repeatPassword;
    private Integer accountId;
    private String token;

    public ResetPasswordForm() {
    }

    public ResetPasswordForm(Integer accountId) {
        this.accountId = accountId;
    }

    public ResetPasswordForm(Integer accountId, String token) {
        this.accountId = accountId;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
