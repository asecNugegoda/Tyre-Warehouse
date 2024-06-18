/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.profile.service;

/**
 *
 * @author thilinath
 */
public class UserProfileFactory {
    
    private UserProfileInf factory;

    public UserProfileFactory() {
        this.factory = new UserProfileService();
    }

    public UserProfileInf getProfileService() {
        return factory;
    }

}
