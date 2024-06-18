/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.action;

import com.myapp.constant.SystemMessage;
import com.myapp.constant.SystemModule;
import com.myapp.constant.TaskVarList;
import com.myapp.createPO.bean.PurchaseOrderBean;
import com.myapp.createPO.service.ExcelReportPurchaseOrder;
import com.myapp.createPO.service.createPOServiceFactory;
import com.myapp.login.bean.SessionUserBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.util.AccessControlService;
import com.myapp.util.Common;
import com.myapp.util.LogFileCreator;
import com.myapp.util.Util;
import com.myapp.util.constant.PageVarList;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;



/**
 *
 * @author thilinath
 */
public class CreatePOAction extends ActionSupport implements AccessControlService, ModelDriven<PurchaseOrderBean> {

    PurchaseOrderBean inputBean = new PurchaseOrderBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public createPOServiceFactory getService() {
        return new createPOServiceFactory();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status;
        applyUserPrivileges();
        String page = PageVarList.Create_PO;
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
        } else if ("more".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("POView".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("export".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("XSLcreat".equals(method)) {
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.Create_PO, request);
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

    public String more() {
        try {
            if (doAddValidation(inputBean)) {
                if (req.getSession().getAttribute("ItemList") == null) {
                    inputBean.getDatacollection().add(inputBean);
                    req.getSession().setAttribute("ItemList", inputBean.getDatacollection());
                } else {
                    List<PurchaseOrderBean> list = (List<PurchaseOrderBean>) req.getSession().getAttribute("ItemList");
                    list.add(inputBean);
                    req.getSession().setAttribute("ItemList", list);
                }
                addActionMessage(SystemMessage.SUCCESS_ITEM_ADD);
            } else {
                addActionError(SystemMessage.FAIL_ITEM_ADD);
            }
        } catch (Exception e) {
            addActionError(SystemMessage.FAIL_ITEM_ADD);
            LogFileCreator.writeErrorToLog(e);
        }
        return "more";
    }

    @Override
    public PurchaseOrderBean getModel() {
        try {
            getService().getCreaPOServiceInf().getAgent(inputBean);
            getService().getCreaPOServiceInf().getBrands(inputBean);
            getService().getCreaPOServiceInf().getCategory(inputBean);
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    public String loadItem() {
        try {
            getService().getCreaPOServiceInf().getItems(inputBean);

        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
        }
        return "loadItems";
    }

    public String add() {
        try {
            if (req.getSession().getAttribute("ItemList") != null) {
                List<PurchaseOrderBean> ll = (List<PurchaseOrderBean>) req.getSession().getAttribute("ItemList");
                inputBean.setDatacollection(ll);
                getService().getCreaPOServiceInf().insertWhItem(inputBean);
                addActionMessage(SystemMessage.PO_CREATE_SUCCESS);
                LogFileCreator.writeInforToLog(SystemMessage.PO_CREATE_SUCCESS);
                Util.insertHistoryRecord(getSessionBean().getId() + "", SystemModule.Stock_Management, TaskVarList.ADD, SystemMessage.PO_CREATE_SUCCESS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError(SystemMessage.FAIL_ITEM_ADD);
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    private boolean doAddValidation(PurchaseOrderBean purchaseOrderBean) throws Exception {
        boolean ok = false;
        try {

            if (purchaseOrderBean.getBrand().isEmpty() || purchaseOrderBean.getBrand() == null) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            }
            if (purchaseOrderBean.getCategory().isEmpty() || purchaseOrderBean.getCategory() == null) {
                addActionError(SystemMessage.INVALID_CATEGORY);
                return ok;
            }
            if (purchaseOrderBean.getItem() == null || purchaseOrderBean.getItem().isEmpty()) {
                addActionError(SystemMessage.EMPTY_ITEM_NAME);
                return ok;
            }
            if (purchaseOrderBean.getDealer() == null || purchaseOrderBean.getDealer().isEmpty()) {
                addActionError(SystemMessage.INVALID_DEALER);
                return ok;
            }
            if (purchaseOrderBean.getDealer().equals("-1")) {
                addActionError(SystemMessage.INVALID_DEALER);
                return ok;
            } else if (purchaseOrderBean.getBrand().equals("-1")) {
                addActionError(SystemMessage.INVALID_BRAND);
                return ok;
            } else if (purchaseOrderBean.getItem().equals("-1")) {
                addActionError(SystemMessage.EMPTY_ITEM_NAME);
                return ok;
            } else if (purchaseOrderBean.getCategory().equals("-1")) {
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
        List<PurchaseOrderBean> dataList = null;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            dataList = getService().getCreaPOServiceInf().loadWhItem(inputBean, rows, from);

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

    public String POView() {
        return "POView";
    }

    public String find() {
        return "find";
    }

    public String XSLcreat() {
        String retMsg = null;
        try {
            ByteArrayOutputStream outputStream = null;
            Object object = ExcelReportPurchaseOrder.generateExcelReport(inputBean);

            if (object instanceof XSSFWorkbook) {
                XSSFWorkbook workbook = (XSSFWorkbook) object;
                outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                inputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));
                retMsg = "excelreportpo";
            } else if (object instanceof ByteArrayOutputStream) {
                outputStream = (ByteArrayOutputStream) object;
                inputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                retMsg = "zip";
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }
        return retMsg;
    }

}
