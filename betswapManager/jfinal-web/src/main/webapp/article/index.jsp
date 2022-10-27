<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>article</title>
    <script src="<%=ctxPath%>/article/js/jquery.min.js"></script>
    <script src="<%=ctxPath%>/article/js/dropload.min.js"></script>
    <script src="<%=ctxPath%>/article/js/rem.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/article/css/style.css" />
    <script type="text/javascript">var ctxPath="<%=ctxPath%>";</script>
  </head>
  <body class="wihtebg">
    <div class="topBar">
      <h1><lan set-lan="html:article.berita">新闻</lan></h1>
    </div>
    <div class="newsList">
      <div class="nContent">
        <ul id="newsList">
          <!-- <li class="item">
            <a href="newsDetail.html">
              <div class="wz">
                <h3 class="title">
                  Tema festival filmTema festival filmTema festival film
                </h3>
                <p class="desc">
                  Rules Description Provide detailed instructions according to
                  product ...
                </p>
                <p class="time">2021-02-27</p>
              </div>
              <div class="img">
                <img src="./image/2.jpg" alt="" />
              </div>
            </a>
          </li> -->
        </ul>
      </div>
    </div>
    <!-- <div id="footer" class="footer">
      <a class="nav-a" href="#" onclick="javascript:;">
        <i class="icon home-icon"></i>
        <p>Front page</p>
      </a>
      <a class="nav-a cur" href="index.html">
        <i class="icon news-icon"></i>
        <p>News</p>
      </a>
      <a class="nav-a" href="#" onclick="javascript:;">
        <i class="icon shop-icon"></i>
        <p>My shop</p>
      </a>
      <a class="nav-a" href="#" onclick="javascript:;">
        <i class="icon movie-icon"></i>
        <p>movie ticket</p>
      </a>
      <a class="nav-a" href="#" onclick="javascript:;">
        <i class="icon account-icon"></i>
        <p>account</p>
      </a>
    </div> -->
    <%@ include file="../common/resource_mh.jsp"%>
    <script>
      initlan("${lan}");
    </script>
    <script type="text/javascript" src="<%=ctxPath%>/article/js/index.js"></script>

  </body>
</html>
