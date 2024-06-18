/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

/**
 *
 * @author thilinath
 */
public class RackServiceFactory {
    
    private final RackServiceInf rackServiceInf;

    public RackServiceFactory() {
        this.rackServiceInf = new RackService();
    }
    
    public RackServiceInf getRackServiceInf() {
        return rackServiceInf;
    }
    
}
