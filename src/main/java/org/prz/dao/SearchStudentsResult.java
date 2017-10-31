/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.util.Date;

/**
 *
 * @author Ola
 */
public class SearchStudentsResult {

    private Integer accountId;
    private String login;
    private String email;
    private Boolean active;
    private Date registrationTime;
    private Boolean accountConfirmed;
    private Boolean verified;
    private Integer accountRoleId;
    private Date roleStartTime;
    private Date roleEndTime;
    private String indexNo;
    private String firstName;
    private String lastName;

    public SearchStudentsResult() {
    }

    public SearchStudentsResult(Integer accountId, String login, String email, Boolean active, Date registrationTime, Boolean accountConfirmed, Boolean verified, Integer accountRoleId, Date roleStartTime, Date roleEndTime, String indexNo, String firstName, String lastName) {
        this.accountId = accountId;
        this.login = login;
        this.email = email;
        this.active = active;
        this.registrationTime = registrationTime;
        this.accountConfirmed = accountConfirmed;
        this.verified = verified;
        this.accountRoleId = accountRoleId;
        this.roleStartTime = roleStartTime;
        this.roleEndTime = roleEndTime;
        this.indexNo = indexNo;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Boolean getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(Boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(Integer accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public Date getRoleStartTime() {
        return roleStartTime;
    }

    public void setRoleStartTime(Date roleStartTime) {
        this.roleStartTime = roleStartTime;
    }

    public Date getRoleEndTime() {
        return roleEndTime;
    }

    public void setRoleEndTime(Date roleEndTime) {
        this.roleEndTime = roleEndTime;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
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
    
    
}
