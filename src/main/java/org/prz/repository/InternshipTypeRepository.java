/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.List;
import org.prz.entity.InternshipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ola
 */
public interface InternshipTypeRepository extends JpaRepository<InternshipType, Integer>{
    
    public Page<InternshipType> findByTypeIgnoreCaseContainingOrderByTypeAsc(Pageable pageable, String type);
    
    public List<InternshipType> findByEnabledOrderByTypeAsc(Boolean enabled);
}
