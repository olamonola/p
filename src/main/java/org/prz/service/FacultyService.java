/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.Faculty;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface FacultyService {
    
    public Page<Faculty> getFaculties(Integer pageNumber, String name);

    public Faculty findOne(int id);

    public Faculty save(Faculty faculty);

    public void delete(int id);
    
    public List<Faculty> findAll();
    
    public List<Faculty> findBothEnabledAndBisabled();
    
}
