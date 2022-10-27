<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>issue adv</title>
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
            <div class="pageheader"><lan set-lan="html:advinput.advertising.information">广告信息</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                     <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.title">广告标题</lan></span>
                     <input type="hidden" id="id" value="${id}"/>
                     <input class="form-control" type="text" id="ggbt" style="display: inline-block;width: 30%;" maxlength=60 value="${adv.title}">
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:movielist.type">类型</lan></span>
                    <span>
                          <select class="form-control" style="width: 200px;display: inline-block" id="type" onchange="showOrhide()">
                              <option value=""></option>
                              <c:forEach var="adv" items="${advtypelist}" varStatus="index">
                                  <option value="${adv.code}">${adv.dic_describe}</option>
                              </c:forEach>
                          </select>
                    </span>
                </div>
                <div class="HBox">
                    <span class="wwmc"><lan set-lan="html:movielist.sdatetime">预置上架时间</lan></span>
                    <span>
                        <input style="width: 90px" value="${adv.strTimeonline}" type="text" id="sdate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#sdate'})">
                        --
                        <input style="width: 90px" value="${adv.strTimeoffline}" type="text" id="edate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#edate'})">
                    </span>
                </div>
                <!--
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:movielist.edatetime">下架时间</lan></span>
                    <span>

                    </span>
                </div>
                -->
                <div class="HBox" id="fmtpUploadBxo">
                        <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.cover">广告图片</lan></span>
                        <span class="imageupload">
                            <div class="cover-wrap">
                                <div class="coverimgage">
                                    <div id="clipArea"></div>
                                    <div class="wrapImage">
                                        <button id="clipBtn"><lan set-lan="html:button.save">保存</lan></button>
                                    </div>
                                </div>
                            </div>
                            <div id="view">
                            	<img id="finalImg" src="<%=ctxPath%>/assets/img/avatar.png" width="100%" height="100%" />
                            </div>
                            <div class="btnupload">
                            	<span id="replaceImg" style="width: 70px;height: 32px;display: inline-block;"><lan set-lan="html:button.clip">裁剪上传</lan></span>
                                <!--<button id="replaceImg" style="width: 70px;height: 32px;display: inline-block;" type="button">裁剪上传</button>-->
                            </div>
                        </span>
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.ggnr">广告内容</lan></span>
                    <c:choose>
                        <c:when test="${adv.rel_id==null||adv.rel_id==''}">
                            <input type="radio" name="nrxz" onchange="showOrhide()" value="1"><lan set-lan="html:advinput.advertising.bdspdy">本地商品/电影</lan>
                            <input type="radio" name="nrxz" checked onchange="showOrhide()" value="2"><lan set-lan="html:advinput.advertising.wblj">外部链接</lan>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="nrxz" checked onchange="showOrhide()" value="1"><lan set-lan="html:advinput.advertising.bdspdy">本地商品/电影</lan>
                            <input type="radio" name="nrxz" onchange="showOrhide()" value="2"><lan set-lan="html:advinput.advertising.wblj">外部链接</lan>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="HBox" id="spcxdiv" style="display:none">
                    <div class="HBox">
                        <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.xzdsp">选择的商品</lan></span>
                        <input class="form-control" type="text" id="curgoodsid" style="display: inline-block;width: 30%;" maxlength=60 readonly value="${adv.rel_id}">
                    </div>
                    <div class="HBox">
                        <div class="hhBoxeww">
                            <span class="wwmc"><lan set-lan="html:advinput.advertising.cxtjspmc">查询条件:商品名称</lan></span>
                            <input type="text" id="spmc" style="width: 150px">
                            <button type="button" class="btn btn-sm btn-info" onclick="qrygoods()"><lan set-lan="html:movielist.query">查询</lan></button>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover" id="infoTable1">
                        <thead>
                        <tr>
                            <th><lan set-lan="html:movielist.order">序号</lan></th>
                            <th><lan set-lan="html:goodsapplydet.goods.name">商品名称</lan></th>
                            <th width="50%"><lan set-lan="html:goodsapplydet.goods.introduction">商品介绍</lan></th>
                            <th><lan set-lan="html:goodsapplydet.goods.price">商品价格</lan></th>
                            <th><lan set-lan="html:actor.operation">操作</lan></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    </div>
                </div>
                <div class="HBox" id="dycxdiv" style="display: none">
                    <div class="HBox">
                        <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.xzddy">选择的电影</lan></span>
                        <input class="form-control" type="text" id="curmovieid" style="display: inline-block;width: 30%;" maxlength=60 readonly value="${adv.rel_id}">
                    </div>
                    <div class="HBox">
                        <div class="hhBoxeww">
                            <span class="wwmc"><lan set-lan="html:advinput.advertising.cxtjdymc">查询条件:电影名称</lan>></span>
                            <input type="text" id="dymc" style="width: 150px">
                            <button type="button" class="btn btn-sm btn-info" onclick="qrymovies()"><lan set-lan="html:movielist.query">查询</lan></button>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="infoTable2">
                            <thead>
                            <tr>
                                <th style="width: 80px"><lan set-lan="html:movielist.order">序号</lan></th>
                                <th style="width: 300px"><lan set-lan="html:movielist.moviename">电影名</lan></th>
                                <th style="width: 160px"><lan set-lan="html:movielist.area">区域</lan></th>
                                <th style="width: 160px"><lan set-lan="html:movielist.type">类型</lan></th>
                                <th style="width: 120px"><lan set-lan="html:movielist.issuetime">发布时间</lan></th>
                                <th style="width: 240px"><lan set-lan="html:movielist.operation">操作</lan></th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="HBox" id="ljdzdiv" style="display: block">
                    <span class="wwmc color-danger"><lan set-lan="html:advinput.advertising.link">链接地址</lan></span>
                    <input class="form-control" type="text" id="ljdz" style="display: inline-block;width: 30%;" maxlength=60 value="${adv.link_url}">
                </div>
                <div class="center">
                    <button type="button" class="btn btn-sm btn-success" onclick="saveAdv()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:button.save">保存</lan></button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="reback()"><lan set-lan="html:button.back">返回列表</lan></button>
                </div>
            </div>
            <!--  <div class="clear"></div>-->
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
    <script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>

    <script type="text/javascript">
        option= {
            aspectRatio: 1 / 0.8,//默认比例
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
    var vediourl="";
    var vediofile="";
    $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            //编辑时装载数据
            //initselect();
            $("#chooseImg").change(function () {
                if($("#type").find("option:selected").val()=='1'){
                    selectImg(this,1,0.462);
                }
                else{
                    selectImg(this,1,0.415);
                }
            });
            <c:if test="${not empty adv.id}">
               $("#finalImg").attr("src",serviceUrl+"${adv.cover_image}");
               $("#type").val("${adv.type_flg}");
               showOrhide();
           </c:if>
    });


    /*
    function initselect(){
        if(vlan=="cn") {
            $("#type").append("<option value=\"\">"+get_lan("usermgr.select")+"</option>");
            for (var i = 0; i < cn_advtype.length; i++) {
                $("#type").append("<option value=\"" + cn_advtype[i].id + "\">" + cn_advtype[i].name + "</option>");
            }
        }
        else{
            $("#type").append("<option value=\"\">please select</option>");
            for (var i = 0; i < en_advtype.length; i++) {
                $("#type").append("<option value=\"" + en_advtype[i].id + "\">" + en_advtype[i].name + "</option>");
            }
        }
        layui.form.render("select");
    }
    */
        
        function reback(){
            document.location.href="<%=ctxPath%>/system/adlist";
        }

		timer = null;

    function saveAdv(){
        formcheckMode.on('submit(savebtn)', function (data) {
			var shyjArea = "";
			clearTimeout(timer);
			var id = $("#id").val();
            id = isEmpty(id) ? "" : id;
            var type=$("#type").find("option:selected").val();
            var item = $('input[name=nrxz]:checked').val();
            var ggbt = $("#ggbt").val();
            var ljdz = $("#ljdz").val();
            var sdate = $("#sdate").val();
            var edate = $("#edate").val();
            if(sdate!="" && edate!=""){
                if(sdate>=edate){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("advinput.advertising.size")});
                    return;
                }
            }
            if((sdate!="" && edate=="") || (sdate=="" && edate!="")){
                if(sdate>=edate){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("advinput.advertising.null")});
                    return;
                }
            }
			//封面图片
            var zlcover = $("#finalImg").attr("src");
            if(isEmpty(type)){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("advinput.tips.type")});
                return;
            }
			if(isEmpty(ggbt)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("advinput.advertising.title")+get_lan("message.blank")});
				return;
			}
			else{
				if(ggbt.length >40){
					yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("advinput.title.length")});
					return;
				}
			}
            var formData=new FormData();
            var avatar ;
            var urlhead="http://";
            if(zlcover.indexOf(urlhead)!=-1){
                if(zlcover.indexOf("avatar.png")!=-1){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("advinput.advertising.cover")+get_lan("message.blank")});
                }
                else{
                    formData.append("cover_image","${adv.cover_image}");
                }
            }
            else{
                avatar = dataURLtoFile(zlcover, "file.png");
            }
            var curid="";
            if(item=='1') {
                if(type=='1'){
                    ljdz="";
                    curid=$("#curgoodsid").val();
                }
                else{
                    ljdz="";
                    curid=$("#curmovieid").val();
                }
            }
            //外部链接
            else{
                curid="";
            }
            //alert(curid);
            formData.append("file",avatar);
            formData.append("key","lueSGJZetyySpUndWjMBEg");
            formData.append("datatype","advcover");
            formData.append("id",id);
            formData.append("type",type);
            formData.append("relId",curid);
            formData.append("ggbt",ggbt);
            formData.append("ljdz",ljdz);
            formData.append("userId","${user.user_id}");
            formData.append("time_online",sdate);
            formData.append("time_offline",edate);
            $.ajax({
                type:"post",
                url:serviceUrl+'/movie/saveAdv',
                beforeSend:function(){},
                data:formData,
                cache: false, //上传文件不需要缓存
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success:function(res){
                    if(res.code!= 1000){
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.savefaild")});
                    }
                    else {
                        document.location.href="<%=ctxPath%>/system/adlist";
                    }
                }
            });
		});
    }

    function showOrhide(){
        var item = $('input[name=nrxz]:checked').val();
        var type=$("#type").find("option:selected").val();
        if(item=='1'){
            if(type=='1'){
                $("#spcxdiv").show();
                $("#dycxdiv").hide();
                $("#ljdzdiv").hide();
            }
            else{
                $("#spcxdiv").hide();
                $("#dycxdiv").show();
                $("#ljdzdiv").hide();
            }

        }
        //外接链接
        else{
            $("#spcxdiv").hide();
            $("#dycxdiv").hide();
            $("#ljdzdiv").show();
        }

    }

        function qrygoods(){
            var data = {};
            data["spmc"] = $("#spmc").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return data;}},
                {data: "goods_name",render: function(data, type, row, meta) {return data;}},
                {data: "introduce",render: function(data, type, row, meta) {return data;}},
                {data: "base_price",render: function(data, type, row, meta) {return data;}},
                {data: "goods_id",render: function(data, type, row, meta) {
                        var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning\" onclick=\"addGoodsSelect('"+row["goods_id"]+"')\">选择</button>";
                        return btnstr;
                    }}
            ];
            getTableDataServer("infoTable1",ctxPath+"/blindbox/qryPageGoods",columns,data,false);
        }

        function addGoodsSelect(goodid){
            $("#curgoodsid").val(goodid);
        }

        function qrymovies(){
            var data = {};
            data["gjc"] = $("#dymc").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "movie_name",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "areaname",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "typename",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "issue_date",render: function(data, type, row, meta) {return nulltostr(data);}},
                {data: "movie_id",render: function(data, type, row, meta) {
                        var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning\" onclick=\"addMoviesSelect('"+row["movie_id"]+"')\">选择</button>";
                        return btnstr;
                    }},
            ];
            getTableDataServer("infoTable2",ctxPath+"/movie/qryPageMovie",columns,data,false);
        }

        function addMoviesSelect(movieid){
            $("#curmovieid").val(movieid);
        }

</script>
</body>
</html>
