/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.userManagement.service.RegisterUserService;
import com.myapp.userManagement.service.RegisterUserServiceInf;

/**
 *
 * @author thilinath
 */
public class WarehouseItemFactory {

    private final WarehouseItemServiceInf warehouseItemServiceInf;
    public WarehouseItemFactory(){
        this.warehouseItemServiceInf = new WarehouseItemService();
    }

    public WarehouseItemServiceInf getWarehouseItemServiceInf() {
        return warehouseItemServiceInf;
    }
    
}
