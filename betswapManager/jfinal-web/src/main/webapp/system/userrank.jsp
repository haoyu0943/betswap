<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of userrank</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
</head>
<style>
.imageupload{text-align:center;position:relative;display: inline-block;}
.cover-wrap{display:none;position:fixed;left:0;top:0;width:100%;height:100%;background: rgba(0, 0, 0, 0.4);z-index: 10000000;text-align:center;}
.coverimgage{width:900px;height:600px;margin:100px auto;background-color:#FFFFFF;overflow: hidden;border-radius:4px;}
#clipArea{margin:10px;height: 520px;}
.wrapImage{height:56px;line-height:36px;text-align: center;padding-top:8px;}
#clipBtn{width:100px;height: 36px;border-radius: 4px;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;text-align: center;line-height: 36px;outline: none;border: none}
.btnupload{width:70px;height:32px;border-radius: 4px;cursor: pointer;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;    vertical-align: top;text-align:center;line-height:32px;outline:none;margin-left:5px;position:relative;    display: inline-block; cursor: pointer}
#file{cursor:pointer;opacity:0;filter:alpha(opacity=0);width:100%;height:100%;position:absolute;top:0;left:0;cursor: pointer;}
.xgdh{width: 50%; display: inline-block;float: left}
.ml-xs{margin-left: 5px}
.mr-ml{margin-right: 10px}
.ml-m{margin-left: 20px}
.pointer{cursor: pointer}
.shzhilc{height: 30px; line-height: 30px; background: #c5d0f3;border: 1px solid #92a2d6;padding: 0 5px;margin-bottom: 10px;}
.liucheg{margin-bottom: 5px; width: 33%;float: left}
.liucheg100{margin-bottom: 5px;}
.pnane{padding: 2px;background: #eaeaea;}
.nameshe{width: 75px; text-align: right;display: inline-block; color: #1763e0}
.cursor{cursor: pointer;}

#tabsF {
    float:left;
    width:100%;
    background:#efefef;
    font-size:100%;
    line-height:normal;
    border-bottom:1px solid #666;
}
#tabsF ul {
    margin:0;
    padding:10px 10px 0 50px;
    list-style:none;
}
#tabsF li {
    display:inline;
    margin:0;
    padding:0;
}
#tabsF a {
    float:left;
    background:url("<%=ctxPath%>/assets/img/tableftF.gif") no-repeat left top;
    margin:0;
    padding:0 0 0 4px;
    text-decoration:none;
}
#tabsF a span {
    float:left;
    display:block;
    background:url("<%=ctxPath%>/assets/img/tabrightF.gif") no-repeat right top;
    padding:7px 15px 4px 6px;
    color:#666;
}
/* Commented Backslash Hack hides rule from IE5-Mac \*/
#tabsF a span {float:none;}
/* End IE5-Mac hack */
#tabsF a:hover span {
    color:#FFF;
}
#tabsF a:hover {
    background-position:0% -42px;
}
#tabsF a:hover span {
    background-position:100% -42px;
}
</style>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:menu.member">会员等级条件</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th class="compare-label" rowspan="2"><lan set-lan="html:moviepurchase.membership">会员级别</lan></th>
                                <!--<th class="compare-label"><lan set-lan="html:userrank.shop.friend">商城直推有效好友</lan></th>-->
                                <th class="compare-label" rowspan="2"><lan set-lan="html:userrank.movie.friend">影视直推有效好友</lan></th>
                                <th class="compare-label" colspan="3"><lan set-lan="html:member.dlsygz">代理收益规则</lan></th>
                                <th class="compare-label" colspan="3"><lan set-lan="html:member.tbtjlgz">TBT奖励规则</lan></th>
                            </tr>
                            <tr>
                                <th class="compare-label"><lan set-lan="html:agent.income.requirements">个人收益要求</lan></th>
                                <th class="compare-label"><lan set-lan="html:agent.reward.proportion">奖励比例（直推一级）</lan></th>
                                <th class="compare-label"><lan set-lan="html:agent.monthly.pay">月薪</lan></th>
                                <th class="compare-label"><lan set-lan="html:agent.reward.proportion">奖励比例（直推一级）</th>
                                <th class="compare-label"><lan set-lan="html:roletbt.online.day">上线天数</lan></th>
                                <th class="compare-label"><lan set-lan="html:roletbt.release.ratio">每日释放比例</lan></th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td ><lan set-lan="html:member.ordinary">普通会员</lan></td>
                            <!--<td id="numb_ordinary_sc"><span>${mod_sc.numb_ordinary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_ordinary_xs"><span>${mod_xs.numb_ordinary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[0].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[0].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[0].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[0].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[0].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[0].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[0].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[0].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[0].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[0].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[0].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[0].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.gold">黄金会员</lan></td>
                            <!--<td id="numb_gold_sc"><span>${mod_sc.numb_gold}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_gold_xs"><span>${mod_xs.numb_gold}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[1].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[1].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[1].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[1].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[1].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[1].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[1].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[1].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[1].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[1].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[1].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[1].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.platinum">铂金级别</lan></td>
                            <!--<td id="numb_platinum_sc"><span>${mod_sc.numb_platinum}</span><a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_platinum_xs"><span>${mod_xs.numb_platinum}</span><a href="#"><i class="fa fa-pencil ml-5 color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[2].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[2].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[2].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[2].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[2].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[2].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[2].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[2].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[2].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[2].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[2].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[2].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.diamond">钻石会员</lan></td>
                            <!--<td id="numb_diamond_sc"><span>${mod_sc.numb_diamond}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_diamond_xs"><span>${mod_xs.numb_diamond}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[3].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[3].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[3].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[3].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[3].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[3].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[3].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[3].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[3].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[3].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[3].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[3].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.intermediate">中级合伙人</lan></td>
                            <!--<td id="numb_middle_sc"><span>${mod_sc.numb_middle}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_middle_xs"><span>${mod_xs.numb_middle}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[4].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[4].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[4].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[4].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[4].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[4].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[4].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[4].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[4].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[4].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[4].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[4].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.senior">高级合伙人</lan></td>
                            <!--<td id="numb_senior_sc"><span>${mod_sc.numb_senior}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_senior_xs"><span>${mod_xs.numb_senior}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[5].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[5].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[5].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[5].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[5].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[5].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[5].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[5].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[5].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[5].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[5].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[5].agent_rank}','2')"></i></a></td>
                        </tr>
                        <tr>
                            <td ><lan set-lan="html:member.directo">董事合伙人</lan></td>
                            <!--<td id="numb_director_sc"><span>${mod_sc.numb_director}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>-->
                            <td id="numb_director_xs"><span>${mod_xs.numb_director}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a></td>
                            <td ><span>${rolelist[6].threshold_income}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[6].agent_rank}','thresholdIncome')"></i></a></td>
                            <td ><span>${rolelist[6].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[6].agent_rank}','rewardsRatio')"></i></a></td>
                            <td ><span>${rolelist[6].salary}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,'${rolelist[6].agent_rank}','salary')"></i></a></td>
                            <td><span>${rolelist_xs[6].rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'rewardsRatio','${rolelist_xs[6].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[6].online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'onlineDays','${rolelist_xs[6].agent_rank}','2')"></i></a></td>
                            <td><span>${rolelist_xs[6].daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,'daliyReleaseRatio','${rolelist_xs[6].agent_rank}','2')"></i></a></td>
                        </tr>
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

        function edit(obj){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            var id=$(obj).parents("td").attr("id");
            $(parent$).html('<input class="form-control d-ib" id="input' + id + '" type="text" ' +
                    'value="' + text + '" style="width: 90px"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this)"></i>' +
                    '</a>');
        }

        function save(obj){
            var id;
            var fld=$(obj).parents("td").attr("id");
            var updateVal=$(obj).parents("td").find("input").val();
            //alert(fld);
            //alert(updateVal);
            if(checkNumber(updateVal)==false){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("error.person.number")});
                return;
            }
            var fldname=fld.substr(0,fld.length-3);
            if(fld.indexOf("sc")>0){
                id="${mod_sc.id}";
            }
            else{
                id="${mod_xs.id}";
            }
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/updateUserRank",
                data:{
                    "id":id,
                    "fldname":fldname,
                    "updateVal":updateVal,
                },
                success:function(res) {
                    $("#" + fld).html('<span>'+updateVal+'</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this)"></i></a>');
                }
            });
        }

        function agentedit(obj,agent_rank,fldname){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            $(parent$).html('<input class="form-control d-ib" type="text"  ' +
                'value="' + text + '" style="width: 90px"><a href="#">' +
                '<i class="fa fa-check ml-xs color-red" onclick="agentsave(this,\''+agent_rank+'\',\''+fldname+'\')"></i>' +
                '</a>');

        }

        function agentsave(obj,agent_rank,fld){
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
                        $(obj).parents("td").html('<span>'+updateVal+'</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,\''+agent_rank+'\',\''+fld+'\')"></i></a>');
                    }
                    else {
                        $(obj).parents("td").html('<span>'+updateVal+'</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="agentedit(this,\''+agent_rank+'\',\''+fld+'\')"></i></a>');
                    }
                }
            });
        }

        function tbtedit(obj,fld,agent_rank,datatype){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            $(parent$).html('<input class="form-control d-ib" type="text"  ' +
                'value="' + text + '" style="width: 90px"><a href="#">' +
                '<i class="fa fa-check ml-xs color-red" onclick="tbtsave(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i>' +
                '</a>');

        }

        function tbtsave(obj,fld,agent_rank,datatype){
            var updateVal=$(obj).parents("td").find("input").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/updateRoleTbt",
                data:{
                    "agentRank":agent_rank,
                    "fld":fld,
                    "val":updateVal,
                    "dataType":datatype
                },
                success:function(res) {
                    if(fld=="rewardsRatio"||fld=="daliyReleaseRatio"){
                        $(obj).parents("td").html('<span>'+updateVal+'</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i></a>');
                    }
                    else {
                        $(obj).parents("td").html('<span>'+updateVal+'</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="tbtedit(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i></a>');
                    }
                }
            });
        }
    </script>
</body>
</html>
