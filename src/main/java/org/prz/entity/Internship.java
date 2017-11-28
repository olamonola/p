/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Ola
 */
@Entity
@Table(name = "internship")
public class Internship implements Serializable {

    private static final long serialVersionUID = 4342894542234548179L;

    @Id
    @SequenceGenerator(name = "faculty_faculty_id_seq", sequenceName = "faculty_faculty_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_faculty_id_seq")
    @Basic(optional = false)
    @Column(name = "internship_id")
    private Integer internshipId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private Integer year;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmation")
    private boolean confirmation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "exemption")
    private boolean exemption;
    @Column(name = "dismissed")
    private Boolean dismissed;
    @Size(max = 2147483647)
    @Column(name = "comments")
    private String comments;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "grade")
    private BigDecimal grade;
    @Size(max = 2147483647)
    @Column(name = "company_owner_first_name")
    private String companyOwnerFirstName;
    @Size(max = 2147483647)
    @Column(name = "company_owner_last_name")
    private String companyOwnerLastName;
    @Size(max = 2147483647)
    @Column(name = "company_owner_position")
    private String companyOwnerPosition;
    @Size(max = 2147483647)
    @Column(name = "company_internship_administrator_first_name")
    private String companyInternshipAdministratorFirstName;
    @Size(max = 2147483647)
    @Column(name = "company_internship_administrator_last_name")
    private String companyInternshipAdministratorLastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "archived")
    private boolean archived;
    @Column(name = "company_phone")
    private String companyPhone;
    @Column(name = "company_street")
    private String companyStreet;
    @Column(name = "company_street_no")
    private String companyStreetNo;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_city")
    private String companyCity;
    @Column(name = "created")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "last_edited")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date lastEdited;
    @Column(name = "company_zip")
    private String companyZip;
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @ManyToOne(optional = false)
    private Company companyId;
    @JoinColumn(name = "faculty_id", referencedColumnName = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;
    @JoinColumn(name = "internship_type_id", referencedColumnName = "internship_type_id")
    @ManyToOne(optional = false)
    private InternshipType internshipTypeId;
    @JoinColumn(name = "specialization_id", referencedColumnName = "specialization_id")
    @ManyToOne(optional = false)
    private Specialization specializationId;
    @JoinColumn(name = "system_system_id", referencedColumnName = "system_id")
    @ManyToOne(optional = false)
    private System systemSystemId;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne(optional = false)
    private UserProfile accountId;

    public Internship() {
    }

    public Internship(Integer internshipId) {
        this.internshipId = internshipId;
    }

    public Internship(Integer internshipId, int year, boolean confirmation, boolean exemption, boolean archived) {
        this.internshipId = internshipId;
        this.year = year;
        this.confirmation = confirmation;
        this.exemption = exemption;
        this.archived = archived;
    }

    public Internship(Company c) {
        this.companyCity = c.getCity();
        this.companyName = c.getName();
        this.companyPhone = c.getPhone();
        this.companyStreet = c.getStreet();
        this.companyStreetNo = c.getStreetNo();
        this.companyZip = c.getZip();
    }

    public Integer getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Integer internshipId) {
        this.internshipId = internshipId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public boolean getExemption() {
        return exemption;
    }

    public void setExemption(boolean exemption) {
        this.exemption = exemption;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public String getCompanyOwnerFirstName() {
        return companyOwnerFirstName;
    }

    public void setCompanyOwnerFirstName(String companyOwnerFirstName) {
        this.companyOwnerFirstName = companyOwnerFirstName;
    }

    public String getCompanyOwnerLastName() {
        return companyOwnerLastName;
    }

    public void setCompanyOwnerLastName(String companyOwnerLastName) {
        this.companyOwnerLastName = companyOwnerLastName;
    }

    public String getCompanyOwnerPosition() {
        return companyOwnerPosition;
    }

    public void setCompanyOwnerPosition(String companyOwnerPosition) {
        this.companyOwnerPosition = companyOwnerPosition;
    }

    public String getCompanyInternshipAdministratorFirstName() {
        return companyInternshipAdministratorFirstName;
    }

    public void setCompanyInternshipAdministratorFirstName(String companyInternshipAdministratorFirstName) {
        this.companyInternshipAdministratorFirstName = companyInternshipAdministratorFirstName;
    }

    public String getCompanyInternshipAdministratorLastName() {
        return companyInternshipAdministratorLastName;
    }

    public void setCompanyInternshipAdministratorLastName(String companyInternshipAdministratorLastName) {
        this.companyInternshipAdministratorLastName = companyInternshipAdministratorLastName;
    }

    public boolean getArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyStreetNo() {
        return companyStreetNo;
    }

    public void setCompanyStreetNo(String companyStreetNo) {
        this.companyStreetNo = companyStreetNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyZip() {
        return companyZip;
    }

    public void setCompanyZip(String companyZip) {
        this.companyZip = companyZip;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Faculty facultyId) {
        this.facultyId = facultyId;
    }

    public InternshipType getInternshipTypeId() {
        return internshipTypeId;
    }

    public void setInternshipTypeId(InternshipType internshipTypeId) {
        this.internshipTypeId = internshipTypeId;
    }

    public Specialization getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Specialization specializationId) {
        this.specializationId = specializationId;
    }

    public System getSystemSystemId() {
        return systemSystemId;
    }

    public void setSystemSystemId(System systemSystemId) {
        this.systemSystemId = systemSystemId;
    }

    public UserProfile getAccountId() {
        return accountId;
    }

    public void setAccountId(UserProfile accountId) {
        this.accountId = accountId;
    }

    public Boolean getDismissed() {
        return dismissed;
    }

    public void setDismissed(Boolean dismissed) {
        this.dismissed = dismissed;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (internshipId != null ? internshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Internship)) {
            return false;
        }
        Internship other = (Internship) object;
        if ((this.internshipId == null && other.internshipId != null) || (this.internshipId != null && !this.internshipId.equals(other.internshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Internship[ internshipId=" + internshipId + " ]";
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public boolean isExemption() {
        return exemption;
    }

    public boolean isArchived() {
        return archived;
    }

}
