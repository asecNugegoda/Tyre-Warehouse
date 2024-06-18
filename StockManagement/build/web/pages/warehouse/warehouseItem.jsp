<%-- 
    Document   : warehouseItem
    Created on : Nov 6, 2018, 8:01:29 PM
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

            function viewCategory() {
                $("#AddCategoryDialog").dialog('open');
            }
            $.subscribe('openview', function () {
                utilityManager.resetMessage();
                var $led = $("#AddCategoryDialog");
                $led.load("AddCategoryItemStore");
            });

            function viewBrand() {
                $("#AddBrandDialog").dialog('open');
            }
            $.subscribe('openview', function () {
                utilityManager.resetMessage();
                var $led = $("#AddBrandDialog");
                $led.load("AddBrandItemStore");
            });

            function viewBrandPopupClose() {
                $("#AddBrandDialog").dialog('close');
            }
            function viewCategoryPopupClose() {
                $("#AddCategoryDialog").dialog('close');
            }

            function Statusformatter(cellvalue) {
                if (cellvalue == 1) {
                    return "<i class='fa fa-circle active' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle' aria-hidden='true'></i>";
                }
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' disabled='#vupdate' onClick='javascript:editBinProfile(&#34;" + rowObject.whItemId + "&#34;)'><i class='fa fa-pencil' aria-hidden='true' title='Edit Block BIN'></i></a> <a href='#' disabled='#vdelete' onClick='javascript:deleteBinProfile(&#34;" + rowObject.whItmeName + "&#34;,&#34;" + rowObject.whItemId + "&#34;)'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Block BIN'></i></a>";
            }

            function editBinProfile(keyval) {
                $('.lnk-back').removeClass('hide-element');
                utilityManager.resetMessage();
                $('#task').empty();
                var text = ' Update Item Details';
                $('#task').append(text);
                $.ajax({
                    url: '${pageContext.request.contextPath}/findItemStore',
                    data: {whItemId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        utilityManager.resetMessage();
                        $('#addForm').hide();
                        $('#SearchForm').hide();
                        $("#Updateform").show();
                        $('#upWhItemId').val(data.upWhItemId);
                        $('#upBrand').val(data.upBrand);
                        $('#upCategory').val(data.upCategory);
                        $('#upStatus').val(data.upStatus);
                        $('#upWhItmeName').val(data.upWhItmeName);
                        jQuery("#gridtable").trigger("reloadGrid");
                    }
                });
            }

            function resetUpdateForm() {
                var upID = $('#upWhItemId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findItemStore',
                    data: {whItemId: upID},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        utilityManager.resetMessage();
                        $('#addForm').hide();
                        $('#SearchForm').hide();
                        $("#Updateform").show();
                        $('#upWhItemId').val(data.upWhItemId);
                        $('#upBrand').val(data.upBrand);
                        $('#upCategory').val(data.upCategory);
                        $('#upStatus').val(data.upStatus);
                        $('#upWhItmeName').val(data.upWhItmeName);
                        jQuery("#gridtable").trigger("reloadGrid");
                    }
                });

            }

            function confrmDeleteBinProfile(keyval) {
                alert(keyval);
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteItemStore',
                    data: {whItemId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.isDeleted === true) {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'successmsg');
                        } else {
                            utilityManager.showMessage('.del-user-msg', data.dmessage, 'errormsg');
                        }
                        resetAddForm();
                    }
                });

            }

            $.subscribe('onclicksearch', function (event, data) {
                var searchName = $('#searchAgent').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {whItmeName: searchName}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });

            function deleteBinProfile(uname, id) {
                utilityManager.resetMessage();
                $("#deleteConfirmDialog").data('uname', uname);
                $("#deleteConfirmDialog").data('id', uname).dialog('open');
                $("#deleteConfirmDialog").html('<p>Please confirm delete : ' + uname + "</p>");
                return false;
            }


            function backToMain() {
                utilityManager.resetMessage();
                $('#Updateform').hide();
                $('#addForm').hide();
                $('#SearchForm').show();
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
                    $("#SearchForm").hide();
                    $("#Updateform").hide();
                    $("#addForm").show();
                    $('#task').empty();
                    var text = ' Add BIN Profile';
                    $('#task').append(text);
                })

            })

            function resetAddForm() {
                $('#brand').val("-1");
                $('#category').val("-1");
                $('#status').val("-1");
                $('#whItmeName').val("");
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
                <div class="content-section search-form" id="SearchForm">
                    <s:form id="SearchForm" theme="simple">
                        <div class="content-data">
                            <h2 class="section-title">Search</h2>
                            <div class="d-row singlecol-row">
                                <label class="left-col form-label">Item Name</label>
                                <div class="right-col form-field">
                                    <s:textfield name="searchAgent" id="searchAgent" cssClass="txt-input width-35"/>
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
                <s:form id="addFillForm" theme="simple">
                    <div class="content-section data-form" id="addForm">

                        <div class="content-data">
                            <h2 class="section-title">Fill Item Details</h2>
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="left-col form-label">Brand Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:select  name="brand" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{brandList}" id="brand" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Category<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:select  name="category" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{categoryList}" id="category" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Item Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="whItmeName" id="whItmeName" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Status<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:select  name="status" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{statusList}" id="status" cssClass="ddl-input width-35"/>
                                </div>
                            </div>

                            <div class="d-row">
                                <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="saveurl" action="addItemStore"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-floppy-o" aria-hidden="true"></i><sj:submit  href="%{saveurl}" targets="divmsg" value="Save" disabled="#vadd" button="true" cssClass="btn default-button" /></div>  

                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset"  cssClass="btn reset-button" onclick="resetAddForm()"  /></div>

                                    <sj:a id="categoryBtn" button="true" disabled="#vadd" onclick="viewCategory()"
                                          cssClass="btn default-button" role="button" aria-disabled="false" >
                                        <i class="fa fa-tasks" aria-hidden="true"></i>
                                        Category</sj:a>
                                    <sj:a id="brandBtn" button="true" disabled="#vadd" onclick="viewBrand()()"
                                          cssClass="btn default-button" role="button" aria-disabled="false" >
                                        <i class="fa fa-leaf" aria-hidden="true"></i>
                                        Brand</sj:a>

                                    </div>
                                </div>

                            </div>

                        </div>
                </s:form>

                <s:form  theme="simple"  method="post" id="Updateformdiv" >
                    <div class="content-section data-form"  id="Updateform" style="display: none;">
                        <div class="content-data">
                            <h2 class="section-title">Update Agent Details</h2>
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="left-col form-label">Brand Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:hidden name="upWhItemId" id="upWhItemId" value="0"/>
                                    <s:select  name="upBrand" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{brandList}" id="upBrand" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Category<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:select  name="upCategory" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{categoryList}" id="upCategory" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Item Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="upWhItmeName" id="upWhItmeName" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Status<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:select  name="upStatus" headerKey="" headerValue="---Select---"
                                               listKey="key" listValue="value"
                                               list="%{statusList}" id="upStatus" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="updateuserurl" action="updateItemStore"/>                                   
                                    <div class="btn-wrap"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><sj:submit  href="%{updateuserurl}" targets="divmsg" disabled="#vupdate" value="Update" button="true" cssClass="btn default-button"/></div>
                                    <div class="btn-wrap"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset"  cssClass="btn reset-button" onclick="resetUpdateForm()"  /></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </s:form>

                <!-- Grid data begin -->
                <div class="content-section">
                    <div class="content-data">
                        <h2 class="section-title">All Agent List</h2>
                        <!-- Error and success message panel begin -->
                        <div class="msg-panel del-user-msg" >
                            <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                        </div>
                        <!-- End -->
                    </div>


                    <div id="tablediv" class="custom-grid">

                        <sj:dialog 
                            id="AddBrandDialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="1000"
                            height="500"
                            position="center"
                            title="Brands"
                            onOpenTopics="openview" 
                            loadingText="Loading .."
                            />

                        <sj:dialog 
                            id="AddCategoryDialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="1000"
                            height="500"
                            position="center"
                            title="Categories"
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

                        <s:url var="listurl" action="listItemStore" />

                        <!--caption="Edit and View User Details"-->
                        <sjg:grid
                            id="gridtable"                                
                            caption="All Agent List"
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
                            <sjg:gridColumn name="whItemId" index="whItemId" title="whItemId"   hidden="true" />
                            <sjg:gridColumn name="brand" index="brand" title="Brand" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="category" index="category" title="Category" align="left" sortable="true"/>
                            <sjg:gridColumn name="whItmeName" index="whItmeName" title="Item Name" align="left" sortable="false"/>
                            <sjg:gridColumn name="status" index="status" title="Status" formatter="Statusformatter" align="center" sortable="false"/>
                            <sjg:gridColumn name="itemId"  title="Action"  width="50" align="center" formatter="editformatter" sortable="false" cssClass="action-col"/>
                            <sjg:gridColumn name="itemId"  title="Delete"  width="10" align="center" hidden="true" sortable="false"/>
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
