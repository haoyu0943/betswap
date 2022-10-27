<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of presell</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
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
                            <li><a href="<%=ctxPath%>/movie/rolePurchase?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.purchase">抢购规则设置</lan></span></a></li>
                            <li><a href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}" class="active-li"><span><lan set-lan="html:moviemenu.presell">预售规则设置</lan></span></a></li>
                            <!--<li><a href="<%=ctxPath%>/movie/fund?movieId=${movieId}"><span><lan set-lan="html:moviemenu.fund">影视基金设置</lan></span></a></li>-->
                        </c:if>
                        <div class="clear"></div>
                    </ul>
                </div>
            <div class="page-inner">
                    <table class="table table-bordered table-compare">
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepreselllist.days">预售天数</lan><input type="hidden" id="id" value="${id}"></td>
                            <td ><input id="days" type="text" width="20" value="${role.days}" class="form-control"></td>
                            <td class="compare-label"><lan set-lan="html:moviepreselllist.starttime">开始日期</lan></td>
                            <td ><input id="startDate"  class="form-control" type="text" width="20" autocomplete="off" onClick="laydate({format: 'YYYY-MM-DD',elem:'#startDate'})" value="${role.start_date}"></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepurchase.condition">参与条件是否启用</lan></td>
                            <td style="text-align: left">
                                <c:choose>
                                    <c:when test="${role.if_have_condition==1}">
                                        <input name="ifHaveCondition" type="radio" value="0"><lan set-lan="html:moviepresell.notenabled">未启用</lan>
                                        <input name="ifHaveCondition" type="radio" value="1" checked><lan set-lan="html:moviepresell.enabled">启用</lan>
                                    </c:when>
                                    <c:otherwise>
                                        <input name="ifHaveCondition" type="radio" value="0" checked><lan set-lan="html:moviepresell.notenabled">未启用</lan>
                                        <input name="ifHaveCondition" type="radio" value="1"><lan set-lan="html:moviepresell.enabled">启用</lan>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="compare-label"><lan set-lan="html:moviepresell.conditionstotalsales">参与条件销售总量</lan></td>
                            <td ><input id="conditionNumb"  class="form-control" type="text" width="20" value="${role.condition_numb}"></td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepresell.successrate">中签率</lan></td>
                            <td style="text-align: left"><input id="successRatio" type="text"  class="form-control d-ib" width="20" value="${role.success_ratio}">%</td>
                            <td class="compare-label"><lan set-lan="html:moviepresell.earnrate">日收益率</lan></td>
                            <td style="text-align: left"><input id="dailyRatio" type="text"  class="form-control d-ib" width="20" value="${role.daily_ratio}">%</td>
                        </tr>
                        <tr>
                            <td class="compare-label"><lan set-lan="html:moviepresell.totalsales">出售总量</lan></td>
                            <td style="text-align: left"><input id="totalSales" type="text"  class="form-control d-ib" width="20" value="${role.total_count}"></td>
                            <td class="compare-label"><lan set-lan="html:moviepresell.psnsales">个人限购量</lan></td>
                            <td style="text-align: left"><input id="perLimit" type="text"  class="form-control d-ib" width="20" value="${role.per_limit}"></td></td>
                        </tr>
                    </table>
                <div class="center">
                    <!--<button type="button" class="btn btn-sm btn-success" onclick="setDefault()">设置默认值</button>-->
                    <button type="button" class="btn btn-sm btn-success" onclick="saveRole()"><lan set-lan="html:button.save">保存</lan></button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="reback()"><lan set-lan="html:button.back">返回列表</lan></button>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../common/modpwd.jsp"%>

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


        function reback(){
            document.location.href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}";
        }



        function checkvalid(){
            var days=$("#days").val();
            var startDate=$("#startDate").val();
            //var ifHaveCondition=$('input:radio[name="ifHaveCondition"]:checked').val();
            var conditionNumb=$("#conditionNumb").val();
            var successRatio=$("#successRatio").val();
            var dailyRatio=$("#dailyRatio").val();
            var totalSales=$("#totalSales").val();
            var perLimit=$("#perLimit").val();
            if(startDate==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.starttime")+get_lan("error.notfill")});
                return false;
            }
            if(totalSales==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.totalsales")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(totalSales)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.totalsales")+get_lan("error.mustbenumber")});
                    return
                }
            }
            if(days==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("movielist.days")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(days)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("movielist.days")+get_lan("error.mustbenumber")});
                    return
                }
            }
            if(dailyRatio==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.earnrate")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(dailyRatio.replace(".",""))==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.earnrate")+get_lan("error.mustbenumber")});
                    return false;
                }
            }
            if(successRatio==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.successrate")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(successRatio)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.successrate")+get_lan("error.mustbenumber")});
                    return false;
                }
            }
            if(conditionNumb!=""){
                if(checkNumber(conditionNumb)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.totalsales")+get_lan("error.mustbenumber")});
                    return false;
                }

            }
            if(perLimit==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.psnsales")+get_lan("error.notfill")});
                return false;
            }
            else{
                if(checkNumber(perLimit)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepresell.psnsales")+get_lan("error.mustbenumber")});
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
            data["days"]=$("#days").val();
            data["startDate"]=$("#startDate").val();
            data["ifHaveCondition"]=$('input:radio[name="ifHaveCondition"]:checked').val();
            data["conditionNumb"]=$("#conditionNumb").val();
            data["successRatio"]=$("#successRatio").val();
            data["dailyRatio"]=$("#dailyRatio").val();
            data["totalSales"]=$("#totalSales").val();
            data["perLimit"]=$("#perLimit").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/savePresellRole",
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
    </script>
</body>
</html>
