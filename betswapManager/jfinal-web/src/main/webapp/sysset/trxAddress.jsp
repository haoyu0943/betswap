<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>GAS</title>
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
            <div class="pageheader" >比赛地址GAS查询<i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="hhBox">
                    <span class="wwmc">主队名称</span>
                    <input type="text" id="homeTeam" placeholder="主队名称" class="form-control" style="width: 150px;display: inline-block" />
                </div>
                <div class="hhBox">
                    <span class="wwmc">客队名称</span>
                    <input type="text" id="guestTeam" placeholder="客队名称" class="form-control" style="width: 150px;display: inline-block" />
                </div>
                <div class="hhBox">
                    <span class="wwmc">比赛时间</span>
                    <input class="form-control timeInput inputR" type="text" id="startDate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#startDate'})">至
                    <input class="form-control timeInput inputL" type="text" id="endDate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#endDate'})">
                </div>
                <button type="button" class="btn btn-sm btn-success" onclick="initData(1)" >查询</button>
                <button type="button" class="btn btn-sm btn-success" onclick="reset()" >重置</button>
                <div class="clear"></div>
            </div>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="compare-label" style="width: 180px">主队名称</th>
                        <th class="compare-label" style="width: 180px" >主队名称繁体</th>
                        <th class="compare-label" style="width: 150px" >主队名称英文</th>
                        <th class="compare-label" style="width: 180px" >客队名称</th>
                        <th class="compare-label" style="width: 180px" >客队名称繁体</th>
                        <th class="compare-label" style="width: 150px" >客队名称英文</th>
                        <th class="compare-label" style="width: 180px" >比赛时间</th>
                        <th class="compare-label" style="width: 150px" >钱包地址</th>
                        <th class="compare-label" style="width: 150px" >TRX余额</th>
                        <th class="compare-label" style="width: 150px" >GAS转入</th>
                        <th class="compare-label" style="width: 150px" >归集状态</th>

                    </tr>
                    </thead>
                    <tbody id="dataTable">
                    </tbody>
                </table>
                <div id="laypage1"></div>
            </div>
        </div>
    </div>

<%@ include file="../common/modpwd.jsp"%>
<script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
<script>



    initData(1);
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
    
    function reset() {
        $("#homeTeam").val("");
        $("#guestTeam").val("");
        $("#startDate").val("");
        $("#endDate").val("");
    }
    
    function initData(pageNum){
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/sysset/getTRXPage",
            data:{
                "pageNum":pageNum,
                "homeTeam":$("#homeTeam").val(),
                "guestTeam":$("#guestTeam").val(),
                "startDate":$("#startDate").val(),
                "endDate":$("#endDate").val(),
            },
            success:function(res){
                var _html="";
                res.records.forEach(function(value){
                    _html+="<tr>\n" +
                        "<td class=\"compare-label\">"+ value.home_team +"</td>" +
                        "<td class=\"compare-label\">"+ value.home_team_zht +"</td>" +
                        "<td class=\"compare-label\">"+ value.home_team_en +"</td>" +
                        "<td class=\"compare-label\">"+ value.guest_team +"</td>" +
                        "<td class=\"compare-label\">"+ value.guest_team_zht +"</td>" +
                        "<td class=\"compare-label\">"+ value.guest_team_en +"</td>" +
                        "<td class=\"compare-label\">"+ $.tmDate._toString(new Date(value.match_time) ,'yyyy-MM-dd HH:mm') +"</td>" +
                        "<td class=\"compare-label\">"+ value.wallet_address +"</td>" +
                        "<td class=\"compare-label\">"+ value.balance +"</td>" +
                        "<td class=\"compare-label\">"+ (value.intogasstatus==1?value.into_amount:"否") +"</td>" +
                        "<td class=\"compare-label\">"+ (value.status==1?"已归集":"未归集") +"</td>" ;
                });
                $("#dataTable").html(_html);

                layui.laypage.render({
                    elem: 'laypage1',
                    limit: 10,
                    count: res.total,
                    curr:pageNum,
                    jump: function(obj, first){
                        if(!first){
                            initData(obj.curr);
                        }
                    }
                });
            }
        });
    }


</script>
</body>
</html>
