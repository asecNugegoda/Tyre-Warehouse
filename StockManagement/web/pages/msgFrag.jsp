<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<s:if test="hasActionMessages()">
    <script type="text/javascript">resetForm();
        <s:iterator value="actionMessages">
        utilityManager.showMessage('.add-form-msg', '<s:property escapeJavaScript="true" />', 'successmsg');
        </s:iterator>
    </script>
    <s:actionmessage theme="jquery"/>
</s:if>
<s:if test="hasActionErrors()">
    <script type="text/javascript">
        <s:iterator value="actionErrors">
        utilityManager.showMessage('.add-form-msg', '<s:property escapeJavaScript="true" />', 'errormsg');
        </s:iterator>
    </script>
    <s:actionerror theme="jquery"/>
</s:if>

