/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

/**
 *
 * @author Ola Klasa z której dziedziczy np Grade.java, InternshipConfirmation,
 * InternshipArchived i inne klasy dao służące do operacji na dzienniku opiekuna
 * praktyk studenckich. Pola to parametry z linku oraz id danej praktyki
 */
public class CurrentPage {

    private Integer internshipId;

    private Integer year;

    private Integer schoolyear;

    private Integer specId;

    private Integer pageNumber;

    public CurrentPage() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Integer schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Integer internshipId) {
        this.internshipId = internshipId;
    }

}
