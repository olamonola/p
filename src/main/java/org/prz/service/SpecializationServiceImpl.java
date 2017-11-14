/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.Specialization;
import org.prz.repository.SpecializationRepository;
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
public class SpecializationServiceImpl implements SpecializationService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public Page<Specialization> getElements(Integer pageNumber, String name) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE);
        return specializationRepository.
                findByNameIgnoreCaseContainingOrderByNameAsc(pageRequest, name);
    }

    @Override
    public Specialization findOne(int id) {
        return specializationRepository.findOne(id);
    }

    @Transactional
    @Override
    public Specialization save(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Transactional
    @Override
    public void delete(int id) {
        specializationRepository.delete(id);
    }

    @Override
    public List<Specialization> findAll() {
        return specializationRepository.findByEnabledOrderByNameAsc(Boolean.TRUE);
    }
    
    @Override
    public List <Specialization> findBothDisabledAndEnabled(){
        return specializationRepository.findAll();
    }
    
    @Override
    public List<Specialization> findEnabledAndDisabled() {
        return specializationRepository.findAll();
    }

}
