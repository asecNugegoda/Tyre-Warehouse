/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.warehouse.bean.BrandBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface BrandServiceInf {
    
    public List<BrandBean> loadBrand(BrandBean inputBean, int max, int first) throws Exception;
    
    public boolean insertBrand(BrandBean inputBean)throws Exception;
    
    public boolean  deleteBrand(BrandBean inputBean) throws Exception;
    
    public String GetResult(BrandBean inputBean) throws Exception;
    
}
