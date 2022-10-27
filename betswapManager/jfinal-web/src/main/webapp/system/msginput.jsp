<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>issue message</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
</head>
<style>
.imageupload{text-align:center;position:relative;display: inline-block;}
.cover-wrap{display:none;position:fixed;left:0;top:0;width:100%;height:100%;background: rgba(0, 0, 0, 0.4);z-index: 10000000;text-align:center;}
.coverimgage{width:900px;height:600px;margin:100px auto;background-color:#FFFFFF;overflow: hidden;border-radius:4px;}
#clipArea{margin:10px;height: 520px;}
.wrapImage{height:56px;line-height:36px;text-align: center;padding-top:8px;}
#clipBtn{width:100px;height: 36px;border-radius: 4px;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;text-align: center;line-height: 36px;outline: none;border: none}
.btnupload{width:70px;height:32px;border-radius: 4px;cursor: pointer;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;    vertical-align: top;text-align:center;line-height:32px;outline:none;margin-left:5px;position:relative;    display: inline-block; cursor: pointer}
#file{cursor:pointer;opacity:0;filter:alpha(opacity=0);width:100%;height:100%;position:absolute;top:0;left:0;cursor: pointer;}
.xgdh{width: 50%; display: inline-block;float: left}
.ml-xs{margin-left: 5px}
.mr-ml{margin-right: 10px}
.ml-m{margin-left: 20px}
.pointer{cursor: pointer}
.shzhilc{height: 30px; line-height: 30px; background: #c5d0f3;border: 1px solid #92a2d6;padding: 0 5px;margin-bottom: 10px;}
.liucheg{margin-bottom: 5px; width: 33%;float: left}
.liucheg100{margin-bottom: 5px;}
.pnane{padding: 2px;background: #eaeaea;}
.nameshe{width: 75px; text-align: right;display: inline-block; color: #1763e0}
.cursor{cursor: pointer;}

#tabsF {
    float:left;
    width:100%;
    background:#efefef;
    font-size:100%;
    line-height:normal;
    border-bottom:1px solid #666;
}
#tabsF ul {
    margin:0;
    padding:10px 10px 0 50px;
    list-style:none;
}
#tabsF li {
    display:inline;
    margin:0;
    padding:0;
}
#tabsF a {
    float:left;
    background:url("<%=ctxPath%>/assets/img/tableftF.gif") no-repeat left top;
    margin:0;
    padding:0 0 0 4px;
    text-decoration:none;
}
#tabsF a span {
    float:left;
    display:block;
    background:url("<%=ctxPath%>/assets/img/tabrightF.gif") no-repeat right top;
    padding:7px 15px 4px 6px;
    color:#666;
}
/* Commented Backslash Hack hides rule from IE5-Mac \*/
#tabsF a span {float:none;}
/* End IE5-Mac hack */
#tabsF a:hover span {
    color:#FFF;
}
#tabsF a:hover {
    background-position:0% -42px;
}
#tabsF a:hover span {
    background-position:100% -42px;
}
</style>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:msginput.system.message">系统消息</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                     <span class="wwmc color-danger"><lan set-lan="html:msginput.message.title">消息标题</lan></span>
                     <input type="hidden" id="messageid" value="${messageid}"/>
                     <input class="form-control" type="text" id="xxbt" style="display: inline-block;width: 30%;" maxlength=60 value="${msg.title}">
                </div>
                <div class="HBox" id="fmtpUploadBxo">
                        <span class="wwmc color-danger"><lan set-lan="html:msginput.message.picture">消息图片</lan></span>
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
                       <span class="wwmc color-danger"><lan set-lan="html:msginput.message.content">消息内容</lan></span>
                       <textarea class="form-control" id="xxnr" style="display: inline-block;width: 60%;height: 300px">${msg.content}</textarea>
                </div>
                <div class="center">
                    <button type="button" class="btn btn-sm btn-success" onclick="saveMsg()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:button.save">保存</lan></button>
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

    <script type="text/javascript">
        option= {
            aspectRatio: 1 / 0.563,//默认比例
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
            $("#chooseImg").change(function () {
                selectImg(this,1,0.563);
            });
            <c:if test="${not empty msg.message_id}">
               $("#finalImg").attr("src",serviceUrl+"${msg.pic_url}");
           </c:if>
    });

        
    function reback(){
       document.location.href="<%=ctxPath%>/system/msglist";
    }

	timer = null;
    function saveMsg(){
        formcheckMode.on('submit(savebtn)', function (data) {
			var shyjArea = "";
			clearTimeout(timer);
			var messageid = $("#messageid").val();
            messageid = isEmpty(messageid) ? "" : messageid;
            var xxbt = $("#xxbt").val();
            var xxnr = $("#xxnr").val();
			//封面图片
            var zlcover = $("#finalImg").attr("src");
            if(isEmpty(xxnr)){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("msginput.message.content")+get_lan("message.blank")});
                return;
            }
			if(isEmpty(xxbt)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("msginput.message.title")+get_lan("message.blank")});
				return;
			}
            var formData=new FormData();
            var avatar ;
            var urlhead="http://";
            if(zlcover.indexOf(urlhead)!=-1){
                if(zlcover.indexOf("avatar.png")!=-1){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("moviepub.cover")+get_lan("error.notfill")});
                }
                else{
                    formData.append("pic_url","${msg.pic_url}");
                }
            }
            else{
                avatar = dataURLtoFile(zlcover, "file.png");
            }
            formData.append("file",avatar);
            formData.append("key","lueSGJZetyySpUndWjMBEg");
            formData.append("datatype","msgcover");
            formData.append("messageid",messageid);
            formData.append("xxbt",xxbt);
            formData.append("xxnr",xxnr);
            formData.append("userId","${user.user_id}");
            $.ajax({
                type:"post",
                url:serviceUrl+'/movie/saveMsg',
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
                        document.location.href="<%=ctxPath%>/system/msglist";
                    }
                }
            });
		});
    }

</script>
</body>
</html>
