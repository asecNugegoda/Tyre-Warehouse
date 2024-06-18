/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.util;

import com.myapp.login.bean.TaskBean;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author thilinath
 */
public class Common {
    
    public boolean checkMethodAccess( String task, int page, HttpSession sessionk){
        boolean access = false;
        try{
            HttpSession session = sessionk;
            List<String> profilePageidList = (List<String>) session.getAttribute("profilePageidList");
             HashMap<String, List<TaskBean>> pageTaskList =(HashMap<String, List<TaskBean>>) session.getAttribute("pageTaskList");
               
                if(profilePageidList.contains(page)){                    
                    if(Common.checkTaskAvaliable(pageTaskList.get(page+""), task)){
                        access=true;
                    }else{
                        access=false;
                    }
                    
                }else{
                    access=false;
                }
        }catch(Exception e){
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }      
        return access;
    }
    private static boolean checkTaskAvaliable(List<TaskBean> taskList, String task) {
       for(int i=0;i<taskList.size();i++){
           if(taskList.get(i).getTASK_ID().equals(task)){
               return true;
           }
       }
       return false;
    }
    
     //returns allowed task list of current user
    public List<TaskBean> getUserTaskListByPage(String page, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        HashMap<String, List<TaskBean>> pageMap = (HashMap<String, List<TaskBean>>) session.getAttribute("pageTaskList");     
        List<TaskBean> taskList = pageMap.get(page);
               
        return taskList;
    }
    
}
