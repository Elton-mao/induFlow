package com.compoldata.induflow.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StopCodes {
    SEPARACAO_MATERIAL("Separação de Material"),
    CONFIGURACAO_MANUTENCAO("Configuração de Manutenção"),
    AGUARDANDO_PESAGEM("Aguardando Pesagem"),
    ANALISE_LABORATORIAL("Análise de Laboratório"),
    PREPARACAO_MISTURA("Preparação de Mistura"),
    PRODUCAO_INICIADA("Produção Iniciada"),
    CONFIGURACAO_PRODUCAO("Configuração de Produção"),
    FALTA_DE_ENERGIA("Falta de Mão de Energia"),
    FALTA_DE_MÃO_DE_OBRA("Falta de Mão de Obra"),
    MANUTENÇÃO_ELETRICA("Manutenção Elétrica"),
    TROCA_DE_TELA("Troca de Tela"), 
    TROCA_DE_ROSCA_EXTRUSORA("Troca de Rosca Extrussora"),
    AJUSTE_DE_PROCESSO_POROSIDADE("Ajuste de Processo Porosidade"),
    ATRASO_NO_PAGAMENTO_DE_ISUMO("Atraso No Pagamento de Isumo");       
    private final String descricao;

}
