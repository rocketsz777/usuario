package com.javanauta.usuario.infrastructure.exceptions;



public class UnauthorizedException extends RuntimeException {
import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException(String mensagem){
        super (mensagem);
    }
    public UnauthorizedException(String mensagem, Throwable throwable){
        super (mensagem , throwable);
    }
}
