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
    <div class="prizePage">
      <div class="head">
        <!--<a href="javascript:;" onclick="backpage()"><i class="arrowIcon"></i></a>-->
        <h1><lan set-lan="html:openblindbox.title">Virtual currency blind box</lan></h1>
      </div>
      <canvas id="canvas">Canvas is not supported in your browser.</canvas>
      <div class="prize">
        <ul class="itemCont">
          <!--
          <li class="item">
            <div class="im">
              <img src="./image/pr1.png" alt="" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li class="item">
            <div class="im">
              <img src="./image/pr1.png" alt="" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li class="item">
            <div class="im">
              <img src="./image/pr1.png" alt="" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          <li class="item">
            <div class="im">
              <img src="./image/pr1.png" alt="" />
            </div>
            <div class="wz">
              <p>Rp 200.00</p>
              <p>USDT 0.04</p>
            </div>
          </li>
          -->
          <c:forEach var="goods" items="${goodslist}" varStatus="item">
            <li class="item">
              <div class="im">
                <img src="<%=serviceUrl%>/${goods.goods_cover}" />
              </div>
              <div class="wz">
                <p>Rp ${goods.base_price}</p>
                <p>≈${goods.base_price_usdt}USDT </p>
              </div>
            </li>
          </c:forEach>
        </ul>
      </div>
      <div class="pNum">${price}</div>
      <div class="tips">
        <p><lan set-lan="html:openblindbox.totalval">Total value：</lan>Rp ${goodsprice}
           <c:if test="${blindboxmould.tbt_count>0}">
            +${blindboxmould.tbt_count}TBT
           </c:if>
        </p>
      </div>

      <div class="btnCont">
        <a class="recyclBtn" href="javascript:;" onclick="recycle()"><lan set-lan="html:openblindbox.one">One key recycling</lan></a>
        <a class="confirmBtn" href="javascript:;" onclick="receipt()"><lan set-lan="html:openblindbox.receipt">Confirm receipt</lan></a>
      </div>
    </div>
    <%@ include file="../common/resource_mh.jsp"%>
    <script src="<%=ctxPath%>/openblindbox/js/stars.js?v=${randomNum}"></script>
    <script>

      initlan("${lan}");

        function backpage(){
           //alert("111");
           document.location.href="<%=ctxPath%>/openblindbox/reindex?lan=${lan}&os=${os}&price=${price}";
        }

        function recycle() {
          $.ajax({
            type: "post",
            url: "<%=ctxPath%>/openblindbox/recycle",
            data: {"blindboxid": "${blindboxid}"},
            success: function (data) {
              backpage();
            }
          });
        }

        function receipt() {
           document.location.href="<%=ctxPath%>/openblindbox/receipt?lan=${lan}&os=${os}&blindboxid=${blindboxid}&price=${price}";
        }
    </script>
  </body>
</html>
