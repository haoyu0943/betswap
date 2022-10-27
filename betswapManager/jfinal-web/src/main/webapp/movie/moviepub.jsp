<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>issue movie</title>
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
                    <li><a href="<%=ctxPath%>/movie/crtOreditMovie?movieId=${movieId}" class="active-li"><span><lan set-lan="html:moviemenu.info">电影信息</lan></span></a></li>
                    <c:if test="${not empty movie.movie_id}">
                        <li><a href="<%=ctxPath%>/movie/movieActor?movieId=${movieId}"><span><lan set-lan="html:moviemenu.actor">电影参影人员</lan></span></a></li>
                        <li><a href="<%=ctxPath%>/movie/rolePurchase?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.purchase">抢购规则设置</lan></span></a></li>
                        <li><a href="<%=ctxPath%>/movie/rolePresell?movieId=${movieId}" ><span><lan set-lan="html:moviemenu.presell">预售规则设置</lan></span></a></li>
                        <!--<li><a href="<%=ctxPath%>/movie/fund?movieId=${movieId}"><span><lan set-lan="html:moviemenu.fund">影视基金设置</lan></span></a></li>-->
                    </c:if>
                    <div class="clear"></div>
                </ul>
            </div>
            <div class="page-inner">
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.area">区域</lan></span>
                    <span>
                          <input type="hidden" id="movieId" value="${movieId}"/>
                          <select class="form-control" style="width: 200px;display: inline-block" id="area">
                              <option value=""></option>
                              <c:forEach var="moviearea" items="${moviearealist}" varStatus="index">
                                  <option value="${moviearea.code}">${moviearea.dic_describe}</option>
                              </c:forEach>
                          </select>
                    </span>
                </div>
                <!--
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.type">理财类型</lan></span>
                    <span>
                          <select class="form-control" style="width: 200px;display: inline-block" id="type">
                              <option value=""></option>
                              <c:forEach var="moviefinancingtype" items="${moviefinancingtypelist}" varStatus="index">
                                  <option value="${moviefinancingtype.code}">${moviefinancingtype.dic_describe}</option>
                              </c:forEach>
                          </select>
                    </span>
                </div>
                -->
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.name">电影名</lan></span>
                    <input class="form-control" type="text" id="dym" style="display: inline-block;width: 55%;" maxlength=60 value="${movie.movie_name}">
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.type">电影类型</lan></span>
                    <span>
                          <select class="form-control" style="width: 200px;display: inline-block" id="movietype">
                              <option value=""></option>
                              <c:forEach var="movietype" items="${movietypelist}" varStatus="index">
                                  <option value="${movietype.code}">${movietype.dic_describe}</option>
                              </c:forEach>
                          </select>
                    </span>
                </div>
                <div class="HBox" id="videoUploadBxo" >
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.video">预览视频</lan></span>
                        <input type="file" id="videoUpload" name="videoUpload" accept=".mp4" style="display:none;"/>
                        <span id="videoUploadmc" style="padding: 5px 0;display: inline-block"></span>
                        <a href="javascript:void(0);" class="color-success" style="padding: 5px 0">
                        	<img src="<%=ctxPath%>/assets/img/tihuan.png" >
                            <span id="videoUploadBtn"><lan set-lan="html:moviepub.upload">上传</lan></span>
                        </a>
                        <img src="<%=ctxPath%>/assets/img/loading1.gif" style="display:none;" id="vedioLoading">
                        <input type="hidden" id="framefileurl" value="${movie.video_image}">
                    <span style="color: #aaa;padding: 5px 0"><lan set-lan="html:moviepub.info">（支持mp4视频格式，大小不超过200M）</lan></span>
                 </div>
                <div class="HBox" id="fmtpUploadBxo2">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.videocover">视频封面</lan></span>
                    <span class="imageupload">
                            <div class="cover-wrap">
                                <div class="coverimgage">
                                    <div id="clipArea2"></div>
                                    <div class="wrapImage">
                                        <button id="clipBtn2"><lan set-lan="html:button.save">保存</lan></button>
                                    </div>
                                </div>
                            </div>
                            <div id="view2">
                            	<img id="finalImg2" src="<%=ctxPath%>/assets/img/avatar.png" style="width: 270px;height: 180px"/>
                            </div>
                            <div class="btnupload">
                                <span id="replaceImg2" style="width: 70px;height: 32px;display: inline-block;"><lan set-lan="html:button.clip">裁剪上传</lan></span>
                                <!--<button id="replaceImg" style="width: 70px;height: 32px;display: inline-block;" type="button">裁剪上传</button>-->
                            </div>
                        </span>
                </div>
                <div class="HBox" id="fmtpUploadBxo">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.cover">电影封面</lan></span>
                        <span class="imageupload">
                            <div class="cover-wrap">
                                <div class="coverimgage">
                                    <div id="clipArea"></div>
                                    <div class="wrapImage">
                                        <button id="clipBtn"><lan set-lan="html:button.save">保存</lan></button>
                                    </div>
                                </div>
                            </div>
                            <div id="view1">
                            	<img id="finalImg" src="<%=ctxPath%>/assets/img/avatar.png" style="width: 150px;height: 240px"/>
                            </div>
                            <div class="btnupload">
                                <span id="replaceImg" style="width: 70px;height: 32px;display: inline-block;"><lan set-lan="html:button.clip">裁剪上传</lan></span>
                                <!--<button id="replaceImg" style="width: 70px;height: 32px;display: inline-block;" type="button">裁剪上传</button>-->
                            </div>
                        </span>
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.review">电影描述</lan></span>
                    <textarea class="form-control" id="dyms" style="display: inline-block;width: 55%;height: 60px">${movie.movie_introduce}</textarea>
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.time">发行时间</lan></span>
                    <input type="text" style="width:220px" autocomplete="off" id="fxsj" class="form-control" value="${movie.issue_date}"
                           onClick="laydate({format: 'YYYY-MM-DD',elem:'#fxsj'})">
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.numb">发行量</lan></span>
                    <input type="text" id="fxl" style="width:220px" maxlength=60 value="${movie.purchase_limit}">张
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.price">单价</lan></span>
                    <input type="text" id="dj" style="width:220px" maxlength=60 value="${movie.per_price}">Rp
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:moviepub.score">影评分</lan></span>
                    <!--<input type="text" id="ps" style="width:220px" maxlength=60 value="${movie.score}">Rp-->
                    <select class="form-control" style="width: 200px;display: inline-block" id="score">
                         <option value=""></option>
                         <option value="1">1</option>
                         <option value="2">2</option>
                         <option value="3">3</option>
                         <option value="4">4</option>
                         <option value="5">5</option>
                    </select>
                </div>
                <div class="center">
                    <button type="button" class="btn btn-sm btn-success" onclick="saveZlwz()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:button.save">保存</lan></button>
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
    <script type="text/javascript" src="<%=ctxPath%>/js/cropper-two.js"></script>

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
                if(divflg=="1") {
                    selectImg(this, 1, 1.6);
                }
                else{
                    selectImg(this, 1.5, 1);
                }
            });
            <c:if test="${not empty movie.movie_id}">
               //alert(serviceUrl+"${movie.cover_image}");
               $("#finalImg").attr("src",serviceUrl+"${movie.cover_image}");
               $("#finalImg2").attr("src",serviceUrl+"${movie.video_image}");
               $("#area").val("${movie.area_flg}");
               $("#type").val("${movie.type_flg}");
               $("#movietype").val("${movie.movie_type}");
               var iscor=parseInt("${movie.score}");
               $("#score").val(iscor);
               vediourl="${movie.preview_video}";
               vediofile="${movie.video_file}";
               $("#videoUploadmc").text(vediofile);
           </c:if>
    });



    layui.use('laydate', function(){
        var $ = layui.jquery,
            laydate = layui.laydate,upload = layui.upload
            ,layer = layui.layer,form = layui.form;

        upload.render({
            elem: '#videoUploadBtn'
            ,url: serviceUrl+'/file/url/uploadOther',
            accept:'video'
            ,before: function(obj){
                obj.preview(function(index, file, result){
                    var size = file.size;
                    var videosize=100;
                    if(size > (videosize * 1024 * 1024)){
                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("error.filesize")+videosize+"MB"});
                        $("#vedioLoading").hide();
                        return false;
                    }
                    vediofile=file.name;
                    $("#videoUploadmc").text(file.name);
                    $("#vedioLoading").show();
                });
                this.data={'key':"lueSGJZetyySpUndWjMBEg",'type':"movievedio"};
            }
            ,done: function(res){
                if(res.code != 1000){
                    return layer.msg(get_lan("error.uploadfaild"));
                }
                $("#vedioLoading").hide();
                var wjlj=res.data.fileUrl;
                var framefileurl=res.data.framefileurl;
                //$("#framefileurl").val(framefileurl);
                vediourl=wjlj;
                $("#videoUpload").data("wjlj",wjlj);
                $('#demoText').html('');
            }
            ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">'+get_lan("error.uploadfaild")+'</span>');
            }
        });

    });
    /*
    function initselect(){
        if(vlan=="cn") {
            $("#area").append("<option value=\"\">全部</option>");
            for (var i = 0; i < cn_areatype.length; i++) {
                $("#area").append("<option value=\"" + cn_areatype[i].id+ "\">" + cn_areatype[i].name + "</option>");
            }
            $("#type").append("<option value=\"\">全部</option>");
            for (var i = 0; i < cn_movietype.length; i++) {
                $("#type").append("<option value=\"" + cn_movietype[i].id + "\">" + cn_movietype[i].name + "</option>");
            }
        }
        else{
            $("#area").append("<option value=\"\">all</option>");
            for (var i = 0; i < en_areatype.length; i++) {
                $("#area").append("<option value=\"" + en_areatype[i].id+ "\">" + en_areatype[i].name + "</option>");
            }
            $("#type").append("<option value=\"\">all</option>");
            for (var i = 0; i < en_movietype.length; i++) {
                $("#type").append("<option value=\"" + en_movietype[i].id + "\">" + en_movietype[i].name + "</option>");
            }
        }
        layui.form.render("select");
    }
    */
        
        function reback(){
        	//window.history.back(-1);
            document.location.href="<%=ctxPath%>/movie"
        }

		timer = null;

    function saveZlwz(){
        formcheckMode.on('submit(savebtn)', function (data) {
			var shyjArea = "";
			clearTimeout(timer);
			var movieId = $("#movieId").val();
            movieId = isEmpty(movieId) ? "" : movieId;
			var area=$("#area").find("option:selected").val();
            //var type=$("#type").find("option:selected").val();
            var score=$("#score").find("option:selected").val();
            var movietype=$("#movietype").find("option:selected").val();
            var dym = $("#dym").val();
            var dyms = $("#dyms").val();
            var fxsj = $("#fxsj").val();
            var fxl = $("#fxl").val();
            var dj = $("#dj").val();
            //var framefileurl=$("#framefileurl").val();
            //var videowjlj=$("#videoUpload").data("wjlj");
			//封面图片
            var zlcover = $("#finalImg").attr("src");
            var videocover = $("#finalImg2").attr("src");
			if(isEmpty(area)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.areaselect")});
				return;
			}
            //if(isEmpty(type)){
                //yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.areaselect")});
                //return;
            //}
            if(isEmpty(movietype)){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.typeselect")});
                return;
            }
            if(isEmpty(score)){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.score")+get_lan("error.notfill")});
                return;
            }
			if(isEmpty(dym)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.name")+get_lan("error.notfill")});
				return;
			}
			else{
				if(dym.length >80){
					yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.namelength")});
					return;
				}
			}
			//if(zlcover.indexOf("avatar.png")>-1&&isEmpty($("#tailoringImg").attr("src"))) {
				//yyAlert({title:"提示",style:"s",content:"请添加电影封面图片"});
				//return;
			//}
			if(isEmpty(vediourl)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.video")+get_lan("error.notfill")});
				return;
			}
			if(isEmpty(dyms)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.review")+get_lan("error.notfill")});
				return;
			}
            if(isEmpty(fxsj)) {
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.time")+get_lan("error.notfill")});
                return;
            }
            //console.log("fxl="+fxl);
            if(isEmpty(fxl)) {
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.numb")+get_lan("error.notfill")});
                return;
            }
            else{
                if(checkNumber(fxl.trim())==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.numb")+get_lan("error.mustbenumber")});
                    return;
                }
            }
            //console.log("dj="+dj);
            //console.log("dj1="+dj.trim().replace(".",""));
            if(isEmpty(dj)) {
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.price")+get_lan("error.notfill")});
                return;
            }
            else{
                if(checkNumber(dj.trim().replace(".",""))==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("moviepub.price")+get_lan("error.mustbenumber")});
                    return;
                }
            }
            var formData=new FormData();
            //console.log("zlcover="+zlcover);
            //console.log(zlcover);
            var avatar,avatar2 ;
            var urlhead="http://";
            if(zlcover.indexOf(urlhead)!=-1){
                if(zlcover.indexOf("avatar.png")!=-1){
                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("moviepub.cover")+get_lan("error.notfill")});
                }
                else{
                    formData.append("cover_image","${movie.cover_image}");
                }
            }
            else{
                avatar = dataURLtoFile(zlcover, "file.png");
            }
            if(videocover.indexOf(urlhead)!=-1){
                if(videocover.indexOf("avatar.png")!=-1){
                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("moviepub.videocover")+get_lan("error.notfill")});
                }
                else{
                    formData.append("video_image","${movie.video_image}");
                }
            }
            else{
                avatar2 = dataURLtoFile(videocover, "file2.png");
            }
            formData.append("file",avatar);
            formData.append("key","lueSGJZetyySpUndWjMBEg");
            formData.append("datatype","moviecover");
            formData.append("movieId",movieId);
            formData.append("dym",dym);
            formData.append("dyms",dyms);
            formData.append("videourl",vediourl);
            formData.append("fxsj",fxsj);
            formData.append("score",score);
            formData.append("area",area);
            //formData.append("type","0");
            formData.append("movietype",movietype);
            formData.append("fxl",fxl);
            formData.append("dj",dj);
            formData.append("userId","${user.user_id}");
            formData.append("videoFile",vediofile);
            formData.append("videocoverfile",avatar2);

            $.ajax({
                type:"post",
                url:serviceUrl+'/movie/saveMovie',
                beforeSend:function(){},
                data:formData,
                cache: false, //上传文件不需要缓存
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success:function(res){
                    if(res.code!= 1000){
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("error.savefaild")});
                    }
                    else {
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("sucess.save")});
                        //alert(res.data.movieId);
                        setTimeout("toEdit('"+res.data.movieId+"')",3000);

                    }
                }
            });
		});
    }

    function toEdit(movieId){
        document.location.href="<%=ctxPath%>/movie/crtOreditMovie?movieId="+movieId;
    }
</script>
</body>
</html>
