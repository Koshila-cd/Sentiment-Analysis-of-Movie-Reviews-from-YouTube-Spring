package com.example.movie.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.max-pool-size}")
    private int maxPoolSize;

    @Bean(name = "mysqlDataSource")
    @Primary
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
