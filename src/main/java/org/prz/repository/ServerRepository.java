/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import org.prz.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ola
 */
public interface ServerRepository extends JpaRepository<Server, Integer> {
    
}
