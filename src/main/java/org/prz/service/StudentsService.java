/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import org.prz.dao.SearchStudents;
import org.prz.dao.SearchStudentsAndPageInfo;
import org.prz.dao.StudentDataDao;
import org.prz.dao.StudentRoleDao;
import org.prz.entity.Account;
import org.prz.entity.AccountRole;
import org.prz.entity.Internship;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface StudentsService {

    public Page<Account> findAllUsers(Integer pageNumber);

    public SearchStudentsAndPageInfo searchUsersQueryBeta(SearchStudents searchStudents, Integer pageNumber);

    public Account findOneStudent(int id);
    
    public Page<Internship> findInternshipsByAccount(Integer id, Integer pageNumber);

    public void editStudentData(Integer id, StudentDataDao newData);

    public AccountRole findStudentAccountRole(Integer accountId, Integer roleId);

    public void editStudentRole(Integer id, StudentRoleDao newData);
    
    public Account confirm(Integer id, Boolean confirmation);
}
