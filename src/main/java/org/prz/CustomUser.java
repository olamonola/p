/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz;

import org.prz.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Ola
 */
public class CustomUser extends User {

    private static final long serialVersionUID = -2569405344435124710L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Account account;

    /*public CustomUser(Account account, String[] roles) {
        super(account.getLogin(), account.getPassword(), AuthorityUtils
                .createAuthorityList(roles));
    }*/

    public CustomUser(Account account, String[] roles) {
        super(account.getLogin(), account.getPassword(), account.isVerified(), true, true, true, AuthorityUtils
                .createAuthorityList(roles));
    }

    public Account getAccount() {
        return account;
    }

    public Integer getId() {
        return account.getAccountId();
    }

    public String getLogin() {
        return account.getLogin();
    }

}
