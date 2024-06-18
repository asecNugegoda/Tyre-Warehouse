<%-- 
    Document   : registerUser
    Created on : Feb 13, 2017, 10:42:51 AM
    Author     : thilinath
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>   
<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="../../Styles.jsp" />
        <style>
            .ui-button-text-only .ui-button-text {
                ping: 0;
            }
            .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
                font-weight: normal; 
            }
        </style>
        <script type="text/javascript">
            function resetForm() {
                $('#userName').val("");
                $('#password').val("");
                $('#confirmPassword').val("");
                $('#firstName').val("");
                $('#lastName').val("");
                $('#adr1').val("");
                $('#adr2').val("");
                $('#city').val("");
                $('#contact').val("");
                $('#userType').val(-1);


                $('#upName').val("");
                $('#upuserName').val("");
                $('#upuserTypeId').val("-1");
                $('#upNewPw').val("");
                $('#upRepetedNewPw').val("");
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function VehicleAssignformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:viewVechileProfile(&#34;" + rowObject.userTypeId + "&#34;,&#34;" + rowObject.firstName + "&#34;)'><i class='fa fa-share-square-o' aria-hidden='true'></i></a>";
            }

            function viewVechileProfile(id, name) {
                $("#viewdialog").data('Id', id);
                $("#viewdialog").data('name', name).dialog('open');
            }
            $.subscribe('openview', function (event, data) {
                utilityManager.resetMessage();
//                resetData();
                var $led = $("#viewdialog");
//                alert($led.data('Id').replace(/ /g,"_"));
                $led.load("vehicleusrMng?usrId=" + $led.data('Id') + "&cBinProfile=" + $led.data('name').replace(/ /g, "_"));
            });
            
            function resetFormWithMessage() {
                resetForm();
                utilityManager.resetMessage();
            }
            function changeAction() {
                var isChecked = document.getElementById("isChecked").checked;
                if (isChecked == true) {
                    document.getElementById("upNewPw").disabled = false;
                    document.getElementById("upRepetedNewPw").disabled = false;
                } else {
                    document.getElementById("upNewPw").disabled = true;
                    document.getElementById("upRepetedNewPw").disabled = true;
                }

            }

            $.subscribe('onclicksearch', function (event, data) {
                utilityManager.resetMessage();
                var searchName = $('#searchName').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {searchName: searchName}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });


            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' disabled='vupdate' title='Edit User' onClick='editUser(&#34;" + cellvalue + "&#34;)'><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
            }

            function Statusformatter(cellvalue) {
                if (cellvalue == 1) {
                    return "<i class='fa fa-circle active' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle' aria-hidden='true'></i>";
                }
            }

            function deleteUserInit(uname, utypeid) {
                utilityManager.resetMessage();
                $("#deleteConfirmDialog").data('uname', uname);
                $("#deleteConfirmDialog").data('utypeid', utypeid).dialog('open');
                $("#deleteConfirmDialog").html('<p>Please confirm delete : ' + uname + '</p>');
                return false;
            }

            function deleteUser(uname, utypeid) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteusrMng',
                    data: {duserName: uname, duserTypeId: utypeid},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.isDeleted == true) {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'successmsg');
                        } else {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'errormsg');
                        }
//                        resetData();
                        resetForm();
                    }
                });
            }

            function resetData() {
                utilityManager.resetMessage();
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function editUser(keyval) {
                $('#webuserEditForm').show();
                $('.lnk-back').removeClass('hide-element');
                $('#searchdiv').hide();
                $('#addDiv').hide();
                $('#uplogin_div').hide();
                utilityManager.resetMessage();
                $('#task').empty();
                var text = ' Edit User';
                $('#task').append(text);


                $.ajax({
                    url: '${pageContext.request.contextPath}/findusrMng',
                    data: {upName: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#webuserEditForm').show();
                        $('#searchdiv').hide();

                        $('#upfirstName').val(data.upfirstName);
                        $('#uplastName').val(data.uplastName);
                        $('#upuserTypeId').val(data.upuserType);
                        if (data.upuserType != 5) {
                            $('#uplogin_div').show();
                            $('#upuserName').val(data.upuserName);
                            $('#upuserName').attr('readOnly', true);
                        } else {
                            $('#upuserName').val("");
                        }

                        $('#upadr1').val(data.upadr1);
                        $('#upadr2').val(data.upadr2);
                        $('#upcity').val(data.upcity);
                        $('#upcontact').val(data.upcontact);
                        $('#upCustID').val(data.upCustID);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logOut.action";
                    }
                });
            }

            function resetUpdateForm() {
                utilityManager.resetMessage();
                var keyval = $('#upCustID').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findusrMng',
                    data: {upName: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#upfirstName').val(data.upfirstName);
                        $('#uplastName').val(data.uplastName);
                        $('#upuserTypeId').val(data.upuserType);
                        if (data.upuserType != 5) {
                            $('#uplogin_div').show();
                            $('#upuserName').val(data.upuserName);
                            $('#upuserName').attr('readOnly', true);
                        } else {
                            $('#upuserName').val("");
                        }

                        $('#upadr1').val(data.upadr1);
                        $('#upadr2').val(data.upadr2);
                        $('#upcity').val(data.upcity);
                        $('#upcontact').val(data.upcontact);
                        $('#upCustID').val(data.upCustID);
                        document.getElementById("isChecked").checked = false;
                        document.getElementById("upNewPw").disabled = true;
                        document.getElementById("upRepetedNewPw").disabled = true;

                    }

                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function addUser() {
                utilityManager.resetMessage();
                $('.lnk-back').removeClass('hide-element');
                $('#addDiv').show();
                $('#searchdiv').hide();
                $('#login_div').hide();
                $('#task').empty();
                var text = ' Add User';
                $('#task').append(text);
            }

            function secondaryValues() {
                var status = $('#userType').val();

                if (status != 5) {
                    $('#login_div').show();
                } else {
                    $('#login_div').hide();
                }
            }

            function updateUserLogin() {
                var upstat = $('#upuserTypeId').val();
                if (upstat != 5) {
                    $('#uplogin_div').show();
                } else {
                    $('#uplogin_div').hide();
                }
            }
            $(function () {
                //$('#searchdiv').hide();
                $('#a1').click(function () {
                    $('#searchdiv').show();
                });
            });

        </script>

    </head>
    <s:set id="vadd" var="vadd"><s:property  value="add" default="true"/></s:set>
    <s:set id="vupdate" var="vupdate"><s:property value="update" default="true"/></s:set>
    <s:set id="vdelete" var="vdelete"><s:property value="delete" default="true"/></s:set>
    <s:set id="vview" var="vview"><s:property value="view" default="true"/></s:set>


        <body>
            <section class="app-content">
            <jsp:include page="../../header.jsp" /> 

            <!-- Page content begin -->
            <div class="content innerpage">


                <h1 class="page-title"><s:property value="Section"/><a href="#" class="lnk-back hide-element do-nothing"><i class="fa fa-arrow-left" aria-hidden="true"></i> back</a></h1>

                <div class="content-section search-form" id="searchdiv">

                    <s:form theme="simple" >
                        <div class="content-data">
                            <h2 class="section-title">Search</h2>
                            <div class="d-row singlecol-row">
                                <label class="left-col form-label">Name</label>
                                <div class="right-col form-field">
                                    <s:textfield name="searchName" id="searchName" cssClass="txt-input width-35"/>

                                    <sj:a 
                                        id="searchbut" 
                                        button="true"                                             
                                        onClickTopics="onclicksearch"    
                                        cssClass="btn default-button"
                                        onfocus="true"
                                        ><i class="fa fa-search" aria-hidden="true"></i> Search</sj:a>

                                    <sj:a 
                                        disabled="#vadd"
                                        id="btnAdd" 
                                        button="true"
                                        onclick="addUser()"   
                                        cssClass="btn default-button"
                                        onfocus="true"
                                        ><i class="fa fa-plus" aria-hidden="true"></i> Add</sj:a>

                                    </div>
                                </div>
                            </div>
                    </s:form>
                </div>


                <!-- Data form begin -->
                <div class="content-section data-form" id="addDiv" style="display: none">

                    <s:form theme="simple" method="post" name="adduserkk" id="adduserkkk" >
                        <div class="content-data">

                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg" > 
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <div class="d-row">
                                <label class="col-1 form-label">First Name<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield id="firstName" name="firstName" cssClass="txt-input width-35" />
                                </div>
                                <label class="col-3 form-label">Last Name<sup class="required">*</sup></label>
                                <div class="col-4 form-field">
                                    <s:textfield id="lastName" name="lastName" cssClass="txt-input width-35" />
                                </div>
                            </div>

                            <div class="d-row">
                                <label class="col-1 form-label">Address Line 1<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield id="adr1" name="adr1" cssClass="txt-input width-35" />
                                </div>
                                <label class="col-3 form-label">Address Line 2<sup class="required">*</sup></label>
                                <div class="col-4 form-field">
                                    <s:textfield id="adr2" name="adr2" cssClass="txt-input width-35"/>
                                </div>
                            </div>

                            <div class="d-row">
                                <label class="col-1 form-label">City<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield id="city" name="city" cssClass="txt-input width-35" />
                                </div>
                                <label class="col-3 form-label">Contact<sup class="required">*</sup></label>
                                <div class="col-4 form-field">
                                    <s:textfield id="contact" name="contact" cssClass="txt-input width-35" maxLength="10"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">User Profile<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:select id="userType"  name="userType"  headerKey="-1"  headerValue="---Select---"  list="%{userTypeMap}" onchange="secondaryValues()" cssClass="ddl-input" />
                                </div>
                            </div>

                            <div class="content-data" id="login_div">
                                <div class="d-row">
                                    <label class="col-1 form-label">User Name<sup class="required">*</sup></label>
                                    <div class="col-2 form-field">
                                        <s:textfield id="userName" name="userName" cssClass="txt-input width-35"/>
                                    </div>
                                </div>

                                <div class="d-row">
                                    <label  class="col-1 form-label">Password<sup class="required">*</sup></label>
                                    <div class="col-2 form-field">
                                        <s:password id="password" name="password" cssClass="txt-input width-60"/>
                                    </div>

                                    <label class="col-3 form-label">Repeat Password<sup class="required">*</sup></label>
                                    <div class="col-4 form-field">
                                        <s:password id="confirmPassword" name="confirmPassword" cssClass="txt-input width-60"/>
                                    </div>
                                </div>
                                <div class="d-row">
                                    <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                                </div>
                            </div>

                            <!-- Tow column form button panel begin -->		
                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <!--<button id="btnSubmit" type="submit" class="btn default-button"><i class="fa fa-floppy-o" aria-hidden="true"></i> save</button>-->
                                    <s:url var="saveurl" action="addusrMng"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-floppy-o" aria-hidden="true"></i><sj:submit  href="%{saveurl}"  targets="divmsg" value="Save" button="true" cssClass="btn default-button searchicon" disabled="#vadd"/></div>
                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit  button="true" onclick="resetFormWithMessage()"  value="Reset" cssClass="btn reset-button"/></div>
                                </div>
                            </div>
                            <!-- End -->
                        </div>
                    </s:form>
                </div>

                <s:form theme="simple" method="post" id="webuserEditForm" cssClass="content-section data-form" cssStyle="display: none">
                    <div class="content-data">
                        <!-- Error and success message panel begin -->
                        <div class="msg-panel add-form-msg" >
                            <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>
                        <!-- End -->

                        <!-- Two colum form row begin -->
                        <div class="d-row">
                            <label class="col-1 form-label">First Name<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:hidden name="upCustID" id="upCustID" cssClass="txt-input width-35"/>
                                <s:textfield id="upfirstName" name="upfirstName" cssClass="txt-input width-35" />
                            </div>
                            <label class="col-3 form-label">Last Name<sup class="required">*</sup></label>
                            <div class="col-4 form-field">
                                <s:textfield id="uplastName" name="uplastName" cssClass="txt-input width-35" />
                            </div>
                        </div>

                        <div class="d-row">
                            <label class="col-1 form-label">Address Line 1<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:textfield id="upadr1" name="upadr1" cssClass="txt-input width-35" />
                            </div>
                            <label class="col-3 form-label">Address Line 2<sup class="required">*</sup></label>
                            <div class="col-4 form-field">
                                <s:textfield id="upadr2" name="upadr2" cssClass="txt-input width-35"/>
                            </div>
                        </div>

                        <div class="d-row">
                            <label class="col-1 form-label">City<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:textfield id="upcity" name="upcity" cssClass="txt-input width-35" />
                            </div>
                            <label class="col-3 form-label">Contact<sup class="required">*</sup></label>
                            <div class="col-4 form-field">
                                <s:textfield id="upcontact" name="upcontact" cssClass="txt-input width-35" maxLength="10"/>
                            </div>
                        </div>
                        <div class="d-row">
                            <label class="col-1 form-label">User Profile<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:select  name="upuserTypeId" headerKey="-1" 
                                           headerValue="---Select User Type ---" listKey="key" listValue="value"
                                           onchange="updateUserLogin()"
                                           list="%{userTypeMap}" id="upuserTypeId" cssClass="ddl-input"/>
                            </div>
                        </div>

                        <div class="content-data" id="uplogin_div">
                            <div class="d-row">
                                <label class="col-1 form-label">User Name</label>
                                <div class="col-2 form-field">
                                    <s:hidden name="upusrName" id="upusrName" cssClass="txt-input width-35"/>
                                    <s:textfield name="upuserName" id="upuserName" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Password Change</label>
                                <div class="col-2 form-field">
                                    <s:checkbox label="checkboxpw" name="isChecked" id="isChecked" onclick="changeAction()" /> 
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">New Password</label>
                                <div class="col-2 form-field">
                                    <s:password name="upNewPw" id="upNewPw" cssClass="txt-input width-70" disabled="true"/>
                                </div>

                                <label class="col-3 form-label">Repeat New Password</label>
                                <div class="col-4 form-field">
                                    <s:password name="upRepetedNewPw" id="upRepetedNewPw" cssClass="txt-input width-70" disabled="true"/>
                                </div>
                            </div>
                        </div>

                        <div class="d-row">
                            <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                        </div>

                        <div class="d-row cpanel">
                            <label class="left-col">&nbsp;</label>
                            <div class="right-col">
                                <s:url var="updateuserurl" action="updateusrMng"/>                                   
                                <div class="btn-wrap"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><sj:submit  href="%{updateuserurl}"  targets="divmsg" value="Update" button="true" cssClass="btn default-button" disabled="#vupdate"/></div>
                                <div class="btn-wrap"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset" onClick="resetUpdateForm()" cssClass="btn reset-button"/></div>
                            </div>
                        </div>
                    </div>

                </s:form>
                <!-- End -->
                <!-- Grid data begin -->
                <div class="content-section">
                    <div class="content-data">
                        <h2 class="section-title">All registered users</h2>
                        <!-- Error and success message panel begin -->
                        <div class="msg-panel del-user-msg" >
                            <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>
                        <!-- End -->
                    </div>

                    <sj:dialog 
                            id="viewdialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="1000"
                            height="500"
                            position="center"
                            title="Add Vehicle"
                            onOpenTopics="openview" 
                            loadingText="Loading .."
                            />

                    <sj:dialog 
                        id="deleteConfirmDialog" 
                        buttons="{ 
                        'OK':function() { deleteUser($(this).data('uname'),$(this).data('utypeid'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete User Confirmation"
                        width="400"
                        position="center"
                        />
                    <sj:dialog 
                        id="deleteMessageDialog" 
                        buttons="{
                        'OK':function() { $(this).data('dmessage'); $( this ).dialog( 'close' );}
                        }"  
                        autoOpen="false" 
                        modal="true" 
                        title="Delete User" 
                        width="400"
                        height="150"
                        position="center"
                        />

                    <div id="tablediv" class="custom-grid">

                        <s:url var="listurl" action="listusrMng"/>
                        <!--caption="Edit and View User Details"-->
                        <sjg:grid
                            id="gridtable"                                
                            caption="All Registered User"
                            dataType="json"
                            href="%{listurl}"
                            pager="true"
                            gridModel="gridModel"
                            rowList="10,15,20"
                            rowNum="10"
                            autowidth="true"
                            rownumbers="true"
                            onCompleteTopics="completetopics"
                            rowTotal="false"
                            viewrecords="true"
                            >
                            <sjg:gridColumn name="userTypeId" title="userTypeId" hidden="true"/>
                            <sjg:gridColumn name="firstName" index="firstName" title="First Name" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="lastName" index="lastName" title="Last Name" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="contact" index="contact" title="Contact" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="adr1" index="adr1" title="Address Line 1" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="adr2" index="adr2" title="Address Line 2" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="city" index="city" title="City" align="left" sortable="true"  />  
                            <sjg:gridColumn name="userTypeId"  title="Add Vehicle"  width="90" align="center" formatter="VehicleAssignformatter" sortable="false"/>
                            <sjg:gridColumn name="userType" index="epicTleUserProfile.description" title="User Role"  align="left" sortable="true"/>
                            <sjg:gridColumn name="userTypeId"  title="Action" align="center" formatter="editformatter" sortable="true" width="80" cssClass="action-col"/>
                        </sjg:grid> 
                    </div>

                </div>
                <!-- End -->

                <!--End of Body Content-->
            </div>
            <jsp:include page="../../footer.jsp" />

        </section>

        <script type="text/javascript">
            $(document).ready(function () {
                //Back button event
                $('.lnk-back').on('click', function () {
                    $('#webuserEditForm').hide();
                    $('#searchdiv').show();
                    utilityManager.resetMessage();
                    $('#addDiv').hide();
                    $('#task').empty();
                    jQuery("#gridtable").trigger("reloadGrid");
                    $('.lnk-back').addClass('hide-element');
                    var text = ' Search User';
                    $('#task').append(text);
                    return false;
                });

                $(document).ready(function () {

                    setTimeout(function () {
                        $(window).trigger('resize');
                    }, 500);

                });

            });
        </script>
        <!-- End -->
    </body>
</html>