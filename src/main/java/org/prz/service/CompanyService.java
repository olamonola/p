/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import org.prz.entity.Company;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface CompanyService {

    public Page<Company> getElements(Integer pageNumber, String name);

    public Company findOne(int id);

    public Company save(Company company);

    public void delete(int id);
}