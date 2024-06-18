<%-- 
    Document   : addBrandView
    Created on : Nov 6, 2018, 9:49:38 PM
    Author     : thilinath
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%> 

<!DOCTYPE html>
<html>
    <script type="text/javascript">

        function resetData1() {
            $('#category').val("");
            $('#cdivmsg').empty();
            utilityManager.resetMessage();
            jQuery("#gridtable2").trigger("reloadGrid");
        }

        function fdeleteformatter(cellvalue, options, rowObject) {
            return "<a href='#' onClick='fdeleteInit(&#34;" + rowObject.categoryId + "&#34;,&#34;" + rowObject.category + "&#34;)' title='Delete BIN'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Block BIN'></i></a>";
        }

        function fdeleteInit(id, name) {
            $("#confirmdialogboxcat").data('keyval1', id).dialog('open');
            $("#confirmdialogboxcat").data('keyname', name).dialog('open');
            $("#confirmdialogboxcat").html('<p>Please confirm to delete Bin : ' + name);

            return false;
        }

        function fdeleteNow(id, name) {
            $.ajax({
                url: '${pageContext.request.contextPath}/deleteitemCat',
                data: {categoryId: id, category: name},
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if (data.isDeleted == true) {
                        utilityManager.showMessage('.del-user-msg', data.dmessage, 'successmsg');
                    } else {
                        utilityManager.showMessage('.del-user-msg', data.dmessage, 'errormsg');
                    }
                    jQuery("#gridtable2").trigger("reloadGrid");
                },
                error: function (data) {
                    window.location = "${pageContext.request.contextPath}/LogoutloginCall";
                }
            });

        }

    </script>
    <body>
        <section class="app-content popup-window">
            <div class="content innerpage">
                <s:set var="fvupdate"><s:property value="vupdate" default="true"/></s:set>
                <s:set id="fvadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                    <div class="content-section data-form">
                        
                        <div class="content-data">
                        <s:form id="addcate" theme="simple">
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span id="cdivmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Category Name<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield name="category" id="category" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="addurl1" action="additemCat"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-plus" aria-hidden="true"></i><sj:submit button="true" href="%{addurl1}" value="Add" targets="cdivmsg"  cssClass="btn default-button" disabled="#fvadd"/></div> 
                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit id="resetidf" button="true" value="Reset" onclick="resetData1()"   cssClass="btn reset-button" disabled="false" /></div>
                                        <%--<sj:submit id="backidf" button="true" value="Back" onclick="facilityPopupClose()"   cssClass="button_aback" disabled="false" />--%> 
                                </div>
                            </div>
                        </s:form>
                    </div>
                        
                    <s:url var="listur" action="listitemCat"/>
                        
                    <div class="content-section">
                        <div class="content-data">
                            <h2 class="section-title">Category List</h2>
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel del-user-msg" >
                                <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->
                        </div>
                        <div class="viewuser_tbl">
                            <div id="tablediv" class="custom-grid">

                                <sj:dialog 
                                    id="confirmdialogboxcat" 
                                    buttons="{ 
                                    'OK':function() { fdeleteNow($(this).data('keyval1'),$(this).data('keyname'));$( this ).dialog( 'close' ); },
                                    'Cancel':function() { $( this ).dialog( 'close' );} 
                                    }" 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Confirm Message"
                                    width="400"
                                    height="175"
                                    position="center"
                                    />

                                <sj:dialog 
                                    id="dialogboxcat" 
                                    buttons="{
                                    'OK':function() { $( this ).dialog( 'close' );}
                                    }"  
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Delete Message" 
                                    width="400"
                                    height="150"
                                    position="center"
                                    />

                                <sjg:grid
                                    id="gridtable2"                                
                                    caption="Brand"
                                    dataType="json"
                                    href="%{listur}"
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

                                    <sjg:gridColumn name="categoryId" index="categoryId" title="brandId" hidden="true" />
                                    <sjg:gridColumn name="category" index="category" title="Category"  align="center" width="50"  sortable="true"/>
                                    <sjg:gridColumn name="categoryId"  title="Action"  width="30" align="center" formatter="fdeleteformatter" sortable="false" cssClass="action-col"/>
                                    <sjg:gridColumn name="categoryId"  title="Delete"  width="10" align="center" hidden="true" sortable="false"/>
                                </sjg:grid> 

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </body>
</html>

