/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.Collection;
import org.prz.entity.AccountRole;
import org.prz.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ola
 */
@Service
public class AccountRoleServiceImpl implements AccountRoleService {

    private final AccountRoleRepository accountRoleRepository;

    @Autowired
    public AccountRoleServiceImpl(AccountRoleRepository accountRoleRepository) {
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public Collection<AccountRole> getAllByAccountId(int accountId) {
        return accountRoleRepository.findByAccountId(accountId);
    }

}
