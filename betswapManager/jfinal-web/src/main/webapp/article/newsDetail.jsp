<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource_mh.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Blind box</title>
    <script src="<%=ctxPath%>/article/js/jquery.min.js"></script>
    <script src="<%=ctxPath%>/article/js/dropload.min.js"></script>
    <script src="<%=ctxPath%>/article/js/rem.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/article/css/style.css" />
  </head>
  <body class="wihtebg">
<%--    <div class="topBar">--%>
<%--      <i class="arrowIcon" onclick="javascript:history.back(-1);"></i>--%>
<%--    </div>--%>
    <div class="newsDetail">
      <div class="nContent">
        <h2 class="bigTitle">${sysArticle.title}</h2>
        <p class="time">${sysArticle.create_time}</p>
        <p class="desc">
          ${sysArticle.subtitle}
        </p>
        <div class="hotImg">
          <img src="<%=serviceUrl%>${sysArticle.cover}" alt="" />
        </div>
        <div class="nrContent">
         <c:if test="${sysArticle.type_flag=='0'}">
           ${sysArticle.content}
         </c:if>
          <c:if test="${sysArticle.type_flag=='1'}">
            ${sysArticle.web_url}
          </c:if>
        </div>
      </div>
    </div>
  </body>
</html>
