/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import org.prz.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findOneByToken(String token);
    
    @Modifying
    @Transactional
    @Query("delete from VerificationToken t where t.expirationDate<CURRENT_TIMESTAMP")
    void deleteOldTokens();

    
}
