/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.util.List;
import org.prz.entity.StudentView;

/**
 *
 * @author Ola
 */
public class SearchStudentsAndPageInfo {
    
    List<StudentView> studenci;
    int current;
    int end;
    int totalPages;

    public SearchStudentsAndPageInfo() {
    }

    public SearchStudentsAndPageInfo(List<StudentView> studenci, int current, int end) {
        this.studenci = studenci;
        this.current = current;
        this.end = end;
    }

    public SearchStudentsAndPageInfo(List<StudentView> studenci, int current, int end, int totalPages) {
        this.studenci = studenci;
        this.current = current;
        this.end = end;
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    

    public List<StudentView> getStudenci() {
        return studenci;
    }

    public void setStudenci(List<StudentView> studenci) {
        this.studenci = studenci;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
    
}
