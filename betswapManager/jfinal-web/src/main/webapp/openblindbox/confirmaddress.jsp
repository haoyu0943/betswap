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
  <body class="userBody">
    <div class="confirmAddressPage">
      <div class="head">
        <i class="arrowIcon" onclick="javascript:history.back(-1);"></i>
        <h1><lan set-lan="html:openblindbox.confirmaddr">Confirm receiving address</lan></h1>
      </div>

      <div class="tips">
        <!--<p>The goods in the blind box will be sent to the following address. Please confirm the receiving address</p>-->
        <p><lan set-lan="html:openblindbox.confirm">盲盒中的商品将要发送到下列地址. 请确认收货地址</lan></p>
      </div>

      <div class="addressList">
        <ul class="addItemCont">
          <li class="addItem defaultItem curItem">
            <p class="top">
              <span class="username"> ${addr.name}</span>
              <span class="tel"> ${addr.phone}</span>
              <c:if test="${addr.default_key==true}">
              <span class="lable"> Default </span>
              </c:if>
              <c:if test="${addr.default_key==false}">
                <span class="lable">${addr.typename}</span>
              </c:if>
            </p>
            <p class="address">${addr.user_address}</p>
            <i class="editIcon"></i>
          </li>
        </ul>
      </div>

      <div class="btnCont">
        <a class="addBtn" href="javascript:;" onclick="crtorder()">
          <span class="icon">OK</span></a>
      </div>
    </div>
    <%@ include file="../common/resource_mh.jsp"%>
    <script>
      initlan("${lan}");
        function crtorder(){
          $.ajax({
            type: "post",
            url: "<%=ctxPath%>/openblindbox/crtOrder",
            data: {"blindboxid": "${blindboxid}","addressid":"${addr.address_id}"},
            success: function (data) {
               backpage();
            }
          });
        }

        function backpage(){
           document.location.href="<%=ctxPath%>/openblindbox/reindex?lan=${lan}&os=${os}&price=${price}";
        }
    </script>
  </body>
</html>
