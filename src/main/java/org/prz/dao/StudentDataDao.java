/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import org.prz.entity.UserProfile;

/**
 *
 * @author Ola
 */
public class StudentDataDao {

    private Integer accountId;
    private String firstName;
    private String lastName;
    private String indexNo;

    public StudentDataDao() {
    }

    public StudentDataDao(UserProfile u) {
        if (u != null) {
            this.accountId = u.getAccountId();
            this.firstName = u.getFirstName();
            this.indexNo = u.getIndexNo();
            this.lastName = u.getLastName();
        }
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

}
