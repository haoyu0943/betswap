<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>movie fund</title>
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
                            <li><a href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.presell">预售规则设置</lan></span></a></li>
                        <!--<li><a href="<%=ctxPath%>/movie/fund?movieId=${movieId}" class="active-li"><span><lan set-lan="html:moviemenu.fund">影视基金设置</lan></span></a></li>-->
                        </c:if>
                        <div class="clear"></div>
                    </ul>
                </div>
                <div class="page-inner">
                    <button type="button" class="btn btn-sm btn-info"  id="waibao"><lan set-lan="html:button.new">新增</lan></button></center>
                </div>
            <div class="page-inner">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover" id="infoTable">
                        <thead>
                        <tr>
                            <th style="width: 45px"><lan set-lan="html:movielist.order">序号</lan></th>
                            <th><lan set-lan="html:moviefund.fund.name">基金名称</lan></th>
                            <th style="width: 180px"><lan set-lan="html:moviefund.fund.days">收益天数</lan></th>
                            <th style="width: 180px"><lan set-lan="html:moviefund.fund.yield">收益率</lan></th>
                            <th style="width: 200px"><lan set-lan="html:moviepreselllist.settime">设置时间</lan></th>
                            <th style="width: 240px"><lan set-lan="html:actor.operation">操作</lan></th>
                        </tr>
                        </thead>
                        <tbody id="items_body">

                        </tbody>
                    </table>

                </div>
            </div>
        </div>
    </div>
    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width:360px;margin: 8% auto;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close1"><span>&times;</span></button>
                    <h4 class="modal-title" id="divbt"><lan set-lan="html:moviefund.new.fund">新增电影基金</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="layui-form">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:moviefund.fund.name">基金名称</lan>：</dt>
                            <dd>
                                <input type="hidden" id="fundId" >
                                <input class="form-control" type="text" style="width: 200px" id="jjmc"  maxlength="7">
                            </dd>
                        </dl>
                    </div>
                        <div class="ypbglist100">
                            <dl>
                                <dt class="color-danger"><lan set-lan="html:moviefund.fund.days">收益天数</lan>：</dt>
                                <dd>
                                    <input class="form-control" type="text" style="width: 200px" id="syts"  maxlength="7">
                                </dd>
                            </dl>
                        </div>
                        <div class="ypbglist100">
                            <dl>
                                <dt class="color-danger"><lan set-lan="html:moviefund.fund.yield">收益率</lan>：</dt>
                                <dd>
                                    <input class="form-control" type="text" style="width: 200px" id="syl"  maxlength="7">
                                </dd>
                            </dl>
                        </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:moviefund.remark">备注</lan>：</dt>
                            <dd>
                                <textarea class="form-control" style="width: 200px;height: 100px" id="bz"></textarea>
                            </dd>
                        </dl>
                    </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type=hidden id="yhbh">
                    <button type="button" class="btn btn-success" onclick="SaveFund()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:message.sureText">确定</lan></button>
                    <button type="button" class="btn btn-default" onclick="closeYhck()"><lan set-lan="html:message.cancelText">取消</lan></button>
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
        $(function(){

            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);

            $("#waibao").on('click',function(){
                $(".zhezhao2").show();
                $("#divbt").html(get_lan("moviefund.new.fund"));
                $("#jjmc").val("");
                $("#syts").val("");
                $("#syl").val("");
                $("#bz").val("");
                $("#fundId").val("");
            });
            $(".close1").on('click',function(){
                $(".zhezhao2").hide();
            });
            qryMovieFund();
        });

        function closeYhck(){
            $(".zhezhao2").hide();
        }

        var searchTimer = null;
        var status = false;
        function qryMovieFund(){
            clearTimeout(searchTimer);
            searchTimer = setTimeout(function(){
                $.ajax({
                    type:"post",
                    url:"<%=ctxPath%>/movie/qryMovieFund",
                    beforeSend:function(){},
                    data:{"movieId":"${movieId}"},
                    success:function(data){
                        var t = $thisTable.DataTable();
                        t.rows("tr").remove().draw(false);
                        var tableRowsArray = [];
                        var list = data.fundlist;
                        for(var i=0;i<list.length;i++){
                            var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='updFund(this)' data-id='"+list[i].fund_id+"'>"+get_lan("button.modify")+"</button>";
                            btnstr=btnstr+"<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='delFund(this)' data-id='"+list[i].fund_id+"'>"+get_lan("button.delete")+"</button>";
                            tableRowsArray = [
                                i+1,
                                list[i].fund_name,
                                list[i].days,
                                list[i].yield,
                                $.tmDate._toString(list[i].create_time,'yyyy-MM-dd HH:mm'),
                                btnstr
                            ];
                            var tr=t.row.add(tableRowsArray).draw().node();
                            //$(tr).find("td:eq(2)").addClass('t-rwmc');
                        }
                    }
                });
            }, 200);
        }

        function delFund(obj){
            var id = $(obj).data("id");
            yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
                    if(ok){
                        $.ajax({
                            type:"post",
                            url:"<%=ctxPath%>/movie/delFundByFundid",
                            data:{"fundid":id},
                            success:function(data){
                                if(data == true){
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


        var waitTimer = null;
        function updFund(obj){
            var id = $(obj).data("id");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/findMovieFundBYFundid",
                data:{"fundid":id},
                success:function(data){
                    $("#jjmc").val(data.fund.fund_name);
                    $("#syts").val(data.fund.days);
                    $("#syl").val(data.fund.yield);
                    $("#divbt").html(get_lan("moviefund.update.fund"));
                    $("#bz").val(data.fund.remark);
                    $("#fundId").val(data.fund.fund_id);
                    $(".zhezhao2").show();

                }
            });
        }

        function SaveFund(){
            formcheckMode.on('submit(savebtn)', function (data) {
                var jjmc=$("#jjmc").val();
                var syts=$("#syts").val();
                var syl=$("#syl").val();
                var bz=$("#bz").val();
                var fundId=$("#fundId").val();
                if(jjmc==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviefund.fund.name")+get_lan("message.blank")});
                    return;
                }
                if(syts==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("ctor.name")+get_lan("message.blank")});
                    return;
                }
                else{
                    if(checkNumber(syts)==false){
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviefund.fund.days")+get_lan("error.mustbenumber")});
                        return;
                    }
                }
                if(syl==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviefund.fund.yield")+get_lan("message.blank")});
                    return;
                }
                else{
                    if(checkNumber(syl.replace(".",""))==false){
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviefund.fund.yield")+get_lan("error.mustbenumber")});
                        return;
                    }
                }
                var formData={};
                formData["jjmc"]=jjmc;
                formData["fundId"]=fundId;
                formData["bz"]=bz;
                formData["syts"]=syts;
                formData["syl"]=syl;
                formData["movieId"]="${movieId}";
                $.ajax({
                    type:"post",
                    url:"<%=ctxPath%>/movie/saveMovieFund",
                    data:formData,
                    success:function(data){
                        if(data == true){
                            $(".zhezhao2").hide();
                            qryMovieFund();
                        }
                        else{
                            yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.savefaild")});
                        }
                    }
                });
            });
        }

    </script>
</body>
</html>
