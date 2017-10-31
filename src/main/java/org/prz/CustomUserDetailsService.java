/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz;

import java.util.Date;
import java.util.List;
import org.prz.entity.Account;
import org.prz.entity.AccountRole;
import org.prz.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ola
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CustomUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CustomUser loadUserByUsername(String login) throws UsernameNotFoundException {

        Account account = accountService.
                getAccountByLogin(login).orElseThrow(()
                -> new UsernameNotFoundException(
                        String.format("Użytkownik o loginie=%s nie został znaleziony",
                                login)));
        List<AccountRole> accountRoles = (List<AccountRole>) account.getAccountRoleCollection();
        if (accountRoles.isEmpty()) {
            //jeśli brak ról
            String[] role = new String[1];
            role[0] = "ROLE_NONE";
            logger.info("roles[0] {}", role[0]);
            return new CustomUser(account, role);
        }
        String[] roles = new String[accountRoles.size()];
        for (int j = 0; j < accountRoles.size(); j++) {
            //jeśli rola to student i ma on role nie ważną w terminie
            Date now = new Date();
            if (accountRoles.get(j).getRoleId().getRoleId() == 3
                    && (accountRoles.get(j).getRoleStartTime().after(now)
                    || accountRoles.get(j).getRoleEndTime().before(now))) {
                roles[j] = "ROLE_EXPIRED";
                logger.info("roles[{}] = {}", j, roles[j]);
                continue;
            }
            roles[j] = accountRoles.get(j).getRoleId().getName();
            logger.info("roles[{}] = {}", j, roles[j]);
        }
        return new CustomUser(account, roles);
    }

}
