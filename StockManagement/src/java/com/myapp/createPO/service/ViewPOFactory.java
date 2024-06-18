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
public class ViewPOFactory {

    private final ViewPOServiceInf viewPOServiceInf;

    public ViewPOFactory() {
        this.viewPOServiceInf = new ViewPOService();
    }

    public ViewPOServiceInf getViewPOServiceInf() {
        return viewPOServiceInf;
    }

}
