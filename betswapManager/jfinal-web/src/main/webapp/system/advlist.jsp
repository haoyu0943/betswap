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
            <div class="pageheader"><lan set-lan="html:menu.adv">轮播广告设置</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:movielist.type">类型：</lan></span>
                        <span>
                            <select class="form-control" style="width: 150px;display: inline-block" id="type">
                                 <option value=""></option>
                              <c:forEach var="advtype" items="${advtypelist}" varStatus="index">
                                  <option value="${advtype.code}">${advtype.dic_describe}</option>
                              </c:forEach>
                            </select>
                         </span>
                    </div>
                    <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:movielist.keyword">关键字：</lan></span>
                        <input class="form-control " type="text" id="gjc" style="width: 150px;display: inline-block">
                    </div>
                    <button type="button" class="btn btn-sm btn-success" onclick="qryPageAdv()"><lan set-lan="html:movielist.query">查询</lan></button>
                    <button type="button" class="btn btn-sm btn-info" onclick="addAdv()"><lan set-lan="html:button.new">新增</lan></button>
                    <div class="clear"></div>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="infoTable">
                                <thead>
                                <tr>
                                    <th style="width: 90px"><lan set-lan="html:movielist.order">序号</lan></th>
                                    <th ><lan set-lan="html:movielist.advtitle">标题</lan></th>
                                    <th style="width: 140px"><lan set-lan="html:movielist.type">类型</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:movielist.issuetime">发布时间</lan></th>
                                    <th style="width: 120px"><lan set-lan="html:movielist.puttop">置顶</lan></th>
                                    <th style="width: 280px"><lan set-lan="html:movielist.operation">操作</lan></th>
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
        //initselect();
        qryPageAdv();

        $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);

        });
        /*
        function initselect(){
            if(vlan=="cn") {
                $("#type").append("<option value=\"\">"+get_lan("all")+"</option>");
                for (var i = 0; i < cn_advtype.length; i++) {
                    $("#type").append("<option value=\"" + cn_advtype[i].id + "\">" + cn_advtype[i].name + "</option>");
                }
            }
            else{
                $("#type").append("<option value=\"\">all</option>");
                for (var i = 0; i < en_advtype.length; i++) {
                    $("#type").append("<option value=\"" + en_advtype[i].id + "\">" + en_advtype[i].name + "</option>");
                }
            }
        }
         */

        function qryPageAdv(){
            //alert("111111");
            var data = {};
            data["gjc"] = $("#gjc").val();
            data["type"] = $("#type").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "title",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "typename",render: function(data, type, row, meta) {return nulltostr(data);}},
                /*
                {data: "type_flg",render: function(data, type, row, meta) {
                        if(vlan=="cn") {
                            return getJsonById(cn_advtype,data,"name");
                        }
                        else{
                            return getJsonById(en_advtype,data,"name");
                        }
                   }
                },
                 */
                {data: "create_time",render: function(data, type, row, meta) {
                    return nulltostr(data);
                    return $.tmDate._toString(row["create_time"],'yyyy-MM-dd HH:mm');
                }},
                {data: "top_flg",render: function(data, type, row, meta) {
                        var btnstr="";
                        if (row["top_flg"] ==0) {
                            btnstr = "<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='setTop(this,1)' data-id='" + row["id"] + "' data-type='" + row["type_flg"] + "'>"+get_lan("button.settop")+"</button>"
                        }
                        else {
                            btnstr = "<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='setTop(this,0)' data-id='" + row["id"] + "' data-type='" + row["type_flg"] + "'>"+get_lan("button.canceltop")+"</button>";
                        }
                        return btnstr;
                    }},
                {data: "id",render: function(data, type, row, meta) {
                        var btnstr="<a title=\"修改\" onclick=\"editAdv(this)\" data-id='"+row["id"]+"' href=\"javascript:;\"><i class=\"layui-icon\">&#xe642;</i>"+get_lan("button.modify")+"</a>";
                        btnstr+="&nbsp;&nbsp;"
                        btnstr+="<a title=\"删除\" onclick=\"delAdv(this)\" data-id='"+row["id"]+"' href=\"javascript:;\"><i class=\"layui-icon\">&#xe640;</i>"+get_lan("button.delete")+"</a>";
                        return btnstr;
                    }},
            ];
            getTableDataServer("infoTable",ctxPath+"/system/qryPageAdv",columns,data,false);
        }

        function delAdv(obj){
        	var id = $(obj).data("id");
        	yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
        		if(ok){
        			$.ajax({
                		type:"post",
                		url:"<%=ctxPath%>/system/delAdv",
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
        
        function addAdv(){
           	document.location.href="<%=ctxPath%>/system/crtOreditAdv";
        }

        function editAdv(obj){
            var id = $(obj).data("id");
            //alert(id);
            window.location.href = "<%=ctxPath%>/system/crtOreditAdv?id="+id;
        }

        function setTop(obj,flg){
            var id = $(obj).data("id");
            var typeflg = $(obj).data("type");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/setAdvTop",
                data:{"id":id,"topFlg":flg,"typeFlg":typeflg},
                success:function(data){
                    if(data.flg == true){
                        document.location.reload();
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
