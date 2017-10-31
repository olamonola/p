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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "server")
public class Server implements Serializable {

    private static final long serialVersionUID = 5477489000366580369L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "server_id")
    private Integer serverId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;

    public Server() {
    }

    public Server(Integer serverId) {
        this.serverId = serverId;
    }

    public Server(Integer serverId, String name) {
        this.serverId = serverId;
        this.name = name;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serverId != null ? serverId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Server)) {
            return false;
        }
        Server other = (Server) object;
        if ((this.serverId == null && other.serverId != null) || (this.serverId != null && !this.serverId.equals(other.serverId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Server[ serverId=" + serverId + " ]";
    }
    
}
