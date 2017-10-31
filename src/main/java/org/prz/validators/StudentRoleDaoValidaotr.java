/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.dao.StudentRoleDao;
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
public class StudentRoleDaoValidaotr implements Validator{
   protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return StudentRoleDao.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        StudentRoleDao model = (StudentRoleDao) o;
        //Wszystkie wymagane pola
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleEndTime", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleStartTime", null, "Pole jest wymagane.");


        if (model.getRoleStartTime() != null && model.getRoleEndTime() != null) {
            if (model.getRoleStartTime().after(model.getRoleEndTime())) {
                errors.rejectValue("roleEndTime", null, "Data rozpoczęcia powinna być wcześniej niż data zakończenia");
                errors.rejectValue("roleStartTime", null, "Data rozpoczęcia powinna być wcześniej niż data zakończenia");
            }
        }
        
        
    }

}
