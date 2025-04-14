package com.hooniegit.KafkaConsumer.MSSQL;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "tagDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tag")
    public DataSource tagDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "stateDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.state")
    public DataSource stateDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tagJdbcTemplate")
    public JdbcTemplate tagJdbcTemplate(@Qualifier("tagDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "stateJdbcTemplate")
    public JdbcTemplate stateJdbcTemplate(@Qualifier("stateDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}

