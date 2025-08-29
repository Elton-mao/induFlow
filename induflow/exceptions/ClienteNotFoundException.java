package com.compoldata.induflow.exceptions;

public class ClienteNotFoundException extends ModelNotfoundException{
    public ClienteNotFoundException(){
        super("erro ao registra cliente");
    }
}
