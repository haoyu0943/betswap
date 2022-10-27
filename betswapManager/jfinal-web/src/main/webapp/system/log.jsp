<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>syslog</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/laydate/need/laydate.css">
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader" set-lan="html:log.log"><lan set-lan="html:menu.syslog">系统日志</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="hhBox">
                    <span class="wwmc">key</span>
                    <input type="text" id="opt_key_id" placeholder="key_id" class="form-control" style="width: 150px;display: inline-block" />
                </div>
                <div class="hhBox">
                    <span class="wwmc" set-lan="html:timerange">操作时间</span>
                    <input class="form-control timeInput inputR" type="text" id="startDate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#startDate'})">至
                    <input class="form-control timeInput inputL" type="text" id="endDate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#endDate'})">
                </div>
                <button type="button" class="btn btn-sm btn-success" onclick="getLog(1)" set-lan="html:button.query"><lan set-lan="html:movielist.query">查询</lan></button>
                <div class="clear"></div>
            </div>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="compare-label" style="width: 180px" set-lan="html:log.tablename">表名称</th>
                        <th class="compare-label" style="width: 180px" set-lan="html:log.rowid">表项id</th>
                        <th class="compare-label" style="width: 150px" set-lan="html:log.option">操作</th>
                        <th class="compare-label" style="width: 150px" set-lan="html:log.operator">操作人</th>
                        <th class="compare-label" set-lan="html:log.content">操作内容</th>
                        <th class="compare-label" style="width: 180px" set-lan="html:time">时间</th>
                    </tr>
                    </thead>
                    <tbody id="LogContent">
                    </tbody>
                </table>
                <div id="laypage1"></div>
            </div>
        </div>
    </div>

<%@ include file="../common/modpwd.jsp"%>
<script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
<script>

    initlan();
    var vlan = $.tmCookie.getCookie('lan');

    getLog(1);
    $(function(){
        $(".xiugmm").on('click',function(){
            $(".zhezhao1").show();
        });
        $(".close2").on('click',function(){
            $(".zhezhao1").hide();
        });
        $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        $("#main-menu").height($(window).height()-$(".navbar").height()-10);
    });
    function getLog(pageNum){
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/log/getSysLog",
            data:{
                "pageNum":pageNum,
                "opt_key_id":$("#opt_key_id").val(),
                "startDate":$("#startDate").val(),
                "endDate":$("#endDate").val(),
            },
            success:function(res){
                console.log(res);
                var _html="";
                res.loglist.forEach(function(value){
                    _html+="<tr>\n" +
                        "<td class=\"compare-label\">"+ value.opt_table +"</td>" +
                        "<td class=\"compare-label\">"+ value.opt_key_id +"</td>" +
                        "<td class=\"compare-label\">"+ value.keyword +"</td>" +
                        "<td class=\"compare-label\">"+ value.opt_user_name +"</td>";
                    if(value.content==null||value.content=="") {
                        _html+="<td class=\"compare-label\">--</td>";
                    }
                    else{
                        _html+="<td class=\"compare-label\">" + value.content + "</td>";
                    }
                    _html+="<td class=\"compare-label\">"+ value.opt_time +"</td></tr>"
                });
                $("#LogContent").html(_html);

                layui.laypage.render({
                    elem: 'laypage1',
                    limit: 20,
                    count: res.total,
                    curr:pageNum,
                    jump: function(obj, first){
                        if(!first){
                            getLog(obj.curr);
                        }
                    }
                });
            }
        });
    }

    /*
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#test6'
            ,range: ['#startDate', '#endDate']
        });

    });
    */

</script>
</body>
</html>
