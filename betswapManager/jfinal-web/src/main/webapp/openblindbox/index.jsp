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
    <link rel="stylesheet" href="<%=ctxPath%>/openblindbox/css/style.css?v=${randomNum}" />
  </head>
  <body>
    <div class="indexPage">
      <div class="head">
        <i class="arrowIcon" onclick="backphone()"></i>
        <h1><lan set-lan="html:openblindbox.title">Virtual currency blind box</lan></h1>
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

    <div class="maskLayer" id="maskLayer1">
      <div class="confirmBox">
        <div class="content">
          <p>
            <!--
            Opening this blind box requires Rp ${price_rp} fee, are you sure to open
            it?-->
            <lan set-lan="html:openblindbox.zf">请出入资金密码</lan><br>
            <input type="password" id="zjmm" value="" style="width: 200px;font-size:16px">
          </p>
        </div>

        <div class="btnCont">
          <a id="cancleBtn" class="cancleBtn" href="javascript:;"><lan set-lan="html:openblindbox.cancel">Cancel</lan></a>
          <a class="confirmBtn" href="javascript:;" onclick="determine1()"><lan set-lan="html:openblindbox.determine">Determine</lan></a>
        </div>
      </div>
    </div>

    <!-- layer -->
    <div class="maskLayer" id="promptLayer">
      <div class="confirmBox">
        <div class="content">
          <p>
            <!--
             Please select one blind box-->
            <lan set-lan="html:openblindbox.select">请选择一个盲盒</lan>
          </p>
        </div>

        <div class="btnCont">
          <a class="confirmBtn" href="javascript:;" onclick="closepromp()"><lan set-lan="html:openblindbox.see">I see</lan></a>
        </div>
      </div>
    </div>

    <div class="maskLayer" id="warnLayer">
      <div class="confirmBox">
        <div class="content">
          <p>
            <!--
            The balance of account is insufficient-->
            <lan set-lan="html:openblindbox.bz">账户余额不足</lan>
          </p>
        </div>
        <div class="btnCont">
          <a class="confirmBtn"id="iseeBtn" href="javascript:;" onclick="closewarn()">I see</a>
        </div>
      </div>
    </div>
    <form method="get" id="sendform" action="<%=ctxPath%>/openblindbox/determine">
      <input type="hidden" name="os" value="${os}">
      <input type="hidden" name="price" value="${price_rp}">
    </form>
  <div id="p"></div>
  </body>
  <%@ include file="../common/resource_mh.jsp"%>
  <script src="<%=ctxPath%>/openblindbox/js/index.js?v=${randomNum}"></script>
  <script>
    var vlan = "${lan}";
    if(vlan==null||vlan=="") {
      $.tmCookie.setCookie("lan", "cn", 1000 * 60 * 60);
      vlan="cn";
    }

    initlan(vlan);
    function determine1() {
       var zjmm=$("#zjmm").val();
       $.ajax({
        type: "post",
        url: "<%=ctxPath%>/openblindbox/checkmoneypassword",
        data: {"zjmm": zjmm},
        success: function (data) {
          if (data.flg == true) {
              var url="<%=ctxPath%>/openblindbox/determine?lan="+vlan+"&os=${os}&price=${price_rp}&v="+ (new Date().getTime());
              window.location.href=url;
          }
          else {
              alert(get_lan("openblindbox.moneypwderr"));
          }
        }
       });
    }

    function determine() {
      $.ajax({
        type: "post",
        url: "<%=ctxPath%>/openblindbox/checkmoney",
        data: {"price": "${price_rp}"},
        success: function (data) {
        //complete: function (data) {
          //alert(data.flg);
          $('#maskLayer1').hide();
          //alert("1111");
          if (data.flg == true) {
              //var url="<%=ctxPath%>/openblindbox/determine?os=${os}&price=${price_rp}&v="+ (new Date().getTime());
              //window.location.href=url;
              document.getElementById("sendform").submit();
              //window.open(url, "_self");
          }
          else {
              $('#warnLayer').show();
          }
          //location.href="www.sina.com.cn";
        }
      });
    }

    function closewarn(){
      $('#warnLayer').hide();
    }

    function closepromp(){
      $('#promptLayer').hide();
    }

    function backphone(){
         //alert("${os}");
         <c:if test="${os=='ios'}">
            window.webkit.messageHandlers.popBack.postMessage({'key':'value'});
         </c:if>
         <c:if test="${os=='android'}">
            //var result =window.android.setToken();
            //document.getElementById("p").innerHTML=result;
             window.location.href="<%=ctxPath%>/openblindbox/backphone";//backphone作为返回安卓原生的特殊标志位，不能在其他链接路径中，出现同样的字符
         </c:if>
    }
  </script>
</html>
