/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import org.prz.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.prz.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    
    private static final int PAGE_SIZE = 10;

    @Autowired
    private CompanyRepository repository;
    
    @Override
    public Page<Company> getElements(Integer pageNumber, String name) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
        return repository.findByNameIgnoreCaseContainingOrderByNameAsc(pageRequest, name);
    }

    @Override
    public Company findOne(int id) {
        return repository.findOne(id);
    }

    @Transactional
    @Override
    public Company save(Company company) {
        return repository.save(company);
    }

    @Transactional
    @Override
    public void delete(int id) {
        repository.delete(id);
    }

}