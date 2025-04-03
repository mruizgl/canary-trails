package es.iespuertodelacruz.mp.canarytrails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    /*@Bean
    @Profile("!test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/canary_trails?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC"); //BBDD en phpMyAdmin
        dataSource.setUsername("root");
        dataSource.setPassword("1q2w3e4r");
        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource dataSourceTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:file:/tmp/canary_trails;DB_CLOSE_DELAY=-1"); //BBDD en local
        dataSource.setUsername("root");
        dataSource.setPassword("1q2w3e4r");
        return dataSource;

    }*/
}
