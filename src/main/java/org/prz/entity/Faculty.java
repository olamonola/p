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
@Table(name = "faculty")
public class Faculty implements Serializable {

    private static final long serialVersionUID = 7229294792775159010L;

    @Id
    @SequenceGenerator(name = "faculty_faculty_id_seq", sequenceName = "faculty_faculty_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_faculty_id_seq")
    @Basic(optional = false)
    @Column(name = "faculty_id")
    private Integer facultyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyId")
    private Collection<Internship> internshipCollection;

    public Faculty() {
    }

    public Faculty(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Faculty(Integer facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
    }

    public Faculty(Integer facultyId, String name, boolean enabled) {
        this.facultyId = facultyId;
        this.name = name;
        this.enabled = enabled;
    }

    public Faculty(Integer facultyId, String name, boolean enabled, Collection<Internship> internshipCollection) {
        this.facultyId = facultyId;
        this.name = name;
        this.enabled = enabled;
        this.internshipCollection = internshipCollection;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (facultyId != null ? facultyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) object;
        if ((this.facultyId == null && other.facultyId != null) || (this.facultyId != null && !this.facultyId.equals(other.facultyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Faculty[ facultyId=" + facultyId + " ]";
    }
    
}
