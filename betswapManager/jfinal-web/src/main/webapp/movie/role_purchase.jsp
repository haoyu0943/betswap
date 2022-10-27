<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of purchase</title>
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
                        <li><a href="<%=ctxPath%>/movie/crtOreditMovie?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.info">电影基础信息</lan></span></a></li>
                        <c:if test="${not empty movie.movie_id}">
                            <li><a href="<%=ctxPath%>/movie/movieActor?movieId=${movieId}"><span><lan set-lan="html:moviemenu.actor">电影参影人员</lan></span></a></li>
                            <li><a href="<%=ctxPath%>/movie/rolePurchase?movieId=${movieId}" class="active-li"><span><lan set-lan="html:moviemenu.purchase">抢购规则设置</lan></span></a></li>
                            <li><a href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.presell">预售规则设置</lan></span></a></li>
                            <!--<li><a href="<%=ctxPath%>/movie/fund?movieId=${movieId}"><span><lan set-lan="html:moviemenu.fund">影视基金设置</lan></span></a></li>-->
                        </c:if>
                        <div class="clear"></div>
                    </ul>
                </div>
            <div class="page-inner">
                    <table class="table table-bordered table-compare">
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.time">起止时间</lan></td>
                            <td id="time" colspan="2"><span></span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.restrictions">限购数</lan><input type="hidden" id="id"></td>
                            <td id="perLimit" colspan="2"><span></span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.condition">参与条件是否启用</lan></td>
                            <td id="ifUseThreshold" colspan="2"><span></span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.threshold">参与条件阀值</lan></td>
                            <td id="threshold" colspan="2"><span></span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepresell.totalsales">出售总量</lan></td>
                            <td id="totalCount" colspan="2"><span></span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>

                        <tr bgcolor="#E0E0E0">
                            <td class="compare-label"><lan set-lan="html:moviepurchase.membership">会员级别</lan></td>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.rate">购票成功率</lan></td>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.daily">日票房分红</lan></td>
                        </tr>
                        <tr>
                            <td><lan set-lan="html:member.ordinary">普通会员</lan></td>
                            <td id="successRatioOrdinary"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioOrdinary"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.gold">黄金会员</lan></td>
                            <td id="successRatioGold"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioGold"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.platinum">铂金级别</lan></td>
                            <td id="successRatioPlatinum"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioPlatinum"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.diamond">钻石会员</lan></td>
                            <td id="successRatioDiamond"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioDiamond"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.intermediate">中级合伙人</lan></td>
                            <td id="successRatioMiddle"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioMiddle"><span></span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.senior">高级合伙人</lan></td>
                            <td id="successRatioSenior"><span></span>%<a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioSenior"><span></span>%<a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.directo">董事合伙人</lan></td>
                            <td id="successRatioDirector"><span></span>%<a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>
                            <td id="dailyRatioDirector"><span></span>%<a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>
                        </tr>

                    </table>
            </div>
        </div>
    </div>

    <%@ include file="../common/modpwd.jsp"%>
    <script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
    <script>
        initlan();
        var vlan = $.tmCookie.getCookie('lan');
        initData();

        $(function(){
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
        });

        function initData(){
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/findRolePurchase",
                data:{
                    "movieId":"${movieId}"
                },
                success:function(res){
                    var  role=res.role;
                    $("#id").val(role.id);
                    $("#perLimit span").text(role.per_limit);
                    if(role.if_use_threshold==0){
                        $("#ifUseThreshold span").text("未启用");
                        $("#ifUseThreshold span").attr("value","0");

                    }
                    else{
                        $("#ifUseThreshold span").text("启用");
                        $("#ifUseThreshold span").attr("value","1");
                    }
                    $("#threshold span").text(role.threshold);
                    $("#successRatioOrdinary span").text(role.success_ratio_ordinary);
                    $("#dailyRatioOrdinary span").text(role.daily_ratio_ordinary);
                    $("#successRatioGold span").text(role.success_ratio_gold);
                    $("#dailyRatioGold span").text(role.daily_ratio_gold);
                    $("#successRatioPlatinum span").text(role.success_ratio_platinum);
                    $("#dailyRatioPlatinum span").text(role.daily_ratio_platinum);
                    $("#successRatioDiamond span").text(role.success_ratio_diamond);
                    $("#dailyRatioDiamond span").text(role.daily_ratio_diamond);
                    $("#successRatioMiddle span").text(role.success_ratio_middle);
                    $("#dailyRatioMiddle span").text(role.daily_ratio_middle);
                    $("#successRatioSenior span").text(role.success_ratio_senior);
                    $("#dailyRatioSenior span").text(role.daily_ratio_senior);
                    $("#successRatioDirector span").text(role.success_ratio_director);
                    $("#dailyRatioDirector span").text(role.daily_ratio_director);
                    $("#time span").text(role.startDate+"--"+role.endDate);
                    $("#totalCount span").text(role.total_count);
                }
            });
        }

        function edit(obj){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            //text=text.replace("%","");
            var id=$(obj).parents("td").attr("id");
            console.log("id="+id);
            //console.log("text="+text);
            if(id=="ifUseThreshold"){

                //if(text=="未启用") {
                if($(parent$).find('span').attr("value")=="0"){
                    $(parent$).html('<input name="ifUseThresholdBtn" type="radio" value="0" checked>未启用' +
                        '<input name="ifUseThresholdBtn" type="radio" value="1">启用' +
                        '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this)"></i>' +
                        '</a>');
                }
                else{
                    $(parent$).html('<input name="ifUseThresholdBtn" type="radio" value="0">未启用' +
                        '<input name="ifUseThresholdBtn" type="radio" value="1" checked>启用' +
                        '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this)"></i>' +
                        '</a>');
                }

                /*
                if(text=="未启用") {
                    $(parent$).html('<select id="ifUseThresholdBtn" class="form-control" style="width: 20%">' +
                            '<option value="0" selected>未启用</option> '+
                            '<option value="1" >启用</option></select> '+
                        '<i class="fa fa-check ml-5 color-red" onclick="save(this)"></i>');
                }
                else{
                    $(parent$).html('<select id="ifUseThresholdBtn" class="form-control" style="width: 20%">' +
                        '<option value="0" >未启用</option> '+
                        '<option value="1" selected>启用</option></select> '+
                        '<i class="fa fa-check ml-5 color-red" onclick="save(this)"></i>');
                }
                 */
            }
            else if(id=="time"){
                var sd="";
                var ed="";
                var p=text.indexOf("--");
                if(p>-1) {
                    sd = text.substr(0, p);
                    ed = text.substr(p + 2, text.length - p - 2);
                }
                $(parent$).html('<input class="form-control timeInput inputR" value="' + sd + '" type="text" id="startDate" onClick="laydate({format: \'YYYY-MM-DD hh \',elem: \'#startDate\',type:\'time\'})">-'+
                '<input class="form-control timeInput inputL" value="' + ed + '" type="text" id="endDate" onClick="laydate({format: \'YYYY-MM-DD hh\',elem: \'#endDate\',type:\'time\'})"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this)"></i>' +
                    '</a>');

            }
            else {
                $(parent$).html('<input id="' + id + 'Btn" type="text" class="form-control d-ib" ' +
                    'value="' + text + '" style="width: 20%"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this)"></i>' +
                    '</a>');
            }
        }

        function save(obj){
            var id=$("#id").val();
            var fld=$(obj).parents("td").attr("id");
            var updateVal="";
            var startDate="";
            var endDate="";
            console.log("fld="+fld);
            if(fld=="ifUseThreshold"){
                //updateVal=$(obj).parents("td").find("radio").val();
                updateVal= $('input:radio[name="ifUseThresholdBtn"]:checked').val();
                //alert("updateVal="+updateVal);
            }
            else if(fld=="time"){
                startDate= $("#startDate").val();
                endDate= $("#endDate").val();
            }
            else{
                updateVal=$(obj).parents("td").find("input").val();
            }
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/updateRolePurchase",
                data:{
                    "id":id,
                    "fld":fld,
                    "val":updateVal,
                    "startDate":startDate,
                    "endDate":endDate
                },
                success:function(res) {
                    if(res.flg==true) {
                        if (fld == "ifUseThreshold") {
                            if (updateVal == "0") {
                                $("#" + fld).html('<span value="0">未启用</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                            } else {
                                $("#" + fld).html('<span value="1">启用</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                            }
                        } else if (fld == "perLimit" || fld == "threshold" || fld == "totalCount") {
                            $("#" + fld).html('<span>' + updateVal + '</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                        } else if (fld == "time") {
                            $("#" + fld).html('<span>' + startDate + '--' + endDate + '</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                        } else {
                            $("#" + fld).html('<span>' + updateVal + '</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                        }
                    }
                    else{
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:res.message});
                    }
                }
            });
        }
    </script>
</body>
</html>
