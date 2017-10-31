/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.prz.CustomUser;
import org.prz.dao.EmailChange;
import org.prz.dao.EmailDao;
import org.prz.dao.MyPasswordDao;
import org.prz.dao.MyProfileDao;
import org.prz.dao.RegisterAccount;
import org.prz.dao.ResetPasswordForm;
import org.prz.entity.Account;
import org.prz.entity.VerificationToken;
import org.prz.exception.EmailNotFoundException;
import org.prz.exception.EmailNotUnique;
import org.prz.exception.EmailNotUniqueAndCodeInvalid;
import org.prz.exception.EmailNotUniqueAndWrongPasswordException;
import org.prz.exception.ForgottenPasswordTokenExpiredException;
import org.prz.exception.InvalidPasswordException;
import org.prz.exception.LoginAndEmailNotUnique;
import org.prz.exception.LoginAndEmailNotUniqueAndCodeInvalid;
import org.prz.exception.LoginNotUniqueAndCodeInvalid;
import org.prz.exception.ModelException;
import org.prz.service.AccountService;
import org.prz.service.ManagementService;
import org.prz.validators.EmailChangeValidator;
import org.prz.validators.EmailDaoValidator;
import org.prz.validators.MyPasswordDaoValidator;
import org.prz.validators.MyProfileDaoValidator;
import org.prz.validators.RegisterAccountValidator;
import org.prz.validators.ResetPasswordFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ola
 */
@SessionAttributes({"myProfile"})
@Controller
@RequestMapping("/Konto")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService service;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RegisterAccountValidator validator;

    @Autowired
    private MyProfileDaoValidator profileValidator;

    @Autowired
    private MyPasswordDaoValidator passwordValidator;

    @Autowired
    private EmailDaoValidator emailDaoValidator;

    @Autowired
    private EmailChangeValidator emailChangeValidator;

    @Autowired
    private ResetPasswordFormValidator resetPasswordFormValidator;

    @InitBinder("emailModel")
    private void changeEmailBinder(WebDataBinder binder) {
        binder.setValidator(emailChangeValidator);
    }

    @InitBinder("registerAccount")
    private void registerAccountBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @InitBinder("myProfile")
    private void myProfileBinder(WebDataBinder binder) {
        binder.setValidator(profileValidator);
    }

    @InitBinder("myPassword")
    private void myPasswordBinder(WebDataBinder binder) {
        binder.setValidator(passwordValidator);
    }

    @InitBinder("myEmail")
    private void myEmailForForgottenPasswordBinder(WebDataBinder binder) {
        binder.setValidator(emailDaoValidator);
    }

    @InitBinder("resetPassword")
    private void resetForgottenPasswordBinder(WebDataBinder binder) {
        binder.setValidator(resetPasswordFormValidator);
    }

    @GetMapping("/rejestracja")
    public String registerAccountForm(@ModelAttribute("registerAccount") RegisterAccount registerAccount,
            BindingResult result) {
        return "account/register";
    }

    @PostMapping("/rejestracja")
    public String registerAccountFormSubmit(
            @ModelAttribute("registerAccount") @Valid RegisterAccount registerAccount,
            BindingResult result,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            logger.info("Blad rejestracji.");
            return "account/register";
        }
        logger.info("Zapis rejestracji");
        String baseUrl = this.getBaseUrl(request);
        Account a = new Account();
        try {
            a = service.saveAccount(registerAccount);
            if (a != null) {
                VerificationToken token = service.saveVerificationToken(a);
                if (token != null) {
                    redirectAttributes.addFlashAttribute("msg", "Dziękujemy za rejestrację. Formularz wysłany poprawnie. Na Towją skrzynkę pocztową został wysłany e-mail z prośbą o potwierdzenie, w celu dokończenia rejestracji.");
                    //try {
                        service.sendTokenViaEmail(token, registerAccount.getEmail(), baseUrl);
                        return "redirect:/";
                   // } catch (MailException ex) {
                    //    redirectAttributes.addFlashAttribute("errormsg", "Wystąpił błąd wysyłania emaila potwierdzającego rejestrację.");
                   //     return "redirect:/";
                   // }

                } else {
                    logger.error("Błąd rejestracji");
                    redirectAttributes.addFlashAttribute("errormsg", "W czasie wysyłania formularza wystąpił błąd.");
                    return "redirect:/Konto/rejestracja";
                }
            } else {
                logger.error("Błąd rejestracji");
                redirectAttributes.addFlashAttribute("errormsg", "W czasie wysyłania formularza wystąpił błąd.");
                return "redirect:/Konto/rejestracja";
            }

        } catch (LoginAndEmailNotUniqueAndCodeInvalid exc) {
            logger.error("Błąd rejestracji. LoginAndEmailNotUniqueAndCodeInvalid exception {}", exc.getMessage());
            result.rejectValue("login", "loginNotUnique");
            result.rejectValue("email", "emailNotUnique");
            result.rejectValue("secretCode", "secretCodeIncorrect");
            return "account/register";
        } catch (LoginAndEmailNotUnique ex) {
            logger.error("Błąd rejestracji. LoginAndEmailNotUnique exception {}", ex.getMessage());
            result.rejectValue("login", "loginNotUnique");
            result.rejectValue("email", "emailNotUnique");
            return "account/register";
        } catch (EmailNotUniqueAndCodeInvalid exc) {
            logger.error("Błąd rejestracji. EmailNotUniqueAndCodeInvalid exception {}", exc.getMessage());
            result.rejectValue("email", "emailNotUnique");
            result.rejectValue("secretCode", "secretCodeIncorrect");
            return "account/register";
        } catch (LoginNotUniqueAndCodeInvalid exc) {
            logger.error("Błąd rejestracji. LoginNotUniqueAndCodeInvalid exception {}", exc.getMessage());
            result.rejectValue("login", "loginNotUnique");
            result.rejectValue("secretCode", "secretCodeIncorrect");
            return "account/register";
        } catch (ModelException ex) {
            logger.error("Błąd rejestracji. ModelException {}", ex.getField());
            result.rejectValue(ex.getField(), ex.getMessage());
            return "account/register";
        } catch (DataIntegrityViolationException e) {
             logger.error("Błąd rejestracji");
             redirectAttributes.addFlashAttribute("errormsg", "W czasie wysyłania formularza wystąpił błąd.");
             return "redirect:/Konto/rejestracja";            
        }
    }

    @RequestMapping(value = "/weryfikacja", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("errormsg", "Błąd potwierdzenia rejestracji. Spróbuj zarejestrować się ponownie.");
            return "redirect:/";
        }
        Account account = verificationToken.getAccountId();
        Calendar cal = Calendar.getInstance();
        if (verificationToken.getExpirationDate().before(new Date())) {
            model.addAttribute("errormsg", "Wystąpił błąd potwierdzenia rejestracji. Potwierdzenie wykonane zbyt późno.");
            service.deleteTokenAndAccount(verificationToken);
            return "redirect:/";
        }
        account.setVerified(true);
        service.saveRegisteredUser(account);
        model.addAttribute("msg", "Potwierdzenie rejestracji przebiegło poprawnie. Użytkownik może się zalogować.");
        service.deleteToken(verificationToken);
        return "redirect:/login";
    }

    private String getBaseUrl(HttpServletRequest request) {
        String baseUrl = new String();
        baseUrl = managementService.getServer().getName();
        logger.info("baseUrl {}", baseUrl);
        return baseUrl;
    }

    @GetMapping("/profil")
    public String myProfileView(Model model) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        model.addAttribute("account", a);
        return "account/myProfile";
    }

    @GetMapping("/zmianaMaila")
    public String changeEmail(Model model) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        if (a == null) {
            logger.error("Konto nie istnieje");
        }
        model.addAttribute("emailModel", new EmailChange(a.getEmail()));
        return "account/editEmail";
    }

    @PostMapping("/zmianaMaila")
    public String changeEmailPost(@ModelAttribute("emailModel") @Valid EmailChange emailModel,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "account/editEmail";
        }
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        try {
            logger.info("Edycja profilu o ID {}", a.getAccountId());
            service.editMyEmail(a, emailModel);
            redirectAttributes.addFlashAttribute("msg", "Email został pomyślnie zapisany.");
            return "redirect:/Konto/profil";
        } catch (EmailNotUniqueAndWrongPasswordException ex) {
            result.rejectValue("email", "emailNotUnique");
            result.rejectValue("password", "invalidPassword");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "account/editEmail";
        } catch (EmailNotUnique ex) {
            result.rejectValue("email", "emailNotUnique");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "account/editEmail";
        } catch (InvalidPasswordException ex) {
            result.rejectValue(ex.getField(), ex.getMessage());
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "account/editEmail";
        }

    }

    @GetMapping("/edycjaProfilu")
    public String editMyProfileForm(
            ModelMap model) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        if (a == null) {
            logger.error("Konto nie istnieje");
        }
        MyProfileDao profile2 = new MyProfileDao();
        if (!model.containsAttribute("myProfile")) {
            logger.info("Model nie ma atrybutu");
            profile2 = new MyProfileDao(a);
        }
        if (model.containsAttribute("myProfile")) {
            logger.info("Model ma atrybut");
            profile2 = (MyProfileDao) model.get("myProfile");
        }
        model.addAttribute("myProfile", profile2);
        return "account/editProfile";
    }

    @PostMapping("/edycjaProfilu")
    public String editMyProfilePost(@ModelAttribute("myProfile") @Valid MyProfileDao myProfile,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            logger.info("result{}", result.getAllErrors().toString());
            return "account/editProfile";
            //return "redirect:/Konto/edycjaProfilu";
        }
        try {
            logger.info("Edycja profilu o ID {}", a.getAccountId());
            service.editMyProfile(a, myProfile);
            redirectAttributes.addFlashAttribute("msg", "Profil został pomyślnie zapisany.");
            return "redirect:/Konto/profil";
        } catch (ModelException ex) {
            result.rejectValue(ex.getField(), ex.getMessage());
            logger.info("Bledy: {}", result.getAllErrors().toString());
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "account/editProfile";
        }
    }

    /**
     * Zmiana hasła, jeśli użytkownik jest zalogowany.
     *
     * @param myPassword
     * @param result
     * @param model
     * @return
     */
    @GetMapping("/zmianaHasla")
    public String editMyPasswordForm(@ModelAttribute("myPassword") MyPasswordDao myPassword,
            BindingResult result, Model model) {
        model.addAttribute("myPassword", myPassword);
        return "account/editPassword";
    }

    @PostMapping("/zmianaHasla")
    public String editMyPasswordPost(@ModelAttribute("myPassword") @Valid MyPasswordDao myPassword,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "account/editPassword";
        }
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        try {
            logger.info("Edycja hasła");
            service.changePassword(a, myPassword);
            redirectAttributes.addFlashAttribute("msg", "Hasło pomyślnie zapisane.");
            return "redirect:/Konto/zmianaHasla";
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Błąd zmiany hasła.");
            return "redirect:/Konto/zmianaHasla";
        }
    }

    /**
     * Jeśli hasło zostało zapomniane, użytkownik podaje maila w formularzu. Na
     * tem e-mail wysyłany jest link aktywny przez około 30 minut.
     *
     * @param myEmail
     * @param result
     * @param model
     * @return
     */
    @GetMapping("/zapomnianeHaslo")
    public String emailForForgottenPasswordForm(@ModelAttribute("myEmail") EmailDao myEmail,
            BindingResult result, Model model) {
        model.addAttribute("myEmail", myEmail);
        return "account/giveEmailForForgottenPassword";
    }

    @PostMapping("/zapomnianeHaslo")
    public String emailForForgottenPasswordPost(@ModelAttribute("myEmail") @Valid EmailDao myEmail,
            BindingResult result,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "account/zapomnianeHaslo";
        }
        String baseUrl = this.getBaseUrl(request);
        try {
            logger.info("Wysyłanie tokena do resetowania zapomnianego hasła");
            service.savePasswordTokenAndSendEmail(myEmail, baseUrl);
            redirectAttributes.addFlashAttribute("msg", "Na podany email została wysłana wiadomość.");
            return "redirect:/";
        } catch (EmailNotFoundException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Wystąpił błąd. Użytkownik z podanym emailem nie istnieje.");
            return "redirect:/Konto/zapomnianeHaslo";
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Wystąpił błąd.");
            return "redirect:/Konto/zapomnianeHaslo";
        }
    }

    @RequestMapping(value = "/zmianaZapomnianegoHasla", method = RequestMethod.GET)
    public String changeForgottenPasswordForm(WebRequest request, Model model,
            @RequestParam("passToken") String passToken,
            @ModelAttribute("resetPassword") ResetPasswordForm resetPassword,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (service.isTokenExpired(passToken) != null) {
            ResetPasswordForm resetPass = new ResetPasswordForm(service.isTokenExpired(passToken));
            model.addAttribute("resetPassword", resetPass);
            return "account/editForgottenPassword";
        } else {
            redirectAttributes.addAttribute("errormsg", "Link do resetowania konta jest już przeterminowany. Aby ponownie otrzymać link do zresetowania hasła, wypełnij poniższy formularz.");
            return "redirect:/Konto/zapomnianeHaslo";
        }
    }

    @PostMapping("/zmianaZapomnianegoHasla")
    public String changeForgottenPasswordPost(@ModelAttribute("resetPassword") @Valid ResetPasswordForm resetPassword,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "account/editForgottenPassword";
        }
        try {
            logger.info("Zmiana zapomnianego hasła.");
            service.changePasswordAndDeleteToken(resetPassword);
            redirectAttributes.addFlashAttribute("msg", "Hasło pomyślnie zapisane.");
            return "redirect:/";
        } catch (ForgottenPasswordTokenExpiredException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", ex.getMessage());
            return "redirect:/Konto/zmianaZapomnianegoHasla?passToken=" + resetPassword.getToken();
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Wystąpił błąd.");
            return "redirect:/Konto/zmianaZapomnianegoHasla?passToken=" + resetPassword.getToken();
        }
    }

}
