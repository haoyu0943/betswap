<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>movie actor list</title>
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
                            <li><a href="<%=ctxPath%>/movie/movieActor?movieId=${movieId}" class="active-li"><span><lan set-lan="html:moviemenu.actor">电影参影人员</lan></span></a></li>
                            <li><a href="<%=ctxPath%>/movie/rolePurchase?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.purchase">抢购规则设置</lan></span></a></li>
                            <li><a href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.presell">预售规则设置</lan></span></a></li>
                        <!--<li><a href="<%=ctxPath%>/movie/fund?movieId=${movieId}"><span><lan set-lan="html:moviemenu.fund">影视基金设置</lan></span></a></li>-->
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
                                    <th style="width: 45px"><lan set-lan="html:actor.order">序号</lan></th>
                                    <th style="width: 180px"><lan set-lan="html:actor.role">角色</lan></th>
                                    <th style="width: 150px"><lan set-lan="html:actor.name">姓名</lan></th>
                                    <th ><lan set-lan="html:actor.introduce">人员介绍</lan></th>
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
    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width:499px;margin: 8% auto;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close1"><span>&times;</span></button>
                    <h4 class="modal-title" id="divbt"><lan set-lan="html:actor.newpsn">新增电影相关人</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:actor.role">参与角色</lan>：</dt>
                            <dd>
                                <select class="form-control"  name="rylx" id="rylx">
                                    <option value=""></option>
                                    <c:forEach var="psnrole" items="${psnrolelist}" varStatus="index">
                                        <option value="${psnrole.code}">${psnrole.dic_describe}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="id" value="">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:actor.photo">人员照片</lan>：</dt>
                            <dd>
                                <img id="finalImg" src="<%=ctxPath%>/assets/img/avatar.png" style="width:160px;height: 200px" />
                                <div class="btnupload">
                                    <span id="replaceImg" style="width: 70px;height: 32px;display: inline-block;"><lan set-lan="html:button.clip">裁剪上传</lan></span>
                                </div>
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:actor.name">人员姓名</lan>：</dt>
                            <dd>
                                <input class="form-control" type="text" style="width: 200px" id="ryxm"  maxlength="30">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:actor.introduce">人员介绍</lan>：</dt>
                            <dd>
                                <textarea class="form-control" style="width: 90%;height: 100px" id="ryjs"></textarea>
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type=hidden id="yhbh">
                    <button type="button" class="btn btn-success" onclick="SaveRyxx()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:message.sureText">确定</lan></button>
                    <button type="button" class="btn btn-default" onclick="closeYhck()"><lan set-lan="html:message.cancelText">取消</lan></button>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>
    <!--图片裁剪框 start-->
    <div style="display: none" class="tailoring-container">
        <div class="black-cloth" onclick="closeTailor(this)"></div>
        <div class="tailoring-content">
            <div class="tailoring-content-one">
                <label title="上传图片" for="chooseImg" class="l-btn choose-btn">
                    <!--<input type="file" accept="image/jpg,image/jpeg,image/png" name="file" id="chooseImg" class="hidden" onchange="selectImg(this,1,0.5)">-->
                    <input type="file" accept="image/jpg,image/jpeg,image/png" name="file" id="chooseImg" class="hidden">
                    <lan set-lan="html:picclip.select">选择图片</lan>
                </label>
                <div class="close-tailoring"  onclick="closeTailor(this)">×</div>
            </div>
            <div class="tailoring-content-two">
                <div class="tailoring-box-parcel">
                    <img id="tailoringImg">
                </div>
                <div class="preview-box-parcel">
                    <p><lan set-lan="html:picclip.preview">图片预览</lan>：</p>
                    <div class="square previewImg"></div>
                    <div class="circular previewImg"></div>
                </div>
            </div>
            <div class="tailoring-content-three">
                <button class="l-btn cropper-reset-btn"><lan set-lan="html:picclip.reset">复位</lan></button>
                <button class="l-btn cropper-rotate-btn"><lan set-lan="html:picclip.rotate">旋转</lan></button>
                <button class="l-btn cropper-scaleX-btn"><lan set-lan="html:picclip.reversal">换向</lan></button>
                <button class="l-btn sureCut" id="sureCut"><lan set-lan="html:message.sureText">确定</lan></button>
            </div>
        </div>
    </div>
    <!--图片裁剪框 end-->
    <script type="text/javascript" src="<%=ctxPath%>/js/cropper.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/cropper-zj.js"></script>
    <script>
        option= {
            aspectRatio: 0.6/ 1,//默认比例
            preview: '.previewImg',//预览视图
            guides: false,  //裁剪框的虚线(九宫格)
            autoCropArea: 0.8,  //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
            movable: false, //是否允许移动图片
            dragCrop: true,  //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
            movable: true,  //是否允许移动剪裁框
            resizable: true,  //是否允许改变裁剪框的大小
            zoomable: false,  //是否允许缩放图片大小
            mouseWheelZoom: false,  //是否允许通过鼠标滚轮来缩放图片
            touchDragZoom: true,  //是否允许通过触摸移动来缩放图片
            rotatable: true,  //是否允许旋转图片
            crop: function (e) {
                // 输出结果数据裁剪图像。
            }
        }
        initlan();
        var vlan = $.tmCookie.getCookie('lan');
        var $thisTable = $("#infoTable");
        initTable("infoTable");
        //initselect();
        qryMovieActor();


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
                $("#divbt").html(get_lan("actor.newpsn"));
                $("#ryxm").val("");
                $("#ryjs").val("");
                $("#id").val("");
                $("#rylx").val("");
                $("#finalImg").attr("src","<%=ctxPath%>/assets/img/avatar.png");

            });
            $(".close1").on('click',function(){
                $(".zhezhao2").hide();
            });
            $("#chooseImg").change(function () {
                selectImg(this,1,1);
            });
        });
        /*
        function initselect(){
            if(vlan=="cn") {
                $("#rylx").append("<option value=\"\">等待选择</option>");
                for (var i = 0; i < cn_movieactortype.length; i++) {
                    $("#rylx").append("<option value=\"" + cn_movieactortype[i].id+ "\">" + cn_movieactortype[i].name + "</option>");
                }
            }
            else{
                $("#rylx").append("<option value=\"\">waitting select</option>");
                for (var i = 0; i < en_movieactortype.length; i++) {
                    $("#rylx").append("<option value=\"" + en_movieactortype[i].id+ "\">" + en_movieactortype[i].name + "</option>");
                }
            }
            layui.form.render("select");
        }
        */
        function closeYhck(){
            $(".zhezhao2").hide();
        }


        var searchTimer = null;
        var status = false;
        function qryMovieActor(){
            clearTimeout(searchTimer);
            searchTimer = setTimeout(function(){
                $.ajax({
                    type:"post",
                    url:"<%=ctxPath%>/movie/qryMovieActor",
                    beforeSend:function(){},
                    data:{"movieId":"${movieId}"},
                    success:function(data){
                        var t = $thisTable.DataTable();
                        t.rows("tr").remove().draw(false);
                        var tableRowsArray = [];
                        var list = data.psnlist;
                        for(var i=0;i<list.length;i++){
                            btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='updActor(this)' data-id='"+list[i].id+"'>"+get_lan("button.modify")+"</button>";
                            btnstr=btnstr+"<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='delActor(this)' data-id='"+list[i].id+"'>"+get_lan("button.delete")+"</button>";
                            var jsstr=list[i].rolename;
                            /*
                            if(vlan=="cn") {
                                jsstr=getJsonById(cn_movieactortype,list[i].psn_role,"name");
                            }
                            else{
                                jsstr=getJsonById(en_movieactortype,list[i].psn_role,"name");
                            }
                             */
                            var psnms=list[i].psn_introduce;
                            if(psnms.length>80){
                                psnms=psnms.substr(0,80);
                            }
                            tableRowsArray = [
                                i+1,
                                jsstr,
                                list[i].psn_name,
                                psnms,
                                btnstr
                            ];
                            var tr=t.row.add(tableRowsArray).draw().node();
                            //$(tr).find("td:eq(2)").addClass('t-rwmc');
                        }
                    }
                });
            }, 200);
        }

        function delActor(obj){
            var id = $(obj).data("id");
            yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("head.delprop"),callback:function(ok){
                    if(ok){
                        $.ajax({
                            type:"post",
                            url:"<%=ctxPath%>/movie/delActorById",
                            data:{"id":id},
                            success:function(data){
                                if(data == true){
                                    document.location.reload();
                                }
                                else{
                                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("message.prompttitle")});
                                }
                            }
                        });
                    }
                }});
        }


        var waitTimer = null;
        function updActor(obj){
            var id = $(obj).data("id");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/movie/findMovieActorBYId",
                data:{"id":id},
                success:function(data){
                    $("#ryxm").val(data.psn.psn_name);
                    $("#ryjs").val(data.psn.psn_introduce);
                    $("#rylx").val(data.psn.psn_role);
                    $("#finalImg").attr("src",serviceUrl+"/"+data.psn.psn_image);
                    $("#divbt").html(get_lan("actor.editpsn"));
                    $("#id").val(id);
                    $(".zhezhao2").show();
                }
            });
        }

        function SaveRyxx(){
            formcheckMode.on('submit(savebtn)', function (data) {
                var rylx=$("#rylx").val();
                var ryxm=$("#ryxm").val();
                var ryjs=$("#ryjs").val();
                var id=$("#id").val();
                if(rylx==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("actor.role")+get_lan("error.notfill")});
                    return;
                }
                if(ryxm==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("actor.name")+get_lan("error.notfill")});
                    return;
                }
                if(ryjs==""){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("actor.introduce")+get_lan("error.notfill")});
                    return;
                }

                var avatar ;
                var urlhead="http://";
                var zlcover = $("#finalImg").attr("src");
                if(zlcover.indexOf(urlhead)!=-1){
                    if(zlcover.indexOf("avatar.png")!=-1){
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("actor.photo")+get_lan("error.notfill")});
                    }
                }
                else{
                    avatar = dataURLtoFile(zlcover, "file.png");
                }
                var formData=new FormData();
                formData.append("file",avatar);
                formData.append("key","lueSGJZetyySpUndWjMBEg");
                formData.append("datatype","actor");
                formData.append("movieId","${movieId}");
                formData.append("id",id);
                formData.append("ryjs",ryjs);
                formData.append("ryxm",ryxm);
                formData.append("rylx",rylx);
                formData.append("userId","${user.user_id}");
                $.ajax({
                    type:"post",
                    url:serviceUrl+'/movie/saveMovieActor',
                    beforeSend:function(){},
                    data:formData,
                    cache: false, //上传文件不需要缓存
                    processData: false, // 告诉jQuery不要去处理发送的数据
                    contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                    success:function(data){
                        if(data.code== 1000){
                            closeYhck();
                            document.location.reload();
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
