package ru.job4j.cinema.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {
    @Bean
    @Primary
    public DataSource postgresDataSource(@Value("${datasource.url}") String url,
                                         @Value("${datasource.username}") String username,
                                         @Value("${datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcDatabaseClient(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
