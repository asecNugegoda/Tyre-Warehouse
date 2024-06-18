/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.login.service;

import com.myapp.mapping.HibernateUtil;

/**
 *
 * @author thilinath
 */
public class LoginServiceFactory {
    
    private LoginServiceInf loginInf;

    public LoginServiceFactory() {
        this.loginInf = new LoginService();
    }

    public LoginServiceInf getLoginService() {
        return loginInf;
    }
    
}
