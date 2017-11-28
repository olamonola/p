/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.math.BigDecimal;

/**
 *
 * @author Ola
 */
public class Grade extends CurrentPage{

    private BigDecimal grade;

    public Grade() {
    }

    public Grade(BigDecimal grade) {
        this.grade = grade;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

}
