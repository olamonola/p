/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.InternshipType;
import org.prz.repository.InternshipTypeRepository;
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
public class InternshipTypeServiceImpl implements InternshipTypeService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private InternshipTypeRepository repository;

    @Override
    public Page<InternshipType> getFaculties(Integer pageNumber, String type) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "type");
        return repository.findByTypeIgnoreCaseContainingOrderByTypeAsc(pageRequest, type);
    }

    @Override
    public InternshipType findOne(int id) {
        return repository.findOne(id);
    }

    @Transactional
    @Override
    public InternshipType save(InternshipType entity) {
        return repository.save(entity);
    }

    @Transactional
    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<InternshipType> findAll() {
        return repository.findByEnabledOrderByTypeAsc(Boolean.TRUE);
    }
    
    @Override
    public List<InternshipType> findBothDisabledAndEnabled() {
        return repository.findAll();
    }

}
