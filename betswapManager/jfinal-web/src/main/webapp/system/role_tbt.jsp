<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of tbt</title>
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
            <div class="pageheader"><lan set-lan="html:menu.tbt">TBT规则</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="compare-label"><lan set-lan="html:moviepurchase.membership">会员级别</lan></th>
                            <th class="compare-label"><lan set-lan="html:agent.reward.proportion">奖励比例（直推一级）</th>
                            <th class="compare-label"><lan set-lan="html:roletbt.online.day">上线天数</lan></th>
                            <th class="compare-label"><lan set-lan="html:roletbt.release.ratio">每日释放比例</lan></th>
                        </tr>

                        <tr style="display: none">
                            <th class="compare-label" colspan="4"><lan set-lan="html:roletbt.shop">商城</lan></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="role" items="${rolelist_sc}" varStatus="item">
                        <tr style="display: none">
                            <td >${role.agent_rank_name}</td>
                            <td><span>${role.rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'rewardsRatio','${role.agent_rank}','1')"></i></a></td>
                            <td><span>${role.online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'onlineDays','${role.agent_rank}','1')"></i></a></td>
                            <td><span>${role.daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'daliyReleaseRatio','${role.agent_rank}','1')"></i></a></td>
                        </tr>
                        </c:forEach>
                        <tr style="display: none">
                            <th class="compare-label" colspan="4" style="background: #ececec"><lan set-lan="html:roletbt.movie">影视</lan></th>
                        </tr>
                        <c:forEach var="role" items="${rolelist_xs}" varStatus="item">
                            <tr>
                                <td >${role.agent_rank_name}</td>
                                <td><span>${role.rewards_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'rewardsRatio','${role.agent_rank}','2')"></i></a></td>
                                <td><span>${role.online_days}</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'onlineDays','${role.agent_rank}','2')"></i></a></td>
                                <td><span>${role.daliy_release_ratio}</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'daliyReleaseRatio','${role.agent_rank}','2')"></i></a></td>
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
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        });


        function edit(obj,fld,agent_rank,datatype){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            $(parent$).html('<input class="form-control d-ib" type="text"  ' +
                    'value="' + text + '" style="width: 90px"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i>' +
                    '</a>');

        }

        function save(obj,fld,agent_rank,datatype){
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
                        $(obj).parents("td").html('<span>'+updateVal+'</span>%<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i></a>');
                    }
                    else {
                        $(obj).parents("td").html('<span>'+updateVal+'</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\''+fld+'\',\''+agent_rank+'\',\''+datatype+'\')"></i></a>');
                    }
                }
            });
        }
    </script>
</body>
</html>
