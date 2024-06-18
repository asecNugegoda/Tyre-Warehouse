/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.login.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thilinath
 */
public class UserLoginBean {

    private String userName;
    private String password;
    private String serverNode;
    private int id;
    private String message;
    private String serviceStatus;
    private String Database;

    private String DBpassword;
    private String DBuserName;
    private String DBname;
    private String userRole;
    private int DBuserappCode; //admin,fieldManager,...
    private int DBuserStatus;

    List<ProcessingBean> chartMap = new ArrayList<ProcessingBean>();
    private String FromDate;
    private String ToDate;

    public String getServiceStatus() {
        return serviceStatus;
    }

    public String getServerNode() {
        return serverNode;
    }

    public void setServerNode(String serverNode) {
        this.serverNode = serverNode;
    }
    
    

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public List<ProcessingBean> getChartMap() {
        return chartMap;
    }

    public void setChartMap(List<ProcessingBean> chartMap) {
        this.chartMap = chartMap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDBname() {
        return DBname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDBname(String DBname) {
        this.DBname = DBname;
    }

    public String getDBpassword() {
        return DBpassword;
    }

    public void setDBpassword(String DBpassword) {
        this.DBpassword = DBpassword;
    }

    public String getDBuserName() {
        return DBuserName;
    }

    public void setDBuserName(String DBuserName) {
        this.DBuserName = DBuserName;
    }

    public int getDBuserStatus() {
        return DBuserStatus;
    }

    public void setDBuserStatus(int DBuserStatus) {
        this.DBuserStatus = DBuserStatus;
    }

    public int getDBuserappCode() {
        return DBuserappCode;
    }

    public void setDBuserappCode(int DBuserappCode) {
        this.DBuserappCode = DBuserappCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the FromDate
     */
    public String getFromDate() {
        return FromDate;
    }

    /**
     * @param FromDate the FromDate to set
     */
    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    /**
     * @return the ToDate
     */
    public String getToDate() {
        return ToDate;
    }

    /**
     * @param ToDate the ToDate to set
     */
    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    /**
     * @return the Database
     */
    public String getDatabase() {
        return Database;
    }

    /**
     * @param Database the Database to set
     */
    public void setDatabase(String Database) {
        this.Database = Database;
    }

   

}
