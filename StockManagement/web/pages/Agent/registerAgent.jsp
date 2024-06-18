<%-- 
    Document   : registerAgent
    Created on : Nov 5, 2018, 7:39:28 PM
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' disabled='#vupdate' onClick='javascript:editBinProfile(&#34;" + rowObject.agentId + "&#34;)'><i class='fa fa-pencil' aria-hidden='true' title='Edit Block BIN'></i></a> <a href='#' disabled='#vdelete' onClick='javascript:deleteBinProfile(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.binProfileDes + "&#34;)'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Block BIN'></i></a>";
            }

            function editBinProfile(keyval) {
                $('.lnk-back').removeClass('hide-element');
                utilityManager.resetMessage();
                $('#task').empty();
                var text = ' Update Agent Details';
                $('#task').append(text);
                $.ajax({
                    url: '${pageContext.request.contextPath}/findregAgent',
                    data: {agentId: keyval, },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        utilityManager.resetMessage();
                        $('#addForm').hide();
                        $('#SearchForm').hide();
                        $("#Updateform").show();
                        $('#upAgentId').val(data.upAgentId);
                        $('#upAgentName').val(data.upAgentName);
                        $('#upAdr1').val(data.upAdr1);
                        $('#upAdr2').val(data.upAdr2);
                        $('#upCity').val(data.upCity);
                        $('#upContact').val(data.upContact);
                        jQuery("#gridtable").trigger("reloadGrid");
                    }
                });
            }

            function resetUpdateForm() {
                var upID = $('#upAgentId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findregAgent',
                    data: {agentId: upID},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        jQuery("#gridtable").trigger("reloadGrid");
                        utilityManager.resetMessage();
                        $('#userProfileSearchForm').hide();
                        $('#userProfileEditForm').show();
                        $('#upAgentId').val(data.upAgentId);
                        $('#upAgentName').val(data.upAgentName);
                        $('#upAdr1').val(data.upAdr1);
                        $('#upAdr2').val(data.upAdr2);
                        $('#upCity').val(data.upCity);
                        $('#upContact').val(data.upContact);
                    }
                });

            }

            function confrmDeleteBinProfile(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteregAgent',
                    data: {DbinId: keyval},
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

                $("#gridtable").jqGrid('setGridParam', {postData: {agentName: searchName}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });

            function deleteBinProfile(uname, id) {
                utilityManager.resetMessage();
                $("#deleteConfirmDialog").data('uname', uname);
                $("#deleteConfirmDialog").data('id', uname).dialog('open');
                $("#deleteConfirmDialog").html('<p>Please confirm delete : ' + id + "</p>");
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
                $('#agentName').val("");
                $('#adr1').val("");
                $('#adr2').val("");
                $('#city').val("");
                $('#contact').val("");
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
                                <label class="left-col form-label">Agent</label>
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
                <div class="content-section data-form" id="addForm">
                    <s:form id="addFillForm" theme="simple">
                        <div class="content-data">
                            <h2 class="section-title">Fill Agent Details</h2>
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="left-col form-label">Agent Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="agentName" id="agentName" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Address Line 1<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="adr1" id="adr1" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Address Line 2<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="adr2" id="adr2" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">City<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="city" id="city" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Contact<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="contact" id="contact" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="saveurl" action="addregAgent"/>                                   
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
                            <h2 class="section-title">Update Agent Details</h2>
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="left-col form-label">Agent Name<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:hidden name="upAgentId" id="upAgentId" cssClass="txt-input width-35"/>
                                    <s:textfield name="upAgentName" id="upAgentName" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Address Line 1<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="upAdr1" id="upAdr1" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Address Line 2<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="upAdr2" id="upAdr2" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">City<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="upCity" id="upCity" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">Contact<sup class="required">*</sup></label>
                                <div class="right-col form-field">
                                    <s:textfield name="upContact" id="upContact" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="left-col form-label">(Mandatory fields are marked with<sup class="required">*</sup>)</label>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel">
                                <label class="left-col">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="updateuserurl" action="updateregAgent"/>                                   
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
                        <h2 class="section-title">All Agent List</h2>
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
                            title="Block BIN"
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
                        
                        <s:url var="listurl" action="listregAgent" />

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
                            <sjg:gridColumn name="agentId" index="agentId" title="agentId"   hidden="true" />
                            <sjg:gridColumn name="agentName" index="agentName" title="Agent Name" align="left" sortable="true"  /> 
                            <sjg:gridColumn name="adr1" index="adr1" title="Address Line 1" align="left" sortable="true"/>
                            <sjg:gridColumn name="adr2" index="adr2" title="Address Line 2" align="left" sortable="false"/>
                            <sjg:gridColumn name="city" index="city" title="City" align="left" sortable="false"/>
                            <sjg:gridColumn name="contact" index="contact" title="Contact" align="left" sortable="false"/>
                            <sjg:gridColumn name="id"  title="Action"  width="50" align="center" formatter="editformatter" sortable="false" cssClass="action-col"/>
                            <sjg:gridColumn name="agentId"  title="Delete"  width="10" align="center" hidden="true" sortable="false"/>
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

