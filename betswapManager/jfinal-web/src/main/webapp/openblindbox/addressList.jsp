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
    <div class="addressListPage">
      <div class="head">
        <i class="arrowIcon" onclick="javascript:history.back(-1);"></i>
        <h1><lan set-lan="html:openblindbox.addrlist">Address List</lan></h1>
      </div>

      <div class="addressList">
        <ul class="addItemCont">
            <c:forEach var="addr" items="${addrlist}" varStatus="item">
              <c:if test="${addr.default_key==true}">
              <li class="addItem defaultItem curItem" data-id="${addr.address_id}">
                <p class="top">
                  <span class="username">${addr.name}</span>
                  <span class="tel"> ${addr.phone}</span>
                  <span class="lable"> Default </span>
                </p>
                <p class="address">${addr.user_address}</p>
                <i class="editIcon"></i>
              </li>
              </c:if>
              <c:if test="${addr.default_key==false}">
                <li class="addItem" data-id="${addr.address_id}">
                  <p class="top">
                    <span class="username">${addr.name}</span>
                    <span class="tel"> ${addr.phone}</span>
                    <span class="lable">${addr.typename}</span>
                  </p>
                  <p class="address">${addr.user_address}</p>
                  <i class="editIcon"></i>
                </li>
              </c:if>
            </c:forEach>
          <!--
          <li class="addItem defaultItem curItem">
            <p class="top">
              <span class="username"> Emma </span>
              <span class="tel"> 13734128782 </span>
              <span class="lable"> Default </span>
            </p>
            <p class="address">Residential AddressResidential Address</p>
            <i class="editIcon"></i>
          </li>
          <li class="addItem">
            <p class="top">
              <span class="username"> Emma </span>
              <span class="tel"> 13734128782 </span>
              <span class="lable"> Family </span>
            </p>
            <p class="address">Residential AddressResidential Address</p>
            <i class="editIcon"></i>
          </li>
          <li class="addItem">
            <p class="top">
              <span class="username"> Emma </span>
              <span class="tel"> 13734128782 </span>
              <span class="lable"> Family </span>
            </p>
            <p class="address">Residential AddressResidential Address</p>
            <i class="editIcon"></i>
          </li>
          -->
        </ul>
      </div>

      <div class="btnCont" style="display: none">
        <a class="addBtn" href="javascript:;">
          <span class="icon">+</span><lan set-lan="html:openblindbox.newaddr">New Address</lan></a>
      </div>
    </div>
    <%@ include file="../common/resource_mh.jsp"%>
    <script>
      initlan("${lan}");
      $(document).ready(function () {
        $('.addressList .addItem').on('click', function () {
          var id = $(this).data("id");
          $(this).addClass('curItem').siblings().removeClass('curItem');
          window.location.href = "<%=ctxPath%>/openblindbox/confirmAddress?lan=${lan}&os=${os}&price=${price}&addrid="+id+"&blindboxid=${blindboxid}";
          //console.log(1);
        })
      })


    </script>
  </body>
</html>
