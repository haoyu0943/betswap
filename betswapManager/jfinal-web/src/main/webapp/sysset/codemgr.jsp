<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>code manager</title>
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
                <div class="pageheader"><lan set-lan="html:menu.dictionary">数据字典维护</lan><i class="fa fa-caret-down ml-xs"></i></div>
                <div class="page-inner">
                    <div class="row">
                        <div class="col-md-2">
                            <div class="line_tab">
                            <c:forEach var="r" items="${rlist}" varStatus="item">
                                <c:if test="${item.index==0}">
                                   <a href="javascript:;" class="line_tab_active" id="${r.dic_type}" onclick="qryPageCode('${r.dic_type}','${r.dic_type_describe}')">${r.dic_type_describe}</a>
                                </c:if>
                                <c:if test="${item.index>0}">
                                    <a href="javascript:;" id="${r.dic_type}" onclick="qryPageCode('${r.dic_type}','${r.dic_type_describe}')">${r.dic_type_describe}</a>
                                </c:if>
                            </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="pageheader">
                                <button type="button" class="btn btn-sm btn-success" id="dianji"><lan set-lan="html:button.new">新增</lan></button>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="infoTable">
                                    <thead>
                                    <tr>
                                        <th width="10%"><lan set-lan="html:movielist.order">序号</lan></th>
                                        <th width="15%"><lan set-lan="html:codemgr.code">编码</lan></th>
                                        <th width="50%"><lan set-lan="html:codemgr.describe">描述</lan></th>
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
                        </div>
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
                            <dt class="color-danger"><lan set-lan="html:codemgr.code">编码</lan>：</dt>
                            <dd>
                                <input class="form-control " type="text" id="newcode">
                                <input type="hidden" id="id">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:codemgr.describe">描述</lan>：</dt>
                            <dd>
                                <input class="form-control " type="text" id="newdescribe">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100 HBox" id="videoUploadBxo" style="display: none;">
                        <span class="wwmc color-danger"><lan set-lan="html:picclip.select">选择图片</lan></span>
                        <span id="videoUploadmc" style="padding: 5px 0;display: inline-block"></span>
                        <a href="javascript:void(0);" class="color-success" style="padding: 5px 0">
                            <img src="<%=ctxPath%>/assets/img/tihuan.png" >
                            <span id="videoUploadBtn"><lan set-lan="html:moviepub.upload">上传</lan></span>
                        </a>
                        <img src="" style="display:none;width: 70px;" id="vedioLoading">
                        <input type="hidden" id="framefileurl" value="">
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
        var cur_dictype="${dictype}";
        var cur_dictype_describe="${dictypedescribe}";
        qryPageCode(cur_dictype,cur_dictype_describe);
        //qryCode();

        $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#dianji").on('click',function(){
                $(".zhezhao2").show();
                $("#newcode").val("");
                $("#newdescribe").val("");
                $("#vedioLoading").val("");
                $("#vedioLoading").hide();
                $("#framefileurl").val("");
            });
            $(".close").on('click',function(){
                $("#vedioLoading").val("");
                $("#vedioLoading").hide();
                $("#framefileurl").val("");
                $(".zhezhao2").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $("#main-menu").height($(window).height()-$(".navbar").height()-10);
        });
        layui.use('laydate', function(){
            var $ = layui.jquery,
                laydate = layui.laydate,upload = layui.upload
                ,layer = layui.layer,form = layui.form;

            upload.render({
                elem: '#videoUploadBtn'
                ,url: serviceUrl+'/file/url/uploadOther',
                accept:'images'
                ,before: function(obj){
                    this.data={'key':"lueSGJZetyySpUndWjMBEg",'type':"typeicon"};
                }
                ,done: function(res){
                    if(res.code != 1000){
                        return layer.msg(get_lan("error.uploadfaild"));
                    }
                    var wjlj=res.data.fileUrl
                    $("#vedioLoading").attr("src",serviceUrl+wjlj);
                    $("#framefileurl").val(wjlj);
                    $("#vedioLoading").show();
                }
                ,error: function(){
                    layer.msg(get_lan("error.uploadfaild"));
                }
            });

        });
        function ShowActive(codetype){
            <c:forEach var="r" items="${rlist}" varStatus="item">
                if($("#${r.dic_type}").hasClass("line_tab_active")){
                   $("#${r.dic_type}").removeClass("line_tab_active");
                }
            </c:forEach>
            $("#"+codetype).addClass("line_tab_active");
        }

        function qryPageCode(codetype,codetypedesc){
            $("#videoUploadBxo").hide();
            ShowActive(codetype);
            cur_dictype=codetype;
            cur_dictype_describe=codetypedesc;
            if(cur_dictype=="goodsSeries" && cur_dictype_describe=="商品系列类型"){
                $("#videoUploadBxo").show();
            }
            qryCode();
        }

        function qryCode(){
            var data = {};
            data["codetype"] = cur_dictype;
            data["codetypedesc"] = cur_dictype_describe;
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "code",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "dic_describe",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "id",render: function(data, type, row, meta) {
                        var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" data-id=\""+row["id"]+"\" onclick=\"edit(this)\">"+get_lan("button.modify")+"</button>";
                        btnstr+="&nbsp;&nbsp;"
                        btnstr+="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" data-id=\""+row["id"]+"\" onclick=\"del(this)\">"+get_lan("button.delete")+"</button>";
                        return btnstr;
                    }},
            ];
            getTableDataServer("infoTable",ctxPath+"/sysset/qryPageCode",columns,data,false);
        }

        function cancel(){
            $(".zhezhao2").hide();
        }

        function save(){
            var data = {};
            data["codetype"] = cur_dictype;
            data["codetypedesc"] = cur_dictype_describe;
            data["newcode"] = $("#newcode").val();
            data["newdescribe"] = $("#newdescribe").val();
            data["picurl"] = $("#framefileurl").val();
            data["id"] =$("#id").val();
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/sysset/saveCode",
                data:data,
                success:function(data){
                    qryCode();
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
                            url:"<%=ctxPath%>/sysset/delCode",
                            data:{"id":id},
                            success:function(data){
                                qryCode();
                            }
                        });
                    }
                }});
        }

        function edit(obj){
            var id = $(obj).data("id");
            $("#vedioLoading").val("");
            $("#vedioLoading").hide();
            $("#framefileurl").val("");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/sysset/qryCode",
                data:{"id":id},
                success:function(data){
                    $("#id").val(data.dicmdl.id);
                    $("#newcode").val(data.dicmdl.code);
                    $("#newdescribe").val(data.dicmdl.dic_describe);
                    if(data.dicmdl.picurl){
                        $("#vedioLoading").attr("src",serviceUrl+data.dicmdl.picurl);
                        $("#vedioLoading").show();
                    }
                    $("#framefileurl").val(data.dicmdl.picurl);
                    //cur_dictype=data.dicmdl.dic_type;
                    //cur_dictype_describe=data.dicmdl.dic_type_describe;
                    $(".zhezhao2").show();
                }
            });
        }
    </script>
</body>
</html>
