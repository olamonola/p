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
public class Wykaz {
    private String kierunek;
    private String system;
    private String nazwaPraktyki;
    private String czasPraktyki;
    private String opiekunUczelniany;
    private String lp;
    private String specializations;
    private String student;
    private String firma;
    private String opiekunFirmowy;
    private String termin;

    public Wykaz() {
    }

    public String getKierunek() {
        return kierunek;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getNazwaPraktyki() {
        return nazwaPraktyki;
    }

    public void setNazwaPraktyki(String nazwaPraktyki) {
        this.nazwaPraktyki = nazwaPraktyki;
    }

    public String getCzasPraktyki() {
        return czasPraktyki;
    }

    public void setCzasPraktyki(String czasPraktyki) {
        this.czasPraktyki = czasPraktyki;
    }

    public String getOpiekunUczelniany() {
        return opiekunUczelniany;
    }

    public void setOpiekunUczelniany(String opiekunUczelniany) {
        this.opiekunUczelniany = opiekunUczelniany;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getOpiekunFirmowy() {
        return opiekunFirmowy;
    }

    public void setOpiekunFirmowy(String opiekunFirmowy) {
        this.opiekunFirmowy = opiekunFirmowy;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
    
}
