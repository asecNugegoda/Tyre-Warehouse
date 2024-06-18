/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

/**
 *
 * @author thilinath
 */
public class createPOServiceFactory {

    private final createPOServiceInf creaPOServiceInf;

    public createPOServiceFactory() {
        this.creaPOServiceInf = new createPOService();
    }

    public createPOServiceInf getCreaPOServiceInf() {
        return creaPOServiceInf;
    }

}
