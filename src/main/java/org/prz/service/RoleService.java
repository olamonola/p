/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.List;
import java.util.Optional;
import org.prz.entity.Role;

/**
 *
 * @author Ola
 */
public interface RoleService {

    Optional<Role> getRoleById(int id);
    List<Role> getAll();

}
