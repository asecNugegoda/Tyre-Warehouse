/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.login.action;

import com.myapp.constant.Configurations;
import com.myapp.constant.SessionVariable;
import com.myapp.constant.SystemMessage;
import com.myapp.constant.SystemModule;
import com.myapp.constant.TaskVarList;
import com.myapp.login.bean.HomeValues;
import com.myapp.login.bean.ModuleBean;
import com.myapp.login.bean.PageBean;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.login.bean.UserLoginBean;
import com.myapp.login.service.LoginServiceFactory;
import com.myapp.mapping.HibernateUtil;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import static com.opensymphony.xwork2.Action.LOGIN;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author thilinath
 */
public class LoginAction extends ActionSupport implements ModelDriven<UserLoginBean>, SessionAware {

    LoginServiceFactory service;
    private final Timer time = new Timer(true);
    private static int counter;
    UserLoginBean userLoginBean = new UserLoginBean();
    SessionUserBean sessionUserBean = new SessionUserBean();
    private SessionMap<String, Object> sessionMap;
    HomeValues homeValues = new HomeValues();
    Map<ModuleBean, List<PageBean>> modulePageList = null;
    List<Integer> profilePageidList = new ArrayList<Integer>();
    Map<String, List<TaskBean>> taskList;
    
    @Override
    public UserLoginBean getModel() {
        userLoginBean.setServerNode(Integer.toString(Configurations.SERVER_NODE));
        return userLoginBean;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap) map;
    }
    
    public LoginServiceFactory getService() {
        return new LoginServiceFactory();
    }

    public String execute() {
        userLoginBean.setDatabase(Configurations.DB_CONF.toUpperCase());
        return SUCCESS;
    }

    public boolean validateLogin(UserLoginBean bean) throws Exception {
                
        if (bean.getUserName() != null && bean.getUserName().equals("")) {
            addActionError(SystemMessage.USER_EMPTY_USERNAME);
            return false;
        }
        if (!getService().getLoginService().checkUsername(bean)) {
            addActionError(SystemMessage.USER_INVALID_USERNAME);
            return false;
        } else if (bean.getPassword() != null && bean.getPassword().equals("")) {
            addActionError(SystemMessage.USER_LOGIN_EMPTY_PWD);
            return false;
        }
        return true;

    }


    public String loginCheck() {
 
        try {
            if (validateLogin(userLoginBean)) {
                userLoginBean = getService().getLoginService().getDbUserPassword(userLoginBean);

                if (getService().getLoginService().varifilogin(userLoginBean)) {
//                    if (StatusValues.ACTIVE == userLoginBean.getDBuserStatus()) {

                        sessionUserBean.setName(userLoginBean.getDBname());
                        sessionUserBean.setUserName(userLoginBean.getDBuserName());
                        sessionUserBean.setUserLevel(userLoginBean.getDBuserappCode());
                        sessionUserBean.setId(userLoginBean.getId());
                        sessionUserBean.setUserRole(userLoginBean.getUserRole());

                        HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                        if (sessionPrevious != null) {
                            sessionPrevious.removeAttribute("SessionObject");
                            sessionPrevious.removeAttribute("profilePageidList");
                            sessionPrevious.removeAttribute("modulePageList");
                            sessionPrevious.removeAttribute("pageTaskList");
                            sessionPrevious.removeAttribute("SessionHomeValues");
                            sessionPrevious.invalidate();
                            sessionPrevious = null;
                        }

                        HttpSession session = ServletActionContext.getRequest().getSession(true);
                        sessionUserBean.setCurrentSessionId(session.getId());
                        session.setAttribute("SessionObject", sessionUserBean);

                        //set user and sessionid to hashmap              
                        ServletContext sc = ServletActionContext.getServletContext();
                        HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVariable.USERMAP);
                        if (userMap == null) {
                            userMap = new HashMap<String, String>();
                        }
                        userMap.put(sessionUserBean.getUserName(), session.getId());
                        sc.setAttribute(SessionVariable.USERMAP, userMap);

//                        getService().getLoginService().getHomeValues(homeValues);
//                        session.setAttribute("SessionHomeValues", homeValues);
                        profilePageidList = getService().getLoginService().getUserprofilePageidList(userLoginBean.getDBuserappCode());
                        session.setAttribute("profilePageidList", profilePageidList);

                        taskList = getService().getLoginService().getAllPageTask(userLoginBean.getDBuserappCode());
                        session.setAttribute("pageTaskList", taskList);

                        modulePageList = getService().getLoginService().getModulePageTaskByUser(userLoginBean.getDBuserappCode());
                        session.setAttribute("modulePageList", modulePageList);

                        SessionUserBean sessionUser1 = (SessionUserBean) session.getAttribute("SessionObject");

                        HttpServletRequest request1 = ServletActionContext.getRequest();
                        HttpSession session1 = request1.getSession(false);
                        SessionUserBean sessionUser = (SessionUserBean) session1.getAttribute("SessionObject");
                        Util.insertHistoryRecord(sessionUserBean.getId()+"", SystemModule.Ware_House, TaskVarList.LOGIN, SystemMessage.USER_LOGIN_SUCCESS);
                        LogFileCreator.writeInforToLog(SystemMessage.USER_LOGIN_SUCCESS);

//                        if (!isStartMonitorService) {
//                            isStartMonitorService = true;
//
//                            new Thread(new MonitorServerStatus()).start();
//                        }
                        return SUCCESS;
//                    } else {
//                        addActionError(SystemMessage.USER_LOGIN_STATUSE_NOT_ACTIVATE);
//                        return LOGIN;
//                    }

                } else {
                    addActionError(SystemMessage.USER_LOGIN_FAIL);
                    // addActionMessage("correct");
                    return LOGIN;
                }
            }

        } catch (org.hibernate.exception.JDBCConnectionException hib) {
            System.out.println("-------------- connection ------------------");
            new HibernateUtil().initialize();
            hib.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError(SystemMessage.USER_LOGIN_ERROR);
            LogFileCreator.writeErrorToLog(ex);
            return LOGIN;
        }
        return LOGIN;
    }
    
    public String homeFunction() {
        return SUCCESS;
    }
    
    public String logoutFunction() {
        System.out.println("logout-------");

        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);

//            executor.shutdown();
            if (session != null) {
                if (userLoginBean.getMessage() != null && !userLoginBean.getMessage().isEmpty()) {
                    String message = userLoginBean.getMessage();
                    if (message.equals("error1")) {
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged another mechine.");
                    } else if (message.equals("success1")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                    }
                }
                SessionUserBean su = (SessionUserBean) session.getAttribute("SessionObject");
                if (su != null) {
                    LogFileCreator.writeInforToLog(SystemMessage.USER_LOGOUT_SUCCESS);
                    session.removeAttribute("SessionObject");
                    session.removeAttribute("profilePageidList");
                    session.removeAttribute("modulePageList");
                    session.removeAttribute("pageTaskList");
                    session.removeAttribute("SessionHomeValues");
                    session.invalidate();
                    session = null;

                } else {
                    addActionError("Session timeout.");
                }

            } else {
                addActionError("Session timeout");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }
        return LOGIN;
    }
    
}
