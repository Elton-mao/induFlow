package com.compoldata.induflow.exceptions;

public class ExternalProductionOrderNotFoundException extends ModelNotfoundException{
    public ExternalProductionOrderNotFoundException(String message) {
            super(message);
        }
    
        public ExternalProductionOrderNotFoundException(){
        super("erro ao registra cliente");
    }
}
