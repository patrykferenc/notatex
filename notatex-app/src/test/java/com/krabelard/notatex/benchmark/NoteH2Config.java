package com.krabelard.notatex.benchmark;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.krabelard.notatex.note.repository")
@PropertySource("testdatasource.properties")
@EnableTransactionManagement
public class NoteH2Config {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        val config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("jdbc.url"));
        return new HikariDataSource(config);
    }

}
