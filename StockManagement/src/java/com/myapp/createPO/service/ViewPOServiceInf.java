/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.POItemBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface ViewPOServiceInf {
    
    public List<POItemBean> loadPurchasingOrder(POItemBean inputBean, int max, int first) throws Exception;
    
    public boolean deleteWhItem(POItemBean inputBean) throws Exception;
    
}
