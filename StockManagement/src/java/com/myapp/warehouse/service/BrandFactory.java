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
public class BrandFactory {

    private final BrandServiceInf brandServiceInf;

    public BrandFactory() {
        this.brandServiceInf = new BrandService();
    }
    
    public BrandServiceInf getBrandServiceInf() {
        return brandServiceInf;
    }
    
}
