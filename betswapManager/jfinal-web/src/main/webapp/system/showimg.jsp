<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!doctype html>
<html lang="en" class="fixed">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>image show</title>
<link href="<%=ctxPath%>/assets/img/favicon.ico" rel="shortcut icon" />
<style>
@font-face {
	font-family: 'FZDS';
	font-weight: normal;
	font-style: normal;
}
  .BoxHead{background: #0060c6;padding:10px;color:#fff;position: relative;}
  .buttonan{background:#88b93c; padding:5px;color: #fff;outline: none;margin: 2px;border: none;cursor: pointer;margin-right: 5px;border-radius:2px;}
  .hymain{background: #333;width: 100%;}
  .fujianyulan{width: 340px;font-size: 30px;font-family: "FZDS";position:absolute;left:50%;margin-left: -60px;}
  .padding10{padding:10px;display:flex;justify-content:center; position:absolute;top:45px;left:0px;}
  .closewindow{width: 16px;height: 16px;background: url(<%=ctxPath%>/assets/img/guanbi.png) no-repeat;float: right;margin:5px 5px 0 0;text-indent: -9999px}
</style>
</head>
<body style="margin: 0;background:#333">
<div class="BoxHead" id="titlediv">
    <button onclick="downloadfile()" class="buttonan"><lan set-lan="html:imgshow.download">下载文件</lan></button>
    <span class="fujianyulan"><lan set-lan="html:imgshow.detail">材料图片预览</lan></span>
    <button onclick="zoom(1)" class="buttonan"><lan set-lan="html:imgshow.big">放大</lan></button>
    <button onclick="zoom(-1)" class="buttonan"><lan set-lan="html:imgshow.small">缩小</lan></button>
    <button onclick="redo()" class="buttonan"><lan set-lan="html:imgshow.reset">还原</lan></button>
    <!--
<input type=button value="放大" onclick="zoom(1)" class="buttonan">
<input type=button value="缩小" onclick="zoom(-1)" class="buttonan">
<input type=button value="还原" onclick="redo()" class="buttonan">
    -->
    <a href="javascript:window.opener=null;window.open('','_self');window.close();"><lan set-lan="html:imgshow.close">关闭</lan></a>

</div>

<div class="hymain">
  <div class="padding10" id="imgdiv">
      <img id="curimg" src="${serviceUrl}/${imgurl}">
  </div>
</div>

<%@include file="/common/resource.jsp" %>
<script type="text/javascript">
    initlan();
    var vlan = $.tmCookie.getCookie('lan');
$(function(){
	$("#ypbgPdfIframe").height($(window).height()-$(".BoxHead").height()-$(".buttonan").height()-25);
    putImgPosition();
});

function closew(){
   window.close();
}


//iframe全屏显示
window.isflsgrn = false;//ie11以下是否进入全屏标志，true为全屏状态，false为非全屏状态
window.ieIsfSceen = false;//ie11是否进入全屏标志，true为全屏状态，false为非全屏状态
//跨浏览器返回当前 document 是否进入了可以请求全屏模式的状态
function fullscreenEnable(){
  var isFullscreen = document.fullscreenEnabled || window.fullScreen || document.mozFullscreenEnabled || document.webkitIsFullScreen;
  return isFullscreen;
}

function downloadfile(){
	window.location.href="${serviceUrl}/${imgurl}"
}



  //照片原始的大小
  var oimgh="${imgH}";
  var oimgw="${imgW}";
  //变化的变量
  //照片原始的大小
  var imgh="${imgH}";
  var imgw="${imgW}";
  //缩放的比例
  var xs=1.2;
  //加初始化自动缩放
  var titlediv=document.getElementById("titlediv");
  while((imgh>window.screen.height-titlediv.offsetHeight)||(imgw>window.screen.width)){
	  zoom(-1);  
  }
  oimgh=imgh;
  oimgw=imgw;
  
  
  function initwh(){
	 imgw=document.getElementById("curimg").width;
	 imgh=document.getElementById("curimg").height;
  }
	    
  function redo(){
     imgh=oimgh;
     imgw=oimgw;
	 document.getElementById("curimg").width=imgw;
	 document.getElementById("curimg").height=imgh;
	 putImgPosition();
  }
	    
  function zoom(flg){
	  if(flg==1){
		  imgh=Math.round(imgh*xs);
		  imgw=Math.round(imgw*xs);
	   }
	   else{
		  imgh=Math.round(imgh/xs);
		  imgw=Math.round(imgw/xs);
	   }
	   document.getElementById("curimg").width=imgw;
	   document.getElementById("curimg").height=imgh;
	   putImgPosition();
  }
  
  
  function putImgPosition(){
    //控制照片图层的显示
    //var titlediv=document.getElementById("titlediv");
    var titlediv_h=titlediv.offsetHeight;
    var imgdiv=document.getElementById("imgdiv");
    var imgdiv_h=imgdiv.offsetHeight;
    var imgdiv_w=imgdiv.offsetWidth;
    var window_h=window.screen.height;
    var window_w=window.screen.width;
    //alert("window_h="+window_h+",titlediv_h="+titlediv_h+",imgdiv_h="+imgdiv_h);
    var itop,iwidth;
    var cl=0;//调节常量
    if(window_h-titlediv_h-imgdiv_h>0){
    	itop=(window_h-titlediv_h-imgdiv_h)/2+titlediv_h-cl;
    }
    else{
    	itop=titlediv_h-cl
    }
    if(window_w-imgdiv_w>0){
       iwidth=(window_w-imgdiv_w)/2;
    }
    else{
    	iwidth=0;
    }
    $("#imgdiv").offset({top:itop,left:iwidth});
  }
  
  function getClientHeight(){
    var clientHeight=0;
    if(document.body.clientHeight&&document.documentElement.clientHeight){
      var clientHeight = (document.body.clientHeight<document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
    }
    else{
      var clientHeight = (document.body.clientHeight>document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
    }
    return clientHeight;
  }

</script>
</body>
</html>

