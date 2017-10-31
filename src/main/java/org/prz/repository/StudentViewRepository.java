/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import org.prz.entity.StudentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ola
 */
public interface StudentViewRepository extends JpaRepository<StudentView, Integer>{
  
    
    @Query(value = "SELECT s from StudentView s"
            + " where lower(s.firstName) like CONCAT('%', :firstname, '%')"
            + " and lower(s.lastName) like CONCAT('%', :lastname, '%')"
            + " and lower(s.indexNo) like CONCAT('%', :index, '%')"
            + " and lower(s.login) like CONCAT('%', :login, '%')"
            + " and lower(s.email) like CONCAT('%', :email, '%')"
            + " and (s.accountConfirmed = :onlyConfirmed"
            + " or s.accountConfirmed = :onlyNotConfirmed)")
    public Page<StudentView> searchConfirmedOrNotConfirmed(
            Pageable pageable,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname, 
            @Param("index") String index,
            @Param("login") String login, 
            @Param("email") String email,
            @Param("onlyConfirmed") Boolean onlyConfirmed,
            @Param("onlyNotConfirmed") Boolean onlyNotConfirmed);
    
}
