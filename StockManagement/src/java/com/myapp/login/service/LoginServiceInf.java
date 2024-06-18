/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.login.service;

import com.myapp.login.bean.HomeValues;
import com.myapp.login.bean.ModuleBean;
import com.myapp.login.bean.PageBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.login.bean.UserLoginBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thilinath
 */
public interface LoginServiceInf {
    
    public UserLoginBean getDbUserPassword(UserLoginBean userLoginBean) throws Exception;
    public boolean checkUsername(UserLoginBean userLoginBean) throws Exception;
    public boolean varifilogin(UserLoginBean userLoginBean) throws Exception ;
    public List<Integer> getUserprofilePageidList(int dbUserprofile)   throws Exception ;
    public Map<ModuleBean, List<PageBean>> getModulePageByUser(int dBuserappCode) throws Exception;
    public void getHomeValues(HomeValues homeValues) throws Exception;
    public Map<ModuleBean,List<PageBean>> getModulePageTaskByUser(int dBuserappCode);
    public HashMap<String, List<TaskBean>> getAllPageTask(int profileID) throws Exception;
    public void getProcessingData(UserLoginBean userLoginBean)throws Exception;
    public void getFilterData(UserLoginBean userLoginBean)throws Exception;
    
}
