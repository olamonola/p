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
public class SearchStudents {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String indexNo;
    private Boolean accountNotConfirmed;
    private Boolean accountConfirmed;
    private Boolean studentRoleExpired;
    private Boolean studentsWithoutRole;

    public SearchStudents() {
    }

    public Boolean getStudentsWithoutRole() {
        return studentsWithoutRole;
    }

    public void setStudentsWithoutRole(Boolean studentsWithoutRole) {
        this.studentsWithoutRole = studentsWithoutRole;
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

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Boolean getAccountNotConfirmed() {
        return accountNotConfirmed;
    }

    public void setAccountNotConfirmed(Boolean accountNotConfirmed) {
        this.accountNotConfirmed = accountNotConfirmed;
    }


    public Boolean getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(Boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    public Boolean getStudentRoleExpired() {
        return studentRoleExpired;
    }

    public void setStudentRoleExpired(Boolean studentRoleExpired) {
        this.studentRoleExpired = studentRoleExpired;
    }

}
