/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.Optional;
import org.prz.entity.Account;
import org.prz.entity.ForgottenPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
public interface ForgottenPasswordTokenRepository extends JpaRepository<ForgottenPasswordToken, Integer> {

    @Modifying
    @Transactional
    @Query("delete from ForgottenPasswordToken fpt where fpt.accountId=?1)")
    void deletePreviousTokens(Account account);

    @Modifying
    @Transactional
    @Query("delete from ForgottenPasswordToken ftp where ftp.expirationDate<CURRENT_TIMESTAMP)")
    void deleteExpiredTokens();

    Optional<ForgottenPasswordToken> findOneByAccountId_Email(String email);

    Optional<ForgottenPasswordToken> findOneByToken(String token);
}
