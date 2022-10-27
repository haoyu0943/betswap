<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>role of presell list</title>
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
                <button type="button" class="btn btn-sm btn-info"  onclick="sendnew()"><lan set-lan="html:button.new">新增</lan></button></center>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="infoTable">
                                <thead>
                                <tr>
                                    <th style="width: 80px"><lan set-lan="html:movielist.order">序号</lan></th>
                                    <th style="width: 200px"><lan set-lan="html:movielist.days">预售天数</lan></th>
                                    <th style="width: 260px"><lan set-lan="html:moviepreselllist.starttime">预售开始时间</lan></th>
                                    <th style="width: 260px"><lan set-lan="html:moviepreselllist.settime">设置时间</lan></th>
                                    <th style="width: 180px"><lan set-lan="html:moviepreselllist.status">当前状态</lan></th>
                                    <th style="width: 200px"><lan set-lan="html:actor.operation">操作</lan></th>
                                </tr>
                                </thead>
                                <tbody id="items_body">

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
        var vlan = $.tmCookie.getCookie('lan');
        var $thisTable = $("#infoTable");
        initTable("infoTable");
        qryPresellRoles();
        $(function(){
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });

        });

        var searchTimer = null;
        var status = false;
        function qryPresellRoles(){
            clearTimeout(searchTimer);
            searchTimer = setTimeout(function(){
                $.ajax({
                    type:"post",
                    url:"<%=ctxPath%>/movie/qryPresellRoles",
                    beforeSend:function(){},
                    data:{"movieId":"${movieId}"},
                    success:function(data){
                        var t = $thisTable.DataTable();
                        t.rows("tr").remove().draw(false);
                        var tableRowsArray = [];
                        var list = data.rolelist;
                        for(var i=0;i<list.length;i++){
                            var btnstr="--";
                            var stadustr="";
                            if(list[i].stadu=="1") {
                                btnstr = "<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='updPresellrole(this)' data-id='" + list[i].id + "'>"+get_lan("button.modify")+"</button>";
                                btnstr = btnstr + "<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='delPresellrole(this)' data-id='" + list[i].id + "'>"+get_lan("button.delete")+"</button>";
                                stadustr="<spn style=\"color:blue\">"+get_lan("stadu.notstart")+"</font>";
                            }
                            else if(list[i].stadu=="2") {
                                stadustr="<spn style=\"color:green\">"+get_lan("stadu.underway")+"</font>";
                            }
                            else{
                                stadustr="<spn style=\"color:red\">"+get_lan("stadu.haveend")+"</font>";
                            }
                            tableRowsArray = [
                                i+1,
                                list[i].days,
                                $.tmDate._toString(list[i].start_date,'yyyy-MM-dd'),
                                $.tmDate._toString(list[i].create_time,'yyyy-MM-dd HH:mm'),
                                stadustr,
                                btnstr
                            ];
                            var tr=t.row.add(tableRowsArray).draw().node();
                            //$(tr).find("td:eq(2)").addClass('t-rwmc');
                        }
                    }
                });
            }, 200);
        }

        function delPresellrole(obj){
            var id = $(obj).data("id");
            yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("head.delprop"),callback:function(ok){
                    if(ok){
                        $.ajax({
                            type:"post",
                            url:"<%=ctxPath%>/movie/delPresellroleById",
                            data:{"id":id},
                            success:function(data){
                                if(data == true){
                                    document.location.reload();
                                }
                                else{
                                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("head.delprop")});
                                }
                            }
                        });
                    }
                }});
        }

        function updPresellrole(obj) {
            var id = $(obj).data("id");
            document.location.href="<%=ctxPath%>/movie/newOreditPresell?movieId=${movieId}&id="+id;
        }

        function sendnew(){
            document.location.href="<%=ctxPath%>/movie/newOreditPresell?movieId=${movieId}";
        }


    </script>
</body>
</html>
