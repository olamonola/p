/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

/**
 *
 * @author Ola
 */
public class SprawozdanieSearch {
    private Integer facultyId; //kierunek studiów
    private Integer specializationId;
    private Integer systemId;
    private Integer year; //rok studiów - 1, 2, 3, 4
    private Integer internshipTypeId;
    private String teacher; //opiekun praktyki do pdf
    private String comments; //uwagi i wnioski do pdf
    private Integer schoolYear; //rok akademicki. Jeśli jest 2016, to oznacza 2016/2017

    public SprawozdanieSearch() {
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getInternshipTypeId() {
        return internshipTypeId;
    }

    public void setInternshipTypeId(Integer internshipTypeId) {
        this.internshipTypeId = internshipTypeId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
}
