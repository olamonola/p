/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.Collection;
import org.prz.entity.AccountRole;

/**
 *
 * @author Ola
 */
public interface AccountRoleService {

    Collection<AccountRole> getAllByAccountId(int accountId);

}
