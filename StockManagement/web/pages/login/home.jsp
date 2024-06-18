<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <jsp:include page="../../Styles.jsp" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/morris.css">
    </head>
    <body>
        <section class="app-content">
            <jsp:include page="../../header.jsp" /> 

            <div class="content innerpage">
                <div class="content dashboard">
                    <h1 class="page-title">System Dashboard</h1>

                    <div class="content-section">

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
                                title="PO Items"
                                onOpenTopics="openview" 
                                loadingText="Loading .."
                                />

                            <div class="content-data">
                                <h2 class="section-title">All Pending Purchasing Orders</h2>
                            </div>
                            <s:url var="listurl" action="listcreatPo"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Pending PO"
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

                                <sjg:gridColumn name="create_date" index="create_date" title="Create Date" align="left" sortable="true"/>  
                                <sjg:gridColumn name="dealer" index="dealer" title="Agent" align="left"  sortable="true"/>  
                                <sjg:gridColumn name="order_id" index="order_id" title="Purchasing Order" formatter="ViewItemformatter" align="center"  sortable="true"/>  
                                <sjg:gridColumn name="po_status" index="po_status" title="PO Status" align="center"  formatter="Statusformatter" sortable="false"/>  

                            </sjg:grid> 

                        </div>

                    </div>

                </div>
            </div>
            <!-- End -->

        </section>
        <jsp:include page="../../footer.jsp" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.2.7/raphael.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/morris.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/GridRowCount.js"></script>

    </body>

    <script>
        function Statusformatter(cellvalue) {
            if (cellvalue == 3) {
                return "<i class='fa fa-circle concern' aria-hidden='true'></i>";
            } else {
                return "<i class='fa fa-circle' aria-hidden='true'></i>";
            }
        }

        function ViewItemformatter(cellvalue, options, rowObject) {
            return "<a href='#' onClick='javascript:viewPO(&#34;" + rowObject.order_id + "&#34;,&#34;" + rowObject.dealer + "&#34;)'><i class='fa fa-share-square-o' aria-hidden='true'></i></a>";
        }

        function viewPO(id, bin) {
            $("#viewdialog").data('Id', id);
            $("#viewdialog").data('dealer', bin).dialog('open');
        }
        $.subscribe('openview', function (event, data) {
            utilityManager.resetMessage();
//                resetData();
            var $led = $("#viewdialog");
//                alert($led.data('Id').replace(/ /g,"_"));
            $led.load("POViewcreatPo?Id=" + $led.data('Id') + "&cName=" + $led.data('dealer').replace(/ /g, "_"));
        });


    </script>
    <style>
        .ui-jqgrid-sortable{
            font-weight: normal !important;
        }
        .add{
            width:auto !important;
        }
    </style>

</html>
