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
public class CategoryFactory {

    private final CategoryServiceInf categoryServiceInf;

    public CategoryFactory() {
        this.categoryServiceInf = new CategoryService();
    }
    
    public CategoryServiceInf getCategoryServiceInf() {
        return categoryServiceInf;
    }
    
}
