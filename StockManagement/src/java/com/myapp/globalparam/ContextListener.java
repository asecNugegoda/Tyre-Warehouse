/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.globalparam;

import com.myapp.constant.Configurations;
import com.myapp.constant.SystemMessage;
import com.myapp.mapping.HibernateUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author thilinath
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            if (System.getenv(Configurations.ENV_VARIABLE_CONFIG) != null) {
                //intialize hibernate
                Configurations.PATH_ROOT = System.getenv(Configurations.ENV_VARIABLE_CONFIG);
                HibernateUtil hibernateInit = new HibernateUtil();
                hibernateInit.initialize();

            } else {
                System.out.println(SystemMessage.INITIAL_ERROR);
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("Global Variable Destroyed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
