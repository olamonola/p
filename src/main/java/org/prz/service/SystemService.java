/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.prz.entity.System;

/**
 *
 * @author Ola
 */
public interface SystemService {

    public Page<org.prz.entity.System> getSystems(Integer pageNumber, String name);

    public org.prz.entity.System findOne(int id);

    public org.prz.entity.System save(org.prz.entity.System system);

    public void delete(int id);
    
    public List<org.prz.entity.System> findAll();
    
    public List<System> findBothEnabledAndDisabled();
}
