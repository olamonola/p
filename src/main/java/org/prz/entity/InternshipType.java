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
@Table(name = "internship_type")
public class InternshipType implements Serializable {

    private static final long serialVersionUID = -6723480754598995L;

    @Id
    @SequenceGenerator(name = "internship_type_internship_type_id_seq", sequenceName = "internship_type_internship_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_type_internship_type_id_seq")
    @Basic(optional = false)
    @Column(name = "internship_type_id")
    private Integer internshipTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "type")
    private String type;
    @Column(name ="duration")
    private String duration;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipTypeId")
    private Collection<Internship> internshipCollection;

    public InternshipType() {
    }

    public InternshipType(Integer internshipTypeId) {
        this.internshipTypeId = internshipTypeId;
    }

    public InternshipType(Integer internshipTypeId, String type) {
        this.internshipTypeId = internshipTypeId;
        this.type = type;
    }

    public InternshipType(Integer internshipTypeId, String type, boolean enabled) {
        this.internshipTypeId = internshipTypeId;
        this.type = type;
        this.enabled = enabled;
    }

    public InternshipType(Integer internshipTypeId, String type, boolean enabled, Collection<Internship> internshipCollection) {
        this.internshipTypeId = internshipTypeId;
        this.type = type;
        this.enabled = enabled;
        this.internshipCollection = internshipCollection;
    }

    public Integer getInternshipTypeId() {
        return internshipTypeId;
    }

    public void setInternshipTypeId(Integer internshipTypeId) {
        this.internshipTypeId = internshipTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (internshipTypeId != null ? internshipTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InternshipType)) {
            return false;
        }
        InternshipType other = (InternshipType) object;
        if ((this.internshipTypeId == null && other.internshipTypeId != null) || (this.internshipTypeId != null && !this.internshipTypeId.equals(other.internshipTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.InternshipType[ internshipTypeId=" + internshipTypeId + " ]";
    }
    
}
