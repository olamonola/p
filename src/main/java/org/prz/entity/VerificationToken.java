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
import javax.persistence.FetchType;
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
@Table(name = "verification_token")
public class VerificationToken implements Serializable {

    private static final long serialVersionUID = -1910769679606617945L;
    
    @Id
    @SequenceGenerator(name="verification_token_verification_token_id_seq", sequenceName="verification_token_verification_token_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="verification_token_verification_token_id_seq")
    @Column(name = "verification_token_id")
    private Integer verificationTokenId;
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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Account accountId;

    public VerificationToken() {
    }

    public VerificationToken(Integer verificationTokenId) {
        this.verificationTokenId = verificationTokenId;
    }

    public VerificationToken(Integer verificationTokenId, String token, Date expirationDate) {
        this.verificationTokenId = verificationTokenId;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public Integer getVerificationTokenId() {
        return verificationTokenId;
    }

    public void setVerificationTokenId(Integer verificationTokenId) {
        this.verificationTokenId = verificationTokenId;
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
        hash += (verificationTokenId != null ? verificationTokenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificationToken)) {
            return false;
        }
        VerificationToken other = (VerificationToken) object;
        if ((this.verificationTokenId == null && other.verificationTokenId != null) || (this.verificationTokenId != null && !this.verificationTokenId.equals(other.verificationTokenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.VerificationToken[ verificationTokenId=" + verificationTokenId + " ]";
    }
    
}
