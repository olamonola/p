/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "secret_code")
public class SecretCode implements Serializable {

    private static final long serialVersionUID = 2801638653649905963L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "secret_code_id")
    private Integer secretCodeId;
    @Column(name = "code")
    private String code;

    public SecretCode() {
    }

    public SecretCode(Integer secretCodeId) {
        this.secretCodeId = secretCodeId;
    }

    public Integer getSecretCodeId() {
        return secretCodeId;
    }

    public void setSecretCodeId(Integer secretCodeId) {
        this.secretCodeId = secretCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secretCodeId != null ? secretCodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecretCode)) {
            return false;
        }
        SecretCode other = (SecretCode) object;
        if ((this.secretCodeId == null && other.secretCodeId != null) || (this.secretCodeId != null && !this.secretCodeId.equals(other.secretCodeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.SecretCode[ secretCodeId=" + secretCodeId + " ]";
    }
    
}
