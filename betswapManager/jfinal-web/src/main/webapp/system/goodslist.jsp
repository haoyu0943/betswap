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
                <div id="tabsF"class="ejdhBox">
                    <ul class="ejcdUl">
                        <li><a href="<%=ctxPath%>/system/dsgoodslist"><lan set-lan="html:dsgoodslist.pending.goods">待审商品</lan></a></li>
                        <li><a href="<%=ctxPath%>/system/goodslist" class="active-li"><lan set-lan="html:dsgoodslist.approved.goods">已审商品</lan></a></li>
                        <div class="clear"></div>
                    </ul>
                </div>
            <div class="page-inner">
                    <div class="hhBox">
                       <span class="wwmc"><lan set-lan="html:movielist.keyword">关键词：</lan></span>
                       <input class="form-control" type="text" id="gjc" style="width: 150px;display: inline-block">
                    </div>
                    <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:movielist.category">类别：</lan></span>
                        <select class="form-control" id="gcategory" style="width: 150px;display: inline-block">
                            <option value="">请选择</option>
                            <c:forEach items="${goodsSeries}" var="value">
                                <option value="${value.code}">${value.dic_describe}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="button" class="btn btn-sm btn-success" onclick="qryPageGoods()"><lan set-lan="html:movielist.query">查询</lan></button>
                    <!--<button type="button" class="btn btn-sm btn-success" onclick="test()">md5测试</button>-->
                    <div class="clear"></div>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="" id="infoTable">
                                <thead>
                                <tr><th></th></tr>
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
        qryPageGoods();

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

        function qryPageGoods(){
            var data = {};
            data["gjc"] = $("#gjc").val();
            data["gcategory"]=$("#gcategory").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {
                    var showstr="";
                        showstr+="<table class=\"table table-striped table-bordered table-hover\">";
                        showstr+="<thead>";
                        showstr+="<tr>";
                        showstr+="<th colspan=\"4\" class=\"text-left\" style='padding-left: 5px;padding-right: 5px'>"+get_lan("application.no")+"："+row["record_id"]+"<span class=\"float-right\">"+$.tmDate._toString(row["create_time"],'yyyy-MM-dd HH:mm')+"</span></th>";
                        showstr+="</tr>";
                        showstr+="</thead>";
                        showstr+="<tbody>";
                        showstr+="<tr>";
                        showstr+="<td class=\"th-price\" width='200px'>"+
                            "<img src=\""+serviceUrl+"/"+row["cover"]+"\" alt=\"cart\" width='160px' height='90px'></td>";
                        showstr+="<td class=\"text-left\" width='160px' valign='middle'><br>"+
                            "<i class=\"fa fa-share-square mr-xs\"></i><span style=\"font-weight: bold\">"+get_lan("dsgoodslist.brand")+":</span>";
                        if(row["brand"]!=null) {
                            showstr+=row["brand"];
                        }
                        showstr+="<br>";
                        showstr+="<i class=\"fa fa-share-square mr-xs\"></i><span style=\"font-weight: bold\">"+get_lan("dsgoodslist.category")+":</span>";
                        if(row["series_name"]!=null) {
                            showstr+=row["series_name"];
                        }
                        showstr+="<br>";
                        showstr+="<i class=\"fa fa-share-square mr-xs\"></i><span style=\"font-weight: bold\">"+get_lan("dsgoodslist.unit")+":</span>";
                        if(row["measurement"]!=null) {
                            showstr+=row["measurement"];
                        }
                        showstr+="</td>";
                        showstr+="<td class=\"th-details\" width='720px'>";
                    showstr+="<strong><p style='font-size:18px;text-align: center'>"+row["goods_name"]+"</p></strong>";
                    if(row["introduce"].length>400){
                        showstr+="<small style='text-align: left;display: inline-block;padding:0 5px;'>"+row["introduce"].substr(0,400)+"...</small>";
                    }
                    else{
                        showstr+="<small style='text-align: left;display: inline-block;padding:0 5px;'>"+row["introduce"]+"</small>";
                    }
                    showstr+="</td>";
                    showstr+="<td class=\"td-pass\" width='180px'>"+
                             "<button type=\"button\" class=\"btn btn-xs btn-success\" onclick=\"showdet('"+row["record_id"]+"')\">"+get_lan("button.query")+"</button></td>";
                    showstr+="</tr>";
                    showstr+="</tbody>";
                    showstr+=" </table>";
                    return showstr;
                }},
            ];
            getTableDataServer("infoTable",ctxPath+"/system/qryPageGoods",columns,data,false);
        }

        function showdet(recordid){
            var url="<%=ctxPath%>/system/goodsDetail?checkflg=2&recordid="+recordid;
            document.location.href=url;
        }

        function test(){
            var s="qryUserFromblockchain";
            var result = hex_md5(encodeURIComponent(s));
            console.log(result);
            alert("ok");
        }
    </script>
</body>
</html>
