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
public class ForgottenPasswordTokenExpiredException extends RuntimeException{
    
    private static final long serialVersionUID = -2747011064751422918L;

    public ForgottenPasswordTokenExpiredException(String message) {
        super(message);
    }
    
}
