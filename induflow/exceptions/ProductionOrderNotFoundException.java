package com.compoldata.induflow.exceptions;

public class ProductionOrderNotFoundException extends ModelNotfoundException{
   
    public ProductionOrderNotFoundException(){
        super("Ordem De Produção Não Encontrada ");
    }

    public ProductionOrderNotFoundException(String message){
        super(message);
    }
}
