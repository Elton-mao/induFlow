package com.compoldata.induflow.exceptions;

public class ProductionLineNotFoundException extends ModelNotfoundException{
    public ProductionLineNotFoundException(Integer productionLineNumber){
        super("A linha de produção "+ productionLineNumber + " está ativa com uma ordem em execução. Finalize a Operação na Linha atual antes de iniciar uma nova. Consulte o painel de controle ou entre em contato com o responsável pela produção para mais detalhes.");

    }
}
