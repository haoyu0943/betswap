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
            <div class="pageheader"><lan set-lan="html:menu.inputmovie">发布电影</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:movielist.area">区域：</lan></span>
                         <span>
                            <select class="form-control" style="width: 150px;display: inline-block" id="area" >
                                <option value=""></option>
                              <c:forEach var="moviearea" items="${moviearealist}" varStatus="index">
                                  <option value="${moviearea.code}">${moviearea.dic_describe}</option>
                              </c:forEach>
                            </select>
                         </span>
                    </div>
                    <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:movielist.keyword">关键字：</lan></span>
                        <input class="form-control " type="text" id="gjc" style="width: 150px;display: inline-block">
                    </div>
                    <button type="button" class="btn btn-sm btn-success" onclick="qryPageMovie()"><lan set-lan="html:movielist.query">查询</lan></button>
                    <button type="button" class="btn btn-sm btn-success" onclick="clearTj()"><lan set-lan="html:movielist.clear">清空</lan></button>
                <button type="button" class="btn btn-sm btn-info" onclick="addMovie()"><lan set-lan="html:button.new">新增</lan></button>
                    <div class="clear"></div>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="infoTable">
                                <thead>
                                <tr>
                                    <th style="width: 80px"><lan set-lan="html:movielist.order">序号</lan></th>
                                    <th style="width: 400px"><lan set-lan="html:movielist.moviename">电影名</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:movielist.area">区域</lan></th>
                                    <!--<th style="width: 160px"><lan set-lan="html:movielist.type">类型</lan></th>-->
                                    <th style="width: 150px"><lan set-lan="html:movielist.issuetime">发布时间</lan></th>
                                    <th style="width: 120px"><lan set-lan="html:movielist.issuenumb">发行量</lan></th>
                                    <th style="width: 100px"><lan set-lan="html:movielist.puttop">置顶</lan></th>
                                    <th style="width: 240px"><lan set-lan="html:movielist.operation">操作</lan></th>
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
        qryPageMovie();

        $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            //初始话选择框
            //initselect();
        });

        function qryPageMovie(){
            var data = {};
            data["area"] = $("#area").find("option:selected").val();
            data["gjc"] = $("#gjc").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "movie_name",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "areaname",render: function(data, type, row, meta) {return nulltostr(data);}},
                //{data: "typename",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "issue_date",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "purchase_limit",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "top_flg",render: function(data, type, row, meta) {
                        var btnstr="";
                        if (row["top_flg"] ==0) {
                            btnstr = "<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='setTop(this,1)' data-id='" + row["movie_id"] + "' data-type='" + row["movie_id"] + "'>"+get_lan("button.settop")+"</button>"
                        }
                        else {
                            btnstr = "<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='setTop(this,0)' data-id='" + row["movie_id"] + "' data-type='" + row["movie_id"] + "'>"+get_lan("button.canceltop")+"</button>";
                        }
                        return btnstr;
                    }},
                {data: "movie_id",render: function(data, type, row, meta) {
                        var btnstr="<a title=\"修改\" onclick=\"editmovie(this)\" data-id='"+row["movie_id"]+"' href=\"javascript:;\"><i class=\"layui-icon\">&#xe642;</i>"+get_lan("button.modify")+"</a>";
                        btnstr+="&nbsp;&nbsp;"
                        btnstr+="<a title=\"删除\" onclick=\"delMovie(this)\" data-id='"+row["movie_id"]+"' href=\"javascript:;\"><i class=\"layui-icon\">&#xe640;</i>"+get_lan("button.delete")+"</a>";
                        return btnstr;
                    }},
            ];
            getTableDataServer("infoTable",ctxPath+"/movie/qryPageMovie",columns,data,false);
        }

        function delMovie(obj){
        	var movieId = $(obj).data("id");
        	yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("head.delprop"),callback:function(ok){
        		if(ok){
        			$.ajax({
                		type:"post",
                		url:"<%=ctxPath%>/movie/delMovie",
                		data:{"movieId":movieId},
                		success:function(data){
                			if(data == true){
                				document.location.reload();
                			}
                			else{
                				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.delete")});
                			}
                		}
                	});
        		}
        	}});
        }

        function clearTj(){
        	$("#area").val("");
        	$("#gjc").val("");
        	$("#type").val("");
        }
        
        function addMovie(){
           	document.location.href="<%=ctxPath%>/movie/crtOreditMovie";
        }

        function editmovie(obj){
            var id = $(obj).data("id");
            //alert(id);
            window.location.href = "<%=ctxPath%>/movie/crtOreditMovie?movieId="+id;
        }

        function setTop(obj,flg){
            var id = $(obj).data("id");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/setTop",
                data:{"movieId":id,"topFlg":flg},
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
