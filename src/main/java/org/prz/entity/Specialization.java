/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "specialization")
public class Specialization implements Serializable {

    private static final long serialVersionUID = -8682135720436017398L;
    
    @Id
    @SequenceGenerator(name = "specialization_specialization_id_seq", sequenceName = "specialization_specialization_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialization_specialization_id_seq")
    @Basic(optional = false)
    @Column(name = "specialization_id")
    private Integer specializationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specializationId")
    private Collection<Internship> internshipCollection;

    public Specialization() {
    }

    public Specialization(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public Specialization(Integer specializationId, String name) {
        this.specializationId = specializationId;
        this.name = name;
    }

    public Specialization(Integer specializationId, String name, boolean enabled) {
        this.specializationId = specializationId;
        this.name = name;
        this.enabled = enabled;
    }

    public Specialization(Integer specializationId, String name, boolean enabled, Collection<Internship> internshipCollection) {
        this.specializationId = specializationId;
        this.name = name;
        this.enabled = enabled;
        this.internshipCollection = internshipCollection;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
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

    public Collection<Internship> getInternshipCollection() {
        return internshipCollection;
    }

    public void setInternshipCollection(Collection<Internship> internshipCollection) {
        this.internshipCollection = internshipCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specializationId != null ? specializationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialization)) {
            return false;
        }
        Specialization other = (Specialization) object;
        if ((this.specializationId == null && other.specializationId != null) || (this.specializationId != null && !this.specializationId.equals(other.specializationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Specialization[ specializationId=" + specializationId + " ]";
    }

}
