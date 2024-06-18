/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.constant;

/**
 *
 * @author thilinath
 */
public class SystemMessage {
    
    public static final String INITIAL_ERROR = "Environmental variable not found";
    public static final String CONFIGURAITON_ERROR = "Configuraiton file read error";

    public static final String OPERATION_SEND_SUCCESS = "Operation send sucessfully";
    public static final String SERVERSTATUS_FAIL = "Server is not responding";
    public static final String OPERATION_SELECT_FAIL = "Please select an operation";
    public static final String OPERATION_SEND_FAIL = "Operation send fail";

    public static final String USER_LOGIN_SUCCESS = "Login Sucessfully";
    public static final String USER_LOGOUT_SUCCESS = "Logout Sucessfully";
    public static final String USER_LOGIN_STATUSE_NOT_ACTIVATE = "status not active";
    public static final String USER_LOGIN_FAIL = "wrong username or password";
    public static final String USER_LOGIN_ERROR = "Error contact administrator";
    public static final String USER_LOGIN_EMPTY_NAME = "Enter user name";
    public static final String USER_LOGIN_EMPTY_PWD = "Enter password";

    public static final String USER_EMPTY_FIRST_NAME = "Empty First Name";
    public static final String USER_EMPTY_LAST_NAME = "Empty Last Name";
    public static final String USER_EMPTY_ADR1 = "Address Line 1 Empty";
    public static final String USER_EMPTY_ADR2 = "Address Line 2 Empty";
    public static final String USER_EMPTY_CITY = "City Empty";
    public static final String USER_EMPTY_CONTACT = "Contact Empty";
    public static final String USER_INVALID_CONTACT = "Invalid Contact";
    public static final String USER_INVALID_NAME = "Invaild name";
    public static final String USER_EMPTY_USERNAME = "Empty user name";
    public static final String USER_INVALID_USERNAME = "Invaild user name";
    public static final String USER_USERNAME_ALREADY = "User name already exist";
    public static final String USER_PW_EMPTY = "Empty User password";
    public static final String USER_PW_INVALID = "Invaild User password";
    public static final String USER_CONF_PW_EMPTY = "Empty User confirm password";
    public static final String USER_PASS_POLICY_CONFIRM = "Does not match with Password Policy";
    public static final String USER_CONF_PW_INVALID = "Invaild User confirm password";
    public static final String USER_CONF_PW_MISMATCH = "Password not matched";
    public static final String USER_EMPTY_USERTYPE = "Empty User Type";
    public static final String USER_ADD_SUCESS = "User registration is successfully done";
    public static final String USER_ADD_FAIL = "User registation is fail ";

    public static final String USER_COUNT_EXCEEDED = "Maximum user's exists";
    public static final String USER_UPDATED = "User update is successfully done ";
    public static final String USER_UPDATED_ERROR = "User update error";
    public static final String USER_DELETED = "User delete is successfully done ";
    public static final String USER_DELETED_ERROR = "User delete error";

    public static final String USER_DELETE_CANNOT_DELETE_CURRENT_USER = "Deleting Current Login User Not Allowed";
    public static final String USER_DELETE_CANNOT_DELETE_ADMIN = "Deleting Administrators Not Allowed";

    public static final String USER_CONFIRM_CONFIRMATION_ERROR = "User Activation error";
    public static final String USER_CONFIRM_CONFIRMATION_SUCCESS = "User Activation Success";

    public static final String USRP_PROFILE_ADD_SUCCESS = "User profile add successfully";
    public static final String USRP_PROFILE_ADD_FAIL = "User profile add fail";
    public static final String USRP_PROFILE_NOT_SELECT_PAGES = "Please Select Sections,Modules and Task";
    public static final String USRP_PROFILE_ALREADY = "User profile Name Already inserted";
    public static final String USRP_PROFILE_NAME_EMPTY = "Empty user profile name";
    public static final String USRP_PROFILE_NAME_INVALID = "Invalid user profile name";
    public static final String USRP_PROFILE_STATUS = "Select user profile status";

    public static final String USRP_ROLE_UPDATED = "User profile update successfully";
    public static final String USRP_ROLE_UPDATED_FAIL = "User profile update fail";
    public static final String USRP_PROF_DELETED = "User profile delete successfully";
    public static final String USRP_PROF_DELETED_FAIL = "User profile delete fail";
    public static final String USRP_PROF_ALREADY_USED = "User profile already used";
    public static final String USRP_ROLE_INVALID_SEARCH = "User Name Invalid fail";
    
    public static final String AGENT_ADD_SUCCESS = "Agent add successfully";
    public static final String AGENT_ADD_FAIL = "Agent add fail";
    public static final String AGENT_ALREADY = "Agent Name Already inserted";
    public static final String AGENT_NAME_EMPTY = "Empty Agent name";
    public static final String AGENT_NAME_INVALID = "Invalid Agent name";
    public static final String AGENT_EMPTY_ADR1 = "Address Line 1 Empty";
    public static final String AGENT_EMPTY_ADR2 = "Address Line 2 Empty";
    public static final String AGENT_EMPTY_CITY = "City Empty";
    public static final String AGENT_EMPTY_CONTACT = "Contact Empty";
    public static final String AGENT_INVALID_CONTACT = "Invalid Contact";
    
    public static final String EMPTY_BRAND_NAME = "Brand Name is Empty";
    public static final String INVALID_BRAND_NAME = "Brand Name Invalid";
    public static final String FAIL_BRAND_NAME = "Brand Name Update Fail";
    public static final String SUCCESS_BRAND_NAME = "Brand Name Add Success";
    public static final String SUCCESS_BRAND_DELETE = "Brand Delete Success";
    public static final String FAIL_BRAND_DELETE = "Brand Delete Error";
    
    public static final String EMPTY_CATEGORY_NAME = "Category Name is Empty";
    public static final String INVALID_CATEGORY_NAME = "Category Name Invalid";
    public static final String FAIL_CATEGORY_NAME = "Category Name Update Fail";
    public static final String SUCCESS_CATEGORY_NAME = "Category Name Add Success";
    public static final String SUCCESS_CATEGORY_DELETE = "Category Delete Success";
    public static final String FAIL_CATEGORY_DELETE = "Category Delete Fail";
    
    public static final String EMPTY_ITEM_NAME = "Item Name is Empty";
    public static final String INVALID_CATEGORY = "Please Select Category";
    public static final String INVALID_BRAND = "Please Select Brand";
    public static final String INVALID_STATUS = "Please Select Status";
    public static final String SUCCESS_ITEM_ADD = "Item Add Success";
    public static final String FAIL_ITEM_ADD = "Item Add Fail";
    public static final String SUCCESS_ITEM_UPDATE = "Item Update Success";
    public static final String FAIL_ITEM_UPDATE = "Item Update Fail";
    public static final String INVALID_DEALER = "Please Select Agent";
    
    public static final String PO_CREATE_SUCCESS = "Purchasing Order Create Success";
    public static final String PO_CREATE_FAIL = "Purchasing Order Create Fail";
    
    public static final String FILL_BRAND = "Vehicle Brand Empty";
    public static final String FILL_NUMBER = "Vehicle Number Empty";
    
    public static final String WAREHOUSE_NAME_EMPTY = "Warehouse Name Empty";
    public static final String WAREHOUSE_SUCCESS_ADD = "Warehouse add Succesfully";
    public static final String WAREHOUSE_SUCCESS_UPDATE = "Warehouse Update Succesfully";
    public static final String WAREHOUSE_FAIL_UPDATE = "Warehouse Update Fail";
    public static final String WAREHOUSE_FAIL_ADD = "Warehouse add fail";
    public static final String WAREHOUSE_SUCCESS_DELETE = "Warehouse Delete Succesfully";
    public static final String WAREHOUSE_FAIL_DELETE = "Warehouse Delete Fail";
    
    public static final String RACKS_NAME_EMPTY = "Racks Name Empty";
    public static final String RACKS_HR_VALUE_EMPTY = "Racks Horizontal Cell Count Empty";
    public static final String RACKS_VR_VALUE_EMPTY = "Racks Vertical Cell Count Empty";
    public static final String CELL_STORAGE_EMPTY = "Cell Storage Empty";
    public static final String RACKS_HR_VALUE_INVALID = "Racks Horizontal Cell Count Invalid";
    public static final String RACKS_VR_VALUE_INVALID = "Racks Vertical Cell Count Invalid";
    public static final String CELL_STORAGE_INVALID = "Cell Storage Invalid";
    public static final String RACKS_SUCCESSFULY_ADD = "Racks Add Successfully";
    public static final String RACKS_FAIL_ADD = "Racks Add Fail";
    public static final String RACKS_SUCCESS_DELETE = "Rack Delete Succesfully";
    public static final String RACKS_FAIL_DELETE = "Rack Delete Fail";
    
    public static final String PO_ITEM_SUCCESS_DELETE = "PO Item Delete Succesfully";
    public static final String PO_ITEM_FAIL_DELETE = "PO Item Delete Fail";
    
}
