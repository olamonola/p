/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "student_view")
public class StudentView implements Serializable {

    private static final long serialVersionUID = 5672799145032251063L;

    
    @Column(name = "role_id")
    private Integer roleId;
    @Id
    @Column(name = "account_id")
    private Integer accountId;
    @Size(max = 2147483647)
    @Column(name = "login")
    private String login;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;
    @Column(name = "account_confirmed")
    private Boolean accountConfirmed;
    @Column(name = "verified")
    private Boolean verified;
    @Column(name = "account_role_id")
    private Integer accountRoleId;
    @Column(name = "role_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date roleStartTime;
    @Column(name = "role_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date roleEndTime;
    @Size(max = 2147483647)
    @Column(name = "index_no")
    private String indexNo;
    @Size(max = 2147483647)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 2147483647)
    @Column(name = "last_name")
    private String lastName;

    public StudentView() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
