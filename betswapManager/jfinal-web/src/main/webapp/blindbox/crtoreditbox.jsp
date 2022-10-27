<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>create or edit blindbox</title>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
            <div id="page-wrapper">
                <div class="pageheader"><lan set-lan="html:blindboxlist.setting">盲盒配置</lan><i class="fa fa-caret-down ml-xs"></i></div>
                <div class="page-inner">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="xztitle"><lan set-lan="html:blindboxlist.add.goods">加入盲盒的商品</lan><i class="fa fa-caret-down ml-xs"></i></div>
                            <div class="xzbox">
                                <ul id="selectul">
                                    <!--
                                    <li>*****牌雨鞋<a href="#"><i class="fa fa-close"></i></a></li>
                                    <li>*****牌雨鞋<a href="#"><i class="fa fa-close"></i></a></li>
                                    <li>*****牌雨鞋<a href="#"><i class="fa fa-close"></i></a></li>
                                    -->
                                </ul>
                            </div>
                            <div class="addhj"><lan set-lan="html:crtoreditbox.total">合计</lan>&nbsp;Rp<span id="strprice">0</span></div>
                            <div class="xztitle"><lan set-lan="html:crtoreditbox.other.set">盲盒其他信息设定</lan><i class="fa fa-caret-down ml-xs"></i></div>
                            <!--
                            <div class="addhj">盲盒名称<span><input class="form-control" type="text" style="width: 180px" id="mhmc"></span></div>
                            <div class="center" style="height: 10px"></div>
                            -->
                            <div class="addhj"><lan set-lan="html:crtoreditbox.other.set">盲盒其他信息设定</lan>加入TBT<span><input class="form-control" type="text" style="width: 180px" value="0" id="tbt"></span></div>
                            <div class="center" style="height: 10px"></div>
                            <div class="addhj"><lan set-lan="html:blindboxlist.number">发行数量</lan><span><input class="form-control" type="text" style="width: 180px" id="fxsl"></span></div>
                            <div class="center" style="height: 10px"></div>
                            <div class="addhj"><lan set-lan="html:crtoreditbox.price">盲盒定价</lan>(Rp)<span>
                                <select id="mhdj" class="form-control" style="width: 180px">
                                            <option value=""></option>
                                            <c:forEach var="price" items="${pricelist}" varStatus="item">
                                                <option value="${price.price_set_id}">${price.blind_box_name}(${price.price_rp})</option>
                                            </c:forEach>
                                 </select>
                            </span></div>
                            <div class="center" style="height: 20px"></div>
                            <div class="addhj"><input type="hidden" id="mouldId"></div>
                            <div class="center"><button type="button" class="btn btn-sm btn-success mr-xs" onclick="save(1)"><lan set-lan="html:button.save">保存</lan></button>
                                                <button type="button" class="btn btn-sm btn-default" onclick="save(0)"><lan set-lan="html:blindboxlist.temporary.storage">暂存中</lan></button></div>
                        </div>
                        <div class="col-md-9">
                            <div class="page-inner">
                                <div class="HBox">
                                    <div class="hhBoxeww">
                                        <span class="wwmc"><lan set-lan="html:movielist.type">商品类别</lan>：</span>
                                        <select id="splb" style="width: 150px">
                                            <option value="">all</option>
                                            <c:forEach var="dic" items="${diclist}" varStatus="item">
                                                <option value="${dic.code}">${dic.dic_describe}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="hhBoxeww">
                                        <span class="wwmc"><lan set-lan="html:goodsapplydet.goods.name">商品名称</lan>：</span>
                                        <input type="text" id="spmc" style="width: 150px">
                                    </div>
                                    <div class="hhBoxeww">
                                        <span class="wwmc"><lan set-lan="html:blindboxlist.price.range">价格区间</lan>：</span>
                                        <input type="text" style="width: 80px" id="sspjg"><lan set-lan="html:blindboxlist.to">到</lan><input type="text" style="width: 80px" id="espjg">
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="center">
                                    <button type="button" class="btn btn-sm btn-info" onclick="qrygoods()"><lan set-lan="html:movielist.query">查询</lan></button>
                                    <button type="button" class="btn btn-sm btn-default" onclick="cleartj()" ><lan set-lan="html:movielist.clear">清空</lan></button>
                                    <button type="button" class="btn btn-sm btn-primary" onclick="goback()"><lan set-lan="html:button.back">返回</lan></button>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="infoTable">
                                    <thead>
                                    <tr>
                                        <th><lan set-lan="html:movielist.order">序号</lan></th>
                                        <th><lan set-lan="html:goodsapplydet.goods.name">商品名称</lan></th>
                                        <th width="50%"><lan set-lan="html:goodsapplydet.goods.introduction">商品介绍</lan></th>
                                        <th><lan set-lan="html:goodsapplydet.goods.price">商品价格</lan></th>
                                        <th><lan set-lan="html:actor.operation">操作</lan></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!--
                                    <tr>
                                        <td>1</td>
                                        <td>皮鞋</td>
                                        <td>商品描述</td>
                                        <td>20</td>
                                        <td><button type="button" class="btn btn-xs btn-warning">加入盲盒</button></td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>皮鞋</td>
                                        <td>商品描述</td>
                                        <td>20</td>
                                        <td><button type="button" class="btn btn-xs btn-warning">加入盲盒</button></td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>皮鞋</td>
                                        <td>商品描述</td>
                                        <td>20</td>
                                        <td><button type="button" class="btn btn-xs btn-warning">加入盲盒</button></td>
                                    </tr>
                                    -->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>
    <script>
        initlan();
        var $thisTable = $("#infoTable");
        //initTable("infoTable");
        var vlan = $.tmCookie.getCookie('lan');
        qrygoods();



        $(function(){
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $("#main-menu").height($(window).height()-$(".navbar").height()-10);
            <c:if test="${mouldId!=''}">
               $("#splb").val();
               var tbt="${boxmould.tbt_count}";
               if(tbt==""||tbt=="null"){
                   $("#tbt").val("0");
               }
               else{
                  $("#tbt").val("${boxmould.tbt_count}");
               }
               //$("#mhmc").val("${boxmould.blindbox_name}");
               //var price="${boxmould.price_rp}";
               //var intPrice=parseInt(price).toString();
               //alert(intPrice);
               var prisesetid="${boxmould.price_set_id}";
               $("#mhdj").val(prisesetid);
               $("#fxsl").val("${boxmould.circulation}");
               $("#mouldId").val("${mouldId}");
               <c:forEach var="goods" items="${goodslist}" varStatus="item">
                  $("#selectul").append("<li data-goodsid='${goods.goods_id}' data-goodsname='${goods.goods_name}'>${goods.goods_name}(Rp${goods.base_price})<a href=\"javascript:;\" onclick=\"cancelselect(this,'${goods.base_price}')\"><i class=\"fa fa-close\"></i></a></li>");
               </c:forEach>
               $("#strprice").html("${boxmould.goods_price}");
            </c:if>
        });

        function goback(){
           document.location.href="<%=ctxPath%>/blindbox"
        }

        function cleartj(){
           $("#splb").val("");
           $("#sspjg").val("");
           $("#espjg").val("");
           $("#spmc").val("");
        }

        function qrygoods(){
            var data = {};
            data["splb"] = $("#splb").val();
            data["sspjg"] = $("#sspjg").val();
            data["espjg"] = $("#espjg").val();
            data["spmc"] = $("#spmc").val();
            var columns =[
                {data: "rownum",render: function(data, type, row, meta) {return data;}},
                {data: "goods_name",render: function(data, type, row, meta) {return data;}},
                {data: "introduce",render: function(data, type, row, meta) {return data;}},
                {data: "base_price",render: function(data, type, row, meta) {return data;}},
                {data: "goods_id",render: function(data, type, row, meta) {
                    var btnstr="<button type=\"button\" class=\"btn btn-xs btn-warning\" onclick=\"addSelect('"+row["goods_id"]+"','"+row["goods_name"]+"','"+row["base_price"]+"')\">"+get_lan("blindboxlist.add")+"</button>";
                    return btnstr;
                }}
            ];
            getTableDataServer("infoTable",ctxPath+"/blindbox/qryPageGoods",columns,data,false);
        }

        function addSelect(goodsid,goodsname,price){
            var liarry = $("#selectul li");
            if(liarry.length>=4){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.length")});
                return;
            }
            $("#selectul").append("<li data-goodsid='"+goodsid+"' data-goodsname='"+goodsname+"'>"+goodsname+"(Rp"+price+")<a href=\"javascript:;\" onclick=\"cancelselect(this,'"+price+"')\"><i class=\"fa fa-close\"></i></a></li>");
            //价值进行累加
            //var curval=new Decimal($("#strprice").html());
            //var addval=new Decimal(price);
            var curval=parseFloat($("#strprice").html());
            var addval=parseFloat(price);
            var newval=curval+addval;
            newval=newval.toFixed(2);
            $("#strprice").html(newval);
        }

        function cancelselect(obj,price){
            $(obj).parent().remove();
            //减去对应价值
            var curval=parseFloat($("#strprice").html());
            var minusval=parseFloat(price);
            var newval=curval-minusval;
            newval=newval.toFixed(2);
            $("#strprice").html(newval);
        }

        function save(saveflg){
            var goodsids=[];
            var goodsnames=[];
            var liarry = $("#selectul li");
            if(liarry.length==0){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.min")});
                return;
            }
            else {
                for (var i = 0; i < liarry.length; i++) {
                    goodsids.push($(liarry[i]).data("goodsid"));
                    goodsnames.push($(liarry[i]).data("goodsname"));
                }
            }
            var tbt=$("#tbt").val();
            var mhdjid=$("#mhdj").val();
            var mhjdxx=$("#mhdj").find("option:selected").text();

            var fxsl=$("#fxsl").val();
            var goodsprice=$("#strprice").html();
            var goodscount=liarry.length;
            var mouldId=$("#mouldId").val();
            if(tbt!=""&&checkNumber(tbt)==false){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.tbt")+get_lan("error.mustbenumber")});
                return;
            }
            if(mhdjid==""){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.ladder.setting")+get_lan("message.blank")});
                return;
            }
            if(fxsl==""){
                yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.number")+get_lan("message.blank")});
                return;
            }
            else{
                if(checkNumber(fxsl)==false){
                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.number")+get_lan("message.number")});
                    return;
                }
                else{
                    if(parseInt(fxsl)>${maxnum}){
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:get_lan("blindboxlist.not.maX")+"${maxnum}"});
                        return;
                    }
                }
            }
            var p1=mhjdxx.indexOf("(");
            var p2=mhjdxx.indexOf(")");
            var mhdj=mhjdxx.substr(p1+1,p2-p1-1);
            var mhmc=mhjdxx.substr(0,p1)
            console.log(mhmc);
            console.log(mhdj);
            var data={};

            data["goodsids"]=goodsids.join("#");
            data["goodsnames"]=goodsnames.join(",");
            data["tbt"]=tbt;
            data["mhdjid"]=mhdjid;
            data["mhdj"]=mhdj;
            data["fxsl"]=fxsl;
            data["goodsprice"]=goodsprice;
            data["goodscount"]=goodscount;
            data["saveflg"]=saveflg;
            data["mouldId"]=mouldId;
            data["mhmc"]=mhmc;
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/saveBlindbox",
                data:data,
                success:function(data){
                    if(data.flag == true){
                        goback();
                    }
                    else{
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("error.savefaild")});
                    }
                }
            });

        }
    </script>
</body>
</html>
