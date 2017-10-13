/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "system") 
public class System implements Serializable {

    private static final long serialVersionUID = -4904221266970592117L;

    @Id
    @SequenceGenerator(name="system_system_id_seq", sequenceName="system_system_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="system_system_id_seq")
    @Column(name = "system_id")
    private Integer systemId;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemSystemId")
    private Collection<Internship> internshipCollection;

    public System() {
    }

    public System(Integer systemId) {
        this.systemId = systemId;
    }

    public System(Integer systemId, String name) {
        this.systemId = systemId;
        this.name = name;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public Collection<Internship> getInternshipCollection() {
        return internshipCollection;
    }

    public void setInternshipCollection(Collection<Internship> internshipCollection) {
        this.internshipCollection = internshipCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemId != null ? systemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof System)) {
            return false;
        }
        System other = (System) object;
        if ((this.systemId == null && other.systemId != null) || (this.systemId != null && !this.systemId.equals(other.systemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.System[ systemId=" + systemId + " ]";
    }
    
}
