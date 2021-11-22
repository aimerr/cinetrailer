/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author aimer
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{
    
    
    public FileNotFoundException(String message){
        super(message);
    }
    public FileNotFoundException(String message,Throwable eThrowable){
        super(message, eThrowable);
    }
}
