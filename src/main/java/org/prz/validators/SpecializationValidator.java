/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.entity.Specialization;
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
public class SpecializationValidator implements Validator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return Specialization.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Specialization model = (Specialization) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "Pole jest wymagane.");
        log.info("Pole nazwy specjalizacji jest puste"); }

}
