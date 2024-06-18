<%-- 
    Document   : viewPOItems
    Created on : Nov 11, 2018, 3:58:12 PM
    Author     : thilinath
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%> 

<!DOCTYPE html>
<html>
    <script type="text/javascript">

        function fdeleteformatter(cellvalue, options, rowObject) {
            return "<a href='#' onClick='fdeleteInit(&#34;" + rowObject.po_id + "&#34;,&#34;" + rowObject.whItmeName + "&#34;)' title='Delete BIN'><i class='fa fa-trash-o' aria-hidden='true' title='Delete Block BIN'></i></a>";
        }

        function fdeleteInit(id, name) {
            $("#confirmdialogboxf").data('keyval1', id).dialog('open');
            $("#confirmdialogboxf").data('keyname', name).dialog('open');
            $("#confirmdialogboxf").html('<p>Please confirm to delete Item : ' + name);

            return false;
        }

        function fdeleteNow(id, name) {
            $.ajax({
                url: '${pageContext.request.contextPath}/deleteviewPO',
                data: {po_id: id, whItmeName: name},
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

                <div class="content-section data-form">

                    <s:url var="listur" action="listviewPO">
                        <s:param name="po_id"><s:property value="%{#parameters.Id}"/></s:param>
                    </s:url>

                    <div class="content-section">
                        <div class="content-data">
                            <h2 class="section-title">Brand List</h2>
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

                                    <sjg:gridColumn name="po_id" index="po_id" title="brandId" hidden="true" />
                                    <sjg:gridColumn name="brand" index="brand" title="Brand"  align="center" width="50"  sortable="true"/>
                                    <sjg:gridColumn name="category" index="category" title="Category"  align="center" width="50"  sortable="true"/>
                                    <sjg:gridColumn name="whItmeName" index="whItmeName" title="Item"  align="center" width="50"  sortable="true"/>
                                    <sjg:gridColumn name="qnty" index="qnty" title="Quantity"  align="center" width="50"  sortable="true"/>
                                    <sjg:gridColumn name="po_id"  title="Action"  width="30" align="center" formatter="fdeleteformatter" sortable="false" cssClass="action-col"/>
                                    <sjg:gridColumn name="po_id"  title="Delete"  width="10" align="center" hidden="true" sortable="false"/>
                                </sjg:grid> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>

    </body>
</html>

