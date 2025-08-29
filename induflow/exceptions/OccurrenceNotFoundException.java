package com.compoldata.induflow.exceptions;

public class OccurrenceNotFoundException extends ModelNotfoundException{
    public OccurrenceNotFoundException(){
        super("Ocorrência Não Encontrada"); 

    }
    public OccurrenceNotFoundException(String message){
        super(message); 
    }
}
