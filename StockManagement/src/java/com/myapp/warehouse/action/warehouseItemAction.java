/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.TaskVarList;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import com.myapp.warehouse.bean.WarehouseItemBean;
import com.myapp.warehouse.service.WarehouseItemFactory;
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
public class warehouseItemAction extends ActionSupport implements AccessControlService, ModelDriven<WarehouseItemBean> {

    WarehouseItemBean inputBean = new WarehouseItemBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public WarehouseItemFactory getService() {
        return new WarehouseItemFactory();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status;
        applyUserPrivileges();
        String page = PageVarList.Item_Store;
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
        } else if ("AddCategory".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("AddBrand".equals(method)) {
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Item_Store, request);
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
    public WarehouseItemBean getModel() {
        try {
            getService().getWarehouseItemServiceInf().getBrands(inputBean);
            getService().getWarehouseItemServiceInf().getCategory(inputBean);
            inputBean.getStatusList().put(1, "ACTIVE");
            inputBean.getStatusList().put(2, "INACTIVE");

            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    public String AddCategory() {
        return "Category";
    }

    public String AddBrand() {
        return "Brand";
    }

    public String add() {

        try {
            if (doAddValidation(inputBean)) {
                getService().getWarehouseItemServiceInf().insertWhItem(inputBean);
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

    private boolean doAddValidation(WarehouseItemBean warehouseItemBean) throws Exception {
        boolean ok = false;
        try {
//            String terminal = getTerminalFactory().getRegisterTerminalServiceInf().GetResult(inputBean);
//            if (terminal != null) {
//                addActionError("This Terminal ID Already Define.");
//                return ok;
//            }
            if (warehouseItemBean.getBrand().isEmpty() || warehouseItemBean.getBrand() == null) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            }
            if (warehouseItemBean.getCategory().isEmpty() || warehouseItemBean.getCategory() == null) {
                addActionError(SystemMessage.INVALID_CATEGORY);
                return ok;
            }
            if (warehouseItemBean.getWhItmeName() == null || warehouseItemBean.getWhItmeName().isEmpty()) {
                addActionError(SystemMessage.EMPTY_ITEM_NAME);
                return ok;
            }
            if (warehouseItemBean.getStatus().equals("-1")) {
                addActionError(SystemMessage.INVALID_STATUS);
                return ok;
            } else if (warehouseItemBean.getBrand().equals("-1")) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            } else if (warehouseItemBean.getCategory().equals("-1")) {
                addActionError(SystemMessage.INVALID_CATEGORY);
                return ok;
            } else {
                ok = true;
            }
        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    private boolean doEditValidation(WarehouseItemBean warehouseItemBean) throws Exception {
        boolean ok = false;
        try {
//            String terminal = getTerminalFactory().getRegisterTerminalServiceInf().GetResult(inputBean);
//            if (terminal != null) {
//                addActionError("This Terminal ID Already Define.");
//                return ok;
//            }
            if (warehouseItemBean.getUpBrand().isEmpty() || warehouseItemBean.getUpBrand() == null) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            }
            if (warehouseItemBean.getUpCategory().isEmpty() || warehouseItemBean.getUpCategory() == null) {
                addActionError(SystemMessage.INVALID_CATEGORY);
                return ok;
            }
            if (warehouseItemBean.getUpWhItmeName() == null || warehouseItemBean.getUpWhItmeName().isEmpty()) {
                addActionError(SystemMessage.EMPTY_ITEM_NAME);
                return ok;
            }
            if (warehouseItemBean.getUpStatus().equals("-1")) {
                addActionError(SystemMessage.INVALID_STATUS);
                return ok;
            } else if (warehouseItemBean.getUpBrand().equals("-1")) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            } else if (warehouseItemBean.getUpCategory().equals("-1")) {
                addActionError(SystemMessage.INVALID_CATEGORY);
                return ok;
            } else {
                ok = true;
            }
        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    public String list() {
        List<WarehouseItemBean> dataList = null;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getWarehouseItemServiceInf().loadWhItem(inputBean, rows, from);

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
            getService().getWarehouseItemServiceInf().getUpdateWhItem(inputBean);
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
                ok = getService().getWarehouseItemServiceInf().updateWhItem(inputBean);
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

    

}
