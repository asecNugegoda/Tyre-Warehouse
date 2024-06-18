/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.warehouse.bean.CategoryBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface CategoryServiceInf {
    
    public List<CategoryBean> loadCategory(CategoryBean inputBean, int max, int first) throws Exception;
    
    public boolean insertCategory(CategoryBean inputBean)throws Exception;
    
    public boolean  deleteCategory(CategoryBean inputBean) throws Exception;
    
    public String GetResult(CategoryBean inputBean) throws Exception;
    
}
