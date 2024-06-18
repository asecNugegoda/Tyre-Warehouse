/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.mapping;

import com.myapp.constant.Configurations;
import com.myapp.constant.DbConfiguraitonBean;
import com.myapp.constant.SystemMessage;
import com.myapp.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author thilinath
 */
public class HibernateUtil {

    public static SessionFactory sessionFactory;
    public static String dbConfig;

    {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            initialize();
        }
    }
    
    private void setDbConfig(Configuration config) {
        String path = Configurations.PATH_ROOT + Configurations.PATH_CONFIG;
        DbConfiguraitonBean dbConfigBean = new DbConfiguraitonBean();
        try {
            dbConfigBean = Util.xmlConfiguraion(path);
            HibernateUtil.dbConfig = dbConfigBean.getDbpooltype();
            System.out.println("Connection Create Succesfully..........");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(SystemMessage.CONFIGURAITON_ERROR);
        }

        config.configure("hibernate.cfg.xml");

        config.setProperty("hibernate.dialect", dbConfigBean.getDialect());
        config.setProperty("hibernate.connection.driver_class", dbConfigBean.getDriverClass());
        config.setProperty("hibernate.connection.url", dbConfigBean.getUrl().concat("?sessionVariables=sql_mode=''"));
        config.setProperty("hibernate.connection.username", dbConfigBean.getUsername());
        config.setProperty("hibernate.connection.password", dbConfigBean.getPassword());
        config.setProperty("hibernate.connection.zeroDateTimeBehavior", dbConfigBean.getZeroDateTimeBehavior());
        config.setProperty("hibernate.show_sql", dbConfigBean.getShowSql());

        config.setProperty("hibernate.c3p0.min_size", dbConfigBean.getDbpoolmin());
        config.setProperty("hibernate.c3p0.max_size", dbConfigBean.getDbpoolmax());
        config.setProperty("hibernate.c3p0.timeout", dbConfigBean.getDbpooltimeout());
        config.setProperty("hibernate.c3p0.max_statements", dbConfigBean.getPoolSize());
        config.setProperty("hibernate.c3p0.idle_test_period", "3000");
        config.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true");
        config.setProperty("hibernate.c3p0.validationQuery", "select 1;");

    }
    
    public SessionFactory initialize() {
        if (this.sessionFactory == null || this.sessionFactory.isClosed()) {
            Configuration configuration = new Configuration();
            setDbConfig(configuration);
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return this.sessionFactory;
    }
    
    
    
//    static {
//        try {
//            // Create the SessionFactory from standard (hibernate.cfg.xml) 
//            // config file.
//            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//        } catch (Throwable ex) {
//            // Log the exception. 
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
    
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
    
}
