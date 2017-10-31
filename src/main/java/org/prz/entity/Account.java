/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = -2904089745503832704L;

    @Id
    @SequenceGenerator(name = "account_account_id_seq", sequenceName = "account_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_account_id_seq")
    @Basic(optional = false)
    @Column(name = "account_id")
    private Integer accountId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password")
    private String password;
    @Size(max = 2147483647)
    @Column(name = "password_hash_algorithm")
    private String passwordHashAlgorithm;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_confirmed")
    private boolean accountConfirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "verified")
    private boolean verified;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<AccountRole> accountRoleCollection;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    private UserProfile userProfile;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Status statusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<ForgottenPasswordToken> forgottenPasswordTokenCollection;

    public Account() {
    }

    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    public Account(Integer accountId, String login, String password, String email, boolean active, Date registrationTime, boolean accountConfirmed) {
        this.accountId = accountId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.active = active;
        this.registrationTime = registrationTime;
        this.accountConfirmed = accountConfirmed;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHashAlgorithm() {
        return passwordHashAlgorithm;
    }

    public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {
        this.passwordHashAlgorithm = passwordHashAlgorithm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public boolean getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    public Collection<AccountRole> getAccountRoleCollection() {
        return accountRoleCollection;
    }

    public void setAccountRoleCollection(Collection<AccountRole> accountRoleCollection) {
        this.accountRoleCollection = accountRoleCollection;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Account[ accountId=" + accountId + " ]";
    }

    public Collection<ForgottenPasswordToken> getForgottenPasswordTokenCollection() {
        return forgottenPasswordTokenCollection;
    }

    public void setForgottenPasswordTokenCollection(Collection<ForgottenPasswordToken> forgottenPasswordTokenCollection) {
        this.forgottenPasswordTokenCollection = forgottenPasswordTokenCollection;
    }

}
