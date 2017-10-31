/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.Collection;
import java.util.Optional;
import org.prz.dao.EmailChange;
import org.prz.dao.EmailDao;
import org.prz.dao.MyPasswordDao;
import org.prz.dao.MyProfileDao;
import org.prz.dao.RegisterAccount;
import org.prz.dao.ResetPasswordForm;
import org.prz.entity.Account;
import org.prz.entity.ForgottenPasswordToken;
import org.prz.entity.VerificationToken;
import org.prz.exception.EmailNotFoundException;
import org.prz.exception.EmailNotUnique;
import org.prz.exception.EmailNotUniqueAndCodeInvalid;
import org.prz.exception.ForgottenPasswordTokenExpiredException;
import org.prz.exception.InvalidPasswordException;
import org.prz.exception.LoginAndEmailNotUnique;
import org.prz.exception.LoginAndEmailNotUniqueAndCodeInvalid;
import org.prz.exception.LoginNotUnique;
import org.prz.exception.LoginNotUniqueAndCodeInvalid;
import org.prz.exception.SecretCodeIncorrect;
import org.springframework.mail.MailException;

/**
 *
 * @author Ola
 */
public interface AccountService {
    public Account editMyEmail(Account currentData, EmailChange newData) throws EmailNotUnique, InvalidPasswordException;
    

    Optional<Account> getAccountById(int id);

    Optional<Account> getAccountByLogin(String login);

    Collection<Account> getAllAccounts();
    
    public Account saveAccount(RegisterAccount registerAccount) throws LoginAndEmailNotUniqueAndCodeInvalid,
            LoginNotUniqueAndCodeInvalid, EmailNotUniqueAndCodeInvalid,LoginAndEmailNotUnique,
            SecretCodeIncorrect, LoginNotUnique, EmailNotUnique;
    
    public VerificationToken saveVerificationToken(Account account);
    
    public void sendTokenViaEmail(VerificationToken verificationToken, String emailAddress, String baseUrl);
    
    public VerificationToken getVerificationToken(String token);
    
    public Account saveRegisteredUser(Account account);
    
    public void deleteToken(VerificationToken token);
    
    public void deleteTokenAndAccount(VerificationToken token);
    
    public void deleteAllExpiredTokens();
    
    public Account editMyProfile(Account currentData, MyProfileDao newData);
    
    public Account changePassword(Account a, MyPasswordDao formData);
    
    public ForgottenPasswordToken savePasswordTokenAndSendEmail(EmailDao myEmail, String baseUrl)
            throws EmailNotFoundException, MailException;
    
    public void deleteAllExpiredTokensForForgottenPasswords();
    
    public Integer isTokenExpired(String passToken);
    
    public Account changePasswordAndDeleteToken(ResetPasswordForm rpf) throws ForgottenPasswordTokenExpiredException;

    
}
