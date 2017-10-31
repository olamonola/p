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
public class SecretCodeIncorrect extends ModelException{

    private static final long serialVersionUID = -1404035676588193789L;
    
    public SecretCodeIncorrect(String field, String message) {
        super(field, message);
    }
    
}
