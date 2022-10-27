<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource_mh.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Protocol</title>
    <script src="<%=ctxPath%>/article/js/jquery.min.js"></script>
    <script src="<%=ctxPath%>/article/js/dropload.min.js"></script>
    <script src="<%=ctxPath%>/article/js/rem.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/article/css/style.css" />
  </head>
  <body class="wihtebg">
    <div class="newsDetail">
      <div class="nContent">
        <div class="nrContent">
          ${sysProtocol.content}
        </div>
      </div>
    </div>
  </body>
</html>
