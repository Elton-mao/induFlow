package com.compoldata.induflow.exceptions;

public class OcurrenceNotFoundException extends ModelNotfoundException{
    public OcurrenceNotFoundException(){
        super("Ocorrência Não Encontrada"); 

    }
    public OcurrenceNotFoundException(String message){
        super(message); 
    }
    
}
