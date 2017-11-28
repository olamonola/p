/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.util.List;

/**
 *
 * @author Ola
 */
public class WykazSearch {
    private Integer internshipType;
    private Integer schoolYear;
    private Integer system;
    private List<String>specializations;
    private String teacher;

    public WykazSearch() {
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }
    

    public Integer getInternshipType() {
        return internshipType;
    }

    public void setInternshipType(Integer internshipType) {
        this.internshipType = internshipType;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }
    
    
}
