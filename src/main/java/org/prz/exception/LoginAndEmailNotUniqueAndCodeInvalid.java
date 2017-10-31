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
public class LoginAndEmailNotUniqueAndCodeInvalid extends RuntimeException{
    
    private static final long serialVersionUID = 5228996712142246514L;

    public LoginAndEmailNotUniqueAndCodeInvalid(String message) {
        super(message);
    }
    
}
