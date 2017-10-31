/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.prz.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Modifying
    @Transactional
    @Query("delete from Account a where a.verified = false and a.accountId in (select t.accountId from VerificationToken t where t.expirationDate<CURRENT_TIMESTAMP)")
    void deleteOldTokensAndAccounts();

    @Modifying
    @Transactional
    @Query("delete from Account a where a.verified = false and a.accountId in (select t.accountId from VerificationToken t where t.token=?1)")
    void deleteOldTokenAndAccount(String token);

    public Optional<Account> findOneByLogin(String login);

    public Optional<Account> findOneByEmail(String email);
    
    public List<Account> findByUserProfile_IndexNo(String indexNo);

    public Page<Account> findAllByOrderByUserProfile_LastNameAsc(Pageable pageable);

    //7 arg
    public Page<Account> findAllByUserProfile_FirstNameIgnoreCaseContainingAndUserProfile_LastNameIgnoreCaseContainingAndUserProfile_IndexNoIgnoreCaseContainingAndLoginIgnoreCaseContainingAndEmailIgnoreCaseContainingAndAccountRoleCollection_RoleId_NameIsOrderByUserProfile_LastNameAsc(Pageable pageable, String firstName, String lastName, String indexNo, String login, String email, String roleName);

    //6 arg
    public Page<Account> findAllByUserProfile_FirstNameIgnoreCaseContainingAndUserProfile_LastNameIgnoreCaseContainingAndUserProfile_IndexNoIgnoreCaseContainingAndLoginIgnoreCaseContainingAndEmailIgnoreCaseContainingOrderByUserProfile_LastNameAsc(Pageable pageable, String firstName, String lastName, String indexNo, String login, String email);

    
    //8 arg
    public Page<Account> findAllByUserProfile_FirstNameIgnoreCaseContainingAndUserProfile_LastNameIgnoreCaseContainingAndUserProfile_IndexNoIgnoreCaseContainingAndLoginIgnoreCaseContainingAndEmailIgnoreCaseContainingAndAccountConfirmedAndAccountRoleCollection_RoleId_NameIsOrderByUserProfile_LastNameAsc(Pageable pageable, String firstName, String lastName, String indexNo, String login, String email, Boolean confirmed, String roleName);

    //sprawdza również czy rola jest ważna (pod względem czasu jej trwania)
    //10 arg
    public Page<Account> findAllByUserProfile_FirstNameIgnoreCaseContainingAndUserProfile_LastNameIgnoreCaseContainingAndUserProfile_IndexNoIgnoreCaseContainingAndLoginIgnoreCaseContainingAndEmailIgnoreCaseContainingAndAccountConfirmedAndAccountRoleCollection_RoleStartTimeBeforeAndAccountRoleCollection_RoleEndTimeAfterAndAccountRoleCollection_RoleId_NameIsOrderByUserProfile_LastNameAsc(Pageable pageable, String firstName, String lastName, String indexNo, String login, String email, Boolean confirmed, Date now, Date alsoNow, String roleName);

    //9 arg
    public Page<Account> findAllByUserProfile_FirstNameIgnoreCaseContainingAndUserProfile_LastNameIgnoreCaseContainingAndUserProfile_IndexNoIgnoreCaseContainingAndLoginIgnoreCaseContainingAndEmailIgnoreCaseContainingAndAccountRoleCollection_RoleStartTimeBeforeAndAccountRoleCollection_RoleEndTimeAfterOrderByUserProfile_LastNameAsc(Pageable pageable, String firstName, String lastName, String indexNo, String login, String email, Date now, Date alsoNow);

}
