<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>goods apply detail</title>
    <style>
    .ZTgreen{
    display: inline-block;
    background:#5cb85c;
    color: #fff;
    font-size: 12px;
    padding: 0 2px;
    border-radius: 2px;
    }
    .ZTred{
    display: inline-block;
    background: #d2322d;
    color: #fff;
    font-size: 12px;
    padding: 0 2px;
    border-radius: 2px;
    }
    .ZTorange{
    display: inline-block;
    background: #df850a;
    color: #fff;
    font-size: 12px;
    padding: 0 2px;
    border-radius: 2px;
    }
    .ZTblue{
    display: inline-block;
    background: #0d67d3;
    color: #fff;
    font-size: 12px;
    padding: 0 2px;
    border-radius: 2px;
    }

    .define-table{
        border-collapse:collapse;
        border-spacing:0;
        border-left:1px solid #888;
        border-top:1px solid #888;
    }
    .define-table th,.define-table td{
        border-right:1px solid #888;
        border-bottom:1px solid #888;
        padding:5px 15px;
    }
    .define-table th{
        font-weight:bold;background:#ccc;
    }
    </style>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:goodsapplydet.goods.detail">商品详情</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.category">商品类型</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.series_name}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.brand">商品品牌</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.brand}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.name">商品名称</lan>:</span>
                     <input type="hidden" id="recordid" value="${recordid}"/>
                     <input type="hidden" id="checkflg" value="${checkflg}"/>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.goods_name}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.cover">商品封面</lan>:</span>
                        <span class="imageupload">
                            <div id="view">
                                <a onclick="showimg('goods','goods_id','${goodsapply.goods_id}','cover')"><img id="finalImg" src="<%=serviceUrl%>/${goodsapply.cover}" width="100%" height="100%" /></a>
                            </div>
                        </span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.introduction">商品介绍</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.introduce}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.price">商品价格</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.base_price}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.unit">计量单位</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.measurement}</span>
                </div>
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.freight">商品运费</lan>:</span>
                    <span style="padding: 5px 0;display: inline-block;">${goodsapply.freight_bottom}~${goodsapply.freight_top}</span>
                </div>
                <c:if test="${goodsImgList!=null && fn:length(goodsImgList) > 0}">
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.graphic.introduction">图文介绍</lan>:</span>
                     <table border="0" width="100%">
                          <c:forEach var="goodsImg" items="${goodsImgList}" varStatus="item">
                              <c:choose>
                                  <c:when test="${item.index%4==0}">
                                      <tr><td  width="25%"><a onclick="showimg('goods_attach','id','${goodsImg.id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${goodsImg.url}" style="width: 300px;height: 300px"/></a></td>
                                  </c:when>
                                  <c:when test="${item.index%4==3}">
                                      <td  width="25%"><a onclick="showimg('goods_attach','id','${goodsImg.id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${goodsImg.url}" style="width: 300px;height: 300px"/></a></td></tr>
                                  </c:when>
                                  <c:otherwise>
                                      <td  width="25%"><a onclick="showimg('goods_attach','id','${goodsImg.id}','url')"><img class="product-image-photo" src="<%=serviceUrl%>/${goodsImg.url}" style="width: 300px;height: 300px"/></a></td>
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
                </c:if>
                <c:if test="${specsList!=null && fn:length(specsList) > 0}">
                    <div class="HBox">
                        <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.specs">定义的规格</lan>:</span>
                        <span style="padding: 5px 0;display: inline-block;">
                            <c:forEach var="specs" items="${specsList}" varStatus="item">
                                <span class="ZTorange">${specs.specs_name}</span>
                            </c:forEach>
                        </span>
                    </div>
                </c:if>
                <c:if test="${colorList!=null && fn:length(colorList) > 0}">
                <div class="HBox">
                    <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.goods.color">定义的颜色</lan>:</span>
                    <table border="0" width="100%">
                        <c:forEach var="color" items="${colorList}" varStatus="item">
                            <c:choose>
                                <c:when test="${item.index%4==0}">
                                    <tr><td  width="25%">${color.color_name}<br><a onclick="showimg('goods_define_color','id','${color.id}','color_image')"><img class="product-image-photo" src="<%=serviceUrl%>/${color.color_image}" style="width: 300px;height: 300px"/></a></td>
                                </c:when>
                                <c:when test="${item.index%4==3}">
                                    <td  width="25%">${color.color_name}<br><a onclick="showimg('goods_define_color','id','${color.id}','color_image')"><img class="product-image-photo" src="<%=serviceUrl%>/${color.color_image}" style="width: 300px;height: 300px"/></a></td></tr>
                                </c:when>
                                <c:otherwise>
                                    <td  width="25%">${color.color_name}<br><a onclick="showimg('goods_define_color','id','${color.id}','color_image')"><img class="product-image-photo" src="<%=serviceUrl%>/${color.color_image}" style="width: 300px;height: 300px"/></a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${colorys==4}">
                                <td  width="25%"></td>
                                <td  width="25%"></td>
                                <td  width="25%"></td>
                                <td  width="25%"></td></tr>
                            </c:when>
                            <c:when test="${colorys==3}">
                                <td  width="25%"></td>
                                <td  width="25%"></td>
                                <td  width="25%"></td></tr>
                            </c:when>
                            <c:when test="${colorys==2}">
                                <td  width="25%"></td>
                                <td  width="25%"></td></tr>
                            </c:when>
                            <c:when test="${colorys==1}">
                                <td  width="25%"></td></tr>
                            </c:when>
                            <c:otherwise>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>
                </c:if>
                <c:if test="${goodsdifList!=null && fn:length(goodsdifList) > 0}">
                    <div class="HBox">
                        <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.multiple.types">多种类型</lan>:</span>
                        <table class="define-table" width="100%">
                             <tr>
                                 <td  width="10%" align="center"><span style="font-weight: bold"><lan set-lan="html:actor.order">序号</lan></span></td>
                                 <td  width="20%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.color">颜色</lan></span></td>
                                 <td  width="20%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.specs">规格</lan></span></td>
                                 <td  width="30%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.price">定价</lan></span></td>
                                 <td  width="20%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.stock">库存</lan></span></td>
                             </tr>
                            <c:forEach var="dif" items="${goodsdifList}" varStatus="item">
                                <tr>
                                    <td align="center">${item.index+1}</td>
                                    <td align="center">${dif.color_name}</td>
                                    <td align="center">${dif.specs_name}</td>
                                    <td align="center">${dif.price}</td>
                                    <td align="center">${dif.stock_amount}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
                <c:if test="${goodspropList!=null && fn:length(goodspropList) > 0}">
                    <div class="HBox">
                        <span class="wwmc font-weight"><lan set-lan="html:goodsapplydet.additional.properties">附加属性</lan>:</span>
                        <table class="define-table" width="100%">
                            <tr>
                                <td  width="10%" align="center"><span style="font-weight: bold"><lan set-lan="html:movielist.order">序号</lan></span></td>
                                <td  width="30%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.attribute.name">属性名称</lan></span></td>
                                <td  width="70%" align="center"><span style="font-weight: bold"><lan set-lan="html:goodsapplydet.attribute.content">属性内容</lan></span></td>
                            </tr>
                            <c:forEach var="prop" items="${goodspropList}" varStatus="item">
                                <tr>
                                    <td align="center">${item.index+1}</td>
                                    <td align="center">${prop.prop_name}</td>
                                    <td align="center">${prop.prop_value}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
                <div class="HBox" style="display: block">
                    <span class="wwmc font-weight color-danger"><lan set-lan="html:goodsapplydet.xgs">限购数</lan>:</span>
                    <input type="text" id="xgs" style="width: 80px" value="">
                    <input type="checkbox" id="sfrm"><lan set-lan="html:goodsapplydet.sfrm">是否加入热门商品</lan>
                    <input type="checkbox" id="sftj"><lan set-lan="html:goodsapplydet.sftj">是否加入推荐商品</lan>
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
        if($("#checkflg").val()=="1"){
            $("#shyjdiv").show();
            $("#shbtn").show();
            $("#xqbtn").hide();
        }
        else{
            $("#shyjdiv").hide();
            $("#shbtn").hide();
            $("#xqbtn").show();
        }
    }

    function reback(){
        document.location.href="<%=ctxPath%>/system/dsgoodslist";
    }

    function backlist(){
        document.location.href="<%=ctxPath%>/system/goodslist";
    }

    function examine(result){
        var shyj=$("#shyj").val();
        var recordId=$("#recordid").val();
        var xgs=$("#xgs").val();
        var sfrm="0";
        var sftj="0"
        //if($("#sfrm").attr("checked")==true){
        if($("#sfrm").get(0).checked){
            sfrm="1" ;
        }
        //if($("#sftj").attr("checked")==true){
        if($("#sftj").get(0).checked){
            sftj="1" ;
        }
        if(result=='2'){
            if(shyj==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"s",content:get_lan("review.comments")+get_lan("message.blank")});
                return;
            }
        }
        else{
            if(xgs==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("goodsapplydet.xgs")+get_lan("message.blank")});
                return;
            }
            else{
                if(checkNumber(xgs)==false){
                    yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("goodsapplydet.xgs")+get_lan("error.mustbenumber")});
                    return;
                }
            }
        }
        //alert(sfrm);
        //return;
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/system/examGoods",
            data:{"recordId":recordId,"shyj":shyj,"result":result,"xgs":xgs,"sfrm":sfrm,"sftj":sftj},
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
