/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.TaskVarList;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.userManagement.bean.RegisterUserBean;
import com.myapp.userManagement.bean.vehicleBean;
import com.myapp.userManagement.service.RegisterVehicleFactory;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import com.myapp.warehouse.bean.WarehouseItemBean;
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
public class RegisterVehicle extends ActionSupport implements AccessControlService, ModelDriven<vehicleBean>{

    vehicleBean inputBean = new vehicleBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public RegisterVehicleFactory getService() {
        return new RegisterVehicleFactory();
    }
    
    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status;
        applyUserPrivileges();
        String page = PageVarList.Registration;
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
        }else if ("vehicle".equals(method)) {
            task = TaskVarList.ADD;
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Registration, request);
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
    public vehicleBean getModel() {
        try {
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }
    
    public String add() {

        try {
            if (doAddValidation(inputBean)) {
                getService().getRegisterVehicleServiceInf().addVehicle(inputBean);
                addActionMessage(SystemMessage.SUCCESS_ITEM_ADD);
                LogFileCreator.writeInforToLog(SystemMessage.SUCCESS_ITEM_ADD);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError(SystemMessage.FAIL_ITEM_ADD);
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";

    }
    
    private boolean doAddValidation(vehicleBean vehBean) throws Exception {
        boolean ok = false;
        try {
            if (vehBean.getVehicleBrand().isEmpty() || vehBean.getVehicleBrand() == null) {
                addActionError(SystemMessage.FILL_BRAND);
                return ok;
            }
            if (vehBean.getVehicleNumber().isEmpty() || vehBean.getVehicleNumber() == null) {
                addActionError(SystemMessage.FILL_NUMBER);
                return ok;
            }else {
                ok = true;
            }
        } catch (Exception e) {
            throw e;
        }
        return ok;
    }
    
    public String list() {
        List<vehicleBean> dataList = null;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getRegisterVehicleServiceInf().loadVehicle(inputBean, rows, from);

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
    
}
