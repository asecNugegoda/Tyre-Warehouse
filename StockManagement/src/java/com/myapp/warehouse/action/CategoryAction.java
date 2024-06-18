/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.TaskVarList;
import com.myapp.createPO.bean.AgentBean;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import com.myapp.warehouse.bean.BrandBean;
import com.myapp.warehouse.bean.CategoryBean;
import com.myapp.warehouse.bean.ItemCategoryBean;
import com.myapp.warehouse.service.CategoryFactory;
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
public class CategoryAction extends ActionSupport implements AccessControlService, ModelDriven<CategoryBean>{

    CategoryBean inputBean = new CategoryBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public CategoryFactory getService() {
        return new CategoryFactory();
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
    public CategoryBean getModel() {
        try {
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }    
    
    public String add() {
        boolean ok;
        try {

            if (doValidationAdd(inputBean)) {
                ok = getService().getCategoryServiceInf().insertCategory(inputBean);

                if (ok == false) {
                    addActionError(SystemMessage.FAIL_CATEGORY_NAME);
                } else {
                    addActionMessage(SystemMessage.SUCCESS_CATEGORY_NAME);
                    LogFileCreator.writeInforToLog(SystemMessage.SUCCESS_CATEGORY_NAME);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.FAIL_CATEGORY_NAME);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "add";
    }
    
    private boolean doValidationAdd(CategoryBean categoryBean) throws Exception {
        boolean ok = false;
        try {
            if (categoryBean.getCategory()== null || categoryBean.getCategory().isEmpty()) {
                addActionError(SystemMessage.EMPTY_CATEGORY_NAME);
                return ok;
            }
            if (!Util.validateString(categoryBean.getCategory())) {
                addActionError(SystemMessage.INVALID_CATEGORY_NAME);
                return ok;
            }else{
                ok = true;
            }
        } catch (Exception e) {
            throw e;
        }

        return ok;
    }
    
    public String list(){
        
        List<CategoryBean> dataList;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records;

            dataList = getService().getCategoryServiceInf().loadCategory(inputBean, rows, from);

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
            if (getService().getCategoryServiceInf().deleteCategory(inputBean)) {
                inputBean.setIsDeleted(true);
                inputBean.setDmessage(SystemMessage.SUCCESS_CATEGORY_DELETE);
            } else {
                inputBean.setIsDeleted(false);
                inputBean.setDmessage(SystemMessage.FAIL_CATEGORY_DELETE);
            }
        } catch (Exception e) {
            inputBean.setIsDeleted(false);
            inputBean.setDmessage(SystemMessage.FAIL_CATEGORY_DELETE);
            LogFileCreator.writeErrorToLog(e);
            e.printStackTrace();
        }

        return "Delete";
    }
}
