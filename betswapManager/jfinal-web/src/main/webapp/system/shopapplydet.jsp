<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>shop apply detail</title>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"> <lan set-lan="html:shopapplydet.shop.details">店铺详情</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                    <span class="wwmc font-weight"> <lan set-lan="html:shopapplydet.shop.name">店铺名称</lan>:</span>
                     <input type="hidden" id="recordid" value="${recordid}"/>
                     <input type="hidden" id="checkflg" value="${checkflg}"/>
                    <span style="padding: 5px 0;display: inline-block;">${shopapplymdl.name}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:shopapplydet.shop.cover">店铺封面</lan>:</span>
                        <span class="imageupload">
                            <div id="view">
                                <a onclick="showimg('merchant_apply_record','record_id','${shopapplymdl.record_id}','store_cover')"><img id="finalImg" src="<%=serviceUrl%>/${shopapplymdl.store_cover}" width="100%" height="100%" /></a>
                            </div>
                        </span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:shopapplydet.shop.introduction">店铺介绍</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${shopapplymdl.store_introduction}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:shopapplydet.business.scope">经营范围</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${shopapplymdl.content}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:shopapplydet.shop.qualification.certificate">店铺资质证明</lan>:</span><br>
                     <table border="0" width="100%">
                          <c:forEach var="qualifications" items="${qualificationsList}" varStatus="item">
                              <c:choose>
                                  <c:when test="${item.index%4==0}">
                                      <tr><td  width="25%"><a onclick="showimg('qualifications_attach','record_id','${qualifications.record_id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${qualifications.url}" style="width: 300px;height: 300px"/></a></td>
                                  </c:when>
                                  <c:when test="${item.index%4==3}">
                                      <td  width="25%"><a onclick="showimg('qualifications_attach','record_id','${qualifications.record_id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${qualifications.url}" style="width: 300px;height: 300px"/></a></td></tr>
                                  </c:when>
                                  <c:otherwise>
                                      <td  width="25%"><a onclick="showimg('qualifications_attach','record_id','${qualifications.record_id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${qualifications.url}" style="width: 300px;height: 300px"/></a></td>
                                  </c:otherwise>
                              </c:choose>
                          </c:forEach>
                          <c:choose>
                             <c:when test="${bgys==4}">
                                 <td  width="25%"></td>
                                 <td  width="25%"></td>
                                 <td  width="25%"></td>
                                 <td  width="25%"></td></tr>
                             </c:when>
                             <c:when test="${bgys==3}">
                                 <td  width="25%"></td>
                                 <td  width="25%"></td>
                                 <td  width="25%"></td></tr>
                             </c:when>
                              <c:when test="${bgys==2}">
                                  <td  width="25%"></td>
                                  <td  width="25%"></td></tr>
                              </c:when>
                              <c:when test="${bgys==1}">
                                  <td  width="25%"></td></tr>
                              </c:when>
                             <c:otherwise>
                                 </tr>
                             </c:otherwise>
                         </c:choose>
                     </table>

                </div>
                   <!--
                   <div class="related-product-area">
                       <span style="font-size: 14px;font-weight: bold;">店铺资质证明:</span>
                       <div class="related-products-pro">
                           <div class="slider-items-products">
                               <div id="related-product-slider" class="product-flexslider hidden-buttons mb-0">
                                   <div class="slider-items slider-width-col4">
                                       <c:forEach var="qualifications" items="${qualificationsList}" varStatus="item">
                                       <div class="product-item">
                                           <div class="teamer">
                                               <div class="product-thumbnail">
                                                   <a class="product-item-photo">
                                                   <img class="product-image-photo" src="<%=serviceUrl%>/${qualifications.url}" style="width: 300px;height: 300px"/>
                                                   </a></div>
                                           </div>
                                       </div>
                                       </c:forEach>
                                   </div>
                               </div>
                           </div>
                       </div>
                   </div>-->
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:shopapplydet.shop.divide">购物提成比例</lan>:</span>
                       <div id="bl_xs" style="display: block">${shopapplymdl.platform_divide}%</div>
                       <div id="bl_ll" style="display: block"><input class="form-control" id="gwpttc" style="width:80px;">%</div>
                </div>
                <div class="HBox" id="shyjdiv" style="display: block">
                    <span class="wwmc font-weight color-danger"><lan set-lan="html:review.comments">审核意见</lan>:</span>
                    <textarea class="form-control" id="shyj" style="display: inline-block;width: 55%;height: 60px"></textarea>
                </div>

                <div class="center" id="shbtn" style="display: block">
                    <button type="button" class="btn btn-sm btn-success" onclick="examine('1')"><lan set-lan="html:button.adopt">通过</lan></button>
                    <button type="button" class="btn btn-sm btn-danger" onclick="examine('2')"><lan set-lan="html:button.reject">退回</lan></button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="reback()"><lan set-lan="html:button.back.pending">返回待审列表</lan></button>
                </div>

                 <div class="center" id="xqbtn" style="display: none">
                    <button type="button" class="btn btn-sm btn-primary" onclick="backlist()"><lan set-lan="html:button.back.approved">返回已审列表</lan></button>
                 </div>
            </div>
            <!--  <div class="clear"></div>-->
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>
    <script type="text/javascript">

    initlan();
    var vlan = $.tmCookie.getCookie('lan');

    $(function(){
        $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
        });
        $(".close2").on('click',function(){
                $(".zhezhao1").hide();
        });
        $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        initshow();
    });

    function initshow(){
        //审核模式下
        if($("#checkflg").val()=="1"){
            $("#shyjdiv").show();
            $("#shbtn").show();
            $("#xqbtn").hide();
            $("#bl_xs").hide();
            $("#bl_ll").show();
        }
        else{
            $("#shyjdiv").hide();
            $("#shbtn").hide();
            $("#xqbtn").show();
            $("#bl_xs").show();
            $("#bl_ll").hide();
        }
    }

    function reback(){
        document.location.href="<%=ctxPath%>/system/dsshoplist";
    }

    function backlist(){
        document.location.href="<%=ctxPath%>/system/shoplist";
    }

    function examine(result){
        var shyj=$("#shyj").val();
        var recordId=$("#recordid").val();
        var gwpttc=$("#gwpttc").val();
        if(result=='2'){
            if(shyj==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("review.comments")+get_lan("message.blank")});
                return;
            }
        }
        else{
           if(gwpttc==""){
               yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.blank")});
               return;
           }
           else{
               if(checkNumber(gwpttc.replace(".",""))==false){
                   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.number")});
                   return;
               }
               else{
                   //var tf=float.Parse(gwpttc);
                   var tf=new Decimal(gwpttc)
                   if(tf>10||tf<0){
                       yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("shopapplydet.shop.divide")+get_lan("message.unreasonable")});
                       return;
                   }
               }
           }
        }
        //alert(recordId);
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/system/examShop",
            data:{"recordId":recordId,"shyj":shyj,"result":result,"gwpttc":gwpttc},
            success:function(data){
                if(data.flg == true){
                    reback();
                }
                else{
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:data.message});
                }
            }
        });
    }
    /*
    function showimg(imgpath){
       var imgurl=imgpath+"";
       var serviceUrl="<%=serviceUrl%>";
       val="<%=ctxPath%>/system/imageShow"
       window.open(url);
    }
    */

    function showimg(tbl,keyfld,keyid,urlfld){
        var url="<%=ctxPath%>/system/imageShow?tbl="+tbl+"&keyid="+keyid+"&urlfld="+urlfld+"&serviceUrl=<%=serviceUrl%>&keyfld="+keyfld;
        window.open(url);
    }

</script>
</body>
</html>
