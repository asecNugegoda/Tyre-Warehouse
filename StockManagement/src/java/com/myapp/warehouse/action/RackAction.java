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
import com.myapp.warehouse.bean.RacksBean;
import com.myapp.warehouse.bean.WarehouseBean;
import com.myapp.warehouse.service.RackServiceFactory;
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
public class RackAction extends ActionSupport implements AccessControlService, ModelDriven<RacksBean> {

    RacksBean inputBean = new RacksBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public RackServiceFactory getService() {
        return new RackServiceFactory();
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
        } else if ("load".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("add".equals(method)) {
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
    public RacksBean getModel() {
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
                getService().getRackServiceInf().insertRacks(inputBean);
                addActionMessage(SystemMessage.RACKS_SUCCESSFULY_ADD);
                LogFileCreator.writeInforToLog(SystemMessage.RACKS_SUCCESSFULY_ADD);
                Util.insertHistoryRecord(getSessionBean().getId() + "", SystemModule.Ware_House, TaskVarList.ADD, SystemMessage.RACKS_SUCCESSFULY_ADD);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError(SystemMessage.WAREHOUSE_FAIL_ADD);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "add";
    }

    private boolean doAddValidation(RacksBean racksBean) throws Exception {
        boolean ok = false;
        if (racksBean.getRack().isEmpty() || racksBean.getRack() == null) {
            addActionError(SystemMessage.RACKS_NAME_EMPTY);
            return ok;
        } else if (racksBean.getHrCount().isEmpty() || racksBean.getHrCount() == null) {
            addActionError(SystemMessage.RACKS_HR_VALUE_EMPTY);
            return ok;
        } else if (racksBean.getVrCount().isEmpty() || racksBean.getVrCount() == null) {
            addActionError(SystemMessage.RACKS_VR_VALUE_EMPTY);
            return ok;
        } else if (racksBean.getMaxStorage().isEmpty() || racksBean.getMaxStorage() == null) {
            addActionError(SystemMessage.CELL_STORAGE_EMPTY);
            return ok;
        } else if (!Util.validateNUMBER(racksBean.getHrCount())) {
            addActionError(SystemMessage.RACKS_HR_VALUE_INVALID);
            return ok;
        } else if (!Util.validateNUMBER(racksBean.getVrCount())) {
            addActionError(SystemMessage.RACKS_VR_VALUE_INVALID);
            return ok;
        } else if (!Util.validateNUMBER(racksBean.getMaxStorage())) {
            addActionError(SystemMessage.CELL_STORAGE_INVALID);
            return ok;
        } else {
            ok = true;
        }
        return ok;
    }

    public String list() {
        List<RacksBean> dataList = null;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getRackServiceInf().loadRacks(inputBean, rows, from);

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
    
    public String delete() {
        try {
            if (getService().getRackServiceInf().deleteRacks(inputBean)) {
                Util.insertHistoryRecord(getSessionBean().getId()+"", SystemModule.Ware_House, TaskVarList.DELETE, SystemMessage.RACKS_SUCCESS_DELETE);
                LogFileCreator.writeInforToLog(SystemMessage.RACKS_SUCCESS_DELETE);
                inputBean.setIsDeleted(true);
                inputBean.setDmessage(SystemMessage.RACKS_SUCCESS_DELETE);
            } else {
                inputBean.setIsDeleted(false);
                inputBean.setDmessage(SystemMessage.RACKS_FAIL_DELETE);
            }
        } catch (Exception e) {
            inputBean.setIsDeleted(false);
            inputBean.setDmessage(SystemMessage.RACKS_FAIL_DELETE);
            LogFileCreator.writeErrorToLog(e);
            e.printStackTrace();
        }

        return "delete";
    }
    
}
