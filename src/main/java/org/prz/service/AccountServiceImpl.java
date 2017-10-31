/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.prz.dao.EmailChange;
import org.prz.dao.EmailDao;
import org.prz.dao.MyPasswordDao;
import org.prz.dao.MyProfileDao;
import org.prz.dao.RegisterAccount;
import org.prz.dao.ResetPasswordForm;
import org.prz.entity.Account;
import org.prz.entity.ForgottenPasswordToken;
import org.prz.entity.Status;
import org.prz.entity.UserProfile;
import org.prz.entity.VerificationToken;
import org.prz.exception.EmailNotFoundException;
import org.prz.exception.EmailNotUnique;
import org.prz.exception.EmailNotUniqueAndCodeInvalid;
import org.prz.exception.EmailNotUniqueAndWrongPasswordException;
import org.prz.exception.ForgottenPasswordTokenExpiredException;
import org.prz.exception.IndexNoNotUnique;
import org.prz.exception.InvalidPasswordException;
import org.prz.exception.LoginAndEmailNotUnique;
import org.prz.exception.LoginAndEmailNotUniqueAndCodeInvalid;
import org.prz.exception.LoginNotUnique;
import org.prz.exception.LoginNotUniqueAndCodeInvalid;
import org.prz.exception.SecretCodeIncorrect;
import org.prz.repository.AccountRepository;
import org.prz.repository.ForgottenPasswordTokenRepository;
import org.prz.repository.SecretCodeRepository;
import org.prz.repository.UserProfileRepository;
import org.prz.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
@Service
@EnableScheduling
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int EXPIRATION = 60 * 24;

    private static final int EXPIRATION_FOR_PASSWORD_TOKEN = 30;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final UserProfileRepository userProfiletRepository;

    @Autowired
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private final ForgottenPasswordTokenRepository forgottenPasswordTokenRepository;

    @Autowired
    private final SecretCodeRepository secretCodeRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
            VerificationTokenRepository verificationTokenRepository,
            SecretCodeRepository secretCodeRepository,
            UserProfileRepository userProfiletRepository,
            ForgottenPasswordTokenRepository forgottenPasswordTokenRepository) {
        this.accountRepository = accountRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.secretCodeRepository = secretCodeRepository;
        this.userProfiletRepository = userProfiletRepository;
        this.forgottenPasswordTokenRepository = forgottenPasswordTokenRepository;
    }

    @Override
    public Optional<Account> getAccountById(int id) {
        return Optional.ofNullable(accountRepository.findOne(id));
    }

    @Override
    public Optional<Account> getAccountByLogin(String login) {
        return accountRepository.findOneByLogin(login);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Rejestracja nowego konta
     *
     * @param registerAccount Parametry z formularza
     * @return Zapisane konto
     */
    @Transactional
    @Override
    public Account saveAccount(RegisterAccount registerAccount) throws LoginAndEmailNotUniqueAndCodeInvalid,
            LoginNotUniqueAndCodeInvalid, EmailNotUniqueAndCodeInvalid, LoginAndEmailNotUnique,
            SecretCodeIncorrect, LoginNotUnique, EmailNotUnique {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = new Account();

        if (!this.isLoginUnique(registerAccount.getLogin())
                && !this.isEmailUnique(registerAccount.getEmail())
                && !this.isSecretCodeCorrect(registerAccount.getSecretCode())) {
            logger.info("LoginAndEmailNotUniqueAndCodeInvalid exception thrown");
            throw new LoginAndEmailNotUniqueAndCodeInvalid("loginAndEmailNotUniqueAndCodeInvalid");
        }

        /*Login i kod*/
        if (!this.isLoginUnique(registerAccount.getLogin())
                && !this.isSecretCodeCorrect(registerAccount.getSecretCode())) {
            logger.info("LoginNotUniqueAndCodeInvalid exception thrown");
            throw new LoginNotUniqueAndCodeInvalid("loginNotUniqueAndCodeInvalid");
        }

        /*Email i kod*/
        if (!this.isEmailUnique(registerAccount.getEmail())
                && !this.isSecretCodeCorrect(registerAccount.getSecretCode())) {
            logger.info("EmailNotUniqueAndCodeInvalid exception thrown");
            throw new EmailNotUniqueAndCodeInvalid("emailNotUniqueAndCodeInvalid");
        }

        /*Email i login*/
        if (!this.isLoginUnique(registerAccount.getLogin())
                && !this.isEmailUnique(registerAccount.getEmail())) {
            logger.info("LoginAndEmailNotUnique exception thrown");
            throw new LoginAndEmailNotUnique("loginAndEmailNotUnique");
        }

        if (!this.isLoginUnique(registerAccount.getLogin())) {
            logger.info("LoginNotUnique Exception thrown");
            throw new LoginNotUnique("login", "loginNotUnique");
        }
        if (!this.isEmailUnique(registerAccount.getEmail())) {
            logger.info("EmailNotUnique Exception thrown");
            throw new EmailNotUnique("email", "emailNotUnique");
        }
        if (!this.isSecretCodeCorrect(registerAccount.getSecretCode())) {
            logger.info("SecretCodeIncorrect Exception thrown");
            throw new SecretCodeIncorrect("secretCode", "secretCodeIncorrect");
        }
        if (this.isSecretCodeCorrect(registerAccount.getSecretCode())) {
            account.setAccountConfirmed(false);
            account.setActive(true);
            account.setEmail(registerAccount.getEmail());
            account.setLogin(registerAccount.getLogin());
            account.setPassword(passwordEncoder.encode(registerAccount.getPassword()));
            account.setRegistrationTime(new Date());
            account.setPasswordHashAlgorithm("BCrypt");
            account.setVerified(false);
            Status status = new Status();
            status.setStatusId(2);
            account.setStatusId(status);
            accountRepository.save(account);
            return account;

        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public VerificationToken saveVerificationToken(Account account) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setAccountId(account);
        verificationToken.setExpirationDate(calculateExpiryDate(EXPIRATION));
        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    private String htmlMsg(VerificationToken verificationToken, String baseUrl) {
        String link = baseUrl + "/Konto/weryfikacja?token=" + verificationToken.getToken();
        String htmlMsg = "<h3 style=\"text-align: center; color: #000000;\">Potwierdź rejestrację</h3>"
                + "<p style=\"color: #000000;\">Aby potwierdzić rejestrację w aplikacji "
                + "w przeciągu 24 godzin kliknij w poniższy przycisk</p>"
                + "<p style=\"text-align: center;\">"
                + "<a href=" + link + " type=\"button\" "
                + "style="
                + "\"    color: #fff;\n"
                + "    background-color: #337ab7;\n"
                + "    text-decoration: none;"
                + "    -webkit-appearance: button;\n"
                + "    -moz-appearance: button;\n"
                + "    appearance: button;"
                + "    border-color: #2e6da4;"
                + "    display: inline-block;\n"
                + "    padding: 6px 12px;\n"
                + "    touch-action: manipulation;\n"
                + "    border: 1px solid transparent;\n"
                + "    border-radius: 4px;"
                + "    cursor: pointer;"
                + "    margin-bottom: 0;\n"
                + "    font-size: 14px;\n"
                + "    font-weight: 400;\n"
                + "    line-height: 1.42857143;\n"
                + "    text-align: center;\n"
                + "    white-space: nowrap;\n"
                + "    vertical-align: middle;\">"
                + "Potwierdzam</a></p><br> "
                + "lub skopiuj link do wyszukiwarki. \n " + link;
        return htmlMsg;
    }

    @Override
    public void sendTokenViaEmail(VerificationToken verificationToken,
            String emailAddress, String baseUrl)
            throws MailException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            String htmlMsg = htmlMsg(verificationToken, baseUrl);
            mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
            helper.setTo(emailAddress);
            helper.setSubject("Potwierdzenie rejestracji");
            helper.setFrom("applicationunofficial@o2.pl");
            mailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            java.util.logging.Logger.
                    getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailAddress);
        email.setSubject("Potwierdzenie rejestracji");
        email.setText("Aby potwierdzić rejestrację w aplikacji w przeciągu 24 godzin kliknij w poniższy link.\n " + baseUrl + "/Konto/weryfikacja?token=" + verificationToken.getToken());
        mailSender.send(email);*/
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findOneByToken(token);
    }

    @Transactional
    @Override
    public Account saveRegisteredUser(Account account) {
        Account a = accountRepository.save(account);
        return a;
    }

    @Transactional
    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }

    @Transactional
    @Override
    public void deleteTokenAndAccount(VerificationToken token) {
        accountRepository.deleteOldTokenAndAccount(token.getToken());
    }

    @Transactional
    @Override
    public Account editMyEmail(Account currentData, EmailChange newData) throws
            EmailNotUniqueAndWrongPasswordException, EmailNotUnique, InvalidPasswordException {
        if (!newData.getEmail().equals(currentData.getEmail())) {
            if (!this.isEmailUnique(newData.getEmail())
                    && !new BCryptPasswordEncoder().matches(newData.getPassword(), currentData.getPassword())) {
                logger.info("EmailNotUniqueAndWrongPasswordException Exception thrown");
                throw new EmailNotUniqueAndWrongPasswordException("emailNotUniqueAndCodeInvalid");
            }
            /*Jeśli email ma być zmieniany, to trzeba sprawdzić unikalność maila*/ {
                if (!this.isEmailUnique(newData.getEmail())) {
                    logger.info("EmailNotUnique Exception thrown");
                    throw new EmailNotUnique("email", "emailNotUnique");
                }
            }

            /*Jeśli email ma być zmieniany, to trzeba sprawdzić poprawność hasła*/
            if (!new BCryptPasswordEncoder().matches(newData.getPassword(), currentData.getPassword())) {
                logger.info("EmailNotUnique Exception thrown");
                throw new InvalidPasswordException("password", "invalidPassword");
            }
        }
        currentData.setEmail(newData.getEmail());
        accountRepository.save(currentData);
        return currentData;
    }

    @Transactional
    @Override
    public Account editMyProfile(Account currentData, MyProfileDao newData)
            throws EmailNotUnique, InvalidPasswordException, IndexNoNotUnique {
        if (!newData.getEmail().equals(currentData.getEmail())) {
            /*Jeśli email ma być zmieniany, to trzeba sprawdzić unikalność maila*/
            if (!this.isEmailUnique(newData.getEmail())) {
                logger.info("EmailNotUnique Exception thrown");
                throw new EmailNotUnique("email", "emailNotUnique");
            }

            /*Jeśli email ma być zmieniany, to trzeba sprawdzić poprawność hasła*/
            if (!new BCryptPasswordEncoder().matches(newData.getPassword(), currentData.getPassword())) {
                logger.info("EmailNotUnique Exception thrown");
                throw new InvalidPasswordException("password", "invalidPassword");
            }
        }
        if (currentData.getUserProfile() == null) {
            if (!this.isIndexNoUnique(newData.getIndexNo())) {
                logger.info("IndexNoNotUnique Exception thrown");
                throw new IndexNoNotUnique("indexNo", "indexNoNotUnique");
            }
        }

        if (currentData.getUserProfile() != null) {
            if (!currentData.getUserProfile().getIndexNo().equals(newData.getIndexNo())) {
                if (!this.isIndexNoUnique(newData.getIndexNo())) {
                    logger.info("IndexNoNotUnique Exception thrown");
                    throw new IndexNoNotUnique("indexNo", "indexNoNotUnique");
                }
            }
        }
        Account newAccount = currentData;
        logger.info("newAccount.getaccountId {}", newAccount.getAccountId());
        currentData.setEmail(newData.getEmail());
        UserProfile userProfile;
        if (currentData.getUserProfile() != null) {
            userProfile = currentData.getUserProfile();
        } else {
            userProfile = new UserProfile();
            //userProfile.setAccount(newAccount);
            userProfile.setAccountId(newAccount.getAccountId());
        }
        userProfile.setFirstName(newData.getFirstName());
        userProfile.setIndexNo(newData.getIndexNo());
        userProfile.setLastName(newData.getLastName());
        accountRepository.save(newAccount);
        userProfiletRepository.save(userProfile);
        return newAccount;
    }

    @Transactional
    @Override
    public Account changePassword(Account a, MyPasswordDao formData) throws MailException {
        a.setPassword(new BCryptPasswordEncoder().encode(formData.getNewPassword()));
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(a.getEmail());
        email.setSubject("Twoje hasło zostało zmienione");
        email.setText("Twoje hasło w aplikacji Praktyki zostało zmienione.");
        mailSender.send(email);
        return a;
    }

    @Transactional
    @Override
    public ForgottenPasswordToken savePasswordTokenAndSendEmail(EmailDao myEmail, String baseUrl)
            throws EmailNotFoundException, MailException {
        ForgottenPasswordToken fpt = new ForgottenPasswordToken();

        /*Czy taki email w ogóle jest w bazie*/
        Optional<Account> account = accountRepository.findOneByEmail(myEmail.getEmail());
        if (!account.isPresent()) {
            logger.error("Email {} nie został odnaleziony wazie.", myEmail.getEmail());
            throw new EmailNotFoundException("emailNotFound");
        }
        /*Jeśli są jakieś wcześniejsze tokeny dla tego użytkownika, to są one usuwane.*/
        forgottenPasswordTokenRepository.deletePreviousTokens(account.get());

        /*Zapisuję token*/
        fpt.setAccountId((Account) account.get());
        fpt.setExpirationDate(calculateExpiryDate(EXPIRATION_FOR_PASSWORD_TOKEN));
        String token = UUID.randomUUID().toString();
        fpt.setToken(token);
        forgottenPasswordTokenRepository.save(fpt);

        /*Wysyłam maila z linkiem*/
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(myEmail.getEmail());
        email.setSubject("Zmiana hasła");
        email.setText("Aby zmienić hasło w aplikacji w ciągu 30 minut kliknij w poniższy link.\n " + baseUrl + "/Konto/zmianaZapomnianegoHasla?passToken=" + token);
        mailSender.send(email);
        return fpt;
    }

    @Transactional
    @Override
    public Account changePasswordAndDeleteToken(ResetPasswordForm rpf) throws ForgottenPasswordTokenExpiredException {
        /*Zapis nowego hasła*/
        Account account = accountRepository.findOne(rpf.getAccountId());
        Optional<ForgottenPasswordToken> token = forgottenPasswordTokenRepository.findOneByAccountId_Email(account.getEmail());
        if (!token.isPresent()) {
            logger.error("Błąd zmiany zapomnianego hasła. Link jest przeterminowany");
            throw new ForgottenPasswordTokenExpiredException("forgottenPasswordTokenExpired");
        }
        if (token.isPresent()) {
            if (token.get().getExpirationDate().before(new Date())) {
                logger.error("Błąd zmiany zapomnianego hasła. Link jest przeterminowany");
                throw new ForgottenPasswordTokenExpiredException("forgottenPasswordTokenExpired");
            }
        }
        account.setPassword(new BCryptPasswordEncoder().encode(rpf.getPassword()));
        accountRepository.save(account);
        /*Usunięcie tokena*/
        if (token.isPresent()) {
            try {
                forgottenPasswordTokenRepository.delete(token.get().getForgottenPasswordTokenId());
            } catch (Exception e) {
                logger.error("Błąd usuwania tokena");
            }
        }
        return account;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        Date date = cal.getTime();
        logger.info("date.toString() {} ", date.toString());
        return date;
    }

    /**
     * Funkcja wykonuje się co godzinę każdego dnia. Usuwane są przeterminowane
     * TOKENY POTWIERDZAJĄCE WERYFIKACJĘ KONTA.
     *
     * @Scheduled(cron = "0 0 * * * *") - co godzinę
     * @Scheduled(cron = "* /10 * * * * *") - co 10 sek
     */
    @Override
    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void deleteAllExpiredTokens() {
        try {
            //verificationTokenRepository.deleteOldTokens();
            accountRepository.deleteOldTokensAndAccounts();
        } catch (Exception e) {
            logger.error("Błąd usuwania przeterminowanych tokenów weryfikacyjnych, {}", e.getMessage());
        }

        logger.info("Usuwanie przeterminowanych tokenów weryfikacyjnych.");
    }

    /**
     * Funkcja wykonuje się co godzinę każdego dnia. Usuwane są przeterminowane
     * TOKENY UMOŻLIWIAJĄCE ZMIANĘ ZAPOMNIANEGO HASŁA.
     */
    @Override
    @Transactional
    @Scheduled(cron = "30 */30 * * * *")
    public void deleteAllExpiredTokensForForgottenPasswords() {
        try {
            logger.info("deleteAllExpiredTokensForForgottenPasswords.");
            forgottenPasswordTokenRepository.deleteExpiredTokens();
        } catch (Exception ex) {
            logger.error("Blad usuwania przeterminowanych tokenow do resetu zapomnianego hasla");
        }
    }

    /**
     * Zwraca null jeśli jest przeterminowany, lub ID konta użytkownika jeśli
     * nie jest przeterminowany
     *
     * @param passToken
     * @return
     */
    @Override
    public Integer isTokenExpired(String passToken) {
        Optional<ForgottenPasswordToken> fpt = forgottenPasswordTokenRepository.findOneByToken(passToken);
        if (fpt.isPresent()) {
            if (fpt.get().getExpirationDate().after(new Date())) {
                return fpt.get().getAccountId().getAccountId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Pobiera kod z bazy danych, ustalony wcześniej przez administratora lub
     * opiekuna praktyk, następnie porównuje kod z bazy z kodem wpisanym w
     * formularzu rejestracji. Zwraca true jeśli kod się zgadza.
     *
     * Jeżeli nie administrator nie ustalił kodu, to zwracana jest prawda.
     *
     * @param codeFromForm kod wpisany w formularzu rejestracji
     * @return Czy kody są takie same.
     */
    public Boolean isSecretCodeCorrect(String codeFromForm) {
        if (secretCodeRepository.findOne(1) != null) {
            return secretCodeRepository.findOne(1).getCode().equals(codeFromForm);
        } else {
            return true;
        }
    }

    public Boolean isLoginUnique(String loginFromForm) {
        /*Jeśli taki login istnieje już w bazie, to zwraca false*/
        if (accountRepository.findOneByLogin(loginFromForm).isPresent()) {
            logger.info("Login nie jest unikalny");
            return false;
        } else {
            logger.info("Login jest unikalny");
            return true;
        }
    }

    public Boolean isEmailUnique(String emailFromForm) {
        if (accountRepository.findOneByEmail(emailFromForm).isPresent()) {/*email istnieje, więc nie jest unikalny*/
            return false;
        } else {
            return true;
        }
    }

    /*true jeśli jest unikalny*/
    public Boolean isIndexNoUnique(String indexNo) {
        if (!accountRepository.findByUserProfile_IndexNo(indexNo).isEmpty()) {/*index istnieje, więc nie jest unikalny*/
            return false;
        } else {
            return true;
        }
    }

}
