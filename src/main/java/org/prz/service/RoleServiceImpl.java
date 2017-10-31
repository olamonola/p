/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import java.util.Optional;
import org.prz.entity.Role;
import org.prz.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ola
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRoleById(int id) {
        return Optional.ofNullable(roleRepository.findOne(id));
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
