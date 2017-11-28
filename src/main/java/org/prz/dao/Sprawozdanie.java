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
public class Sprawozdanie {
    
    private String kierunek;
    private String specjalnosc;
    private String system;
    private String rokStudiow;
    private String nazwaPraktyki;
    private String dlugoscPraktyki;
    private String opiekun;
    private String studenciNaPraktykach; //ilość
    private String studenciZwolnieni; //ilość
    private String studenciOdwolani; //ilość
    private String uwagi; //na końcu, do punktu 12
    private String lp; //numerowanie
    private String instytucja;
    private String studenciWInstytucji; //ilość studentów w instytucji

    public Sprawozdanie() {
    }

    public String getKierunek() {
        return kierunek;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public String getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(String specjalnosc) {
        this.specjalnosc = specjalnosc;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getRokStudiow() {
        return rokStudiow;
    }

    public void setRokStudiow(String rokStudiow) {
        this.rokStudiow = rokStudiow;
    }

    public String getNazwaPraktyki() {
        return nazwaPraktyki;
    }

    public void setNazwaPraktyki(String nazwaPraktyki) {
        this.nazwaPraktyki = nazwaPraktyki;
    }

    public String getDlugoscPraktyki() {
        return dlugoscPraktyki;
    }

    public void setDlugoscPraktyki(String dlugoscPraktyki) {
        this.dlugoscPraktyki = dlugoscPraktyki;
    }

    public String getOpiekun() {
        return opiekun;
    }

    public void setOpiekun(String opiekun) {
        this.opiekun = opiekun;
    }

    public String getStudenciNaPraktykach() {
        return studenciNaPraktykach;
    }

    public void setStudenciNaPraktykach(String studenciNaPraktykach) {
        this.studenciNaPraktykach = studenciNaPraktykach;
    }

    public String getStudenciZwolnieni() {
        return studenciZwolnieni;
    }

    public void setStudenciZwolnieni(String studenciZwolnieni) {
        this.studenciZwolnieni = studenciZwolnieni;
    }

    public String getStudenciOdwolani() {
        return studenciOdwolani;
    }

    public void setStudenciOdwolani(String studenciOdwolani) {
        this.studenciOdwolani = studenciOdwolani;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getInstytucja() {
        return instytucja;
    }

    public void setInstytucja(String instytucja) {
        this.instytucja = instytucja;
    }

    public String getStudenciWInstytucji() {
        return studenciWInstytucji;
    }

    public void setStudenciWInstytucji(String studenciWInstytucji) {
        this.studenciWInstytucji = studenciWInstytucji;
    }
    
    
    
}
