<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>stat of mall</title>
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
                    <li><a href="<%=ctxPath%>/statistic" class="active-li"><lan set-lan="html:stat.shop">商城统计</lan></a></li>
                    <li><a href="<%=ctxPath%>/statistic/MovieStatisitc"><lan set-lan="html:stat.movie">电影理财统计</lan></a></li>
                    <li><a href="<%=ctxPath%>/statistic/UserStatisitc"><lan set-lan="html:stat.user">用户统计</lan></a></li>
                    <div class="clear"></div>
                </ul>
            </div>
            <div class="page-inner">
                <div class="hhBox">
                    <span class="wwmc"><lan set-lan="html:timerange">时间范围</lan>：</span>
                    <input class="form-control timeInput inputR" type="text" id="sdate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#sdate'})">-
                    <input class="form-control timeInput inputL" type="text" id="edate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#edate'})">
                </div>
                <div class="hhBox">
                    <span class="wwmc"><lan set-lan="html:timescale">时间粒度</lan> ：</span>
                    <select  class="form-control" id="tjld" style="display: inline-block;width: 150px">
                        <option set-lan="html:year" value=1>    年</option>
                        <option set-lan="html:month" value=2>   月</option>
                        <option set-lan="html:day" value=3>     日</option>
                    </select>
                </div>
                <button type="button" class="btn btn-sm btn-success" onclick="dostat()" set-lan="html:button.query">统计</button>
                <button type="button" class="btn btn-sm btn-success" onclick="clearTj()" set-lan="html:button.clear">清空</button>
                <div class="clear"></div>
            </div>
            <div class="page-inner layui-form">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <td class="compare-label" set-lan="html:time">时间</td>
                        <td class="compare-label" set-lan="html:stat.auditedgoods">上架商品量</td>
                        <td class="compare-label" set-lan="html:stat.fiatvol">法币交易额</td>
                        <td class="compare-label" set-lan="html:stat.usdtvol">USDT交易额</td>
                    </tr>
                    </thead>
                    <tbody id="HistoryContent">
                    <td class="compare-label"></td>
                    <td class="compare-label"></td>
                    <td class="compare-label"></td>
                    <td class="compare-label"></td>
                    </tbody>
                </table>

            </div>
        </div>
    </div>

<%@ include file="../common/modpwd.jsp"%>
    <script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
    <script>
        initlan();
        var lan = $.tmCookie.getCookie('lan');
        switch (lan) {
            case 'en':lanindex=0;break;
            case 'cn':lanindex=1;break;
        }
        var msgInLans=[
            ["time range empty","时间范围没有填写完全"],
            ["no longer than 10 years","按年统计时间段不能超过10年"],
            ["no longer than 60 months","按月统计时间段不能超过60个月"],
            ["no longer than 60 days","按日统计时间段不能超过60天"],
            ["reversed time range","时间范围顺序反了"],
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

        function clearTj() {
            $("#sdate").val("");
            $("#edate").val("");
        }

        function checkvalid(){
            var sdate=$("#sdate").val();
            var edate=$("#edate").val();
            var unit=$("#tjld").val();
            if(sdate===""||edate===""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:msgInLans[0][lanindex]});
                return false;
            }
            var diff = (new Date(edate.substr(0,10).replace("-","/")) - new Date(sdate.substr(0,10).replace("-","/")));
            if(diff>=0){
                if (unit === 1) {
                    if (diff > 3650) {
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:msgInLans[1][lanindex]});
                        return false;
                    }
                }
                else if (unit === 2 ) {
                    if (diff > 1825) {
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:msgInLans[2][lanindex]});
                        return false;
                    }
                }
                else if (unit === 3) {
                    if (diff > 60) {
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:msgInLans[3][lanindex]});
                        return false;
                    }
                }
            }
            else{
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:msgInLans[4][lanindex]});
                return false;
            }
            return true;
        }


        function dostat(){
            if(checkvalid()===false){
                return;
            }
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/statistic/shopstat",
                data:{
                    "sdate":$("#sdate").val(),
                    "edate":$("#edate").val(),
                    "tjld":$("#tjld").val()
                },
                success:function(res) {
                    var list=res.statlist;
                    var shtml="";
                    for(var i=0;i<list.length;i++){
                        shtml+="<tr>"
                        shtml+="<td class=\"compare-label\">"+list[i].timestr+"</td>";
                        shtml+="<td class=\"compare-label\">"+list[i].goodsApply+"</td>";
                        shtml+="<td class=\"compare-label\">"+list[i].shopFiat+"</td>";
                        shtml+="<td class=\"compare-label\">"+list[i].shopUsdt+"</td>";
                        shtml+="<tr>"
                    }
                    $("#HistoryContent").html(shtml);
                }
            });
        }


</script>
</body>
</html>
