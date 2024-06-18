/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.service;

import com.myapp.userManagement.bean.vehicleBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface RegisterVehicleServiceInf {
    
    public boolean addVehicle(vehicleBean inputBean) throws Exception;
    public List<vehicleBean> loadVehicle(vehicleBean inputBean, int max, int first) throws Exception;
    public boolean deleteVehicle(vehicleBean inputBean) throws Exception;
    public void findVehicle(vehicleBean inputBean) throws Exception;
    public boolean updateVehicle(vehicleBean inputBean) throws Exception;
    
}
