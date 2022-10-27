<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<body>
<h2>demo page!</h2>
<input type="button" value="保存" onclick="savepsn()">
<lan set-lan="html:actor.name">姓名</lan><input type="text" value="" id="xm">
<lan set-lan="html:usermgr.sex">性别</lan><select id="xb">
    <OPTION value="1"><lan set-lan="html:usermgr.men">男</lan></OPTION>
    <OPTION value="2"><lan set-lan="html:usermgr.women">女</lan></OPTION>
</select>
<lan set-lan="html:actor.idCard">身份证号</lan><input type="text" value="" id="sfzh">
<lan set-lan="html:usermgr.phone">联系方式</lan><input type="text" value="" id="sj">
</body>
<%@include file="/common/resource.jsp" %>
<script type="text/javascript">
    function savepsn() {
        var xm = $("#xm").val();
        var xb = $("#xb").val();
        var xbname = $("#xb").find("option:selected").text();
        var sfzh= $("#sfzh").val();
        var sj= $("#sj").val();
        $.ajax({
            type:"post",
            url:ctxPath+"/user/saveuser",
            data : {
                "xm" : xm,
                "xb" : xb,
                "xbname":xbname,
                "sfzh":sfzh,
                "sj":sj
            },
            success:function(data){
                // alert("新建成功");
                document.location.href=ctxPath+"/user";
            }
        });
    }
</script>
</html>
