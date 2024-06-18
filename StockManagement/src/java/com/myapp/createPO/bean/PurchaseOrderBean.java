/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.bean;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thilinath
 */
public class PurchaseOrderBean {

    /**
     * @return the zipStream
     */
    public ByteArrayInputStream getZipStream() {
        return zipStream;
    }

    /**
     * @param zipStream the zipStream to set
     */
    public void setZipStream(ByteArrayInputStream zipStream) {
        this.zipStream = zipStream;
    }

    /**
     * @return the excelStream
     */
    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    /**
     * @param excelStream the excelStream to set
     */
    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
    }

    /**
     * @return the fromdate
     */
    public String getFromdate() {
        return fromdate;
    }

    /**
     * @param fromdate the fromdate to set
     */
    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    /**
     * @return the todate
     */
    public String getTodate() {
        return todate;
    }

    /**
     * @param todate the todate to set
     */
    public void setTodate(String todate) {
        this.todate = todate;
    }

    /**
     * @return the gridModel
     */
    public List<PurchaseOrderBean> getGridModel() {
        return gridModel;
    }

    /**
     * @param gridModel the gridModel to set
     */
    public void setGridModel(List<PurchaseOrderBean> gridModel) {
        this.gridModel = gridModel;
    }

    /**
     * @return the datacollection
     */
    public List<PurchaseOrderBean> getDatacollection() {
        return datacollection;
    }

    /**
     * @param datacollection the datacollection to set
     */
    public void setDatacollection(List<PurchaseOrderBean> datacollection) {
        this.datacollection = datacollection;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the ItemMap
     */
    public Map<Integer, String> getItemMap() {
        return itemMap;
    }

    /**
     * @param ItemMap the ItemMap to set
     */
    public void setItemMap(Map<Integer, String> ItemMap) {
        this.itemMap = ItemMap;
    }

    /**
     * @return the order_id
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the create_date
     */
    public Date getCreate_date() {
        return create_date;
    }

    /**
     * @param create_date the create_date to set
     */
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    /**
     * @return the dealer
     */
    public String getDealer() {
        return dealer;
    }

    /**
     * @param dealer the dealer to set
     */
    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    /**
     * @return the po_status
     */
    public String getPo_status() {
        return po_status;
    }

    /**
     * @param po_status the po_status to set
     */
    public void setPo_status(String po_status) {
        this.po_status = po_status;
    }

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the qnty
     */
    public double getQnty() {
        return qnty;
    }

    /**
     * @param qnty the qnty to set
     */
    public void setQnty(double qnty) {
        this.qnty = qnty;
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
     * @return the upProfileID
     */
    public int getUpProfileID() {
        return upProfileID;
    }

    /**
     * @param upProfileID the upProfileID to set
     */
    public void setUpProfileID(int upProfileID) {
        this.upProfileID = upProfileID;
    }

    /**
     * @return the upName
     */
    public String getUpName() {
        return upName;
    }

    /**
     * @param upName the upName to set
     */
    public void setUpName(String upName) {
        this.upName = upName;
    }

    /**
     * @return the upBinName
     */
    public String getUpBinName() {
        return upBinName;
    }

    /**
     * @param upBinName the upBinName to set
     */
    public void setUpBinName(String upBinName) {
        this.upBinName = upBinName;
    }

    /**
     * @return the upuserTypeId
     */
    public int getUpuserTypeId() {
        return upuserTypeId;
    }

    /**
     * @param upuserTypeId the upuserTypeId to set
     */
    public void setUpuserTypeId(int upuserTypeId) {
        this.upuserTypeId = upuserTypeId;
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
     * @return the brandMap
     */
    public Map<Integer, String> getBrandMap() {
        return brandMap;
    }

    /**
     * @param brandMap the brandMap to set
     */
    public void setBrandMap(Map<Integer, String> brandMap) {
        this.brandMap = brandMap;
    }

    /**
     * @return the categoryMap
     */
    public Map<Integer, String> getCategoryMap() {
        return categoryMap;
    }

    /**
     * @param categoryMap the categoryMap to set
     */
    public void setCategoryMap(Map<Integer, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    /**
     * @return the agentMap
     */
    public Map<Integer, String> getAgentMap() {
        return agentMap;
    }

    /**
     * @param agentMap the agentMap to set
     */
    public void setAgentMap(Map<Integer, String> agentMap) {
        this.agentMap = agentMap;
    }

    /**
     * @return the isChecked
     */
    public boolean isIsChecked() {
        return isChecked;
    }

    /**
     * @param isChecked the isChecked to set
     */
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    /**
     * @return the getPages
     */
    public String getGetPages() {
        return getPages;
    }

    /**
     * @param getPages the getPages to set
     */
    public void setGetPages(String getPages) {
        this.getPages = getPages;
    }

    /**
     * @return the upRepetedNewPw
     */
    public String getUpRepetedNewPw() {
        return upRepetedNewPw;
    }

    /**
     * @param upRepetedNewPw the upRepetedNewPw to set
     */
    public void setUpRepetedNewPw(String upRepetedNewPw) {
        this.upRepetedNewPw = upRepetedNewPw;
    }

    /**
     * @return the DbinId
     */
    public String getDbinId() {
        return DbinId;
    }

    /**
     * @param DbinId the DbinId to set
     */
    public void setDbinId(String DbinId) {
        this.DbinId = DbinId;
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
     * @return the PagePath
     */
    public String getPagePath() {
        return PagePath;
    }

    /**
     * @param PagePath the PagePath to set
     */
    public void setPagePath(String PagePath) {
        this.PagePath = PagePath;
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

    
    private int order_id;
    private Date create_date;
    private String dealer;
    private String po_status;
    private String category;
    private String brand;
    private String item;
    private double qnty;
    
    private String fromdate;
    private String todate;

    private boolean add;
    private boolean delete;
    private boolean view;
    private boolean update;
    private long fullCount;
    
    //table
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
    private List<PurchaseOrderBean> gridModel;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    //****************Search**********************
    private String searchName = "";
    private String delsuccess;
    private String message;
    private boolean success;

    //****************Edit**********************
    private int upProfileID;
    private String upName;
    private String upBinName;
    private int upuserTypeId;
    private Map<Integer, String> itemMap = new HashMap<Integer, String>();
    private Map<Integer, String> userTypeMap = new HashMap<Integer, String>();
    private Map<Integer, String> brandMap = new HashMap<Integer, String>();
    private Map<Integer, String> categoryMap = new HashMap<Integer, String>();
    private Map<Integer, String> agentMap = new HashMap<Integer, String>();
    private boolean isChecked;
    private String getPages;
    private String upRepetedNewPw;

    //****************delete**********************
    private String DbinId;
    private boolean isDeleted;
    private String dmessage;

    private String PagePath="";
    private String PageCode="";
    private List<PurchaseOrderBean> datacollection = new ArrayList<>();
    
}
