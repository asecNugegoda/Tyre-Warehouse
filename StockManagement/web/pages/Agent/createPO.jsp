<%-- 
    Document   : createPO
    Created on : Nov 8, 2018, 10:51:57 AM
    Author     : thilinath
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>    
<html>
    <head>

        <jsp:include page="../../Styles.jsp" />

        <style>
            .ui-jqgrid-sortable{
                font-weight: normal !important;
            }
            .add{
                width:auto !important;
            }
        </style>
        <script>
            $.subscribe('onclicksearch', function (event, data) {

                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var agent = $('#searchField').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {searchField: agent, fromdate: fromdate, todate: todate}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });

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

            function loadModulePage(selectedModule, flag) {
                var sel = document.getElementById('brand');
                var value = sel.options[sel.selectedIndex].text;

                $('#items option').prop('selected', true);
                if (selectedModule == '0') {
                    $('#items').empty();
                    $('#items').append($('<option></option>').val("0").html("---Select---"));
                } else {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/loadItemcreatPo.action',
                        data: {brand: value, category: selectedModule},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            $('#items').empty();
                            $('#items').append($('<option></option>').val("0").html("---Select---"));
                            $.each(data.itemMap, function (val, text) {
                                $('#items').append($('<option></option>').val(val).html(text));
                            });
                        }

                    });
                }
            }

            function Riskformatter(cellvalue) {
                if (cellvalue == 1) {
                    return "<i class='fa fa-circle concern' aria-hidden='true'></i>";
                } else if (cellvalue == 2) {
                    return "<i class='fa fa-circle warning' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle critical' aria-hidden='true'></i>";
                }
            }

            function Statusformatter(cellvalue) {
                if (cellvalue == 3) {
                    return "<i class='fa fa-circle concern' aria-hidden='true'></i>";
                } else {
                    return "<i class='fa fa-circle' aria-hidden='true'></i>";
                }
            }

            function hideInit() {
                $("#addnewPO").hide();
                $(".itemTable").hide();
            }
            $(document).ready(function () {
                hideInit();
                $("#Addbtn").click(function () {
                    $('.lnk-back').removeClass('hide-element');
                    utilityManager.resetMessage();
                    $("#searchPO").hide();
                    $("#tablediv").hide();
//                    $("#Updateform").hide();
                    $("#addnewPO").show();
                    $(".itemTable").show();
                    $('#task').empty();
                    var text = 'Create Purchasing Orders';
                    $('#task').append(text);
                })

            })

            function resetAddForm() {
                $('#dealer').val(-1);
                $('#brand').val(-1);
                $('#category').val(-1);
                $('#items').val(0);
                $('#qnty').val("");
                jQuery("#gridtable").trigger("reloadGrid");
                utilityManager.resetMessage();
            }

        </script>

    </head>

    <body>
        <section class="app-content">
            <jsp:include page="../../header.jsp" /> 

            <div class="content innerpage">

                <!-- Page title begin -->
                <h1 class="page-title"><s:property value="Section"/><a href="#" class="lnk-back hide-element do-nothing"><i class="fa fa-arrow-left" aria-hidden="true"></i> back</a></h1>
                <!-- End -->

                <div class="content-section data-form" id="searchPO">

                    <s:form id="systemHistoryForm"  action="XSLcreatcreatPo" name="systemHistoryForm" theme="simple" method="post">
                        <div class="content-data">
                            <h2 class="section-title">Search Purchasing Orders</h2>
                        </div>
                        
                        <div class="content-data">

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Start Date</label>
                                <div class="col-2 form-field">
                                    <sj:datepicker id="fromdate" name="fromdate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">End Date</label>
                                <div class="col-2 form-field">
                                    <sj:datepicker id="todate" name="todate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            
                            <!-- End -->

                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col">

                                    <sj:a 
                                        id="searchbut" 
                                        button="true"                                        
                                        onClickTopics="onclicksearch"  cssClass="btn default-button"   role="button" aria-disabled="false"                                    
                                        ><i class="fa fa-search" aria-hidden="true"></i> Search</sj:a>
                                        
                                        <div class="btn-wrap lnk-match"><i class="fa fa-file-excel-o" aria-hidden="true"></i>
                                            <s:submit id="exportXLSbutton" name="exportXLSbutton" value="Export" disabled="#vdownload" cssClass="btn default-button"  />
                                        </div>
                                                                                
                                    <sj:a 
                                        disabled="#vadd"
                                        id="Addbtn" 
                                        button="true" 
                                        cssClass="btn default-button" 
                                        ><i class="fa fa-plus" aria-hidden="true"></i> Create PO</sj:a>

                                    </div>
                                </div>
                        </s:form>
                    </div>
                </div>

                <div class="content-section data-form" id="addnewPO">
                    <div class="content-data">
                        <h2 class="section-title">Create Purchasing Orders</h2>
                    </div>
                    <s:form id="poAddform" name="poAddform" theme="simple" method="post">
                        <div class="content-data">
                            <!-- Error and success message panel begin -->
                            <div class="msg-panel add-form-msg">
                                <label>&nbsp;</label><div><i class="fa fa-times" aria-hidden="true"></i><span id="divmsg"></span></div>
                            </div>
                            <!-- End -->

                            <!-- Two colum form row begin -->
                            <div class="d-row">
                                <label class="col-1 form-label">Agent</label>
                                <div class="col-2 form-field">
                                    <s:select  name="dealer" headerKey="" headerValue="---Select Agent---"
                                               listKey="key" listValue="value"
                                               list="%{agentMap}" id="dealer" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Brand</label>
                                <div class="col-2 form-field">
                                    <s:select  name="brand" headerKey="" headerValue="---Select Brand---"
                                               listKey="key" listValue="value"
                                               list="%{brandMap}" id="brand" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Category</label>
                                <div class="col-2 form-field">
                                    <s:select  name="category" headerKey="" headerValue="---Select Category---"
                                               listKey="key" listValue="value"
                                               list="%{categoryMap}" onchange="loadModulePage(this.value,'Add')"
                                               id="category" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Item</label>
                                <div class="col-2 form-field">
                                    <s:select  name="item" headerKey="" headerValue="---Select Item---"
                                               listKey="key" listValue="value"
                                               list="%{ItemMap}" id="items" cssClass="ddl-input width-35"/>
                                </div>
                            </div>
                            <div class="d-row">
                                <label class="col-1 form-label">Quantity</label>
                                <div class="col-2 form-field">
                                    <s:textfield name="qnty" id="qnty" cssClass="txt-input width-35"/>
                                </div>
                            </div>
                            <!-- End -->

                            <div class="d-row cpanel four-col">
                                <label class="col-1">&nbsp;</label>
                                <div class="right-col"> 

                                    <s:url var="moreurl" action="morecreatPo"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-plus" aria-hidden="true"></i><sj:submit  href="%{moreurl}" targets="divmsg" value="Add More" button="true" cssClass="btn default-button" /></div>

                                    <div class="btn-wrap lnk-match"><i class="fa fa-times" aria-hidden="true"></i><sj:submit button="true" value="Reset"  cssClass="btn reset-button" onclick="resetAddForm()"  /></div>

                                    <s:url var="saveurl" action="addcreatPo"/>                                   
                                    <div class="btn-wrap lnk-match"><i class="fa fa-floppy-o" aria-hidden="true"></i><sj:submit  href="%{saveurl}" targets="divmsg" value="Save" disabled="#vadd" button="true" cssClass="btn default-button" /></div>

                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>


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
        <!--End of Body Content-->

        <jsp:include page="../../footer.jsp" />
    </section>
    <script type="text/javascript">
        $(document).ready(function () {
            //Back button event
            $('.lnk-back').on('click', function () {
                utilityManager.resetMessage();
                $('#addnewPO').hide();
                $('.itemTable').hide();
                $('#searchPO').show();
                $('#tablediv').show();
                $('#task').empty();
                jQuery("#gridtable").trigger("reloadGrid");
                $('.lnk-back').addClass('hide-element');
                var text = 'Search Purchasing Orders';
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
