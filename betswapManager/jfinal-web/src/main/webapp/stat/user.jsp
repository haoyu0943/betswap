<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>stat</title>
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
            <div id="tabsF" class="ejdhBox">
                <ul class="ejcdUl">
                    <li><a href="<%=ctxPath%>/statistic"><lan set-lan="html:stat.shop">商城统计</lan></a></li>
                    <li><a href="<%=ctxPath%>/statistic/MovieStatisitc" ><lan set-lan="html:stat.movie">电影理财统计</lan></a></li>
                    <li><a href="<%=ctxPath%>/statistic/UserStatisitc" class="active-li"><lan set-lan="html:stat.user">用户统计</lan></a></li>
                    <div class="clear"></div>
                </ul>
            </div>

            <div class="page-inner layui-form">
                <table class="table table-striped table-bordered table-hover">
                    <tbody id="HistoryContent">
                        <tr><td class="compare-label"></td>
                        <td class="compare-label"></td></tr>
                    </tbody>
                </table>
                <div id="laypage1"></div>
            </div>
        </div>
    </div>

<%@ include file="../common/modpwd.jsp"%>
<!--<script type="text/javascript" src="<%=ctxPath%>/js/jquery-hunterTimePicker/js/jquery-timepicker.js"></script>-->
<script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
<script>
    initlan();
    var lan = $.tmCookie.getCookie('lan');
    switch (lan) {
        case 'en':lanindex=0;break;
        case 'cn':lanindex=1;break;
    }
    var msgInLans=[
        ["total user count","总用户数量"],
        ["total shop count","总店铺数量"],

        ["shop rank","商城等级"],
        ["movie rank","电影等级"],
        ["user count","用户数量"],

        ["ORDINARY","普通"],
        ["GOLD","黄金"],
        ["PLATINUM","铂金"],
        ["DIAMOND","钻石"],
        ["INTERMEDIATE_PARTNER","中级合伙人"],
        ["SENIOR_PARTNER","高级合伙人"],
        ["DIRECTOR_PARTNER","董事合伙人"],
    ];

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

    dostat();
    function dostat(){
       $.ajax({
           type:"post",
           url:"<%=ctxPath%>/statistic/UserStat",
           data:{},
           success:function(res) {
               var shtml="<tr><td class=\"compare-label\">"+msgInLans[0][lanindex]+"</td>" +
                   "<td class=\"compare-label\">"+res.nuser+"</td></tr>" +
                   "<tr><td class=\"compare-label\">"+msgInLans[1][lanindex]+"</td>" +
                   "<td class=\"compare-label\">"+res.nshop+"</td></tr>";
               /*
               shtml+="<tr><td class=\"compare-label\"></td><td class=\"compare-label\"></td></tr>"+
                   "<tr><td class=\"compare-label\">"+msgInLans[2][lanindex]+"</td>" +
                   "<td class=\"compare-label\">"+msgInLans[4][lanindex]+"</td></tr>";
               for(i=0;i<7;i++){
                   shtml+="<tr><td class=\"compare-label\">"+msgInLans[5+i][lanindex]+"</td>" +
                       "<td class=\"compare-label\">"+res.shop[i]+"</td></tr>"
               }
               */
               shtml+="<tr><td class=\"compare-label\"></td><td class=\"compare-label\"></td></tr>"+
                   "<tr><td class=\"compare-label\">"+msgInLans[3][lanindex]+"</td>" +
                   "<td class=\"compare-label\">"+msgInLans[4][lanindex]+"</td></tr>"
               for(i=0;i<7;i++){
                   shtml+="<tr><td class=\"compare-label\">"+msgInLans[5+i][lanindex]+"</td>" +
                       "<td class=\"compare-label\">"+res.movie[i]+"</td></tr>"
               }
               $("#HistoryContent").html(shtml);
           }
       });
    }


</script>
</body>
</html>
