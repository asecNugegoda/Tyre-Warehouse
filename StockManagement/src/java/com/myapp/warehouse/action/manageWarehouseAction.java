/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.SystemModule;
import com.myapp.constant.TaskVarList;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import com.myapp.warehouse.bean.WarehouseBean;
import com.myapp.warehouse.service.WarehouseServiceFactory;
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
public class manageWarehouseAction extends ActionSupport implements AccessControlService, ModelDriven<WarehouseBean> {

    WarehouseBean inputBean = new WarehouseBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public WarehouseServiceFactory getService() {
        return new WarehouseServiceFactory();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    @Override
    public boolean checkAccess(String method, int userRole) {

        boolean status;
        applyUserPrivileges();
        String page = PageVarList.Warehouse_manage;
        inputBean.setPageCode(page);
        String task = null;

        if ("list".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("loadItem".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD;
        }else if ("Assign".equals(method)) {
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Warehouse_manage, request);
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
    public WarehouseBean getModel() {
        try {
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
        }
        return inputBean;
    }

    public String add() {

        try {
            if (doAddValidation(inputBean)) {
                getService().getWarehouseServiceInf().insertWarehouse(inputBean);
                addActionMessage(SystemMessage.WAREHOUSE_SUCCESS_ADD);
                LogFileCreator.writeInforToLog(SystemMessage.WAREHOUSE_SUCCESS_ADD);
                Util.insertHistoryRecord(getSessionBean().getId() + "", SystemModule.Ware_House, TaskVarList.ADD, SystemMessage.WAREHOUSE_SUCCESS_ADD);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError(SystemMessage.WAREHOUSE_FAIL_ADD);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "add";
    }

    private boolean doAddValidation(WarehouseBean warehouseBean) throws Exception {
        boolean ok = false;
        if (warehouseBean.getLocation().isEmpty() || warehouseBean.getLocation() == null) {
            addActionError(SystemMessage.WAREHOUSE_NAME_EMPTY);
            return ok;
        } else {
            ok = true;
        }
        return ok;
    }

    public String list() {
        List<WarehouseBean> dataList = null;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getWarehouseServiceInf().loadWarehouse(inputBean, rows, from);

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

    public String find() {
        try {
            getService().getWarehouseServiceInf().getUpdateWarehouse(inputBean);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "find";
    }

    public String update() {
        boolean ok = false;
        try {
            if (doEditValidation(inputBean)) {
                ok = getService().getWarehouseServiceInf().updateWarehouse(inputBean);
                if (ok) {
                    LogFileCreator.writeInforToLog(SystemMessage.WAREHOUSE_SUCCESS_UPDATE);
                    addActionMessage(SystemMessage.WAREHOUSE_SUCCESS_UPDATE);
                    Util.insertHistoryRecord(getSessionBean().getId() + "", SystemModule.Ware_House, TaskVarList.UPDATE, SystemMessage.WAREHOUSE_SUCCESS_UPDATE);
                } else {
                    addActionError(SystemMessage.WAREHOUSE_FAIL_UPDATE);
                }
            }
        } catch (Exception ex) {
            addActionError(SystemMessage.WAREHOUSE_FAIL_UPDATE);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);

        }
        return "update";
    }

    private boolean doEditValidation(WarehouseBean warehouseBean) throws Exception {
        boolean ok = false;
        if (warehouseBean.getUpLocation().isEmpty() || warehouseBean.getUpLocation() == null) {
            addActionError(SystemMessage.WAREHOUSE_NAME_EMPTY);
            return ok;
        } else {
            ok = true;
        }
        return ok;
    }

    public String delete() {
        try {
            if (getService().getWarehouseServiceInf().deleteWarehouse(inputBean)) {
                Util.insertHistoryRecord(getSessionBean().getId()+"", SystemModule.Ware_House, TaskVarList.DELETE, SystemMessage.WAREHOUSE_SUCCESS_DELETE);
                LogFileCreator.writeInforToLog(SystemMessage.WAREHOUSE_SUCCESS_DELETE);
                inputBean.setIsDeleted(true);
                inputBean.setDmessage(SystemMessage.WAREHOUSE_SUCCESS_DELETE);
            } else {
                inputBean.setIsDeleted(false);
                inputBean.setDmessage(SystemMessage.WAREHOUSE_FAIL_DELETE);
            }
        } catch (Exception e) {
            inputBean.setIsDeleted(false);
            inputBean.setDmessage(SystemMessage.WAREHOUSE_FAIL_DELETE);
            LogFileCreator.writeErrorToLog(e);
            e.printStackTrace();
        }

        return "delete";
    }
    
    public String Assign(){
        return "Assign";
    }

}
