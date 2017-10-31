/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.List;
import org.prz.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ola
 */
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {

    public List<AccountRole> findByAccountId(Integer accountId);
    public AccountRole findOneByAccountId_AccountIdAndRoleId_RoleId(Integer accountId, Integer roleId);
}
