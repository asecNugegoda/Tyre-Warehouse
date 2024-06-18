
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
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

            function editformatter(cellvalue, options, rowObject) {
//                alert(cellvalue);
                return "<a href='#' title='Edit User Profile' onClick='javascript:editUserProfile(&#34;" + cellvalue + "&#34;)'><i class='fa fa-pencil' aria-hidden='true'></i></a> <a href='#' title='Delete User Profile' onClick='deleteUserProfileInit(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.name + "&#34;)'><i class='fa fa-trash-o' aria-hidden='true'></i></a>";
            }

            function Statusformatter(cellvalue) {
                if (cellvalue == 1) {
                    return "<i class='fa fa-circle active' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle' aria-hidden='true'></i>";
                }
            }


            function imageformatter(cellvalue, options, rowObject) {
                if (cellvalue == 1) {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }

                return html;
            }

            //load update user form
            function editUserProfile(keyval) {
                utilityManager.resetMessage();
                $('.lnk-back').removeClass('hide-element');
                $('#task').empty();
                var symb = '<i class="fa fa-angle-double-right" aria-hidden="true"></i>';
                var text = ' Edit User Profile';
                $('#task').append(symb, text);

                $.ajax({
                    url: '${pageContext.request.contextPath}/LoaduserPrivlage',
                    data: {upProfileID: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#message').empty();
//                        $('#webusersearchForm').hide();
                        $('#addDiv').hide();
//                        $("#Updateform").show();
                        $('.search-form').removeClass('hide-element').removeClass('show-element').addClass('hide-element');
                        $('#Updateform').eq(0).removeClass('hide-element');
                        utilityManager.resetMessage();
                        $('#upProfileID').val(data.upProfileID);
                        $('#upName').val(data.upName);
                        $('#name').val(data.upName);
                        $('#upStatus').val(data.upStatus);
//                        $('#upmoduleList').empty();
//                        $('#upmoduleList').append($('<option></option>').val("").html("--Select Module--"));
//
//                        $.each(data.modulesMap, function (key, value) {
//                            $('#upmoduleList').append($('<option></option>').val(key).html(value));
//                        });


                    }
                });
            }

            function toleft2() {
                $("#newBox2 option:selected").each(function () {
                    if ($(this).val() == "04") {
                        toleftall2();
                    } else {
                        $("#currentBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    }
                })
            }

            function toright2() {
                $("#currentBox2 option:selected").each(function () {
                    if ($(this).val() != "04" && $("#currentBox2 option[value='04']").text() != '') {
                        $("#newBox2").append($('<option></option>').val('04').html('VIEW'));
                        $("#currentBox2 option[value='04']").remove();
                        $("#newBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    } else {
                        $("#newBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    }
                })
            }
            function toleftall2() {
                $("#newBox2 option").each(function () {

                    $("#currentBox2").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();

                });
            }
            function torightall2() {
                $("#currentBox2 option").each(function () {

                    $("#newBox2").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }

            function toleft() {
                $("#upnewBox2 option:selected").each(function () {
                    if ($(this).val() == "04") {
                        toleftall();
                    } else {
                        $("#upcurrentBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    }
                })
            }

            function toright() {
                $("#upcurrentBox2 option:selected").each(function () {
                    if ($(this).val() != "04" && $("#upcurrentBox2 option[value='04']").text() != '') {
                        $("#upnewBox2").append($('<option></option>').val('04').html('VIEW'));
                        $("#upcurrentBox2 option[value='04']").remove();
                        $("#upnewBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    } else {
                        $("#upnewBox2").append($('<option>', {
                            value: $(this).val(),
                            text: $(this).text()
                        }));
                        $(this).remove();
                    }
                })
            }


            function toleftall() {
                $("#upnewBox2 option").each(function () {

                    $("#upcurrentBox2").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();

                });
            }


            function torightall() {
                $("#upcurrentBox2 option").each(function () {

                    $("#upnewBox2").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }

            function loadPageModule(keyval) {
                if (keyval == null) {
                    keyval = $('#upmoduleList').val();
                    $('#upmoduleList').val("--Select Module--");
                    var temp = true;
                }
                $('#newBox2 option').prop('selected', true);
                $('#currentBox2 option').prop('selected', true);
                var upProfileID = $('#upProfileID').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/getPageMapuserPrivlage',
                    data: {upProfileID: upProfileID, selectModule: keyval, selectedpages: $('#newBox2').val()},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#currentBox2').empty();
                        $('#newBox2').empty();

                        $('#upProfileID').val(data.upProfileID);

                        $.each(data.allPageMap, function (val, text) {
                            $('#currentBox2').append($('<option></option>').val(val).html(text));
                        });
                        $.each(data.alreadyAcsessPageMap, function (val, text) {
                            $('#newBox2').append($('<option></option>').val(val).html(text));
                        });
                        jQuery("#gridtable").trigger("reloadGrid");
                        if (temp) {
                            $('#upmoduleList').val("--Select Module--");
                            $("#currentBox2 option").remove();
                            $("#newBox2 option").remove();
                        }
                        jQuery("#gridtable").trigger("reloadGrid");
                    }
                });
            }


            function backToMain() {
                $('#message').empty();
                $('#Updateform').hide();
                $('#addDiv').hide();
                $('#userProfileSearchForm').show();
                $("#currentBox2 option").empty();
                $("#upcurrentBox option").empty();
                $("#newBox2 option").empty();
                $("#upnewBox option").empty();
                $('#task').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }

            //reset form
            function resetForm() {
                $('#upmoduleList').val('0');
                $('#uppages').val('0');
                $('#upcurrentBox2').empty();
                $('#upnewBox2').empty();
//                $('#userProfileSearchForm').hide();
//                $('#userProfileEditForm').show();
                $("#formUpdate").load("pages/userprofile/editAndViewUserProfile");
                jQuery("#gridtable").trigger("reloadGrid");
            }


            function resetUpdateForm() {
                var upProfileID = $('#upProfileID').val();
                utilityManager.resetMessage();
                $.ajax({
                    url: '${pageContext.request.contextPath}/LoaduserPrivlage',
                    data: {upProfileID: upProfileID},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#message').empty();
//                        $('#userProfileSearchForm').hide();
//                        $('#userProfileEditForm').show();
                        $('#upProfileID').val(data.upProfileID);
                        $('#upName').val(data.upName);
                        $('#name').val(data.upName);
                        $('#upStatus').val(data.upStatus);
                        $('#upmoduleList').val('0');
                        $('#uppages').val('0');
                        $('#upcurrentBox2').empty();
                        $('#upnewBox2').empty();

                    }
                });

            }

            function deleteUserProfileInit(keyval, name) {
                utilityManager.resetMessage();
                $('#message').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('<p>Please confirm deletion of Profile Id : ' + name + '</p>');
                return false;
            }


            function deleteUserProfile(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteuserPrivlage',
                    data: {profileID: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.delsuccess === 1) {
                            utilityManager.showMessage('.del-user-msg', data.message, 'successmsg');
                        } else {
                            utilityManager.showMessage('.del-user-msg', data.message, 'errormsg');
                        }
                        jQuery("#gridtable").trigger("reloadGrid");

                    }
                });

            }

            function loadModulePage(selectedModule, flag) {

                if (flag === "Add") {
                    $('#currentBox2').empty();
                    $('#newBox2').empty();
                    $('#pages option').prop('selected', true);
                    if (selectedModule == '0') {
                        $('#pages').empty();
                        $('#currentBox2').empty();
                        $('#newBox2').empty();
                        $('#pages').append($('<option></option>').val("0").html("---Select---"));
                    } else {
                        $.ajax({
                            url: '${pageContext.request.contextPath}/loadPagesuserPrivlage.action',
                            data: {modules: selectedModule},
                            dataType: "json",
                            type: "POST",
                            success: function (data) {
                                $('#pages').empty();
                                $('#currentBox2').empty();
                                $('#pages').append($('<option></option>').val("0").html("---Select---"));
                                $.each(data.allPageMap, function (val, text) {
                                    $('#pages').append($('<option></option>').val(val).html(text));
                                });
                            }

                        });
                    }
                } else {
                    $('#uppages option').prop('selected', true);
                    $('#upcurrentBox2').empty();
                    $('#upnewBox2').empty();
                    if (selectedModule == '0') {
                        $('#uppages').empty();
                        $('#upcurrentBox2').empty();
                        $('#upnewBox2').empty();
                        $('#uppages').append($('<option></option>').val("0").html("---Select---"));
                    } else {
                        $.ajax({
                            url: '${pageContext.request.contextPath}/loadPagesuserPrivlage.action',
                            data: {modules: selectedModule},
                            dataType: "json",
                            type: "POST",
                            success: function (data) {
                                $('#uppages').empty();
                                $('#upcurrentBox2').empty();
                                $('#uppages').append($('<option></option>').val("0").html("---Select---"));
                                $.each(data.allPageMap, function (val, text) {
                                    $('#uppages').append($('<option></option>').val(val).html(text));
                                });
                            }

                        });
                    }
                }

            }
            function loadPageTask(sectionId, flag) {
                if (flag === "Add") {
                    $('#newBox2 option').prop('selected', true);
                    $('#currentBox2 option').prop('selected', true);
                    $.ajax({
                        url: '${pageContext.request.contextPath}/loadTasksuserPrivlage.action',
                        data: {pages: sectionId, flag: 'add'},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {

                            $('#currentBox2').empty();
                            $('#newBox2').empty();

                            $.each(data.unSelectedTaskMap, function (val, text) {
                                $('#currentBox2').append($('<option></option>').val(val).html(text));
                            });
                        }

                    });

                } else {


                    $('#upnewBox2 option').prop('selected', true);
                    $('#upcurrentBox2 option').prop('selected', true);
                    var profile = $("#upProfileID").val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/loadTasksuserPrivlage.action',
                        data: {pages: sectionId, flag: 'update', upProfileID: profile},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            $('#upcurrentBox2').empty();
                            $('#upnewBox2').empty();
                            $.each(data.unSelectedTaskMap, function (val, text) {
                                var validate = 1;
                                $.each(data.selectedTaskMap, function (val1, text1) {
                                    if (val === val1) {
                                        validate = 0;
                                        return false;
                                    } else {
                                        validate = 1;
                                    }
                                });
                                if (validate === 0) {
                                    $('#upnewBox2').append($('<option></option>').val(val).html(text));
                                } else {
                                    $('#upcurrentBox2').append($('<option></option>').val(val).html(text));
                                }
                            });
                        }

                    });

                }


            }

            function clearAddForm() {
                utilityManager.resetMessage();
                $("#addForm input[type=text]").val("");
                $("#modules").val("0");
                $('#currentBox2').empty();
                $('#newBox2').empty();
                $("#pages").val("0");
            }
            function addModulePage() {
                var name = $("#profilename").val();
                var module = $("#modules").val();
                var section = $("#pages").val();
                var task = "";
                var index = 0;
                $("#newBox2 option").each(function () {
//                    alert($(this).val())
                    if (index == 0) {
                        task = $(this).val();
                    } else {
                        task += "|" + $(this).val()
                    }
                    index++;
                })
                $.ajax({
                    url: '${pageContext.request.contextPath}/AdduserPrivlage.action',
                    data: {profilename: name, taskString: task, modules: module, pages: section},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success === true) {
                            utilityManager.showMessage('.add-form-msg', data.message, 'successmsg');
                        } else {
                            utilityManager.showMessage('.add-form-msg', data.message, 'errormsg');
                        }
//                        clearAddForm();
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logOut.action";
                    }
                });

            }
            function editModulePage() {
                var id = $("#upProfileID").val();
                var name = $('#upName').val();
                var status = $('#upStatus').val();
                var module = $('#upmoduleList').val();
                var section = $('#uppages').val();

                var task = "";
                var index = 0;
                $("#upnewBox2 option").each(function () {
//                    alert($(this).val())
                    if (index == 0) {
                        task = $(this).val();
                    } else {
                        task += "|" + $(this).val()
                    }
                    index++;
                })
                $.ajax({
                    url: '${pageContext.request.contextPath}/UpdateuserPrivlage.action',
                    data: {upName: name, upStatus: status, upProfileID: id, taskString: task, modules: module, pages: section},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success === true) {
                            utilityManager.showMessage('.edit-form-msg', data.message, 'successmsg');
                        } else {
                            utilityManager.showMessage('.edit-form-msg', data.message, 'errormsg');
                        }
                        resetForm();
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/logOut.action";
                    }
                });

            }

            //send data to table
            $.subscribe('onclicksearch', function (event, data) {
                utilityManager.resetMessage();
                var searchName = $('#searchName').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {searchName: searchName}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });
            function hideInit() {
//                $("#addDiv").hide();
//                $("#Updateform").hide();
            }
            $(document).ready(function () {
                hideInit();
                $("#Addbtn").click(function () {
                    alert("test")
//                    $("#webusersearchForm").hide();
                    $("#Updateform").hide();
                    $("#addDiv").show();
                    var symb = '<i class="fa fa-angle-double-right" aria-hidden="true"></i>';
                    var text = ' Add User Profile';
                    $('#task').append(symb, text);
                })

            })
        </script>
    </head>

    <s:set id="vadd" var="vadd"><s:property  value="add" default="true"/></s:set>
    <s:set id="vupdate" var="vupdate"><s:property value="update" default="true"/></s:set>
    <s:set id="vdelete" var="vdelete"><s:property value="delete" default="true"/></s:set>

        <body>
            <section class="app-content">
            <jsp:include page="../../header.jsp" /> 

            <div class="content innerpage">
                
                <!-- Page title begin -->
                <h1 class="page-title"><s:property value="Section"/><a href="#" class="lnk-back hide-element do-nothing"><i class="fa fa-arrow-left" aria-hidden="true"></i> back</a></h1>
                <!-- End -->
                <div class="content-section search-form" id="webusersearchForm">
                    <s:form theme="simple" id="userProfileSearchForm">
                        <div class="content-data">
                            <h2 class="section-title">Search</h2>
                            <div class="d-row singlecol-row">
                                <label class="left-col form-label">Name</label>
                                <div class="right-col form-field">
                                    <s:textfield name="searchName" id="searchName" cssClass="txt-input width-35" />

                                    <sj:a 
                                        id="searchbut" 
                                        button="true" 
                                        onClickTopics="onclicksearch"  cssClass="btn default-button"                                    
                                        > <i class="fa fa-search" aria-hidden="true"></i> Search</sj:a>
                                    <sj:a 
                                        id="btnAdd"
                                        button="true"
                                        disabled="#vadd"
                                        cssClass="btn default-button"                                      
                                        > <i class="fa fa-plus" aria-hidden="true"></i> Add</sj:a>
                                    </div>
                                </div>
                            </div>
                    </s:form>
                </div>

                <div class="content-section data-form hide-element" id="addDiv"  style="display: none">
                    <s:form id="addForm" theme="simple">
                        <div class="content-data">
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label class="col-1">&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Name<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield name="profilename" id="profilename" cssClass="txt-input width-35" />
                                </div>
                                <label  class="col-3 form-label" >&nbsp;</label>
                                <div class="col-4 form-field">&nbsp;</div>                                
                            </div>
                            <!-- End -->

                            <!-- Four colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Modules<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:select id="modules" name="modules" headerKey="0" headerValue="---Select---"  
                                              list="%{modulesMap}" 
                                              onchange="loadModulePage(this.value,'Add')" cssClass="ddl-input"/>
                                </div>
                                <label class="col-3 form-label">Section<sup class="required">*</sup></label>
                                <div class="col-4 form-field">
                                    <s:select id="pages" name="pages" headerKey="0" headerValue="---Select---"  
                                              list="%{allPageMap}" 
                                              onchange="loadPageTask(this.value,'Add')" cssClass="ddl-input"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Task<sup class="required">*</sup></label>
                                <div class="right-col form-field move-items">
                                    <s:select multiple="true" name="currentBox2" id="currentBox2" list="pagesMap" ondblclick="toright2()"  cssClass="ddl-input width-15" size="6" />
                                    <div class="inline-fields">
                                        <button type="button" id="left2" onClick="toleft2()" class="btn default-button"><i class="fa fa-angle-left" aria-hidden="true"></i></button>
                                        <button type="button" id="leftall2" onClick="toleftall2()" class="btn default-button"><i class="fa fa-angle-double-left" aria-hidden="true"></i></button>
                                        <button type="button" id="right2" onClick="toright2()" class="btn default-button"><i class="fa fa-angle-right" aria-hidden="true"></i></button>
                                        <button type="button" id="rightall2" onClick="torightall2()" class="btn default-button lnk"><i class="fa fa-angle-double-right" aria-hidden="true"></i></button>
                                    </div>
                                    <s:select multiple="true"  name="newBox2" id="newBox2" list="newList2" ondblclick="toleft2()" cssClass="ddl-input width-15" size="6" />
                                </div>
                            </div>
                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col">                                    
                                    <sj:a button="true" onfocus="true" onclick="addModulePage()" disabled="#vadd" cssClass="btn default-button" ><i class="fa fa-floppy-o" aria-hidden="true"></i> Save</sj:a>
                                    <sj:a button="true" onClick="clearAddForm()" cssClass="btn reset-button"><i class="fa fa-times" aria-hidden="true"></i> Reset</sj:a>
                                    </div>
                                </div>
                        </s:form>    
                        <!-- End -->


                    </div>
                </div>

                <s:form  theme="simple"  method="post" id="Updateform" cssClass="content-section data-form  hide-element">
                    <s:hidden name="upProfileID" id="upProfileID" value="1"/>
                    <s:hidden name="name" id="name" value="1"/>
                    <div class="content-data">
                        <!-- Error and success message panel begin -->
                        <div class="msg-panel edit-form-msg" >
                            <label class="col-1">&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>

                        <div class="d-row">
                            <label class="col-1 form-label">Name<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:textfield name="upName" id="upName" cssClass="txt-input width-35" />
                            </div>
                                                         
                        </div>
                        <!-- End -->

                        <!-- Four colum form row begin -->
                        <div class="d-row">
                            <label class="col-1 form-label">Modules<sup class="required">*</sup></label>
                            <div class="col-2 form-field">
                                <s:select id="upmoduleList" name="upmoduleList" headerKey="0" headerValue="---Select---"  
                                          list="%{modulesMap}" 
                                          onchange="loadModulePage(this.value,'update')" cssClass="ddl-input"/>
                            </div>
                            <label class="col-3 form-label">Section<sup class="required">*</sup></label>
                            <div class="col-4 form-field">
                                <s:select id="uppages" name="uppage" headerKey="0" headerValue="---Select---"  
                                          list="%{allPageMap}" 
                                          onchange="loadPageTask(this.value,'update')" cssClass="ddl-input"/>
                            </div>
                        </div>
                        <div class="d-row">
                            <label class="col-1 form-label">Task<sup class="required">*</sup></label>
                            <div class="right-col form-field move-items">
                                <s:select multiple="true" name="upcurrentBox2" list="%{allPageMap}"  id="upcurrentBox2" ondblclick="toright2()"  cssClass="ddl-input width-15" size="6" />
                                <div class="inline-fields">
                                    <button type="button" id="left" onClick="toleft()" class="btn default-button"><i class="fa fa-angle-left" aria-hidden="true"></i></button>
                                    <button type="button" id="leftall" onClick="toleftall()" class="btn default-button"><i class="fa fa-angle-double-left" aria-hidden="true"></i></button>
                                    <button type="button" id="right" onClick="toright()"  class="btn default-button"><i class="fa fa-angle-right" aria-hidden="true"></i></button>
                                    <button type="button" id="rightall" onClick="torightall()" class="btn default-button lnk"><i class="fa fa-angle-double-right" aria-hidden="true"></i></button>
                                </div>
                                <s:select multiple="true"  name="upnewBox2" id="upnewBox2" list="%{alreadyAcsessPageMap}" ondblclick="toleft2()" cssClass="ddl-input width-15" size="6" />
                            </div>
                        </div>
                        <div class="d-row cpanel four-col">
                            <label class="col-1">&nbsp;</label>
                            <div class="right-col">                                    
                                <sj:a button="true" onfocus="true" onclick="editModulePage()" disabled="#vupdate" cssClass="btn default-button" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> update</sj:a>
                                <sj:a button="true" onclick="resetUpdateForm()" cssClass="btn reset-button"><i class="fa fa-times" aria-hidden="true"></i> Reset</sj:a>
                                </div>
                            </div>

                        </div>      
                </s:form>




                <!-- Grid data begin -->
                <div class="content-section">
                    <div class="content-data">
                        <h2 class="section-title">All Registered User Profiles</h2>
                        <div class="msg-panel del-user-msg" >
                            <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>
                    </div>

                    <div id="tablediv" class="custom-grid">
                        <sj:dialog 
                            id="deletedialog" 
                            buttons="{ 
                            'OK':function() { deleteUserProfile($(this).data('keyval'));$( this ).dialog( 'close' ); },
                            'Cancel':function() { $( this ).dialog( 'close' );} 
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Delete User Profile."
                            width="400"
                            height="200"
                            position="center"
                            />

                        <sj:dialog 
                            id="deletesuccdialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}
                            }"  
                            autoOpen="false" 
                            modal="true" 
                            title="User Profile Deleted Successfully." 
                            width="400"
                            height="150"
                            position="center"
                            />
                        <!-- End delete successfully dialog box -->
                        <!-- Start delete error dialog box -->
                        <sj:dialog 
                            id="deleteerrordialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="User Profile Delete Error. "
                            width="400"
                            height="150"
                            position="center"
                            />
                        <s:url var="listurl" action="ListuserPrivlage"/>
                        <sjg:grid
                            id="gridtable"
                            caption="All Registered User Profiles"
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
                            <sjg:gridColumn name="profileID" index="PROFILE_ID" title="Profile ID" align="center" hidden="true"/>                    
                            <sjg:gridColumn name="name" index="description" title="Name"  align="left" sortable="true"/>
                            <sjg:gridColumn name="profileID" index="profileID" title="Action" align="center" align="center" cssClass="action-col" formatter="editformatter" sortable="false" />

                        </sjg:grid>
                    </div>   

                </div>
                <!-- End -->

            </div>
            <!--End of Body Content-->

            <jsp:include page="../../footer.jsp" />
        </section>
        <script type="text/javascript">
            $(document).ready(function () {

                //Show form validations button event
                $('#btnSubmit').on('click', function () {
                    utilityManager.showMessage('.add-form-msg', 'User name is required', 'errormsg');
                    return false;
                });

                //Add data button event
                $('#btnAdd').on('click', function () {
                    $("#addDiv").show();
                    utilityManager.resetMessage();
                    $('.search-form').removeClass('hide-element').removeClass('show-element').addClass('hide-element');
                    $('.data-form').eq(0).removeClass('hide-element');
                    $('.lnk-back').removeClass('hide-element');
                    return false;
                });

                //Back button event
                $('.lnk-back').on('click', function () {
                    $('.search-form').removeClass('hide-element');
                    $('.data-form').eq(0).addClass('hide-element');
                    $('#Updateform').addClass('hide-element');
                    $('.lnk-back').addClass('hide-element');
                    utilityManager.resetMessage();
                    $('#task').empty();
                    jQuery("#gridtable").trigger("reloadGrid");
                    return false;
                });

                $(document).ready(function () {

                    setTimeout(function () {
                        $(window).trigger('resize');
                    }, 500);

                });

            });


        </script>

    </body>
</html>
