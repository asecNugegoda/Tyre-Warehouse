/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.PurchaseOrderBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface createPOServiceInf {
    
    public List<PurchaseOrderBean> loadWhItem(PurchaseOrderBean inputBean, int max, int first) throws Exception;

    public boolean insertWhItem(PurchaseOrderBean inputBean) throws Exception;

    public void getUpdateWhItem(PurchaseOrderBean inputBean) throws Exception;

    public boolean deleteWhItem(PurchaseOrderBean inputBean) throws Exception;

    public boolean updateWhItem(PurchaseOrderBean inputBean) throws Exception;

    public String GetResult(PurchaseOrderBean inputBean) throws Exception;
    
    public void getItems(PurchaseOrderBean inputBean) throws Exception;
    
    public void getAgent(PurchaseOrderBean inputBean) throws Exception;
    
    public void getBrands(PurchaseOrderBean inputBean) throws Exception;
    
    public void getCategory(PurchaseOrderBean inputBean) throws Exception;
    
}
