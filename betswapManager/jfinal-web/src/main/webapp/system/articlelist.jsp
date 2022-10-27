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
   <style>
    .t-rwmc {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 1px;
    word-break: break-all;
    text-align: left !important;
    }
    .dataTables_length{
    display: none}

    .text-left{text-align: left !important;}
    .text-right{text-align: right !important;}
    .float-left{float: left}
    .float-right{float: right}
    </style>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:msginput.system.article">信息贴文</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <div class="hhBox">
                       <span class="wwmc"><lan set-lan="html:movielist.keyword">关键词：</lan></span>
                       <input class="form-control" type="text" id="gjc" style="width: 150px;display: inline-block">
                    </div>
                    <div class="hhBox" style="width: 800px;display: none">
                        <span class="wwmc">发布时间：</span>
                        <div class="layui-input-block" id="time">
                        <div class="layui-input-inline">
                            <input type="text" autocomplete="off" id="startDate" class="form-control" style="width: 150px">
                        </div>
                        <div class="layui-form-mid">-</div>
                        <div class="layui-input-inline">
                            <input type="text" autocomplete="off" id="endDate" class="form-control" style="width: 150px">
                        </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-sm btn-success" onclick="clearTj()"><lan set-lan="html:movielist.clear">清空</lan></button>
                    <button type="button" class="btn btn-sm btn-success" onclick="qryPageArticle()"><lan set-lan="html:movielist.query">查询</lan></button>
                    <button type="button" class="btn btn-sm btn-info" onclick="addArticle()"><lan set-lan="html:button.new">新增</lan></button>
                    <div class="clear"></div>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="" id="infoTable" >
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
        qryPageArticle();

        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({
                elem: '#time'
                ,range: ['#startDate', '#endDate']
            });
        });

        function clearTj(){
            $("#gjc").val("");
            $("#startDate").val("");
            $("#endDate").val("");
        }

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

        function qryPageArticle(){
            var data = {};
            data["gjc"] = $("#gjc").val();
            data["startDate"] = $("#startDate").val();
            data["endDate"] = $("#endDate").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {
                    var showstr="";
                    showstr+="<table class=\"table table-striped table-bordered table-hover\">";
                    showstr+="<thead>";
                    showstr+="<tr>";
                        showstr+="<th colspan=\"3\" class=\"text-left\" style='padding-left: 5px;padding-right: 5px'>"+get_lan("msglist.messagenumber")+"："+row["id"]+"<span class=\"float-right\">"+$.tmDate._toString(row["create_time"],'yyyy-MM-dd HH:mm')+"</span></th>";
                    showstr+="</tr>";
                    showstr+="</thead>";
                    showstr+="<tbody>";
                    showstr+="<tr>";
                    showstr+="<td class=\"th-price\" width='260px'>"+
                             "<img src=\""+serviceUrl+"/"+row["cover"]+"\" alt=\"cart\" width='160px' height='90px'></td>";
                    showstr+="<td class=\"th-details\" width='900px'>";
                    showstr+="<strong><p style='font-size:18px;text-align: center'>"+row["title"]+"</p></strong>";
                    if(row["subtitle"]!=null){
                        showstr+="<strong><p style='font-size:14px;text-align: center'>"+row["subtitle"]+"</p></strong>";
                    }
                    if(row["type_flag"]==1){
                        showstr+="<small style='text-align: left;display: inline-block;padding:0 5px;'>"+row["web_url"]+"</small>";
                    }else{
                        if(row["contenttxt"].length>400){
                            showstr+="<small style='text-align: left;display: inline-block;padding:0 5px;'>"+row["contenttxt"].substr(0,400)+"...</small>";
                        }
                        else{
                            showstr+="<small style='text-align: left;display: inline-block;padding:0 5px;'>"+row["contenttxt"]+"</small>";
                        }
                    }
                    showstr+="</td>";
                    showstr+="<td class=\"td-pass\" width='180px'>"+
                             "<button type=\"button\" class=\"btn btn-xs btn-success\" onclick=\"editArticle('"+row["id"]+"')\">"+get_lan("button.modify")+"</button>&nbsp;"+
                             "<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick=\"delArticle('"+row["id"]+"')\">"+get_lan("button.delete")+"</button></td>";
                    showstr+="</tr>";
                    showstr+="</tbody>";
                    showstr+=" </table>";
                    return showstr;
                }},
            ];
            getTableDataServer("infoTable",ctxPath+"/system/qryPageArticle",columns,data,false);
        }


        function delArticle(id){
        	yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
        		if(ok){
        			$.ajax({
                		type:"post",
                		url:"<%=ctxPath%>/system/delArticle",
                		data:{"id":id},
                		success:function(data){
                			if(data.flag == true){
                				document.location.reload();
                			}
                			else{
                				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("message.delete.fail")});
                			}
                		}
                	});
        		}
        	}});
        }
        
        function addArticle(){
           	document.location.href="<%=ctxPath%>/system/crtOreditArticle";
        }

        function editArticle(id){
            window.location.href = "<%=ctxPath%>/system/crtOreditArticle?id="+id;
        }
    </script>
</body>
</html>
