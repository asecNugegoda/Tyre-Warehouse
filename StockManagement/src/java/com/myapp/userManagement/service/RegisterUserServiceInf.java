/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.service;

import com.myapp.userManagement.bean.RegisterUserBean;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thilinath
 */
public interface RegisterUserServiceInf {
    public Map<Integer, String> getUserTypes() throws Exception ;
    public Map<Integer, String> getStatusTypes() throws Exception ;
    public boolean checkUserName(String userName) throws Exception;
    public boolean addUser(RegisterUserBean inputBean) throws Exception;
    public List<RegisterUserBean> loadUsers(RegisterUserBean inputBean, int max, int first, String orderBy) throws Exception;
    public boolean deleteUser(String duserName) throws Exception;
    public void findUser(RegisterUserBean inputBean) throws Exception;
    public boolean updateUser(RegisterUserBean inputBean) throws Exception;
    public RegisterUserBean getPagePath(String page,RegisterUserBean inputBean)throws Exception;
    
    
}
