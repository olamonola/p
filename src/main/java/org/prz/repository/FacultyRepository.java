/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.List;
import org.prz.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ola
 */
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    public Page<Faculty> findByNameIgnoreCaseContainingOrderByNameAsc(Pageable pageable, String name);

    public List<Faculty> findByEnabledOrderByNameAsc(Boolean enabled);

}
