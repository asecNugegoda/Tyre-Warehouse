/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.util;

/**
 *
 * @author thilinath
 */
public interface AccessControlService {
    public boolean checkAccess(String method,int userRole);
}
