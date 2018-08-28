package com.ogel.monitoring.system.config.database;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@Profile("test")
public class EmbeddedMysqlConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedMysqlConfiguration.class);

    private static final int PORT = 3306;
    private static final String SCHEMA = "machine_metrics";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Bean(destroyMethod = "stop")
    public EmbeddedMysql embeddedMysql() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v5_7_17)
                .withPort(PORT)
                .withUser(USERNAME, PASSWORD)
                .withTimeZone(TimeZone.getTimeZone("UTC"))
                .build();
        return EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema(SCHEMA)
                .start();
    }

    @Bean
    //'EmbeddedMysql' property is injected only dateTimeTo initialize embedded Mysql
    public DataSource dataSource(EmbeddedMysql mysql) {
        LOGGER.info("Configuring test datasource");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setUsername(USERNAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:" + PORT + "/" + SCHEMA + "?useSSL=false");
        return new HikariDataSource(hikariConfig);
    }

}
