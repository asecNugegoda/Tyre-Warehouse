/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.warehouse.bean.WarehouseItemBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface WarehouseItemServiceInf {
    
    public List<WarehouseItemBean> loadWhItem(WarehouseItemBean inputBean, int max, int first) throws Exception;

    public boolean insertWhItem(WarehouseItemBean inputBean) throws Exception;

    public void getUpdateWhItem(WarehouseItemBean inputBean) throws Exception;

    public boolean deleteWhItem(WarehouseItemBean inputBean) throws Exception;

    public boolean updateWhItem(WarehouseItemBean inputBean) throws Exception;

    public String GetResult(WarehouseItemBean inputBean) throws Exception;
    
    public void getBrands(WarehouseItemBean inputBean) throws Exception;
    
    public void getCategory(WarehouseItemBean inputBean) throws Exception;
    
}
