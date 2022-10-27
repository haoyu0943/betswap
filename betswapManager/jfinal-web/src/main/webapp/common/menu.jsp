<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="sidebar-collapse box-shadow" style="position: relative;">
     <ul class="nav" id="main-menu">
<%--          <c:if test="${fn:indexOf(user.privilege,'G01')>=0}">--%>
<%--          <li>--%>
<%--              <yy:if test="${menuflg=='1'}">--%>
<%--                  <a class="active-menu" href="<%=ctxPath%>/movie"><i class="fa fa-edit"></i><p><lan set-lan="html:menu.inputmovie">发布电影</lan></p></a>--%>
<%--              <yy:else/>--%>
<%--                  <a href="<%=ctxPath%>/movie"><i class="fa fa-edit"></i><p><lan set-lan="html:menu.inputmovie">发布电影</lan></p></a>--%>
<%--              </yy:if>--%>
<%--          </li>--%>
<%--          </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G02')>=0}">--%>
<%--          <li>--%>
<%--              <yy:if test="${menuflg=='2'}">--%>
<%--                  <a class="active-menu" href="<%=ctxPath%>/system/userrank"><i class="fa fa-sort-alpha-asc"></i><p><lan set-lan="html:menu.member">会员规则设置</lan></p></a>--%>
<%--              <yy:else/>--%>
<%--                  <a href="<%=ctxPath%>/system/userrank"><i class="fa fa-sort-alpha-asc"></i><p><lan set-lan="html:menu.member">会员规则设置</lan></p></a>--%>
<%--              </yy:if>--%>
<%--          </li>--%>
<%--             <!--这两个菜单随着功能合并后不需要了-->--%>
<%--         <li style="display: none">--%>
<%--             <yy:if test="${menuflg=='3'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/movie/agent"><i class="fa fa fa-warning"></i><p><lan set-lan="html:menu.agent">影视代理规则</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/movie/agent"><i class="fa fa fa-warning"></i><p><lan set-lan="html:menu.agent">影视代理规则</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         <li style="display: none">--%>
<%--             <yy:if test="${menuflg=='4'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/roletbt"><i class="fa fa-chain"></i><p><lan set-lan="html:menu.tbt">TBT规则</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/roletbt"><i class="fa fa-chain"></i><p><lan set-lan="html:menu.tbt">TBT规则</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         <!--以上菜单是屏蔽了-->--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='5'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/syspara"><i class="fa fa-file"></i><p><lan set-lan="html:menu.syspara">综合参数设置</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/syspara"><i class="fa fa-file"></i><p><lan set-lan="html:menu.syspara">综合参数设置</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G06')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='6'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/adlist"><i class="fa fa-trello"></i><p><lan set-lan="html:menu.adv">轮播广告设置</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/adlist"><i class="fa fa-trello"></i><p><lan set-lan="html:menu.adv">轮播广告设置</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G07')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='7'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/msglist"><i class="fa fa-eye"></i><p><lan set-lan="html:menu.message">系统消息设置</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/msglist"><i class="fa fa-eye"></i><p><lan set-lan="html:menu.message">系统消息设置</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G18')>=0}">--%>
<%--             <li>--%>
<%--                 <yy:if test="${menuflg=='18'}">--%>
<%--                     <a class="active-menu" href="<%=ctxPath%>/system/articlelist"><i class="fa fa-envelope"></i><p><lan set-lan="html:msginput.system.article">新闻配置</lan></p></a>--%>
<%--                     <yy:else/>--%>
<%--                     <a href="<%=ctxPath%>/system/articlelist"><i class="fa fa-envelope"></i><p><lan set-lan="html:msginput.system.article">新闻配置</lan></p></a>--%>
<%--                 </yy:if>--%>
<%--             </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G19')>=0}">--%>
<%--             <li>--%>
<%--                 <yy:if test="${menuflg=='19'}">--%>
<%--                     <a class="active-menu" href="<%=ctxPath%>/system/protocollist"><i class="fa fa-envelope"></i><p><lan set-lan="html:msginput.system.protocol">协议</lan></p></a>--%>
<%--                     <yy:else/>--%>
<%--                     <a href="<%=ctxPath%>/system/protocollist"><i class="fa fa-envelope"></i><p><lan set-lan="html:msginput.system.protocol">协议</lan></p></a>--%>
<%--                 </yy:if>--%>
<%--             </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G08')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='8'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/dsshoplist"><i class="fa fa-table"></i><p><lan set-lan="html:menu.shop">店铺账号审核</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/dsshoplist"><i class="fa fa-table"></i><p><lan set-lan="html:menu.shop">店铺账号审核</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G15')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='15'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/shopbllist"><i class="fa fa-filter"></i><p><lan set-lan="html:menu.ratio">店铺比率调整</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/shopbllist"><i class="fa fa-filter"></i><p><lan set-lan="html:menu.ratio">店铺比率调整</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G14')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='14'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/dsgoodslist"><i class="fa fa-list-ol"></i><p><lan set-lan="html:menu.goods">商品上架审核</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/dsgoodslist"><i class="fa fa-list-ol"></i><p><lan set-lan="html:menu.goods">商品上架审核</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G16')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='16'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/blindbox"><i class="fa fa-github"></i><p><lan set-lan="html:menu.blindbox">盲盒设置</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/blindbox"><i class="fa fa-github"></i><p><lan set-lan="html:menu.blindbox">盲盒设置</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G17')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='17'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/system/coupon"><i class="fa fa-list-ol"></i><p><lan set-lan="html:menu.coupon">优惠券设置</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/system/coupon"><i class="fa fa-list-ol"></i><p><lan set-lan="html:menu.coupon">优惠券设置</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>
<%--         <c:if test="${fn:indexOf(user.privilege,'G13')>=0}">--%>
<%--         <li>--%>
<%--             <yy:if test="${menuflg=='13'}">--%>
<%--                 <a class="active-menu" href="<%=ctxPath%>/statistic"><i class="fa fa-comment"></i><p><lan set-lan="html:menu.stat">信息综合统计</lan></p></a>--%>
<%--                 <yy:else/>--%>
<%--                 <a href="<%=ctxPath%>/statistic"><i class="fa fa-comment"></i><p><lan set-lan="html:menu.stat">信息综合统计</lan></p></a>--%>
<%--             </yy:if>--%>
<%--         </li>--%>
<%--         </c:if>--%>

        <c:if test="${fn:indexOf(user.privilege,'G20')>=0}">
            <li>
                <yy:if test="${menuflg=='20'}">
                    <a class="active-menu" href="<%=ctxPath%>/sysset/addressManagement"><i class="fa fa-users"></i><p>平台地址管理</p></a>
                    <yy:else/>
                    <a href="<%=ctxPath%>/sysset/addressManagement"><i class="fa fa-users"></i><p>平台地址管理</p></a>
                </yy:if>
            </li>
        </c:if>

        <c:if test="${fn:indexOf(user.privilege,'G21')>=0}">
            <li>
                <yy:if test="${menuflg=='21'}">
                    <a class="active-menu" href="<%=ctxPath%>/sysset/trxAddress"><i class="fa fa-users"></i><p>比赛地址GAS查询</p></a>
                    <yy:else/>
                    <a href="<%=ctxPath%>/sysset/trxAddress"><i class="fa fa-users"></i><p>比赛地址GAS查询</p></a>
                </yy:if>
            </li>
        </c:if>
        <c:if test="${fn:indexOf(user.privilege,'G5')>=0}">
            <li>
                <yy:if test="${menuflg=='5'}">
                    <a class="active-menu" href="<%=ctxPath%>/system/syspara"><i class="fa fa-file"></i><p><lan set-lan="html:menu.syspara">综合参数设置</lan></p></a>
                    <yy:else/>
                    <a href="<%=ctxPath%>/system/syspara"><i class="fa fa-file"></i><p><lan set-lan="html:menu.syspara">综合参数设置</lan></p></a>
                </yy:if>
            </li>
        </c:if>


         <c:if test="${fn:indexOf(user.privilege,'G10')>=0}">
         <li>
             <yy:if test="${menuflg=='10'}">
                 <a class="active-menu" href="<%=ctxPath%>/sysset/usermgr"><i class="fa fa-users"></i><p><lan set-lan="html:menu.user">系统用户管理</lan></p></a>
                 <yy:else/>
                 <a href="<%=ctxPath%>/sysset/usermgr"><i class="fa fa-users"></i><p><lan set-lan="html:menu.user">系统用户管理</lan></p></a>
             </yy:if>
         </li>
         </c:if>
         <c:if test="${fn:indexOf(user.privilege,'G11')>=0}">
         <li>
             <yy:if test="${menuflg=='11'}">
                 <a class="active-menu" href="<%=ctxPath%>/sysset/codemgr"><i class="fa fa-cloud-download"></i><p><lan set-lan="html:menu.dictionary">数据字典维护</lan></p></a>
                 <yy:else/>
                 <a href="<%=ctxPath%>/sysset/codemgr"><i class="fa fa-cloud-download"></i><p><lan set-lan="html:menu.dictionary">数据字典维护</lan></p></a>
             </yy:if>
         </li>
         </c:if>
         <c:if test="${fn:indexOf(user.privilege,'G12')>=0}">
         <li>
             <yy:if test="${menuflg=='12'}">
                 <a class="active-menu" href="<%=ctxPath%>/log/log"><i class="fa fa-digg"></i><p><lan set-lan="html:menu.syslog">系统日志</lan></p></a>
                 <yy:else/>
                 <a href="<%=ctxPath%>/log/log"><i class="fa fa-digg"></i><p><lan set-lan="html:menu.syslog">系统日志</lan></p></a>
             </yy:if>
         </li>
         </c:if>
         <div style="display:none">
         <li>
             <a class="active-menu" href="<%=ctxPath%>/openblindbox?price=200&token=a50dd6549b502c9f73b033fa1afb05f2&uinfo=1494602635259547648&os=ios" target="_blank"><i class="fa fa-digg"></i><p>盲盒测试</p></a>
         </li>
         <li>
             <a class="active-menu" href="<%=ctxPath%>/chart" target="_blank"><i class="fa fa-digg"></i><p>k线图</p></a>
         </li>
          <li>
              <a class="active-menu" href="<%=ctxPath%>/message" target="_blank"><i class="fa fa-digg"></i><p>聊天</p></a>
          </li>
             <li>
                 <a class="active-menu" href="<%=ctxPath%>/system/indexArticle" target="_blank"><i class="fa fa-digg"></i><p>新闻</p></a>
             </li>
             <li>
                 <a class="active-menu" href="<%=ctxPath%>/register?yqm=MXMT645p&lan=cn" target="_blank"><i class="fa fa-digg"></i><p>注册</p></a>
             </li>
         </div>
    </ul>
</div>