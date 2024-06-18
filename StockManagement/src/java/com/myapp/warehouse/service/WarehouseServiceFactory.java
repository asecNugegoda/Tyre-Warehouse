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
public class WarehouseServiceFactory {

    public WarehouseServiceInf getWarehouseServiceInf() {
        return warehouseServiceInf;
    }
    
    private final WarehouseServiceInf warehouseServiceInf;

    public WarehouseServiceFactory() {
        this.warehouseServiceInf = new WarehouseService();
    }
}
