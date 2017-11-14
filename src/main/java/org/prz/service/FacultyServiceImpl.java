/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import org.prz.entity.Faculty;
import org.prz.repository.FacultyRepository;
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
public class FacultyServiceImpl implements FacultyService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Page<Faculty> getFaculties(Integer pageNumber, String name) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
        return facultyRepository.findByNameIgnoreCaseContainingOrderByNameAsc(pageRequest, name);
    }

    @Override
    public Faculty findOne(int id) {
        return facultyRepository.findOne(id);
    }

    @Transactional
    @Override
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Transactional
    @Override
    public void delete(int id) {
        facultyRepository.delete(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findByEnabledOrderByNameAsc(Boolean.TRUE);
    }
    
    @Override
    public List<Faculty> findBothEnabledAndBisabled() {
        return facultyRepository.findAll();
    }

}
