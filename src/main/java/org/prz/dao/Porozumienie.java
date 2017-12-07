/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.prz.entity.Internship;

/**
 *
 * @author Ola
 */
public class Porozumienie {

    private String firma;
    private Date dataZawarciaUmowy;
    private String prorektor;
    private String adresFirmy;
    private String dyrektor;
    private String pustyDyrektor;
    private String student;
    private String rok;
    private String opiekunZakladowy;
    private String okresTrwania;

    public Porozumienie() {
        this.dataZawarciaUmowy = new Date();
    }

    public Porozumienie(Internship i) {
        String adres = new String();
        /*jeśli jest ulica*/
        if (i.getCompanyStreet() != null && !i.getCompanyStreet().isEmpty()) {
            this.adresFirmy = i.getCompanyStreet() + " " + i.getCompanyStreetNo()
                    + ", " + i.getCompanyZip() + " " + i.getCompanyCity();
        } /*jeśli nie ma ulicy*/ else {
            this.adresFirmy = i.getCompanyCity() + " " + i.getCompanyStreetNo()
                    + ", " + i.getCompanyZip();
        }
        this.firma = i.getCompanyName();
        this.dataZawarciaUmowy = new Date();
        if (i.getCompanyOwnerFirstName() != null && !i.getCompanyOwnerFirstName().isEmpty()
                && i.getCompanyOwnerLastName() != null && !i.getCompanyOwnerLastName().isEmpty()) {
            this.dyrektor = i.getCompanyOwnerFirstName() + " " + i.getCompanyOwnerLastName()
                    + ", " + i.getCompanyOwnerPosition();
            this.pustyDyrektor = "";
        } else if (i.getCompanyOwnerFirstName() == null || i.getCompanyOwnerFirstName().isEmpty()
                || i.getCompanyOwnerLastName() != null && !i.getCompanyOwnerLastName().isEmpty()) {
            this.pustyDyrektor = ".........................................................................................................................................................\n (imię i nazwisko, stanowisko)";
            this.dyrektor = "";
        }
        this.opiekunZakladowy = i.getCompanyInternshipAdministratorFirstName() + " "
                + i.getCompanyInternshipAdministratorLastName();
        this.student = i.getAccountId().getFirstName() + " "
                + i.getAccountId().getLastName();
        switch (i.getYear()) {
            case 1:
                this.rok = "I";
                break;
            case 2:
                this.rok = "II";
                break;
            case 3:
                this.rok = "III";
                break;
            case 4:
                this.rok = "IV";
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if (i.getEndDate() != null && i.getStartDate() != null) {
            this.okresTrwania = df.format(i.getStartDate()) + " - "
                    + df.format(i.getEndDate());
        }
        if (i.getEndDate() == null && i.getStartDate() == null) {
            this.okresTrwania = "";
        }
        if (i.getEndDate() != null && i.getStartDate() == null) {
            this.okresTrwania = "           - "
                    + df.format(i.getEndDate());
        }
        if (i.getEndDate() == null && i.getStartDate() != null) {
            this.okresTrwania = df.format(i.getStartDate()) + " - ";
        }
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Date getDataZawarciaUmowy() {
        return dataZawarciaUmowy;
    }

    public void setDataZawarciaUmowy(Date dataZawarciaUmowy) {
        this.dataZawarciaUmowy = dataZawarciaUmowy;
    }

    public String getProrektor() {
        return prorektor;
    }

    public void setProrektor(String prorektor) {
        this.prorektor = prorektor;
    }

    public String getAdresFirmy() {
        return adresFirmy;
    }

    public void setAdresFirmy(String adresFirmy) {
        this.adresFirmy = adresFirmy;
    }

    public String getDyrektor() {
        return dyrektor;
    }

    public void setDyrektor(String dyrektor) {
        this.dyrektor = dyrektor;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getOpiekunZakladowy() {
        return opiekunZakladowy;
    }

    public void setOpiekunZakladowy(String opiekunZakladowy) {
        this.opiekunZakladowy = opiekunZakladowy;
    }

    public String getOkresTrwania() {
        return okresTrwania;
    }

    public void setOkresTrwania(String okresTrwania) {
        this.okresTrwania = okresTrwania;
    }

    public String getPustyDyrektor() {
        return pustyDyrektor;
    }

    public void setPustyDyrektor(String pustyDyrektor) {
        this.pustyDyrektor = pustyDyrektor;
    }

}
