<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of agent</title>
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
            <div class="pageheader"><lan set-lan="html:agent.rule.settings">电影投资代理规则设置</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="compare-label"><lan set-lan="html:moviepurchase.membership">会员级别</lan></td>
                            <th class="compare-label"><lan set-lan="html:agent.income.requirements">个人收益要求</lan></th>
                            <th class="compare-label"><lan set-lan="html:agent.reward.proportion">奖励比例（直推一级）</lan></th>
                            <th class="compare-label"><lan set-lan="html:agent.monthly.pay">月薪</lan></th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="role" items="${rolelist}" varStatus="item">
                            <tr>
                                <td>${role.agent_rank_name}</td>
                                <td ><span>${role.threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${role.agent_rank}','thresholdIncome')"></i></a></td>
                                <td ><span>${role.rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${role.agent_rank}','rewardsRatio')"></i></a></td>
                                <td ><span>${role.salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${role.agent_rank}','salary')"></i></a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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


        function edit(obj,agent_rank,fldname){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            $(parent$).html('<input class="form-control d-ib" type="text"  ' +
                    'value="' + text + '" style="width: 90px"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this,\''+agent_rank+'\',\''+fldname+'\')"></i>' +
                    '</a>');

        }

        function save(obj,agent_rank,fld){
            var updateVal=$(obj).parents("td").find("input").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/updateRoleAgent",
                data:{
                    "agentRank":agent_rank,
                    "fld":fld,
                    "val":updateVal,
                },
                success:function(res) {
                    if(fld=="rewardsRatio"){
                        $(obj).parents("td").html('<span>'+updateVal+'</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\''+agent_rank+'\',\''+fld+'\')"></i></a>');
                    }
                    else {
                        $(obj).parents("td").html('<span>'+updateVal+'</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\''+agent_rank+'\',\''+fld+'\')"></i></a>');
                    }
                }
            });
        }
    </script>
</body>
</html>
