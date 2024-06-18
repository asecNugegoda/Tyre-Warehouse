 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.profile.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.SystemModule;
import com.myapp.constant.TaskVarList;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.profile.bean.UserProfileBean;
import com.myapp.profile.bean.UserProfileInputBean;
import com.myapp.profile.service.UserProfileFactory;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author thilinath
 */
public class UserProfileManagement extends ActionSupport implements AccessControlService, ModelDriven<UserProfileInputBean> {

    UserProfileInputBean inputBean = new UserProfileInputBean();
    UserProfileFactory service;
    SessionUserBean sub;

    public UserProfileFactory getService() {
        return new UserProfileFactory();
    }

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession().getAttribute("SessionObject");
    }

//    private static List<String> finalselectpagelist = new ArrayList<String>();

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.Profile_Privilage;
        inputBean.setPageCode(page);
        String task = null;
        if ("Add".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("view".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("Load".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("loadPages".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("loadTasks".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("download".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE;
        }
//        else if ("upload".equals(method)) {
//            task = TaskVarList.UPLOAD;
//        } else if ("confirm".equals(method)) {
//            task = TaskVarList.CONFIRM;
//        } else if ("reject".equals(method)) {
//            task = TaskVarList.REJECT;
//        } 

        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            status = new Common().checkMethodAccess(task, Integer.parseInt(page), session);
        }
        return status;
    }

    public String loadPages() {
        try {
            getService().getProfileService().getmodulePagemap(inputBean);

        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
        }
        return "loadmodulepages";
    }

    public String loadTasks() {
        try {
            getService().getProfileService().getPageTaskmap(inputBean);
        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
        }
        return "loadmodulepages";
    }

    @Override
    public UserProfileInputBean getModel() {
        try {
            getService().getProfileService().getmodulemap(inputBean);
//            getService().getProfileService().getPagePath(inputBean.getPageCode(), inputBean);
            getService().getProfileService().getmodulePagemap(inputBean);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Profile_Privilage, request);
        inputBean.setAdd(true);
        inputBean.setDelete(true);
        inputBean.setView(true);
        inputBean.setUpdate(true);
        if (tasklist != null && tasklist.size() > 0) {
            for (TaskBean task : tasklist) {
                if (task.getTASK_ID().equalsIgnoreCase(TaskVarList.ADD)) {
                    inputBean.setAdd(false);
                } else if (task.getTASK_ID().equalsIgnoreCase(TaskVarList.UPDATE)) {
                    inputBean.setUpdate(false);
                } else if (task.getTASK_ID().equalsIgnoreCase(TaskVarList.DELETE)) {
                    inputBean.setDelete(false);
                } else if (task.getTASK_ID().equalsIgnoreCase(TaskVarList.VIEW)) {
                    inputBean.setView(false);
                }
            }
        }
        return true;
    }

    public String execute() {
        return SUCCESS;
    }

    public String List() {
        List<UserProfileBean> dataList = null;

        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getProfileService().loadSearchUserProfile(inputBean, orderBy, from, to);
            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            addActionError(SystemMessage.USRP_ROLE_INVALID_SEARCH);
            LogFileCreator.writeErrorToLog(e);
        }
        return "list";
    }

    private boolean doValidation(UserProfileInputBean bean) throws Exception {
        boolean ok = false;
        try {
            if (bean.getProfilename() == null || bean.getProfilename().isEmpty()) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_NAME_EMPTY);
                return ok;
            } else if (!Util.validateString(bean.getProfilename())) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_NAME_INVALID);
                return ok;
            } else if (getService().getProfileService().alredyInsertProfile(inputBean.getProfilename())) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_ALREADY);
                return ok;
            } else if (bean.getTaskString().isEmpty() || bean.getTaskString().split("\\|").length < 0) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_NOT_SELECT_PAGES);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    public String Add() {
        try {
            if (doValidation(inputBean)) {
                int profileID = getService().getProfileService().insertUserProfile(inputBean);

                getService().getProfileService().insertUserProfileModule(profileID, inputBean);
//                addActionMessage(SystemMessage.USRP_PROFILE_ADD_SUCCESS);
                Util.insertHistoryRecord(getSub().getId()+"", SystemModule.User_Management, TaskVarList.ADD, SystemMessage.USRP_PROFILE_ADD_SUCCESS);
                LogFileCreator.writeInforToLog(SystemMessage.USRP_PROFILE_ADD_SUCCESS);
                inputBean.setSuccess(true);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_ADD_SUCCESS);
            }
//            finalselectpagelist.removeAll(finalselectpagelist);
        } catch (Exception e) {
            inputBean.setSuccess(false);
            inputBean.setMessage(SystemMessage.USRP_PROFILE_ADD_FAIL);
            LogFileCreator.writeErrorToLog(e);
        }

        return "add";

    }

    public String Load() {
        try {
            getService().getProfileService().getUpdateData(inputBean);
        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
        }
        return "load";
    }

    public String Update() {
        try {
            boolean ok = false;
            if (doUpdateValidation(inputBean)) {
                ok = getService().getProfileService().updateUserProfile(inputBean);

                if (ok == true) {
                    inputBean.setSuccess(true);
                    inputBean.setMessage(SystemMessage.USRP_ROLE_UPDATED);
                    Util.insertHistoryRecord(getSub().getId()+"", SystemModule.User_Management, TaskVarList.UPDATE, SystemMessage.USRP_ROLE_UPDATED);
                    LogFileCreator.writeInforToLog(SystemMessage.USRP_ROLE_UPDATED);
                }
            }
        } catch (Exception ex) {
            inputBean.setSuccess(true);
            inputBean.setMessage(SystemMessage.USRP_ROLE_UPDATED_FAIL);
            LogFileCreator.writeErrorToLog(ex);
        }

        return "update";
    }

    public String Delete() {
        try {
            boolean ok = false;

            if (getService().getProfileService().isAnyUserUsingThisProfile(inputBean)) {
                inputBean.setMessage(SystemMessage.USRP_PROF_ALREADY_USED);
                return "delete";
            } else {
                ok = getService().getProfileService().userRoleDelete(inputBean);
            }

            if (ok == true) {
                inputBean.setMessage(SystemMessage.USRP_PROF_DELETED);
                Util.insertHistoryRecord(getSub().getId()+"", SystemModule.User_Management, TaskVarList.DELETE, SystemMessage.USRP_PROF_DELETED);
                LogFileCreator.writeInforToLog(SystemMessage.USRP_PROF_DELETED);
                
            } else {
                inputBean.setMessage(SystemMessage.USRP_PROF_DELETED_FAIL);
            }
            inputBean.setDelsuccess("1");

        } catch (Exception e) {
            e.printStackTrace();
            inputBean.setMessage(SystemMessage.USRP_PROF_DELETED_FAIL);
            LogFileCreator.writeErrorToLog(e);
        }

        return "delete";
    }

    private boolean doUpdateValidation(UserProfileInputBean bean) throws Exception {
        boolean ok = false;
        try {
            if (bean.getUpName() == null || bean.getUpName().isEmpty()) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_NAME_EMPTY);
                return ok;
            } else if (!Util.validateString(bean.getUpName())) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_NAME_INVALID);
                return ok;
            } else if (inputBean.getUpStatus() == -1) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.USRP_PROFILE_STATUS);
                return ok;
            } //                else if (getService().getProfileService().alredyInsertProfile(inputBean.getUpName())) {
            //                inputBean.setSuccess(false);
            //                inputBean.setMessage(SystemMessage.USRP_PROFILE_ALREADY);
            //                return ok;
            //            } 
            //            else if (bean.getTaskString().isEmpty() || bean.getTaskString().split("\\|").length < 0) {
            //                inputBean.setSuccess(false);
            //                inputBean.setMessage(SystemMessage.USRP_PROFILE_NOT_SELECT_PAGES);
            //                return ok;
            //            } 
            else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

}
