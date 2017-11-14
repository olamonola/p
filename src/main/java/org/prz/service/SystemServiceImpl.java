/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import javax.inject.Inject;
import org.prz.entity.System;
import org.prz.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
@Service
public class SystemServiceImpl implements SystemService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private SystemRepository systemRepository;

    @Override
    public Page<org.prz.entity.System> getSystems(Integer pageNumber, String name) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
        return systemRepository.findByNameIgnoreCaseContainingOrderByNameAsc(pageRequest, name);
    }

    @Override
    public org.prz.entity.System findOne(int id) {
        return systemRepository.findOne(id);
    }

    @Transactional
    @Override
    public System save(System system) {
        return systemRepository.save(system);
    }

    @Transactional
    @Override
    public void delete(int id) {
        systemRepository.delete(id);
    }

    @Override
    public List<System> findAll() {
        return systemRepository.findByEnabledOrderByNameAsc(Boolean.TRUE);
    }
    
    @Override
    public List<System> findBothEnabledAndDisabled() {
        return systemRepository.findAll();
    }
}
