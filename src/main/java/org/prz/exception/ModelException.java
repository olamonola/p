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
public class ModelException extends RuntimeException {

    private static final long serialVersionUID = -7808396136535585717L;

    private final String field;

    /**
     * Tworzy nowy wyjątek systemu.
     *
     * @param field nazwa pola, które wywołało błąd
     * @param message opis błędu
     */
    public ModelException(String field, String message) {
        super(message);
        this.field = field;
    }

    /**
     * Pobiera nazwę pola, które wywołało błąd.
     *
     * @return nazwę pola, które wywołało błąd
     */
    public String getField() {
        return field;
    }
}
