package com.compoldata.induflow.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.DTO.response.ToOcurrence;
import com.compoldata.induflow.DTO.response.ToProductionOrderLog;
import com.compoldata.induflow.exceptions.ClienteNotFoundException;
import com.compoldata.induflow.exceptions.OcurrenceNotFoundException;
import com.compoldata.induflow.exceptions.ProductionOrderNotFoundException;
import com.compoldata.induflow.integration.sigen.model.ExternalProductionOrder;
import com.compoldata.induflow.model.Product;
import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.enums.OccurrenceStatus;
import com.compoldata.induflow.model.enums.ProductionOrderStatus;
import com.compoldata.induflow.repository.appRepositories.ClientRepository;
import com.compoldata.induflow.repository.appRepositories.ProductionOrderRepository;
import com.compoldata.induflow.repository.appRepositories.ProductionRepository;

import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço para gerenciar ordens de produção.
 * 
 * Este serviço fornece métodos para listar, iniciar, parar e finalizar ordens
 * de produção,
 * bem como adicionar quantidades produzidas e recuperar detalhes das ordens de
 * produção.
 * 
 * Métodos:
 * - listAllOpenProductionOrders: Lista todas as ordens de produção abertas.
 * - listAllCloseProductionOrders: Lista todas as ordens de produção fechadas.
 * - listAllIsRuningProductionOrders: Lista todas as ordens de produção que
 * estão em andamento.
 * - listAllIsSetupProductionOrders: Lista todas as ordens de produção que estão
 * em setup.
 * - productionOrderInformation: Recupera detalhes de uma ordem de produção pelo
 * seu ID.
 * - startProductionOrder: Inicia uma ordem de produção.
 * - endProductionOrder: Finaliza uma ordem de produção.
 * - handleProductionOrderStop: Pausa uma ordem de produção registrando uma
 * ocorrência.
 * - addProducedAmount: Adiciona a quantidade produzida a uma ordem de produção.
 * 
 * Exceções:
 * - ProductionOrderNotFoundException: Lançada quando nenhuma ordem de produção
 * é encontrada em setup.
 * - IllegalArgumentException: Lançada quando um ID ou quantidade inválida é
 * fornecido.
 * - IllegalStateException: Lançada quando há falha ao iniciar ou parar a linha
 * de produção.
 */
@Service
public class ProductionOrderService {

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ProductionLineService productionLineService;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductionOrderLogService productionOrderLogService;

    @Autowired 
    AutheticationService autheticationService; 

    // lista todas as ordens de produção que estão abertas
    public List<ProductionOrder> listAllOpenProductionOrders() {
        return productionOrderRepository.findByStatus(ProductionOrderStatus.PENDENTE);
    }

    // lista todas as ordem de produção que já foram fechadas
    public List<ProductionOrder> listAllCloseProductionOrders() {
        return productionOrderRepository.findByStatus(ProductionOrderStatus.FINALIZADA);
    }

    // lista todas as ordens de produção que estão em andamento
    public List<ProductionOrder> listAllIsRuningProductionOrders() {
        return productionOrderRepository.findByStatus(ProductionOrderStatus.PRODUZINDO);
    }

    public List<ProductionOrder> getActiveOrPendingProductionOrders(List<ProductionOrderStatus> status) {
        return productionOrderRepository.findByStatusIn(status); 
    }
    
    // lista todas as ordens de produção que estão em setup
    public List<ProductionOrder> listAllIsSetupProductionOrders() {
        var productionOrdersisSetup = productionOrderRepository.findByStatus(ProductionOrderStatus.SETUP);
        if (productionOrdersisSetup.isEmpty()) {
            throw new ProductionOrderNotFoundException("Não há ordens de produção em setup");
        }
        // filtra apenas os setups ativos
        List<ProductionOrder> activeProductionOrderSetup = productionOrdersisSetup.stream()
                .filter(order -> order.getOcurrences() != null && order.getOcurrences().stream()
                        .anyMatch(occurence -> occurence.getStatus().equals(OccurrenceStatus.ATIVO)))
                .toList();
        return activeProductionOrderSetup;
    }

    // lista detalhes da ordem de produção por id
    public ProductionOrder productionOrderInformation(Long productionId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionId)
                .orElseThrow(() -> new IllegalArgumentException("id informado não encontrado"));
        return productionOrder;

    }

    // inicia a op
    @Transactional(transactionManager = "appTransactionManager")
    public void startProductionOrder(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new IllegalArgumentException("id não existe"));

        // Tenta iniciar a linha de produção, mas se falhar, a transação será revertida
        try {
            productionLineService.startLineProduction(productionOrder.getProductionLine().getProductionLineId(),
                    productionOrderId);
        } catch (Exception e) {
            // Lança uma exceção para garantir que a transação será revertida
            throw new IllegalStateException("Falha ao iniciar a linha de produção", e);
        }
        // altera o estado da linha de produção
        productionOrder.setStatus(ProductionOrderStatus.PRODUZINDO);
        // captura a hora atual do inicio da produção
        productionOrder.setStartProductionDateTime(LocalDateTime.now());

        // envia email para o responsável pela ordem de produção /// EM DESENVOLVIMENTO
        // **********************
        // emailSenderService.emailSender("elton.m.almeida@hotmail.com", "produção
        // iniciada", message);
        productionOrderRepository.save(productionOrder);
    }

    /**
     * Finaliza a ordem de produção com base no ID fornecido.
     * 
     * Este método realiza uma série de operações de forma transacional para
     * garantir a consistência
     * dos dados. As etapas incluem:
     * 1. Busca da ordem de produção pelo ID. Lança uma exceção se o ID não existir
     * no banco de dados.
     * 2. Tentativa de parar a linha de produção associada à ordem.
     * - Caso ocorra uma falha, a transação é revertida, e uma exceção é lançada.
     * 3. Atualização do status da ordem de produção para "FINALIZADA".
     * 4. Registro da data e hora de finalização da produção.
     * 5. Persistência das alterações no repositório.
     * 
     * @param productionOrderId ID da ordem de produção a ser finalizada.
     * @throws IllegalArgumentException se o ID fornecido não for encontrado.
     * @throws IllegalStateException    se houver falha ao pausar a linha de
     *                                  produção.
     */

    @Transactional(transactionManager = "appTransactionManager")
    public void endProductionOrder(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new IllegalArgumentException("id não existe"));

        // modifica o status da linha de produção prafa false = parada
        try {
            productionLineService.stopLineProduction(productionOrder.getProductionLine().getProductionLineId(),
                    productionOrderId);
        } catch (Exception e) {
            // Lança uma exceção para garantir que a transação será revertida
            throw new IllegalStateException("Falha ao parar linha de produção", e);
        }
        // altera o estado da linha de produção
        productionOrder.setStatus(ProductionOrderStatus.FINALIZADA);
        // captura a hora atual do inicio da produção
        productionOrder.setEndproductionDateTime(LocalDateTime.now());

        productionOrderRepository.save(productionOrder);
    }

    /**
     * Pausa a ordem de produção ao registrar uma ocorrência.
     * 
     * Este método realiza as seguintes etapas de forma atômica:
     * 1. Busca a ordem de produção pelo ID informado.
     * - Lança uma exceção caso o ID não seja encontrado.
     * 2. Pausa a linha de produção associada à ordem.
     * - Em caso de falha na operação, uma exceção é lançada para reverter a
     * transação.
     * 3. Atualiza o status da ordem de produção para "PARADA".
     * 4. Registra a data e hora de finalização da produção.
     * 5. Persiste as alterações no banco de dados.
     * 
     * @param productionOrderId ID da ordem de produção a ser pausada.
     * @throws IllegalArgumentException se o ID fornecido não existir no banco de
     *                                  dados.
     * @throws IllegalStateException    se ocorrer uma falha ao tentar pausar a
     *                                  linha de produção.
     */

    @Transactional(transactionManager = "appTransactionManager")
    public void handleProductionOrderStop(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new IllegalArgumentException("id não existe"));

        // altera o estado da linha de produção
        productionOrder.setStatus(ProductionOrderStatus.PARADA);

        // captura a hora atual do inicio da produção
        productionOrder.setEndproductionDateTime(LocalDateTime.now());

        productionOrderRepository.save(productionOrder);
    }

    // entrada de material
    public void addProducedAmount(Long productionOrderId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inserido incorreto");
        }
        ProductionOrder productionOrderAmoutupdate = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new IllegalArgumentException("id informado invalido"));
        productionOrderAmoutupdate.setProducedAmount(productionOrderAmoutupdate.getProducedAmount().add(amount));
        productionOrderLogService.registerProductionOrderLog(productionOrderAmoutupdate,
                "ENTRADA DE MATERIAL REGISTRADA", amount,autheticationService.getAutheticatedUser().getEmployeeName());

        productionOrderRepository.save(productionOrderAmoutupdate);
    }

    /**
     * Registra uma nova ordem de produção com base na ordem de produção externa
     * fornecida.
     *
     * //Cadastra OP vindas do SIGEN
     * 
     * @param externalProductionOrder a ordem de produção externa contendo os
     *                                detalhes a serem registrados
     */
    /**
     * Registra uma nova ordem de produção com base na ordem de produção externa
     * fornecida.
     * Se uma ordem de produção com o mesmo número já existir, não criará uma nova.
     * 
     * @param externalProductionOrder a ordem de produção externa a ser registrada
     * 
     * @Transactional garante que o método seja executado dentro de uma transação
     *                gerenciada por "appTransactionManager".
     * 
     *                O método realiza os seguintes passos:
     *                1. Verifica se uma ordem de produção com o número fornecido já
     *                existe.
     *                2. Se não existir, cria uma nova ordem de produção e define
     *                suas propriedades com base na ordem de produção externa.
     *                3. Define o status da ordem de produção como PENDENTE se o
     *                status da ordem de produção externa for "Em Aberto".
     *                4. Associa um cliente mock e uma linha de produção padrão à
     *                nova ordem de produção.
     *                5. Verifica se o produto existe no banco de dados, e se não
     *                existir, cria um novo produto.
     *                6. Associa o produto à nova ordem de produção.
     *                7. Salva a nova ordem de produção no repositório.
     * 
     * @throws ClienteNotFoundException se o cliente mock com ID 1 não for
     *                                  encontrado no repositório.
     */
    @Transactional(transactionManager = "appTransactionManager")
    public void registerProductionOrder(ExternalProductionOrder externalProductionOrder) {
        productionOrderRepository
                .findByProductionOrderNumber(externalProductionOrder.getProductionOrderNumber())
                .orElseGet(() -> {
                    var newProductionOrder = new ProductionOrder();
                    newProductionOrder.setProductionOrderNumber(externalProductionOrder.getProductionOrderNumber());
                    newProductionOrder.setAmount(externalProductionOrder.getAmount());

                    // Verifica se a ordem de produção está "Em Aberto"
                    if ("Em Aberto                                                   "
                            .equals(externalProductionOrder.getProductionOrderStatus())) {
                        newProductionOrder.setStatus(ProductionOrderStatus.PENDENTE);
                    }

                    // Define um cliente mock, pois o PCP não registra o cliente na OP
                    var client = clientRepository.findById(1L).orElseThrow(ClienteNotFoundException::new);
                    // defini uma linha de produção 01 como padrão para todas as ordens de produção
                    // vindas do SIGEN
                    var productionline = productionLineService.getProductionLineById(1L);
                    newProductionOrder.setClient(client);
                    newProductionOrder.setProductionLine(productionline);
                    // Verifica se o produto existe no banco de dados, senão cria um novo
                    var product = productionRepository.findByProductCode(externalProductionOrder.getMaterialCode())
                            .orElseGet(() -> {
                                var newProduct = new Product();
                                newProduct.setProductCode(externalProductionOrder.getMaterialCode());
                                newProduct.setDescription(externalProductionOrder.getDescription());
                                return productionRepository.save(newProduct);
                            });

                    // Associa o produto à ordem de produção
                    newProductionOrder.setProduct(product);

                    // Salva a nova ordem de produção
                    return productionOrderRepository.save(newProductionOrder);
                });
    }

    /**
     * Recupera uma lista de ocorrências associadas a uma determinada ordem de
     * produção.
     *
     * @param productionOrderId o ID da ordem de produção
     * @return uma lista de objetos ToOcurrence representando as ocorrências
     * @throws OcurrenceNotFoundException se a ordem de produção com o ID fornecido
     *                                    não for encontrada
     */
    public List<ToOcurrence> listOccurrencesForProductionOrder(Long productionOrderId) {
        var productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new OcurrenceNotFoundException());
        return productionOrder.getOcurrences().stream()
                .map(ToOcurrence::toOcurrence)
                .toList();
    }

    /**
     * Retrieves the logs associated with a specific production order.
     *
     * @param ProductionOrderId the ID of the production order for which logs are to be retrieved
     * @return a list of ToProductionOrderLog objects representing the logs of the specified production order
     * @throws ProductionOrderNotFoundException if the production order with the given ID is not found
     */
    public List<ToProductionOrderLog> getLogs(Long ProductionOrderId) {
        var productionOrder = productionOrderRepository.findById(ProductionOrderId)
                .orElseThrow(() -> new ProductionOrderNotFoundException());
        return productionOrder.getProductionOrderLogs().stream()
                .map(ToProductionOrderLog::toProductionOrderLog)
                .toList();

    }

}
