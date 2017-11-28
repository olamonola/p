/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.validators;

import org.prz.entity.Company;
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
public class CompanyValidator implements Validator {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> type) {
        return Company.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Company model = (Company) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "streetNo", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", null, "Pole jest wymagane.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", null, "Pole jest wymagane.");
        
        if (model.getPhone().length() > 13){
            errors.rejectValue("phone", null, "Numer telefonu musi mieć mniej niż 14 znaków.");
        }       
        if (model.getPhone().length() < 9){
            errors.rejectValue("phone", null, "Numer telefonu musi przynajmniej 9 znaków.");
        }
        if (model.getPhone() != null && !model.getPhone()
                .matches("^\\d+$")) {
            errors.rejectValue("phone", null, "Telefon może zawierać tylko cyfry.");
        }
        if (model.getZip()!= null && !model.getZip()
                .matches("^\\d{2}-\\d{3}$")) {
            errors.rejectValue("zip", null, "Kod pocztowy składa się z dwóch cyfr, myślnika i trzech cyfr.");
        }
        
        if (model.getComments().length() > 100){
            errors.rejectValue("comments", null, "Komentarz musi mieć mniej niż 100 znaków.");
        }   
        
        if (model.getName().length() > 300){
            errors.rejectValue("name", null, "Nazwa musi mieć mniej niż 300 znaków.");
        }  
        
        if (model.getCity().length() > 60){
            errors.rejectValue("city", null, "Miejscowość musi mieć mniej niż 60 znaków.");
        } 
        
        if (model.getStreet().length() > 100){
            errors.rejectValue("city", null, "Ulica musi mieć mniej niż 100 znaków.");
        } 
        
        if (model.getStreetNo().length() > 10){
            errors.rejectValue("city", null, "Numer musi mieć mniej niż 100 znaków.");
        } 
        
    }

}
