/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thilinath
 */
public class AgentBean {

    /**
     * @return the agentId
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * @param agentId the agentId to set
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * @return the agentName
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * @param agentName the agentName to set
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * @return the adr1
     */
    public String getAdr1() {
        return adr1;
    }

    /**
     * @param adr1 the adr1 to set
     */
    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    /**
     * @return the adr2
     */
    public String getAdr2() {
        return adr2;
    }

    /**
     * @param adr2 the adr2 to set
     */
    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the upAgentId
     */
    public String getUpAgentId() {
        return upAgentId;
    }

    /**
     * @param upAgentId the upAgentId to set
     */
    public void setUpAgentId(String upAgentId) {
        this.upAgentId = upAgentId;
    }

    /**
     * @return the upAgentName
     */
    public String getUpAgentName() {
        return upAgentName;
    }

    /**
     * @param upAgentName the upAgentName to set
     */
    public void setUpAgentName(String upAgentName) {
        this.upAgentName = upAgentName;
    }

    /**
     * @return the upAdr1
     */
    public String getUpAdr1() {
        return upAdr1;
    }

    /**
     * @param upAdr1 the upAdr1 to set
     */
    public void setUpAdr1(String upAdr1) {
        this.upAdr1 = upAdr1;
    }

    /**
     * @return the upAdr2
     */
    public String getUpAdr2() {
        return upAdr2;
    }

    /**
     * @param upAdr2 the upAdr2 to set
     */
    public void setUpAdr2(String upAdr2) {
        this.upAdr2 = upAdr2;
    }

    /**
     * @return the upCity
     */
    public String getUpCity() {
        return upCity;
    }

    /**
     * @param upCity the upCity to set
     */
    public void setUpCity(String upCity) {
        this.upCity = upCity;
    }

    /**
     * @return the upContact
     */
    public String getUpContact() {
        return upContact;
    }

    /**
     * @param upContact the upContact to set
     */
    public void setUpContact(String upContact) {
        this.upContact = upContact;
    }

    /**
     * @return the userTypeMap
     */
    public Map<Integer, String> getUserTypeMap() {
        return userTypeMap;
    }

    /**
     * @param userTypeMap the userTypeMap to set
     */
    public void setUserTypeMap(Map<Integer, String> userTypeMap) {
        this.userTypeMap = userTypeMap;
    }

    /**
     * @return the deleteAgentId
     */
    public String getDeleteAgentId() {
        return deleteAgentId;
    }

    /**
     * @param deleteAgentId the deleteAgentId to set
     */
    public void setDeleteAgentId(String deleteAgentId) {
        this.deleteAgentId = deleteAgentId;
    }

    /**
     * @return the isDeleted
     */
    public boolean isIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the dmessage
     */
    public String getDmessage() {
        return dmessage;
    }

    /**
     * @param dmessage the dmessage to set
     */
    public void setDmessage(String dmessage) {
        this.dmessage = dmessage;
    }

    /**
     * @return the searchAgent
     */
    public String getSearchAgent() {
        return searchAgent;
    }

    /**
     * @param searchAgent the searchAgent to set
     */
    public void setSearchAgent(String searchAgent) {
        this.searchAgent = searchAgent;
    }

    /**
     * @return the delsuccess
     */
    public String getDelsuccess() {
        return delsuccess;
    }

    /**
     * @param delsuccess the delsuccess to set
     */
    public void setDelsuccess(String delsuccess) {
        this.delsuccess = delsuccess;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the gridModel
     */
    public List<AgentBean> getGridModel() {
        return gridModel;
    }

    /**
     * @param gridModel the gridModel to set
     */
    public void setGridModel(List<AgentBean> gridModel) {
        this.gridModel = gridModel;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the records
     */
    public Long getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Long records) {
        this.records = records;
    }

    /**
     * @return the sord
     */
    public String getSord() {
        return sord;
    }

    /**
     * @param sord the sord to set
     */
    public void setSord(String sord) {
        this.sord = sord;
    }

    /**
     * @return the sidx
     */
    public String getSidx() {
        return sidx;
    }

    /**
     * @param sidx the sidx to set
     */
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    /**
     * @return the searchField
     */
    public String getSearchField() {
        return searchField;
    }

    /**
     * @param searchField the searchField to set
     */
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the searchOper
     */
    public String getSearchOper() {
        return searchOper;
    }

    /**
     * @param searchOper the searchOper to set
     */
    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    /**
     * @return the loadonce
     */
    public boolean isLoadonce() {
        return loadonce;
    }

    /**
     * @param loadonce the loadonce to set
     */
    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    /**
     * @return the add
     */
    public boolean isAdd() {
        return add;
    }

    /**
     * @param add the add to set
     */
    public void setAdd(boolean add) {
        this.add = add;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the view
     */
    public boolean isView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(boolean view) {
        this.view = view;
    }

    /**
     * @return the update
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * @param update the update to set
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * @return the fullCount
     */
    public long getFullCount() {
        return fullCount;
    }

    /**
     * @param fullCount the fullCount to set
     */
    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * @return the PageCode
     */
    public String getPageCode() {
        return PageCode;
    }

    /**
     * @param PageCode the PageCode to set
     */
    public void setPageCode(String PageCode) {
        this.PageCode = PageCode;
    }

    /**
     * @return the Module
     */
    public String getModule() {
        return Module;
    }

    /**
     * @param Module the Module to set
     */
    public void setModule(String Module) {
        this.Module = Module;
    }

    /**
     * @return the Section
     */
    public String getSection() {
        return Section;
    }

    /**
     * @param Section the Section to set
     */
    public void setSection(String Section) {
        this.Section = Section;
    }

    /**
     * @return the Task
     */
    public String getTask() {
        return Task;
    }

    /**
     * @param Task the Task to set
     */
    public void setTask(String Task) {
        this.Task = Task;
    }

    private String agentId;
    private String agentName;
    private String adr1;
    private String adr2;
    private String city;
    private String contact;

    // Edit Field
    private String upAgentId;
    private String upAgentName;
    private String upAdr1;
    private String upAdr2;
    private String upCity;
    private String upContact;

    private Map<Integer, String> userTypeMap = new HashMap<Integer, String>();

    //****************delete**********************
    private String deleteAgentId;
    private boolean isDeleted;
    private String dmessage;

    //****************Search**********************
    private String searchAgent = "";
    private String delsuccess;
    private String message;
    private boolean success;

    // Table
    private List<AgentBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;

    private boolean add;
    private boolean delete;
    private boolean view;
    private boolean update;

    private long fullCount;
    
       //****************Search**********************
    private String searchName="";
    private String PageCode="";
    
    
   //***************Working Path*************
    private String Module="";
    private String Section="";
    private String Task="";

}
