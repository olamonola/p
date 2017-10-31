/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.dao.RegisterAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Ola
 */
@Component
public class RegisterAccountValidator implements Validator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return RegisterAccount.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterAccount model = (RegisterAccount) o;
        //Wszystkie wymagane pola
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", null, "Pole jest wymagane.");

        if (model.getLogin() != null) {
            if (5 > model.getLogin().length() || model.getLogin().length() > 20) {
                log.debug("Nieprawidłowy login: {}", model.getLogin());
                errors.rejectValue("login", null, "Login musi mieć od 5 do 20 znaków.");
            }
            if (!model.getLogin().matches("^[a-z0-9_-]*$")) {
                log.debug("Nieprawidłowy login: {}", model.getLogin());
                errors.rejectValue("login", null,
                        "Login może zawierać małe litery (bez polskich), "
                        + "cyfry i znaki podkreślenia.");
            }
        }

        if (model.getPassword() != null) {
            if (8 > model.getPassword().length()) {
                log.debug("Nieprawidłowe hasło: {}", model.getPassword());
                errors.rejectValue("password", null,
                        "Hasło musi mieć conajmniej 8 znaków.");
            }
            if (!model.getPassword().matches(
                    "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).*$")) {
                log.debug("Nieprawidłowe hasło: {}", model.getPassword());
                errors.rejectValue("password", null,
                        "Hasło musi zawierać conajmniej 1 cyfrę, "
                        + "małą literę, dużą literę i znak specjalny. "
                        + "Nie może zawierać spacji, tabulatorów ani nowych linii.");
            }
            if (!model.getPassword().equals(model.getRepeatPassword())) {
                log.debug("Hasła nie są takie same: {} != {}", model.getPassword(), model.getRepeatPassword());
                errors.rejectValue("repeatPassword", null, "Hasła muszą być takie same.");
            }
        }
        
        if (model.getEmail() != null && !model.getEmail()
                .matches("^[\\w-\\+]+(\\.[\\w-]+)*@"
                        + "[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$")) {
            log.debug("Nieprawidłowy email: {}", model.getEmail());
            errors.rejectValue("email", null, "Nieprawidłowy adres e-mail.");
        }
    }

}
