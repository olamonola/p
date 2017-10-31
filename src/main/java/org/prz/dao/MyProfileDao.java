/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import org.prz.entity.Account;

/**
 *
 * @author Ola
 */
public class MyProfileDao {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String indexNo;
    private Boolean accountConfirmed;

    public MyProfileDao() {
    }

    public MyProfileDao(Account a) {
        if (a.getUserProfile() != null) {
            this.firstName = a.getUserProfile().getFirstName();
            this.lastName = a.getUserProfile().getLastName();
            this.indexNo = a.getUserProfile().getIndexNo();
        }
        this.accountConfirmed = a.getAccountConfirmed();
        this.email = a.getEmail();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Boolean getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(Boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
