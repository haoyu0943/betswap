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
                <div id="tabsF" class="ejdhBox">
                    <ul class="ejcdUl">
                        <li><a href="<%=ctxPath%>/system/dsshoplist" class="active-li"><lan set-lan="html:dsshoplist.pending.shops">待审商铺</lan></a></li>
                        <li><a href="<%=ctxPath%>/system/shoplist" ><lan set-lan="html:dsshoplist.approved.shops">已审商铺</lan></a></li>
                        <div class="clear"></div>
                    </ul>
                </div>
            <div class="page-inner">
                    <div class="hhBox">
                       <span class="wwmc"><lan set-lan="html:movielist.keyword">关键词：</lan></span>
                       <input class="form-control" type="text" id="gjc" style="width: 150px;display: inline-block">
                    </div>
                    <button type="button" class="btn btn-sm btn-success" onclick="qryPageDsShop()"><lan set-lan="html:movielist.query">查询</lan></button>
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
        qryPageDsShop();

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
            //查询默认,很奇怪，这里调用两次才有

        });

        function qryPageDsShop(){
            var data = {};
            data["gjc"] = $("#gjc").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {
                    var showstr="";
                    showstr+="<table class=\"table table-striped table-bordered table-hover\">";
                    showstr+="<thead>";
                    showstr+="<tr>";
                        showstr+="<th colspan=\"3\" class=\"text-left\" style='padding-left: 5px;padding-right: 5px'>"+get_lan("application.no")+"："+row["record_id"]+"<span class=\"float-right\">"+$.tmDate._toString(row["create_time"],'yyyy-MM-dd HH:mm')+"</span></th>";
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
                    showstr+="<td class=\"td-pass\" width='180px'>"+
                             "<button type=\"button\" class=\"btn btn-xs btn-success\" onclick=\"examine('"+row["record_id"]+"')\">"+get_lan("button.verify")+"</button></td>";
                    showstr+="</tr>";
                    showstr+="</tbody>";
                    showstr+=" </table>";
                    return showstr;
                }},
            ];
            getTableDataServer("infoTable",ctxPath+"/system/qryPageDsShop",columns,data,false);
        }

        function examine(recordid){
            var url="<%=ctxPath%>/system/shopDetail?checkflg=1&recordid="+recordid;
            document.location.href=url;
        }
    </script>
</body>
</html>
