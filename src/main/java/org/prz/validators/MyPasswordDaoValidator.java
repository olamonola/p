/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.dao.MyPasswordDao;
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
public class MyPasswordDaoValidator implements Validator{
     protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return MyPasswordDao.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MyPasswordDao model = (MyPasswordDao) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", null, "Pole jest wymagane.");
        
        if (model.getNewPassword() != null) {
            if (8 > model.getNewPassword().length()) {
                log.debug("Nieprawidłowe hasło: {}", model.getNewPassword());
                errors.rejectValue("newPassword", null,
                        "Hasło musi mieć conajmniej 8 znaków.");
            }
            if (!model.getNewPassword().matches(
                    "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).*$")) {
                log.debug("Nieprawidłowe hasło: {}", model.getNewPassword());
                errors.rejectValue("newPassword", null,
                        "Hasło musi zawierać conajmniej 1 cyfrę, "
                        + "małą literę, dużą literę i znak specjalny. "
                        + "Nie może zawierać spacji, tabulatorów ani nowych linii.");
            }
            if (!model.getNewPassword().equals(model.getRepeatPassword())) {
                log.debug("Hasła nie są takie same: {} != {}", model.getNewPassword(), model.getRepeatPassword());
                errors.rejectValue("repeatPassword", null, "Hasła muszą być takie same.");
            }
        }
    }
}
