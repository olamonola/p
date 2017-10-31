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
public class EmailNotUniqueAndCodeInvalid extends RuntimeException{
    
    private static final long serialVersionUID = 1185881841698628867L;

    public EmailNotUniqueAndCodeInvalid(String message) {
        super(message);
    }
    
}
