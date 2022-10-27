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
    <link rel="stylesheet" href="<%=ctxPath%>/assets/dist/summernote.css" />
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
            <div class="pageheader"><lan set-lan="html:msginput.system.protocol">协议</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                     <span class="wwmc color-danger"><lan set-lan="html:msginput.message.title">消息标题</lan></span>
                     <input type="hidden" id="id" value="${id}"/>
                     <input class="form-control" type="text" id="xxbt" style="display: inline-block;width: 30%;" maxlength=60 value="${msg.title}">
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:movielist.order">序号</lan></span>
                    <yy:if test="${not empty id }">
                        <input disabled class="form-control" type="text" id="code" style="display: inline-block;width: 30%;" maxlength=60 value="${msg.type_flag}">
                    <yy:else/>
                        <input class="form-control" type="text" id="code" style="display: inline-block;width: 30%;" maxlength=60 value="${msg.type_flag}">
                    </yy:if>
                </div>
                <div class="HBox">
                    <span class="wwmc color-danger"><lan set-lan="html:movielist.signtype">是否需要签署</lan></span>
                    <c:choose>
                        <c:when test="${msg.if_need_sign==1}">
                            <input name="ifHaveCondition" type="radio" value="0"><lan set-lan="html:movielist.signno">不需要</lan>
                            <input name="ifHaveCondition" type="radio" value="1" checked><lan set-lan="html:movielist.signok">需要</lan>
                        </c:when>
                        <c:otherwise>
                            <input name="ifHaveCondition" type="radio" value="0" checked><lan set-lan="html:movielist.signno">不需要</lan>
                            <input name="ifHaveCondition" type="radio" value="1"><lan set-lan="html:movielist.signok">需要</lan>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="HBox">
                       <span class="wwmc color-danger"><lan set-lan="html:msginput.message.content">消息内容</lan></span>
                       <!--<textarea class="form-control" id="xxnr" style="display: inline-block;width: 60%;height: 300px">${msg.content}</textarea>-->
                      <div id="fwbk" style="display: block"><div class="summernote" ></div></div>
                      <input class="form-control" type="text" id="weburl" style="display: none;width: 30%;" maxlength=60 value="${msg.web_url}">
                </div>
                <div class="center">
                    <button type="button" class="btn btn-sm btn-success" onclick="saveProtocol()" lay-submit="" lay-filter="savebtn"><lan set-lan="html:button.save">保存</lan></button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="reback()"><lan set-lan="html:button.back">返回列表</lan></button>
                </div>
            </div>
            <!--  <div class="clear"></div>-->
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>

    <script type="text/javascript" src="<%=ctxPath%>/js/cropper.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/js/cropper-zj.js"></script>

    <script type="text/javascript" src="<%=ctxPath%>/js/bootstrap/bootstrap.js"></script>

    <script type="text/javascript" src="<%=ctxPath%>/assets/dist/summernote.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/assets/dist/lang/summernote-zh-CN.js"></script>

    <script type="text/javascript">
    initlan();
    var vlan = $.tmCookie.getCookie('lan');
    $(function(){
        $('.summernote').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN'
        });
    });
    function reback(){
       document.location.href="<%=ctxPath%>/system/protocollist";
    }

    //查询内容
    $.ajax({
        type:"post",
        url:"<%=ctxPath%>/system/qryProtocolContent",
        data:{"id":"${id}"},
        success:function(res){
            $('.summernote').summernote('code', res.content);
        }
    });

    function saveProtocol(){
        formcheckMode.on('submit(savebtn)', function (data) {
			var id = $("#id").val();
            id = isEmpty(id) ? "" : id;
            var xxbt = $("#xxbt").val();
            var code = $("#code").val();
            var xxnr=$('.summernote').summernote('code');
            var sign=$('input:radio[name="ifHaveCondition"]:checked').val();;
            if(isEmpty(xxnr)){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("msginput.message.content")+get_lan("message.blank")});
                return;
            }
			if(isEmpty(xxbt)){
				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("msginput.message.title")+get_lan("message.blank")});
				return;
			}
			if(id==""){
                $.ajax({
                    type:"post",
                    url:'<%=ctxPath%>/system/checkProtocolCode',
                    data:{
                        "typeFlag":code
                    },
                    success:function(res){
                        if(res.flag == false){
                            yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("protocol.code.existence")});
                        }else {
                            $.ajax({
                                type:"post",
                                url:'<%=ctxPath%>/system/saveProtocol',
                                data:{
                                    "id":id,
                                    "userId":"${user.user_id}",
                                    "title":xxbt,
                                    "typeFlag":code,
                                    "sign":sign,
                                    "content":xxnr
                                },
                                success:function(res){
                                    if(res.code!= 1000){
                                        yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.savefaild")});
                                    }
                                    else {
                                        document.location.href="<%=ctxPath%>/system/protocollist";
                                    }
                                }
                            });
                        }
                    }
                });
            }else{
                $.ajax({
                    type:"post",
                    url:'<%=ctxPath%>/system/saveProtocol',
                    data:{
                        "id":id,
                        "userId":"${user.user_id}",
                        "title":xxbt,
                        // "typeFlag":code,
                        "sign":sign,
                        "content":xxnr
                    },
                    success:function(res){
                        if(res.code!= 1000){
                            yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.savefaild")});
                        }
                        else {
                            document.location.href="<%=ctxPath%>/system/protocollist";
                        }
                    }
                });
            }


		});
    }

</script>
</body>
</html>
