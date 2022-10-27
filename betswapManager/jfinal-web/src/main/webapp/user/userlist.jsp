<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<body>
<h2>demo page!</h2>
<input type="button" value="查询人员" onclick="qrypsn()">
<input type="button" value="返回" onclick="back()">
<div class="table-responsive ">
    <table id="ryinfo" width="80%" border="1" cellspacing="0" cellpadding="0" class="table mb-none table-striped table-hover table-bordered text-center">
        <thead>
        <tr>
            <th style="width: 60px;"><lan set-lan="html:actor.name">姓名</lan></th>
            <th style="width: 40px;"><lan set-lan="html:usermgr.sex">性别</lan></th>
            <th style="width: 200px;"><lan set-lan="html:actor.idCard">身份证号</lan></th>
            <th style="width: 100px;"><lan set-lan="html:usermgr.phone">联系方式</lan></th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</body>
<%@include file="/common/resource.jsp" %>
<script type="text/javascript">
    function back(){
        document.location.href=ctxPath+"/index";
    }
    function qrypsn() {
        var $thisTable = $("#ryinfo");
        var jsonDate ={};
        initTable("ryinfo");
        $.ajax({
            type:"post",
            url:ctxPath+"/user/qryuser",
            data:jsonDate,
            success:function(data){
                var t = $thisTable.DataTable();
                t.rows("tr").remove().draw(false);
                var tableRowsArray = [];
                for(var i=0;i<data.length;i++) {
                    tableRowsArray = [
                        data[i].name,
                        data[i].sexname,
                        data[i].idcard,
                        data[i].phone
                    ];
                    t.row.add(tableRowsArray).draw();
                }
            }
        });
    }
</script>
</html>
