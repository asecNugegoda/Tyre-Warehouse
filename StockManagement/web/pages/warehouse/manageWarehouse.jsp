<%-- 
    Document   : manageWarehouse
    Created on : Nov 8, 2018, 9:18:24 PM
    Author     : thilinath
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
    <head>

        <jsp:include page="../../Styles.jsp" />

        <script>

            function RackAssignformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:viewRackProfile(&#34;" + rowObject.locationId + "&#34;,&#34;" + rowObject.location + "&#34;)'><i class='fa fa-share-square-o' aria-hidden='true'></i></a>";
            }

            function Statusformatter(cellvalue) {
                if (cellvalue == 1) {
                    return "<i class='fa fa-circle active' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle' aria-hidden='true'></i>";
                }
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' disabled='#vupdate' onClick='javascript:editWarehouse(&#34;" + rowObject.locationId + "&#34;)'><i class='fa fa-pencil' aria-hidden='true' title='Edit Warehouse'></i></a> <a href='#' disabled='#vdelete' onClick='javascript:deleteWarehouse(&#34;" + rowObject.locationId + "&#34;,&#34;" + rowObject.location + "&#34;)'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Block BIN'></i></a>";
            }

            function editWarehouse(keyval) {
                $('.lnk-back').removeClass('hide-element');
                utilityManager.resetMessage();
                $('#task').empty();
                var text = 'Edit Warehouse';
                $('#task').append(text);
                $.ajax({
                    url: '${pageContext.request.contextPath}/findmngWarehouse',
                    data: {upLocationId: keyval, },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        utilityManager.resetMessage();
                        $('#addForm').hide();
                        $('#blockBSearchForm').hide();
                        $("#Updateform").show();
                        $('#upLocationId').val(data.upLocationId);
                        $('#upLocation').val(data.upLocation);
                        jQuery("#gridtable").trigger("reloadGrid");
                    }
                });
            }

            function facilityPopupClose() {
                $("#viewdialog").dialog('close');
            }

            function resetUpdateForm() {
                var upProfileID = $('#upLocationId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findmngWarehouse',
                    data: {upLocationId: upProfileID},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        jQuery("#gridtable").trigger("reloadGrid");
                        utilityManager.resetMessage();
                        $('#userProfileSearchForm').hide();
                        $('#userProfileEditForm').show();
                        $('#upLocationId').val(data.upLocationId);
                        $('#upLocation').val(data.upLocation);

                    }
                });

            }

            function confrmDeleteBinProfile(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deletemngWarehouse',
                    data: {locationId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.isDeleted === true) {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'successmsg');
                        } else {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'errormsg');
                        }
                        resetForm();
                    }
                });

            }


            function BinProfilePopupClose() {
                $("#viewdialog").dialog('close');
            }
            function viewRackProfile(id, bin) {
                $("#viewdialog").data('Id', id);
                $("#viewdialog").data('wareName', bin).dialog('open');
            }
            $.subscribe('openview', function (event, data) {
                utilityManager.resetMessage();
//                resetData();
                var $led = $("#viewdialog");
//                alert($led.data('Id').replace(/ /g,"_"));
                $led.load("AssignmngWarehouse?Id=" + $led.data('Id') + "&cName=" + $led.data('wareName').replace(/ /g, "_"));
            });


            $.subscribe('onclicksearch', function (event, data) {
                var searchName = $('#searchString').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {searchString: searchName}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });

            function deleteWarehouse(id, uname) {
                utilityManager.resetMessage();
                $("#deleteConfirmDialog").data('uname', uname);
                $("#deleteConfirmDialog").data('id', id).dialog('open');
                $("#deleteConfirmDialog").html('<p>Please confirm delete : ' + uname + "</p>");
                return false;
            }


            function backToMain() {
                utilityManager.resetMessage();
                $('#Updateform').hide();
                $('#addForm').hide();
                $('#blockBSearchForm').show();
                $('#task').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function hideInit() {
                $("#addForm").hide();
                $("#Updateform").hide();
            }
            $(document).ready(function () {
                hideInit();
                $("#Addbtn").click(function () {
                    $('.lnk-back').removeClass('hide-element');
                    utilityManager.resetMessage();
                    $("#blockBSearchForm").hide();
                    $("#Updateform").hide();
                    $("#addForm").show();
                    $('#task').empty();
                    var text = ' Add BIN Profile';
                    $('#task').append(text);
                })

            })

            function resetForm() {
                $('#binName').val("");
                $('#BinProfileDes').val("");
                jQuery("#gridtable").trigger("reloadGrid");
                jQuery("#gridtable1").trigger("reloadGrid");
            }
            function resetAddForm() {
                $('#location').val("");
                jQuery("#gridtable").trigger("reloadGrid");
                utilityManager.resetMessage();
            }

        </script>

    </head>

    <body style="overflow:hidden">
        <s:set id="vadd" var="vadd"><s:property  value="add" default="true"/></s:set>
        <s:set id="vupdate" var="vupdate"><s:property value="update" default="true"/></s:set>
        <s:set id="vdelete" var="vdelete"><s:property value="delete" default="true"/></s:set>
        <s:set id="vview" var="vview"><s:property value="view" default="true"/></s:set>

            <section class="app-content">
            <jsp:include page="../../header.jsp" />             

            <div class="content innerpage">
               
                <!-- Page title begin -->
                <h1 class="page-title"><s:property value="Section"/><a href="#" class="lnk-back hide-element do-nothing"><i class="fa fa-arrow-left" aria-hidden="true"></i> back</a></h1>
                <!-- End -->

                <!-- Search form begin -->
                <div class="content-section search-form" id="blockBSearchForm">
                    <s:form id="SearchForm" theme="simple">
                        <div class="content-data">
                            <h2 class="section-title">Search</h2>
                            <div class="d-row singlecol-row">
                                <label class="left-col form-label">Warehouse</label>
                                <div class="right-col form-field">
                                    <s:textfield name="searchString" id="searchString" cssClass="txt-input width-35"/>
                                    <sj:a 
                                        id="searchbut" 
                                        button="true" 
                                        onClickTopics="onclicksearch" 
                                        cssClass="btn default-button" 
                                        ><i class="fa fa-search" aria-hidden="true"></i> Search</sj:a>
                                    <sj:a 
                                        disabled="#vadd"
                                        id="Addbtn" 
                                        button="true" 
                                        cssClass="btn default-button" 
                                        ><i class="fa fa-plus" aria-hidden="true"></i> Add</sj:a>

                                    </div>
                                </div>
                            </div>
                    </s:form>
                </div>
                <!-- End -->

                <!-- Data form begin -->
                <div class="content-section data-form" id="addForm">
                    <s:form id="addFillForm" theme="simple">
                        <div class="content-data">
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="left-col form-label">Warehouse<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="location" id="location" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="saveurl" action="addmngWarehouse"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-floppy-o" aria-hidden="true"></i><sj:submit  href="%{saveurl}" targets="divmsg" value="Save" disabled="#vadd" button="true" cssClass="btn default-button" /></div>  

                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset"  cssClass="btn reset-button" onclick="resetAddForm()"  /></div>
                                </div>
                            </div>

                        </div>
                    </s:form>
                </div>

                <div class="content-section data-form"  id="Updateform" style="display: none;">
                    <s:form  theme="simple"  method="post" id="Updateformdiv" >
                        <div class="content-data">
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <s:hidden name="upProfileID" id="upProfileID" value="1"/>
                                <label class="left-col form-label">Warehouse<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:hidden name="upLocationId" id="upLocationId" value="1"/>
                                    <s:textfield name="upLocation" id="upLocation" cssClass="txt-input width-35"/>
                                </div>
                            </div>

                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="updateuserurl" action="updatemngWarehouse"/>                                   
                                    <div class="btn-wrap"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><sj:submit  href="%{updateuserurl}" targets="divmsg" disabled="#vupdate" value="Update" button="true" cssClass="btn default-button"/></div>
                                    <div class="btn-wrap"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset"  cssClass="btn reset-button" onclick="resetUpdateForm()"  /></div>
                                </div>
                            </div>

                        </div>
                    </s:form>
                </div>

                <!-- Grid data begin -->
                <div class="content-section">
                    <div class="content-data">
                        <h2 class="section-title">ALL WAREHOUSE</h2>
                        <!-- Error and success message panel begin -->
                        <div class="msg-panel del-user-msg" >
                            <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>
                        <!-- End -->
                    </div>


                    <div id="tablediv" class="custom-grid">

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
                            title="Racks Management"
                            onOpenTopics="openview" 
                            loadingText="Loading .."
                            />

                        <sj:dialog 
                            id="deleteConfirmDialog" 
                            buttons="{ 
                            'OK':function() { confrmDeleteBinProfile($(this).data('id'));$( this ).dialog( 'close' ); },
                            'Cancel':function() { $( this ).dialog( 'close' );} 
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Delete Block Bin Profile."
                            width="400"
                            height="200"
                            position="center"
                            />

                        <!-- End delete successfully dialog box -->
                        <!-- Start delete error dialog box -->

                        <s:url var="listurl" action="listmngWarehouse" />

                        <!--caption="Edit and View User Details"-->
                        <sjg:grid
                            id="gridtable"                                
                            caption="All Warehouse"
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
                            <sjg:gridColumn name="locationId" index="locationId" title="locationId"   hidden="true" />
                            <sjg:gridColumn name="location" index="location" title="Warehouse Name" align="left" width="15" sortable="true"  /> 
                            <sjg:gridColumn name="locationId" index="locationId" title="Add Racks"  width="10" align="center" formatter="RackAssignformatter" sortable="false"/>
                            <sjg:gridColumn name="locationId"  title="Action"  width="10" align="center" formatter="editformatter" sortable="false" cssClass="action-col"/>
                            <sjg:gridColumn name="locationId"  title="Delete"  width="10" align="center" hidden="true" sortable="false"/>
                        </sjg:grid> 
                    </div> 


                </div>
                <!-- End -->

            </div>
            <!--End of Body Content-->

            <jsp:include page="../../footer.jsp" />
        </div><!--End of Wrapper-->
    </section>

    <script type="text/javascript">
        $(document).ready(function () {
            //Back button event
            $('.lnk-back').on('click', function () {
                backToMain();
                $('#task').empty();
                $('.lnk-back').addClass('hide-element');
                var text = 'Search Block BIN Profile';
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

</body>
</html>
