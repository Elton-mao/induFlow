/**
 * Configuração do DataSource para o banco de dados SIGEN.
 * <p>
 * Esta classe define os beans necessários para configurar a conexão com o banco de dados SIGEN,
 * incluindo o DataSource, EntityManagerFactory e o TransactionManager.
 * </p>
 * 
 * <ul>
 *   <li>{@link #sigenDataSource()} - Cria e configura um bean DataSource para o banco de dados SIGEN.</li>
 *   <li>{@link #sigenEntityManagerFactory(EntityManagerFactoryBuilder, DataSource)} - Configura o EntityManagerFactory para o banco de dados SIGEN.</li>
 *   <li>{@link #sigenTransactionManager(EntityManagerFactory)} - Cria um gerenciador de transações para o banco de dados SIGEN.</li>
 * </ul>
 * 
 * <p>
 * As propriedades de configuração são prefixadas com "compoldata.datasource".
 * </p>
 * 
 * @see DataSource
 * @see EntityManagerFactoryBuilder
 * @see LocalContainerEntityManagerFactoryBean
 * @see PlatformTransactionManager
 * @see JpaTransactionManager
 */
package com.compoldata.induflow.config.sigenDbconfig;

import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.compoldata.induflow.repository.sigenRepositories",
        entityManagerFactoryRef = "sigenEntityManagerFactory",
        transactionManagerRef = "sigenTransactionManager"
)
public class ErpDataSourceConfig {


    /**
     * Cria e configura um bean DataSource para o banco de dados SIGEN.
     * <p>
     * Este método usa as propriedades prefixadas com "compoldata.datasource" 
     * para configurar o DataSource.
     * </p>
     *
     * @return uma instância configurada de DataSource para o banco de dados SIGEN
     */
    @Bean(name = "sigenDataSource")
    @ConfigurationProperties(prefix = "induflow.datasource")
    DataSource sigenDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configura o EntityManagerFactory para o banco de dados DO ERP.
     * 
     * @param builder o EntityManagerFactoryBuilder usado para criar o EntityManagerFactory
     * @param dataSource o DataSource para o banco de dados SIGEN
     * @return um LocalContainerEntityManagerFactoryBean configurado para o banco de dados SIGEN
     */
    @Bean(name = "sigenEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean sigenEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sigenDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.compoldata.induflow.integration.sigen.model")
                .persistenceUnit("sigen")
                .build();
    }

    /**
     * Cria um gerenciador de transações para o banco de dados SIGEN.
     *
     * @param sigenEntityManagerFactory a fábrica de gerenciadores de entidades para o banco de dados SIGEN
     * @return um PlatformTransactionManager para gerenciar transações no banco de dados SIGEN
     */
    @Bean(name = "sigenTransactionManager")
    PlatformTransactionManager sigenTransactionManager(
            @Qualifier("sigenEntityManagerFactory") EntityManagerFactory sigenEntityManagerFactory) {
        return new JpaTransactionManager(sigenEntityManagerFactory);
    }




    
}
