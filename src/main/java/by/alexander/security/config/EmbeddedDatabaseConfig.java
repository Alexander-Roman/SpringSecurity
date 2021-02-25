package by.alexander.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class EmbeddedDatabaseConfig {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("classpath:database/h2/schema.sql")
                .addScript("classpath:database/h2/test-data.sql")
                .build();
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        Class<EmbeddedDatabaseConfig> clazz = EmbeddedDatabaseConfig.class;
        ClassLoader classLoader = clazz.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("hibernate.properties")) {
            hibernateProperties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Hibernate configuration properties loading failed: ", e);
        }
        return hibernateProperties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("by.alexander.security.entity");

        DataSource dataSource = dataSource();
        factoryBean.setDataSource(dataSource);

        JpaVendorAdapter jpaVendorAdapter = jpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = hibernateProperties();
        factoryBean.setJpaProperties(properties);

        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }


    @Bean
    public TransactionManager transactionManager() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory();
        return new JpaTransactionManager(entityManagerFactory);
    }
}
