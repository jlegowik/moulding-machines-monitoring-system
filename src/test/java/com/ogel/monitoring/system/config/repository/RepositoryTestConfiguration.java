package com.ogel.monitoring.system.config.repository;

import com.ogel.monitoring.system.config.database.EmbeddedMysqlConfiguration;
import com.ogel.monitoring.system.repository.MachineProductionRepository;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@Import({EmbeddedMysqlConfiguration.class, FlywayAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@EnableTransactionManagement
@ComponentScan(basePackageClasses = MachineProductionRepository.class)
public class RepositoryTestConfiguration {

}
