package org.prz.repository;

import java.util.List;
import org.prz.entity.System;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ola
 */
public interface SystemRepository extends JpaRepository<System, Integer> {

    public Page<System> findByNameIgnoreCaseContainingOrderByNameAsc(Pageable pageable, String name);
    
    public List<org.prz.entity.System> findByEnabledOrderByNameAsc(Boolean enabled);

}
