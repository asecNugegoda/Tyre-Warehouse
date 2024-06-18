<%-- 
    Document   : rackAssign
    Created on : Nov 10, 2018, 6:51:14 PM
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
            $('#rack').val("");
            $('#hrCount').val("");
            $('#vrCount').val("");
            $('#maxStorage').val("");
            $('#divmsg').empty();
            utilityManager.resetMessage();
            jQuery("#gridtable1").trigger("reloadGrid");
        }

        function fdeleteformatter(cellvalue, options, rowObject) {
            return "<a href='#' onClick='fdeleteInit(&#34;" + rowObject.rackId + "&#34;,&#34;" + rowObject.rack + "&#34;)' title='Delete Vehicle'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Vehicle'></i></a>";
        }

        function fdeleteInit(id, name) {
            $("#confirmdialogboxf").data('keyval1', id).dialog('open');
            $("#confirmdialogboxf").data('keyname', name).dialog('open');
            $("#confirmdialogboxf").html('<p>Please confirm to delete Rack : ' + name);

            return false;
        }

        function fdeleteNow(id, number) {
            $.ajax({
                url: '${pageContext.request.contextPath}/deletemngRacks',
                data: {rackId: id, rack: number},
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if (data.isDeleted == true) {
                        utilityManager.showMessage('.del-user-msg', data.dmessage, 'successmsg');
                    } else {
                        utilityManager.showMessage('.del-user-msg', data.dmessage, 'errormsg');
                    }
                    jQuery("#gridtable1").trigger("reloadGrid");
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
                    <s:form id="addForm1" theme="simple">
                        <div class="content-data">

                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i> <span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Rack ID/Name<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:hidden name="warehouseId" value="%{#parameters.Id}" id="usrId"/>
                                    <s:textfield name="rack" id="rack" cssClass="txt-input width-35"  />
                                </div>
                                
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Horizontal Count<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield name="hrCount" id="hrCount" cssClass="txt-input width-35"  />
                                </div>
                                
                                <label class="col-3 form-label">Vertical Count<sup class="required">*</sup></label>
                                <div class="col-4 form-field">
                                    <s:textfield id="vrCount" name="vrCount" cssClass="txt-input width-35" />
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Per Cell Max Storage<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield name="maxStorage" id="maxStorage" cssClass="txt-input width-35"  />
                                </div>
                                
                            </div>
                                
                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="addurl1" action="addmngRacks"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-plus" aria-hidden="true"></i><sj:submit button="true" href="%{addurl1}" value="Add"   targets="divmsg"  cssClass="btn default-button" disabled="#fvadd"/></div> 
                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit id="resetidf" button="true" value="Reset" onclick="resetData1()"   cssClass="btn reset-button" disabled="false" /></div>
                                        <%--<sj:submit id="backidf" button="true" value="Back" onclick="facilityPopupClose()"   cssClass="button_aback" disabled="false" />--%> 
                                </div>

                            </s:form>

                            <s:url var="listur" action="listmngRacks">
                                <s:param name="warehouseId"><s:property value="%{#parameters.Id}"/></s:param>
                            </s:url>

                            <div class="content-section">
                                <div class="content-data">
                                    <h2 class="section-title">Racks List</h2>
                                    <!-- Error and success message panel begin -->
                                    <div class="msg-panel del-user-msg" >
                                        <div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                                    </div>
                                    <!-- End -->
                                </div>
                                <div class="viewuser_tbl">
                                    <div id="tablediv" class="custom-grid">

                                        <sj:dialog 
                                            id="confirmdialogboxf" 
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
                                            id="dialogboxf" 
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
                                            id="gridtable1"                                
                                            caption="Bin Profiles"
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

                                            <sjg:gridColumn name="rackId" index="rackId" title="rackId"   hidden="true" />
                                            <sjg:gridColumn name="rack" index="rack" title="Rack ID/Name"  align="center" width="25"  sortable="true"/>
                                            <sjg:gridColumn name="hrCount" index="hrCount" title="Horizontal Count" align="center" width="35" sortable="true"/>
                                            <sjg:gridColumn name="vrCount" index="vrCount" title="Vertical Count" align="center" width="35" sortable="true"/>
                                            <sjg:gridColumn name="maxStorage" index="maxStorage" title="Per Cell Max Storage" align="center" width="35" sortable="true"/>
                                            <sjg:gridColumn name="rackId"  title="Delete"  width="25" align="center" formatter="fdeleteformatter" sortable="false" cssClass="action-col" />
                                        </sjg:grid> 
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </body>
</html>
