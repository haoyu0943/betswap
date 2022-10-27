<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Blind box</title>
    <script src="<%=ctxPath%>/openblindbox/js/jquery.min.js"></script>
    <script src="<%=ctxPath%>/openblindbox/js/rem.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/openblindbox/css/style.css" />
  </head>
  <body>
    <div class="indexPage">
      <div class="head">
        <i class="arrowIcon" onclick="backphone()"></i>
        <h1>Virtual currency blind box</h1>
      </div>

      <div class="boxContainer">
        <div class="title">
          <p>
            <span class="lb">Rp</span>
            <span class="val">${price_rp}</span>
          </p>
          <p>
            <span class="lb">≈USDT</span>
            <span class="val">${price_usdt}</span>
          </p>
        </div>
        <ul class="itemCont" id="blindBox">
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>

          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <!--<li class="endItem"></li>-->
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>

          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>

          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
          <li class="item"><span class="num">${price_rp}</span></li>
        </ul>
        <div class="btnCont">
          <a class="confirmBoxBtn" id="confirmBoxBtn" href="javascript:;"></a>
        </div>
      </div>

      <div id="goods" class="goods">
        <ul>
           <c:forEach var="goods" items="${goodslist}" varStatus="item">
             <li>
               <div class="img">
                 <img src="<%=serviceUrl%>/${goods.goods_cover}" />
               </div>
               <div class="wz">
                 <p>Rp ${goods.base_price}</p>
                 <p>≈${goods.base_price_usdt}USDT </p>
               </div>
             </li>
           </c:forEach>
          <!--
          <li>
            <div class="img">
              <img src="./image/1.jpg" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li>
            <div class="img">
              <img src="./image/2.jpg" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li>
            <div class="img">
              <img src="./image/3.jpg" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li>
            <div class="img">
              <img src="./image/4.jpg" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          -->

        </ul>
      </div>
    </div>
    <!-- layer -->
    <div class="maskLayer" id="promptLayer">
      <div class="confirmBox">
        <div class="content">
          <p>
            <!--There is no blind box at that price.-->
            <lan set-lan="html:openblindbox.no">没有这个价位下的盲盒.</lan>
          </p>
        </div>
        <div class="btnCont">
          <a class="confirmBtn" href="javascript:;" onclick="backphone()">I see</a>
        </div>
      </div>
    </div>

  <div id="p"></div>
  </body>
  <%@ include file="../common/resource_mh.jsp"%>
  <script>
    initlan();
    $("#promptLayer").show();


    function backphone(){
         //alert("${os}");
         <c:if test="${os=='ios'}">
            window.webkit.messageHandlers.popBack.postMessage({'key':'value'});
         </c:if>
         <c:if test="${os=='android'}">
             window.location.href="<%=ctxPath%>/openblindbox/backphone";//backphone作为返回安卓原生的特殊标志位，不能在其他链接路径中，出现同样的字符
         </c:if>
    }
  </script>
</html>
