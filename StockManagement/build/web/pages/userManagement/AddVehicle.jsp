<%-- 
    Document   : AddVehicle
    Created on : Nov 8, 2018, 9:43:10 PM
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
            $('#vehicleBrand').val("");
            $('#vehicleNumber').val("");
            $('#divmsg').empty();
            utilityManager.resetMessage();
            jQuery("#gridtable1").trigger("reloadGrid");
        }

        function fdeleteformatter(cellvalue, options, rowObject) {
            return "<a href='#' onClick='fdeleteInit(&#34;" + rowObject.vehicleId + "&#34;,&#34;" + rowObject.vehicleNumber + "&#34;)' title='Delete Vehicle'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Vehicle'></i></a>";
        }

        function fdeleteInit(id, name) {
            $("#confirmdialogboxf").data('keyval1', id).dialog('open');
            $("#confirmdialogboxf").data('keyname', name).dialog('open');
            $("#confirmdialogboxf").html('<p>Please confirm to delete Vehicle : ' + name);

            return false;
        }

        function fdeleteNow(id, number) {
            $.ajax({
                url: '${pageContext.request.contextPath}/deletevehicle',
                data: {vehicleId: id, vehicleNumber: number},
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
                                <label class="col-1 form-label">Vehicle Brand<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:hidden name="usrId" value="%{#parameters.usrId}" id="usrId"/>
                                    <s:textfield name="vehicleBrand" id="vehicleBrand" cssClass="txt-input width-35"  />
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Vehicle Number<sup class="required">*</sup></label>
                                <div class="col-2 form-field">
                                    <s:textfield name="vehicleNumber" id="vehicleNumber" cssClass="txt-input width-35"  />
                                </div>
                            </div>
                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col">
                                    <s:url var="addurl1" action="addvehicle"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-plus" aria-hidden="true"></i><sj:submit button="true" href="%{addurl1}" value="Add"   targets="divmsg"  cssClass="btn default-button" disabled="#fvadd"/></div> 
                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit id="resetidf" button="true" value="Reset" onclick="resetData1()"   cssClass="btn reset-button" disabled="false" /></div>
                                        <%--<sj:submit id="backidf" button="true" value="Back" onclick="facilityPopupClose()"   cssClass="button_aback" disabled="false" />--%> 
                                </div>

                            </s:form>

                            <s:url var="listur" action="listvehicle">
                                <s:param name="usrId"><s:property value="%{#parameters.usrId}"/></s:param>
                            </s:url>

                            <div class="content-section">
                                <div class="content-data">
                                    <h2 class="section-title">Vehicle List</h2>
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

                                            <sjg:gridColumn name="vehicleId" index="vehicleId" title="vehicleId"   hidden="true" />
                                            <sjg:gridColumn name="vehicleBrand" index="vehicleBrand" title="Brand"  align="center" width="25"  sortable="true"/>
                                            <sjg:gridColumn name="vehicleNumber" index="vehicleNumber" title="Reg Number" align="center" width="35" sortable="true"/>
                                            <sjg:gridColumn name="id"  title="Delete"  width="25" align="center" formatter="fdeleteformatter" sortable="false" cssClass="action-col" />
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
