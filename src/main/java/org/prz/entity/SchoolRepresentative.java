package org.prz.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "school_representative")
public class SchoolRepresentative implements Serializable {

    private static final long serialVersionUID = -7618559610481657464L;

    @Id
    @SequenceGenerator(name = "school_representative_school_representative_id_seq", sequenceName = "school_representative_school_representative_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_representative_school_representative_id_seq")
    @Basic(optional = false)
    @Column(name = "school_representative_id")
    private Integer schoolRepresentativeId;
    @Column(name = "name")
    private String name;

    public SchoolRepresentative() {
    }

    public SchoolRepresentative(Integer schoolRepresentativeId) {
        this.schoolRepresentativeId = schoolRepresentativeId;
    }

    public Integer getSchoolRepresentativeId() {
        return schoolRepresentativeId;
    }

    public void setSchoolRepresentativeId(Integer schoolRepresentativeId) {
        this.schoolRepresentativeId = schoolRepresentativeId;
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
        hash += (schoolRepresentativeId != null ? schoolRepresentativeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolRepresentative)) {
            return false;
        }
        SchoolRepresentative other = (SchoolRepresentative) object;
        if ((this.schoolRepresentativeId == null && other.schoolRepresentativeId != null) || (this.schoolRepresentativeId != null && !this.schoolRepresentativeId.equals(other.schoolRepresentativeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.SchoolRepresentative[ schoolRepresentativeId=" + schoolRepresentativeId + " ]";
    }
    
}
