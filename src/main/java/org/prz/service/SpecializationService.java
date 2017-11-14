/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.Specialization;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface SpecializationService {

    public Page<Specialization> getElements(Integer pageNumber, String name);

    public Specialization findOne(int id);

    public Specialization save(Specialization specialization);

    public void delete(int id);

    public List<Specialization> findAll();

    public List<Specialization> findEnabledAndDisabled();
    
    public List <Specialization> findBothDisabledAndEnabled();
}
