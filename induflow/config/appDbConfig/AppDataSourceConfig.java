package com.compoldata.induflow.config.appDbConfig;

/**
 * Classe de configuração para a fonte de dados primária da aplicação.
 * Esta classe define beans para a fonte de dados, fábrica de gerenciador de entidades e gerenciador de transações.
 */


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "appEntityManagerFactory", 
    transactionManagerRef = "appTransactionManager",
    basePackages = {"com.compoldata.induflow.repository.appRepositories"}

)
public class AppDataSourceConfig {

    /**
     * Cria e configura o bean DataSource primário para a aplicação.
     * <p>
     * Este DataSource é configurado usando propriedades prefixadas com
     * "appDb.datasource"
     * nos arquivos de configuração da aplicação.
     * </p>
     *
     * @return o bean DataSource configurado
     */
    @Primary
    @Bean(name = "appDataSource")
    @ConfigurationProperties(prefix = "appdb.datasource")
    DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configura o {@link LocalContainerEntityManagerFactoryBean} primário para a
     * aplicação.
     * 
     * @param builder    o {@link EntityManagerFactoryBuilder} usado para criar a
     *                   fábrica de gerenciador de entidades
     * @param dataSource o {@link DataSource} usado para a fábrica de gerenciador de
     *                   entidades
     * @return uma instância configurada de
     *         {@link LocalContainerEntityManagerFactoryBean}
     */
    @Primary
    @Bean(name = "appEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean appEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("appDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.compoldata.induflow.model")
                .persistenceUnit("system")
                .build();
    }

    /**
     * Cria e configura um gerenciador de transações para a fonte de dados primária
     * da aplicação.
     *
     * @param entityManagerFactory a fábrica de gerenciador de entidades a ser usada
     *                             pelo gerenciador de transações
     * @return um PlatformTransactionManager configurado com a fábrica de
     *         gerenciador de entidades fornecida
     */
    @Primary
    @Bean(name = "appTransactionManager")
    PlatformTransactionManager appTransactionManager(
            @Qualifier("appEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);

    }
}
