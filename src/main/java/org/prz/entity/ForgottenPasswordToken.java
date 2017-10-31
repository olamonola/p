/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "forgotten_password_token")
public class ForgottenPasswordToken implements Serializable {

    private static final long serialVersionUID = 6592093487018640649L;

    @Id
    @SequenceGenerator(name = "forgotten_password_token_forgotten_password_token_id_seq", sequenceName = "forgotten_password_token_forgotten_password_token_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forgotten_password_token_forgotten_password_token_id_seq")
    @Basic(optional = false)
    @Column(name = "forgotten_password_token_id")
    private Integer forgottenPasswordTokenId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne(optional = false)
    private Account accountId;

    public ForgottenPasswordToken() {
    }

    public ForgottenPasswordToken(Integer forgottenPasswordTokenId) {
        this.forgottenPasswordTokenId = forgottenPasswordTokenId;
    }

    public ForgottenPasswordToken(Integer forgottenPasswordTokenId, String token, Date expirationDate) {
        this.forgottenPasswordTokenId = forgottenPasswordTokenId;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public Integer getForgottenPasswordTokenId() {
        return forgottenPasswordTokenId;
    }

    public void setForgottenPasswordTokenId(Integer forgottenPasswordTokenId) {
        this.forgottenPasswordTokenId = forgottenPasswordTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forgottenPasswordTokenId != null ? forgottenPasswordTokenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForgottenPasswordToken)) {
            return false;
        }
        ForgottenPasswordToken other = (ForgottenPasswordToken) object;
        if ((this.forgottenPasswordTokenId == null && other.forgottenPasswordTokenId != null) || (this.forgottenPasswordTokenId != null && !this.forgottenPasswordTokenId.equals(other.forgottenPasswordTokenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.ForgottenPasswordToken[ forgottenPasswordTokenId=" + forgottenPasswordTokenId + " ]";
    }
    
}
