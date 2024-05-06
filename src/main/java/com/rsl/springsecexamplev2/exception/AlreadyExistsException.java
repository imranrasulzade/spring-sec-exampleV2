package com.rsl.springsecexamplev2.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String msg){
        super(msg);
    }
}
