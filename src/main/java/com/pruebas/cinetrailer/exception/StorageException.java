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


public class StorageException extends RuntimeException{
   
    public StorageException(String message){
        super(message);
    }
    public StorageException(String message,Throwable eThrowable){
        super(message, eThrowable);
    }
}
