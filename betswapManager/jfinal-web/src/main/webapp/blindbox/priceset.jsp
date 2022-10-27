<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>blind box price set</title>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
            <div id="page-wrapper">
                <div class="pageheader"><lan set-lan="html:blindboxlist.ladder.setting">盲盒价格阶梯设置</lan><i class="fa fa-caret-down ml-xs"></i></div>
                <div class="page-inner">
                    <div class="row">
                        <!--<div class="col-md-10">-->
                            <div class="pageheader">
                                <button type="button" class="btn btn-sm btn-success" id="dianji"><lan set-lan="html:button.new">新增</lan></button>
                                <button type="button" class="btn btn-sm btn-success" id="fanhui"><lan set-lan="html:button.back">返回</lan></button>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="infoTable">
                                    <thead>
                                    <tr>
                                        <th width="10%"><lan set-lan="html:movielist.order">序号</lan></th>
                                        <th width="45%"><lan set-lan="html:blindboxlist.name">盲盒名称</lan></th>
                                        <th width="20%"><lan set-lan="html:blindboxlist.setting.price">设定价格</lan></th>
                                        <th width="25%"><lan set-lan="html:actor.operation">操作</lan></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!--
                                    <tr>
                                        <td>100</td>
                                        <td>已经阅读</td>
                                        <td><button type="button" class="btn btn-xs btn-warning mr-xs">修改</button><button type="button" class="btn btn-xs btn-danger">删除</button></td>
                                    </tr>
                                    <tr>
                                        <td>100</td>
                                        <td>已经阅读</td>
                                        <td><button type="button" class="btn btn-xs btn-warning mr-xs">修改</button><button type="button" class="btn btn-xs btn-danger">删除</button></td>
                                    </tr>
                                    <tr>
                                        <td>100</td>
                                        <td>已经阅读</td>
                                        <td><button type="button" class="btn btn-xs btn-warning mr-xs">修改</button><button type="button" class="btn btn-xs btn-danger">删除</button></td>
                                    </tr>
                                    -->
                                    </tbody>
                                </table>
                            </div>
                        <!--</div>-->
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width: 499px;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close"><span>&times;</span></button>
                    <h4 class="modal-title"><lan set-lan="html:button.new">新增</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:blindboxlist.name">盲盒名称</lan>：</dt>
                            <dd>
                                <input class="form-control " type="text" id="mc">
                                <input type="hidden" id="id">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="blindboxlist.ladder.price">阶梯价格</lan>(Rp)：</dt>
                            <dd>
                                <input class="form-control " type="text" id="jg">
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" onclick="save()"><lan set-lan="html:button.save">保存</lan></button>
                    <button type="button" class="btn btn-default" onclick="cancel()"><lan set-lan="html:message.cancelText">取消</lan></button>
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
        qryPriceSet();

        $(function(){
            $("#dianji").on('click',function(){
                $(".zhezhao2").show();
                $("#mc").val("");
                $("#jg").val("");
                $("#id").val("");
            });
            $("#fanhui").on('click',function(){
                document.location.href="<%=ctxPath%>/blindbox";
            });
            $(".close").on('click',function(){
                $(".zhezhao2").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $("#main-menu").height($(window).height()-$(".navbar").height()-10);
            //qryPriceSet();
            //qryPriceSet();
        });

        function qryPriceSet(){
            var data={};
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "blind_box_name",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "price_rp",render: function(data, type, row, meta) {
                    if(data==null){
                        return "0 Rp";
                    }
                    else{
                        return data+" Rp";
                    }
                }},
                {data: "id",render: function(data, type, row, meta) {
                        var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" data-id=\""+row["price_set_id"]+"\" onclick=\"edit(this)\">"+get_lan("button.modify")+"</button>";
                        btnstr+="&nbsp;&nbsp;"
                        btnstr+="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" data-id=\""+row["price_set_id"]+"\" onclick=\"del(this)\">"+get_lan("button.delete")+"</button>";
                        return btnstr;
                    }},
            ];
            getTableDataServer("infoTable",ctxPath+"/blindbox/qryPriceset",columns,data,false);
        }

        function cancel(){
            $(".zhezhao2").hide();
        }

        function save(){
            var data = {};
            var jg=$("#jg").val();
            var mc=$("#mc").val();
            if(mc==""){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("blindboxlist.name")+get_lan("message.blank")});
                return;
            }
            if(jg==""){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("blindboxlist.ladder.price")+get_lan("message.blank")});
                return;
            }
            else{
                if(checkNumber(jg)==false){
                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("blindboxlist.ladder.price")+get_lan("error.mustbenumber")});
                    return;
                }
            }
            data["mc"] = mc;
            data["jg"] = jg;
            data["id"] =$("#id").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/savePriceset",
                data:data,
                success:function(data){
                    qryPriceSet();
                    $(".zhezhao2").hide();
                }
            });
        }

        function del(obj){
            var id = $(obj).data("id");
            yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
                    if(ok){
                        $.ajax({
                            type:"post",
                            url:"<%=ctxPath%>/blindbox/delPriceset",
                            data:{"id":id},
                            success:function(data){
                                qryPriceSet();
                            }
                        });
                    }
                }});
        }

        function edit(obj){
            var id = $(obj).data("id");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/editPriceset",
                data:{"id":id},
                success:function(data){
                    $("#id").val(data.mdl.price_set_id);
                    $("#mc").val(data.mdl.blind_box_name);
                    $("#jg").val(data.mdl.price_rp);
                    $(".zhezhao2").show();
                }
            });
        }
    </script>
</body>
</html>
