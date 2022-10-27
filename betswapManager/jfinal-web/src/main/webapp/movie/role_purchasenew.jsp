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
                            <td colspan="2" align="left">
                                <input type="hidden" id="id" value="${id}">
                                <input style="width: 150px" value="${role.start_date}" type="text" id="startDate" onClick="laydate({format: 'YYYY-MM-DD hh',elem: '#startDate',type:'time'})">-
                                <input style="width: 150px" value="${role.end_date}" type="text" id="endDate" onClick="laydate({format: 'YYYY-MM-DD hh',elem: '#endDate',type:'time'})">
                            </td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.restrictions">限购数</lan></td>
                            <td colspan="2" align="left">
                                <input id="perLimit"  type="text" style="width: 150px" value="${role.per_limit}">
                            </td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.condition">参与条件是否启用</lan></td>
                            <td colspan="2" align="left">
                                <c:choose>
                                    <c:when test="${role.if_use_threshold==1}">
                                        <input name="ifUseThreshold" type="radio" value="0"><lan set-lan="html:moviepresell.notenabled">未启用</lan>
                                        <input name="ifUseThreshold" type="radio" value="1" checked><lan set-lan="html:moviepresell.enabled">启用</lan>
                                    </c:when>
                                    <c:otherwise>
                                        <input name="ifUseThreshold" type="radio" value="0" checked><lan set-lan="html:moviepresell.notenabled">未启用</lan>
                                        <input name="ifUseThreshold" type="radio" value="1"><lan set-lan="html:moviepresell.enabled">启用</lan>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.threshold">参与条件阀值</lan></td>
                            <td colspan="2" align="left">
                                <input id="threshold"  type="text" style="width: 150px" value="${role.threshold}">
                            </td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepresell.totalsales">出售总量</lan></td>
                            <td colspan="2" align="left">
                                <input id="totalCount"  style="width: 150px" type="text" value="${role.total_count}">
                            </td>
                        </tr>

                        <tr bgcolor="#E0E0E0">
                            <td class="compare-label"><lan set-lan="html:moviepurchase.membership">会员级别</lan></td>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.rate">购票成功率</lan></td>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.daily">日票房分红</lan></td>
                        </tr>
                        <tr>
                            <td><lan set-lan="html:member.ordinary">普通会员</lan></td>
                            <td>
                                <input id="successRatioOrdinary"  type="text" style="width: 150px" value="${role.success_ratio_ordinary}">%
                            </td>
                            <td>
                                <input id="dailyRatioOrdinary"  type="text" style="width: 150px" value="${role.daily_ratio_ordinary}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.gold">黄金会员</lan></td>
                            <td>
                                <input id="successRatioGold"  type="text" style="width: 150px" value="${role.success_ratio_gold}">%
                            </td>
                            <td>
                                <input id="dailyRatioGold"   type="text" style="width: 150px" value="${role.daily_ratio_gold}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.platinum">铂金级别</lan></td>
                            <td>
                                <input id="successRatioPlatinum"  type="text" style="width: 150px" value="${role.success_ratio_platinum}">%
                            </td>
                            <td>
                                <input id="dailyRatioPlatinum"  type="text" style="width: 150px" value="${role.daily_ratio_platinum}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.diamond">钻石会员</lan></td>
                            <td>
                                <input id="successRatioDiamond"  type="text" style="width: 150px" value="${role.success_ratio_diamond}">%
                            </td>
                            <td>
                                <input id="dailyRatioDiamond"  type="text" style="width: 150px" value="${role.daily_ratio_diamond}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.intermediate">中级合伙人</lan></td>
                            <td>
                                <input id="successRatioMiddle"  type="text" style="width: 150px" value="${role.success_ratio_middle}">%
                            </td>
                            <td>
                                <input id="dailyRatioMiddle"  type="text" style="width: 150px" value="${role.daily_ratio_middle}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.senior">高级合伙人</lan></td>
                            <td>
                                <input id="successRatioSenior"  type="text" style="width: 150px" value="${role.success_ratio_senior}">%
                            </td>
                            <td >
                                <input id="dailyRatioSenior"  type="text" style="width: 150px" value="${role.daily_ratio_senior}">%
                            </td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.directo">董事合伙人</lan></td>
                            <td>
                                <input id="successRatioDirector"  type="text" style="width: 150px" value="${role.success_ratio_director}">%
                            </td>
                            <td>
                                <input id="dailyRatioDirector"  type="text" style="width: 150px" value="${role.daily_ratio_director}">%
                            </td>
                        </tr>

                    </table>
                <div class="center">
                    <button type="button" class="btn btn-sm btn-success" onclick="setDefault()">设置默认值</button>
                    <button type="button" class="btn btn-sm btn-success" onclick="saveRole()"><lan set-lan="html:button.save">保存</lan></button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="reback()"><lan set-lan="html:button.back">返回列表</lan></button>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../common/modpwd.jsp"%>
    <script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
    <script>
        initlan();
        var vlan = $.tmCookie.getCookie('lan');

        $(function(){
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
        });

        function setDefault(){
            $("#successRatioOrdinary").val("5");
            $("#dailyRatioOrdinary").val("0.5");
            $("#successRatioGold").val("10");
            $("#dailyRatioGold").val("1");
            $("#successRatioPlatinum").val("15");
            $("#dailyRatioPlatinum").val("1.5");
            $("#successRatioDiamond").val("20");
            $("#dailyRatioDiamond").val("2");
            $("#successRatioMiddle").val("25");
            $("#dailyRatioMiddle").val("2.5");
            $("#successRatioSenior").val("30");
            $("#dailyRatioSenior").val("3");
            $("#successRatioDirector").val("35");
            $("#dailyRatioDirector").val("3.5");
        }

        function checkvalid(){
            var perLimit=$("#perLimit").val();
            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
            var threshold=$("#threshold").val();
            var totalCount=$("#totalCount").val();
            var successRatioOrdinary=$("#successRatioOrdinary").val();
            var dailyRatioOrdinary=$("#dailyRatioOrdinary").val();
            var successRatioGold=$("#successRatioGold").val();
            var dailyRatioGold=$("#dailyRatioGold").val();
            var successRatioPlatinum=$("#successRatioPlatinum").val();
            var dailyRatioPlatinum=$("#dailyRatioPlatinum").val();
            var successRatioDiamond=$("#successRatioDiamond").val();
            var dailyRatioDiamond=$("#dailyRatioDiamond").val();
            var successRatioMiddle=$("#successRatioMiddle").val();
            var dailyRatioMiddle=$("#dailyRatioMiddle").val();
            var successRatioSenior=$("#successRatioSenior").val();
            var dailyRatioSenior=$("#dailyRatioSenior").val();
            var successRatioDirector=$("#successRatioDirector").val();
            var dailyRatioDirector=$("#dailyRatioDirector").val();

            var perLimit=$("#perLimit").val();
            if(startDate==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.starttime")+get_lan("error.notfill")});
                return false;
            }
            if(endDate==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.endtime")+get_lan("error.notfill")});
                return false;
            }
            if(perLimit==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.restrictions")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(perLimit)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.restrictions")+get_lan("error.mustbenumber")});
                    return
                }
            }
            if(threshold!=""){
                if(checkNumber(threshold)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.threshold")+get_lan("error.mustbenumber")});
                    return false;
                }

            }
            if(totalCount==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.totalsales")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(totalCount)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.totalsales")+get_lan("error.mustbenumber")});
                    return
                }
            }

            if(dailyRatioOrdinary==""||dailyRatioGold==""||dailyRatioPlatinum==""||dailyRatioDiamond==""||dailyRatioMiddle==""||dailyRatioSenior==""||dailyRatioDirector==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.daily")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(dailyRatioOrdinary.replace(".",""))==false||
                    checkNumber(dailyRatioGold.replace(".",""))==false||
                    checkNumber(dailyRatioPlatinum.replace(".",""))==false||
                    checkNumber(dailyRatioDiamond.replace(".",""))==false||
                    checkNumber(dailyRatioMiddle.replace(".",""))==false||
                    checkNumber(dailyRatioSenior.replace(".",""))==false||
                    checkNumber(dailyRatioDirector.replace(".",""))==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.daily")+get_lan("error.mustbenumber")});
                    return false;
                }
            }
            if(successRatioOrdinary==""||successRatioGold==""||successRatioPlatinum==""||successRatioDiamond==""||successRatioMiddle==""||successRatioSenior==""||successRatioDirector==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.rate")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(successRatioOrdinary)==false||
                    checkNumber(successRatioGold)==false||
                    checkNumber(successRatioPlatinum)==false||
                    checkNumber(successRatioDiamond)==false||
                    checkNumber(successRatioMiddle)==false||
                    checkNumber(successRatioSenior)==false||
                    checkNumber(successRatioDirector)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepurchase.rate")+get_lan("error.mustbenumber")});
                    return false;
                }
            }
            return true;
        }

        function saveRole(){
            if(checkvalid()==false){
                return;
            }
            var data={};
            data["id"]=$("#id").val();
            data["movieid"]="${movieId}";
            data["perLimit"]=$("#perLimit").val();
            data["endDate"]=$("#endDate").val();
            data["startDate"]=$("#startDate").val();
            data["ifUseThreshold"]=$('input:radio[name="ifUseThreshold"]:checked').val();
            data["threshold"]=$("#threshold").val();
            data["totalCount"]=$("#totalCount").val();
            data["successRatioOrdinary"]=$("#successRatioOrdinary").val();
            data["dailyRatioOrdinary"]=$("#dailyRatioOrdinary").val();
            data["successRatioGold"]=$("#successRatioGold").val();
            data["dailyRatioGold"]=$("#dailyRatioGold").val();
            data["successRatioPlatinum"]=$("#successRatioPlatinum").val();
            data["dailyRatioPlatinum"]=$("#dailyRatioPlatinum").val();
            data["successRatioDiamond"]=$("#successRatioDiamond").val();
            data["dailyRatioDiamond"]=$("#dailyRatioDiamond").val();
            data["successRatioMiddle"]=$("#successRatioMiddle").val();
            data["dailyRatioMiddle"]=$("#dailyRatioMiddle").val();
            data["successRatioSenior"]=$("#successRatioSenior").val();
            data["dailyRatioSenior"]=$("#dailyRatioSenior").val();
            data["successRatioDirector"]=$("#successRatioDirector").val();
            data["dailyRatioDirector"]=$("#dailyRatioDirector").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/savePurchaseRole",
                data:data,
                success:function(data){
                    if(data.flg == true){
                        reback();
                    }
                    else{
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:data.message});
                    }
                }
            });
        }

        function reback(){
            document.location.href="<%=ctxPath%>/movie/rolePurchase?movieId=${movieId}";
        }
    </script>
</body>
</html>
