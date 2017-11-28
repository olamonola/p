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
public class InternshipArchived extends CurrentPage{
    
    private Boolean archived;

    public InternshipArchived() {
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

}
