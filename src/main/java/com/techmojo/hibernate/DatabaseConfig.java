package com.techmojo.hibernate;

import com.techmojo.utils.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Autowired
    public AppProperties appProperties;

    @Autowired
    public DataSource dataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(appProperties.getHibernatePackagesToScan());
//	    sessionFactoryBean.setEntityInterceptor(new AuditDBNameInterceptor());

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", appProperties.getDialect());
        hibernateProperties.put("hibernate.show_sql", appProperties.getShowSql());
        hibernateProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        hibernateProperties.put("hibernate.id.new_generator_mappings", appProperties.getHibernateIdNewGeneratorMappings());
        hibernateProperties.put("hibernate.cache.use_query_cache", "true");
        hibernateProperties.put("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");

        sessionFactoryBean.setHibernateProperties(hibernateProperties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

} // class DatabaseConfig