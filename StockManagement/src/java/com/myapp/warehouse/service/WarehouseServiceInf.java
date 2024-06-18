/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.warehouse.bean.WarehouseBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface WarehouseServiceInf {
    
    public List<WarehouseBean> loadWarehouse(WarehouseBean inputBean, int max, int first) throws Exception;

    public boolean insertWarehouse(WarehouseBean inputBean) throws Exception;

    public void getUpdateWarehouse(WarehouseBean inputBean) throws Exception;

    public boolean deleteWarehouse(WarehouseBean inputBean) throws Exception;

    public boolean updateWarehouse(WarehouseBean inputBean) throws Exception;
    
}
