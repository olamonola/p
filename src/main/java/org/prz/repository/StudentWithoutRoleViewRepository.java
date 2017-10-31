/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import org.prz.entity.StudentWithoutRoleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ola
 */
public interface StudentWithoutRoleViewRepository extends JpaRepository<StudentWithoutRoleView, Integer> {

    @Query(value = "SELECT s from StudentWithoutRoleView s"
            + " where lower(s.firstName) like CONCAT('%', :firstname, '%')"
            + " and lower(s.lastName) like CONCAT('%', :lastname, '%')"
            + " and lower(s.indexNo) like CONCAT('%', :index, '%')"
            + " and lower(s.login) like CONCAT('%', :login, '%')"
            + " and lower(s.email) like CONCAT('%', :email, '%')"
            + " and (s.accountConfirmed = :onlyConfirmed"
            + " or s.accountConfirmed = :onlyNotConfirmed)")
    public Page<StudentWithoutRoleView> searchStudentsWithoutRole(
            Pageable pageable,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("index") String index,
            @Param("login") String login,
            @Param("email") String email,
            @Param("onlyConfirmed") Boolean onlyConfirmed,
            @Param("onlyNotConfirmed") Boolean onlyNotConfirmed);
}
