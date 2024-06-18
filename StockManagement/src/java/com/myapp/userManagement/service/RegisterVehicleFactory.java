/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.service;

/**
 *
 * @author thilinath
 */
public class RegisterVehicleFactory {

    private final RegisterVehicleServiceInf registerVehicleServiceInf;

    public RegisterVehicleFactory() {
        this.registerVehicleServiceInf = new RegisterVehicleService();
    }

    public RegisterVehicleServiceInf getRegisterVehicleServiceInf() {
        return registerVehicleServiceInf;
    }
}
