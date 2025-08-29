package com.compoldata.induflow.exceptions;

public class DepartmentNotFoundException extends ModelNotfoundException{
    public DepartmentNotFoundException(){
        super("Departamento Não Encontrado"); 
    }

    
        public DepartmentNotFoundException(String message){
            super(message); 
        }
}
