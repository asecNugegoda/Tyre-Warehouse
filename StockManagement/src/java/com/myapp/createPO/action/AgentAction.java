/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.TaskVarList;
import com.myapp.createPO.bean.AgentBean;
import com.myapp.createPO.service.AgentServiceFactory;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
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
public class AgentAction extends ActionSupport implements AccessControlService, ModelDriven<AgentBean> {

    AgentBean inputBean = new AgentBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public AgentServiceFactory getService() {
        return new AgentServiceFactory();
    }
    
    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status;
        applyUserPrivileges();
        String page = PageVarList.Register_Agent;
        inputBean.setPageCode(page);
        String task = null;

        if ("list".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("export".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            status = new Common().checkMethodAccess(task, Integer.parseInt(page), session);
        }
        return status;
    }

    private boolean applyUserPrivileges() {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Register_Agent, request);
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
    
    @Override
    public AgentBean getModel() {
        try {
//            getService().getAgentServiceInf().getPagePath(inputBean.getPageCode(), inputBean);
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }
    
    private boolean doValidationAdd(AgentBean userBean) throws Exception {
        boolean ok = false;
        try {
            
            if (userBean.getAgentName() == null || userBean.getAgentName().isEmpty()) {
                addActionError(SystemMessage.AGENT_NAME_EMPTY);
                return ok;
            } else if (!Util.validateString(userBean.getAgentName())) {
                addActionError(SystemMessage.AGENT_NAME_INVALID);
                return ok;
            } else if (userBean.getAdr1()== null || userBean.getAdr1().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR1);
                return ok;
            } else if (userBean.getAdr2()== null || userBean.getAdr2().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR2);
                return ok;
            } else if (!Util.validateString(userBean.getAdr2())) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR2);
                return ok;
            } else if (userBean.getCity()== null || userBean.getCity().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_CITY);
                return ok;
            } else if (userBean.getContact()== null || userBean.getContact().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_CONTACT);
                return ok;
            } else if(!Util.validateNUMBER(userBean.getContact())){
                addActionError(SystemMessage.AGENT_INVALID_CONTACT);
                return ok;
            } else{
                ok = true;
            }
            
        } catch (Exception e) {
            throw e;
        }

        return ok;
    }
    
    public String add() {
        boolean ok;
        try {

            if (doValidationAdd(inputBean)) {
                ok = getService().getAgentServiceInf().insertAgentProfile(inputBean);

                if (ok == false) {
                    addActionError(SystemMessage.AGENT_ADD_FAIL);
                } else {
                    addActionMessage(SystemMessage.AGENT_ADD_SUCCESS);
                    LogFileCreator.writeInforToLog(SystemMessage.AGENT_ADD_SUCCESS);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.AGENT_ADD_FAIL);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "add";
    }
    
    public String list() {
        List<AgentBean> dataList;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records;

            dataList = getService().getAgentServiceInf().loadAgentProfile(inputBean, rows, from);

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

        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return "list";
    }

    public String find(){
        try {
            getService().getAgentServiceInf().getUpdateAgent(inputBean);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return "find";
    }
    
    public String update(){
        boolean ok = false;
        try {
            if (doEditValidation(inputBean)) {
                ok = getService().getAgentServiceInf().updateAgent(inputBean);
                if (ok) {
                    LogFileCreator.writeInforToLog(SystemMessage.SUCCESS_ITEM_UPDATE);
                    addActionMessage(SystemMessage.SUCCESS_ITEM_UPDATE);

                } else {
                    addActionError(SystemMessage.FAIL_ITEM_UPDATE);
                }
            }
        } catch (Exception ex) {
            addActionError(SystemMessage.FAIL_ITEM_UPDATE);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);

        }
        return "update";
    }
    
    private boolean doEditValidation(AgentBean userBean) throws Exception {
        boolean ok = false;
        try {
            
            if (userBean.getUpAgentName() == null || userBean.getUpAgentName().isEmpty()) {
                addActionError(SystemMessage.AGENT_NAME_EMPTY);
                return ok;
            } else if (!Util.validateString(userBean.getUpAgentName())) {
                addActionError(SystemMessage.AGENT_NAME_INVALID);
                return ok;
            } else if (userBean.getUpAdr1()== null || userBean.getUpAdr1().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR1);
                return ok;
            } else if (userBean.getUpAdr2()== null || userBean.getUpAdr2().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR2);
                return ok;
            } else if (!Util.validateString(userBean.getUpAdr2())) {
                addActionError(SystemMessage.AGENT_EMPTY_ADR2);
                return ok;
            } else if (userBean.getUpCity()== null || userBean.getUpCity().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_CITY);
                return ok;
            }  else if (userBean.getUpContact()== null || userBean.getUpContact().isEmpty()) {
                addActionError(SystemMessage.AGENT_EMPTY_CONTACT);
                return ok;
            } else if(!Util.validateNUMBER(userBean.getUpContact())){
                addActionError(SystemMessage.AGENT_INVALID_CONTACT);
                return ok;
            } else{
                ok = true;
            }
            
        } catch (Exception e) {
            throw e;
        }

        return ok;
    }
    
}
