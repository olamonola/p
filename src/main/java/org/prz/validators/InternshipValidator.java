/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.entity.Internship;
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
public class InternshipValidator implements Validator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return Internship.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Internship model = (Internship) o;
        //Wszystkie wymagane pola
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "systemSystemId.systemId", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "facultyId.facultyId", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specializationId.specializationId", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "internshipTypeId.internshipTypeId", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyStreetNo", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyCity", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyZip", null, "Pole jest wymagane.");
        
        if (model.getYear() != null) {
            if (model.getYear() < 1 || model.getYear() > 4) {
                log.info("Rok musi być w przedziale 1-4.");
                errors.rejectValue("year", null, "Rok musi być w przedziale 1-4.");
            }
        }

        if (model.getStartDate() != null && model.getEndDate() != null) {
            if (model.getStartDate().after(model.getEndDate())) {
                errors.rejectValue("endDate", null, "Data rozpoczęcia powinna być wcześniej niż data zakończenia");
                errors.rejectValue("startDate", null, "Data rozpoczęcia powinna być wcześniej niż data zakończenia");
            }
        }
        
        if(model.getComments() != null && model.getComments().length() > 150){
            errors.rejectValue("comments", null, "Maksymalna długość komentarza to 15 znaków.");
        }
        
        if(model.getCompanyOwnerFirstName() != null && model.getCompanyOwnerFirstName().length() > 30){
            errors.rejectValue("companyOwnerFirstName", null, "Maksymalna długość pola to 30 znaków.");
        }
        
        if(model.getCompanyOwnerLastName() != null && model.getCompanyOwnerLastName().length() > 30){
            errors.rejectValue("companyOwnerLastName", null, "Maksymalna długość pola to 30 znaków.");
        }
        
        if(model.getCompanyInternshipAdministratorFirstName()!= null && model.getCompanyInternshipAdministratorFirstName().length() > 30){
            errors.rejectValue("companyInternshipAdministratorFirstName", null, "Maksymalna długość pola to 30 znaków.");
        }
        
        if(model.getCompanyInternshipAdministratorLastName() != null && model.getCompanyInternshipAdministratorLastName().length() > 30){
            errors.rejectValue("companyInternshipAdministratorLastName", null, "Maksymalna długość pola to 30 znaków.");
        }
        
        if(model.getCompanyOwnerPosition()!= null && model.getCompanyOwnerPosition().length() > 30){
            errors.rejectValue("companyOwnerPosition", null, "Maksymalna długość pola to 30 znaków.");
        }
        
    }

}
