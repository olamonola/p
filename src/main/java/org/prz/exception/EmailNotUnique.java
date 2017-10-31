/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.exception;

/**
 *
 * @author Ola
 */
public class EmailNotUnique extends ModelException{

    private static final long serialVersionUID = 8490880051638482382L;
    
    public EmailNotUnique(String field, String message) {
        super(field, message);
    }
    
}
