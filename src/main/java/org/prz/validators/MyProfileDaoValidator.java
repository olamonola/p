/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.dao.MyProfileDao;
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
public class MyProfileDaoValidator implements Validator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return MyProfileDao.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MyProfileDao model = (MyProfileDao) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indexNo", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", null, "Pole jest wymagane.");

        if (model.getFirstName() != null) {
            if (model.getFirstName().length() > 30) {
                log.info("Błędna długość imienia");
                errors.rejectValue("firstName", "Imię może mieć najwyżej 30 znaków.");
            }
        }

        if (model.getLastName() != null) {
            if (model.getLastName().length() > 30) {
                log.info("Błędna długość nazwisko");
                errors.rejectValue("lastName", null, "Nazwisko może mieć najwyżej 30 znaków.");
            }
        }

        if (model.getIndexNo() != null && !model.getIndexNo()
                .matches("^\\d+$")) {
            log.info("Nieprawidłowy numer indeksu: {}", model.getIndexNo());
            errors.rejectValue("indexNo", null, "Numer indeku może zawierać tylko cyfry.");
        }

        if (model.getIndexNo() != null) {
            if (model.getIndexNo().length() < 5 || model.getIndexNo().length() > 5) {
                log.info("Błędna długość numeru indeksu");
                errors.rejectValue("indexNo", null, "Numer indeksu powinien mieć dokładnie 5 cyfr.");
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
