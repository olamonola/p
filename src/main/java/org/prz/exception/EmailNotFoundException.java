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
public class EmailNotFoundException extends RuntimeException{
    
    private static final long serialVersionUID = 1628378884177115857L;

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
    
}
