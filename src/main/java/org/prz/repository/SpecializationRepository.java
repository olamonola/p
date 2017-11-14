/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.Date;
import java.util.List;
import org.prz.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ola
 */
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
        
    @Query(value = "SELECT distinct i.specializationId from Internship i"
             + " join i.internshipTypeId it"
             + " where it.internshipTypeId = :internshipTypeId"
             + " and i.confirmation = TRUE"
             + " and i.archived = FALSE"
             + " and i.startDate >= :date1"
             + " and i.startDate < :date2")
    public List<Specialization> specializationsInWykazResult(
            @Param("internshipTypeId") Integer internshipTypeId, 
            @Param("date1") Date date1,
            @Param("date2") Date date2);  

    public Page<Specialization> findByNameIgnoreCaseContainingOrderByNameAsc(Pageable pageable, String name);
    
    public List<Specialization> findByEnabledOrderByNameAsc(Boolean enabled);

}
