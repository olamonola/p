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
public class InvalidPasswordException extends ModelException {

    private static final long serialVersionUID = 2999112038814789732L;

    public InvalidPasswordException(String field, String message) {
        super(field, message);
    }

}
