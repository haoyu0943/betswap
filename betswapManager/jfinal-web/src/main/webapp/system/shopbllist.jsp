<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>movielist</title>
</head>
<body>
<div id="wrapper">
    <%@ include file="../common/head.jsp"%>
    <div class="navbar-default navbar-side" role="navigation">
        <%@ include file="../common/menu.jsp"%>
    </div>
    <div id="page-wrapper">
        <div class="pageheader"><lan set-lan="html:shopbllist.shop.ratio.adjustment">店铺商城比率调整</lan><i class="fa fa-caret-down ml-xs"></i></div>
        <div class="page-inner">
            <div class="hhBox">
                <span class="wwmc"><lan set-lan="html:movielist.starlevel">店铺星级：</lan></span>
                <select id="dpxj" style="width: 100px" class="form-control">
                    <option value="">all</option>
                    <option value="5">5</option>
                    <option value="4">4</option>
                    <option value="3">3</option>
                    <option value="2">2</option>
                    <option value="1">1</option>
                </select>
            </div>
            <div class="hhBox">
                <span class="wwmc"><lan set-lan="html:movielist.keyword">关键词：</lan></span>
                <input class="form-control" type="text" id="gjc" style="width: 150px;display: inline-block">
            </div>
            <button type="button" class="btn btn-sm btn-success" onclick="qryPageShop()"><lan set-lan="html:movielist.query">查询</lan></button>
            <div class="clear"></div>
        </div>
        <div class="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive">
                        <table class="" id="infoTable">
                            <thead>
                            <tr>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/modpwd.jsp"%>
<script>
    initlan();
    var $thisTable = $("#infoTable");
    //initTable("infoTable");
    var vlan = $.tmCookie.getCookie('lan');
    qryPageShop();

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#time'
            ,range: ['#startDate', '#endDate']
        });
    });

    $(function(){
        $(".xiugmm").on('click',function(){
            $(".zhezhao1").show();
        });
        $(".close2").on('click',function(){
            $(".zhezhao1").hide();
        });
        $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
    });

    function qryPageShop(){
        var data = {};
        data["gjc"] = $("#gjc").val();
        data["dpxj"]=$("#dpxj").val();
        var columns =[
            {data: "rownum",render: function(data, type, row, meta) {
                    var showstr="";
                    showstr+="<table class=\"table table-striped table-bordered table-hover\">";
                    showstr+="<thead>";
                    showstr+="<tr>";
                    showstr+="<th colspan=\"3\" class=\"text-left\" style='padding-left: 5px;padding-right: 5px'>"+get_lan("shopbllist.shop.no")+"："+row["shop_id"];
                    var starlevel=0;
                    if(row["star_level"]!=null){
                        starlevel=parseInt(row["star_level"]);
                    }
                    showstr+="&nbsp;&nbsp;"+get_lan("movielist.starlevel")+"<span style='color:red;font-size: 14px'>"
                    for(var i=0;i<starlevel;i++){
                        showstr+="★";
                    }
                    for(var i=5;i>starlevel;i--){
                        showstr+="☆";
                    }
                    showstr+="</span>"
                    showstr+="<span class=\"float-right\">"+$.tmDate._toString(row["create_time"],'yyyy-MM-dd HH:mm')+"</span></th>";
                    showstr+="</tr>";
                    showstr+="</thead>";
                    showstr+="<tbody>";
                    showstr+="<tr>";
                    showstr+="<td class=\"th-price\" width='260px'>"+
                        "<img src=\""+serviceUrl+"/"+row["store_cover"]+"\" alt=\"cart\" width='160px' height='90px'></td>";
                    showstr+="<td class=\"th-details\" width='900px'>";
                    showstr+="<strong><p style='font-size:18px;text-align: center'>"+row["name"]+"</p></strong>";
                    if(row["store_introduction"]!=null) {
                        if (row["store_introduction"].length > 400) {
                            showstr += "<small style='text-align: left;display: inline-block;padding:0 5px;'>" + row["store_introduction"].substr(0, 400) + "...</small>";
                        } else {
                            showstr += "<small style='text-align: left;display: inline-block;padding:0 5px;'>" + row["store_introduction"] + "</small>";
                        }
                    }
                    else{
                        showstr += "<small style='text-align: left;display: inline-block;padding:0 5px;'>"+get_lan("table.no.data")+"</small>";
                    }
                    showstr+="</td>";
                    showstr+="<td class=\"td-pass\" width='180px' id=\"td"+row["shop_id"]+"\"><span style='font-size: 14px'>"+row["platform_divide"]+
                        "</span>%<a href=\"#\"><i class=\"fa fa-pencil ml-xs color-gray\" onclick=\"edit('"+row["shop_id"]+"','"+row["platform_divide"]+"')\"></i></a></td>";
                    showstr+="</tr>";
                    showstr+="</tbody>";
                    showstr+=" </table>";
                    return showstr;
                }},
        ];
        getTableDataServer("infoTable",ctxPath+"/system/qryPageTrueShop",columns,data,false);
    }

    function edit(shopid,curval){
        /*
        var parent$=$(obj).parents("td");
        var text=$(parent$).find('span').text();
        var id=$(obj).parents("td").attr("id");
        $(parent$).html('<input id="' + id + 'Btn" type="text"  ' +
            'value="' + text + '" style="width: 90px"><a href="#">' +
            '<i class="fa fa-check ml-5 color-red" onclick="save(this)"></i>' +
            '</a>');
            */

        $("#td"+shopid).html('<input class="form-control d-ib" id="input' + shopid + '" type="text"  ' +
            'value="' + curval + '" style="width: 90px"><a href="#">' +
            '<i class="fa fa-check ml-xs color-red" onclick="save(\''+shopid+'\')"></i>' +
            '</a>');

    }

    function save(shopid){
        var updateVal=$("#input"+shopid).val();
        if(updateVal==""){
            yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.blank")});
            return;
        }
        else{
            if(checkNumber(updateVal.replace(".",""))==false){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.number")});
                return;
            }
            else{
                //var tf=float.Parse(gwpttc);
                var tf=new Decimal(updateVal)
                if(tf>10||tf<0){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.unreasonable")});
                    return;
                }
            }
        }
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/system/updateShopDivide",
            data:{
                "blval":updateVal,
                "shopid":shopid
            },
            success:function(res) {
               $("#td" + shopid).html("<span style='font-size: 14px'>"+updateVal+"</span>%<a href=\"#\"><i class=\"fa fa-pencil ml-5 color-gray\" onclick=\"('"+shopid+"','"+updateVal+"')\"></i></a>");
            }
        });
    }
</script>
</body>
</html>
