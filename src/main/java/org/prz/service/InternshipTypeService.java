/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.InternshipType;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface InternshipTypeService {

    public Page<InternshipType> getFaculties(Integer pageNumber, String type);

    public InternshipType findOne(int id);

    public InternshipType save(InternshipType internshipType);

    public void delete(int id);
    
    public List<InternshipType> findAll();
    
    public List<InternshipType> findBothDisabledAndEnabled();
}
