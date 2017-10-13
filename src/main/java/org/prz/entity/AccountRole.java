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
@Table(name = "account_role")
public class AccountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "account_role_account_role_id_seq", sequenceName = "account_role_account_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_role_account_role_id_seq")
    @Basic(optional = false)
    @Column(name = "account_role_id")
    private Integer accountRoleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date roleStartTime;
    @Column(name = "role_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date roleEndTime;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Account accountId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role roleId;

    public AccountRole() {
    }

    public AccountRole(Integer accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public AccountRole(Integer accountRoleId, Date roleStartTime) {
        this.accountRoleId = accountRoleId;
        this.roleStartTime = roleStartTime;
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

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountRoleId != null ? accountRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountRole)) {
            return false;
        }
        AccountRole other = (AccountRole) object;
        if ((this.accountRoleId == null && other.accountRoleId != null) || (this.accountRoleId != null && !this.accountRoleId.equals(other.accountRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.AccountRole[ accountRoleId=" + accountRoleId + " ]";
    }
    
}
