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
import com.myapp.userManagement.service.RegisterUserFactory;
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
public class RegisterUser extends ActionSupport implements AccessControlService, ModelDriven<RegisterUserBean> {

    RegisterUserBean inputBean = new RegisterUserBean();
    HttpServletRequest req = ServletActionContext.getRequest();

    public SessionUserBean getSessionBean() {
        return (SessionUserBean) req.getSession().getAttribute("SessionObject");
    }

    public RegisterUserFactory getService() {
        return new RegisterUserFactory();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String list() {
        List<RegisterUserBean> dataList;
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = "order by wu." + inputBean.getSidx() + " " + inputBean.getSord();
            }
            dataList = getService().getRegisterUserServiceInf().loadUsers(inputBean, rows, from, orderBy);

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
            if (inputBean.getDuserName().equals(getSessionBean().getUserName())) {
                inputBean.setDmessage(SystemMessage.USER_DELETE_CANNOT_DELETE_CURRENT_USER);
                inputBean.setIsDeleted(false);
            } else if (inputBean.getDuserName().equalsIgnoreCase("admin")) {
                inputBean.setDmessage(SystemMessage.USER_DELETE_CANNOT_DELETE_ADMIN);
                inputBean.setIsDeleted(false);
            } else {
                if (getService().getRegisterUserServiceInf().deleteUser(inputBean.getDuserName())) {
                    inputBean.setDmessage(SystemMessage.USER_DELETED);
                    inputBean.setIsDeleted(true);

                    LogFileCreator.writeInforToLog(SystemMessage.USER_DELETED);
                }
            }
        } catch (Exception e) {
            LogFileCreator.writeErrorToLog(e);
            inputBean.setDmessage(SystemMessage.USER_DELETED_ERROR);
            inputBean.setIsDeleted(false);
        }
        return "delete";
    }

    public String find() {

        try {
            getService().getRegisterUserServiceInf().findUser(inputBean);
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return "find";
    }

    public String update() {
        boolean ok;
        try {
            if (doValidationUpdate(inputBean)) {
                ok = getService().getRegisterUserServiceInf().updateUser(inputBean);
                if (ok) {
                    LogFileCreator.writeInforToLog(SystemMessage.USER_UPDATED);
                    addActionMessage(SystemMessage.USER_UPDATED);
                } else {
                    addActionError(SystemMessage.USER_UPDATED_ERROR);
                }
            }
        } catch (Exception ex) {
            addActionError(SystemMessage.USER_UPDATED_ERROR);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String add() {
        boolean ok;
        try {

            if (doValidationAdd(inputBean)) {
                ok = getService().getRegisterUserServiceInf().addUser(inputBean);

                if (ok == false) {
                    addActionError(SystemMessage.USER_ADD_FAIL);
                } else {
                    addActionMessage(SystemMessage.USER_ADD_SUCESS);
                    LogFileCreator.writeInforToLog(SystemMessage.USER_ADD_SUCESS);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.USER_ADD_FAIL);
            LogFileCreator.writeErrorToLog(ex);
        }
        return "add";
    }

//    public String export() {
//        ByteArrayOutputStream outputStream;
//        try {
//
//            XSSFWorkbook workbook1 = new XSSFWorkbook();
//            XSSFSheet sheet = workbook1.createSheet("ceft_transaction_report");
//
//            XSSFCellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook1);
//
//            Row row = sheet.createRow(0);
//            Cell cell = row.createCell(0);
//            cell.setCellValue("Name");
//            cell.setCellStyle(fontBoldedUnderlinedCell);
//
//            row = sheet.createRow(0);
//            cell = row.createCell(1);
//            cell.setCellValue("Age");
//            cell.setCellStyle(fontBoldedUnderlinedCell);
//            // Object object =ExcelReportTerminal.generateExcelReport();
//            if (workbook1 instanceof XSSFWorkbook) {
//                XSSFWorkbook workbook = (XSSFWorkbook) workbook1;
//                outputStream = new ByteArrayOutputStream();
//                workbook.write(outputStream);
//                inputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));
//            }
//
//        } catch (Exception e) {
//        }
//        return "excelreportterminal";
//    }
    private boolean doValidationUpdate(RegisterUserBean userBean) throws Exception {

        boolean ok = false;
        try {
            if (userBean.getUpfirstName()== null || userBean.getUpfirstName().isEmpty()) {
                addActionError(SystemMessage.USER_EMPTY_FIRST_NAME);
                return ok;
            }
            if (userBean.getUplastName()== null || userBean.getUplastName().isEmpty()) {
                addActionError(SystemMessage.USER_EMPTY_LAST_NAME);
                return ok;
            }
            if (!Util.validateString(userBean.getUpfirstName())) {
                addActionError(SystemMessage.USER_INVALID_NAME);
                return ok;
            } else if (!Util.validateString(userBean.getUplastName())) {
                addActionError(SystemMessage.USER_INVALID_NAME);
                return ok;
            } else if (userBean.getUpuserTypeId() == -1) {
                addActionError(SystemMessage.USER_EMPTY_USERTYPE);
                return ok;
            } else if(userBean.getUpadr1()== null || userBean.getUpadr1().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_ADR1);
                return ok;
            } else if(userBean.getUpadr2()== null || userBean.getUpadr2().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_ADR2);
                return ok;
            }else if(userBean.getUpcity()== null || userBean.getUpcity().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_CITY);
                return ok;
            }else if(userBean.getUpcontact()== null || userBean.getUpcontact().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_CONTACT);
                return ok;
            }else if(!Util.validateNUMBER(userBean.getUpcontact())){
                addActionError(SystemMessage.USER_INVALID_CONTACT);
                return ok;
            }else if (userBean.getUpuserTypeId() != 5) {
                if (userBean.getUpuserName()== null || userBean.getUpuserName().isEmpty()) {
                    addActionError(SystemMessage.USER_EMPTY_USERNAME);
                    return ok;
                } else if (!Util.validateNAME(userBean.getUpuserName())) {
                    addActionError(SystemMessage.USER_INVALID_USERNAME);
                    return ok;
                } 
            }else{
                ok = true;
            }
            
            if (inputBean.isIsChecked() == true) {
                if ((!userBean.getUpRepetedNewPw().equals(userBean.getUpNewPw()))) {
                    addActionError(SystemMessage.USER_CONF_PW_MISMATCH);//missmatch
                    return ok;
                }
            }
            if ((inputBean.isIsChecked() == true) && (userBean.getUpNewPw().isEmpty() || userBean.getUpNewPw() == null)) {
                addActionError(SystemMessage.USER_PW_EMPTY);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    private boolean doValidationAdd(RegisterUserBean userBean) throws Exception {
        boolean ok = false;
        try {
            if (userBean.getFirstName() == null || userBean.getFirstName().isEmpty()) {
                addActionError(SystemMessage.USER_EMPTY_FIRST_NAME);
                return ok;
            }
            if (userBean.getLastName() == null || userBean.getLastName().isEmpty()) {
                addActionError(SystemMessage.USER_EMPTY_LAST_NAME);
                return ok;
            }
            if (!Util.validateString(userBean.getFirstName())) {
                addActionError(SystemMessage.USER_INVALID_NAME);
                return ok;
            } else if (!Util.validateString(userBean.getLastName())) {
                addActionError(SystemMessage.USER_INVALID_NAME);
                return ok;
            } else if (userBean.getUserType().equals("-1")) {
                addActionError(SystemMessage.USER_EMPTY_USERTYPE);
                return ok;
            } else if(userBean.getAdr1()== null || userBean.getAdr1().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_ADR1);
                return ok;
            } else if(userBean.getAdr2()== null || userBean.getAdr2().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_ADR2);
                return ok;
            }else if(userBean.getCity()== null || userBean.getCity().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_CITY);
                return ok;
            }else if(userBean.getContact()== null || userBean.getContact().isEmpty()){
                addActionError(SystemMessage.USER_EMPTY_CONTACT);
                return ok;
            }else if(!Util.validateNUMBER(userBean.getContact())){
                addActionError(SystemMessage.USER_INVALID_CONTACT);
                return ok;
            }else if (userBean.getUserType().equals(5)) {
                if (userBean.getUserName() == null || userBean.getUserName().isEmpty()) {
                    addActionError(SystemMessage.USER_EMPTY_USERNAME);
                    return ok;
                } else if (!Util.validateNAME(userBean.getUserName())) {
                    addActionError(SystemMessage.USER_INVALID_USERNAME);
                    return ok;
                } else if (getService().getRegisterUserServiceInf().checkUserName(userBean.getUserName())) {
                    addActionError(SystemMessage.USER_USERNAME_ALREADY);
                    return ok;
                } else if (userBean.getPassword().isEmpty() || userBean.getPassword() == null) {
                    addActionError(SystemMessage.USER_PW_EMPTY);
                    return ok;
                } else if (userBean.getConfirmPassword().isEmpty() || userBean.getConfirmPassword() == null) {
                    addActionError(SystemMessage.USER_CONF_PW_EMPTY);
                    return ok;
                } else if (!userBean.getConfirmPassword().equals(userBean.getPassword())) {
                    addActionError(SystemMessage.USER_CONF_PW_MISMATCH);
                    return ok;
                }
            }else{
                ok = true;
            }
        } catch (Exception e) {
            throw e;
        }

        return ok;
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
    public RegisterUserBean getModel() {
        try {
            inputBean.setUserTypeMap(getService().getRegisterUserServiceInf().getUserTypes());
            getService().getRegisterUserServiceInf().getPagePath(inputBean.getPageCode(), inputBean);
//            inputBean.setStatusTypeMap(getService().getRegisterUserServiceInf().getStatusTypes());
            inputBean.setUserTypeMap(Util.getUserTypes());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }
    
    public String vehicle(){
        return "vehicle";
    }    

}
