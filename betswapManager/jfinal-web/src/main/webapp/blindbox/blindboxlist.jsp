<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>blindbox list</title>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
            <div id="page-wrapper">
                <div class="pageheader"><lan set-lan="html:menu.blindbox">盲盒设置</lan><i class="fa fa-caret-down ml-xs"></i></div>
                <div class="page-inner">
                    <div class="HBox">
                        <div class="hhBoxew">
                            <span class="wwmc"><lan set-lan="html:blindboxlist.price">盲盒价格</lan>：</span>
                            <input type="text" style="width: 60px" id="smhjg">Rp&nbsp;<lan set-lan="html:blindboxlist.to">至</lan>
                            <input type="text" style="width: 60px" id="emhjg">Rp
                        </div>
                        <div class="hhBoxew">
                            <span class="wwmc"><lan set-lan="html:blindboxlist.status">盲盒状态</lan>：</span>
                            <lan set-lan="select:blindboxlist.mhzt">
                            <select id="mhzt" style="width: 160px">


                                <!--
                            <option value=""><lan set-lan="html:all">所有</lan></option>
                            <option value="0"><lan set-lan="html:blindboxlist.temporary.storage">暂存中</lan></option>
                            <option value="1"><lan set-lan="html:blindboxlist.grounding">已上架</lan></option>
                            <option value="9"><lan set-lan="html:blindboxlist.not.shelf">已下架</lan></option>
                            -->
                            </select></lan>
                        </div>
                        <button type="button" class="btn btn-sm btn-info" onclick="getlist()"><lan set-lan="html:movielist.query">查询</lan></button>
                        <button type="button" class="btn btn-sm btn-success"><a href="<%=ctxPath%>/blindbox/crtoreditbox"><lan set-lan="html:blindboxlist.new">新增盲盒</lan></a></button>
                        <button type="button" class="btn btn-sm btn-info"><a href="<%=ctxPath%>/blindbox/priceset"><lan set-lan="html:blindboxlist.ladder.setting">盲盒价格阶梯设置</lan></a></button>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="page-inner">
                    <div class="row" id="content">
                        <!--
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_orange">暂存中</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_gary">已下架</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_success">已发布</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_gary">已下架</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_success">已发布</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="mhbox">
                                <div class="mhprice">
                                    <h4>售价&nbsp;¥300</h4>
                                    <a href=""><i class="fa fa-window-close mr-xs"></i>删除</a>
                                    <a href=""><i class="fa fa-pencil mr-xs"></i>修改</a>
                                </div>
                                <div class="mhimg">
                                    <div class="mhimages"><img src="assets/img/mh.jpg" /></div>
                                    <div class="mharticle">
                                        <h4>商品6件，市场总价¥56</h4>
                                        <div class="mhatitle">商品名称商品名称商品名称商品名称商品名称商品名称商品名称商品名称</div>
                                        <div class="mhtbt">TBT&nbsp;300</div>
                                    </div>
                                    <div class="mhzt border_orange">咱村中</div>
                                </div>
                            </div>
                        </div>
                        -->
                    </div>
                    <div id="page" style="margin-top: 8px"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>
    <script>
        initlan();
        $(function(){
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $("#main-menu").height($(window).height()-$(".navbar").height()-10);
        });

        getlist();

        function getlist(obj){

            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/getBoxlist",
                data:{"smhjg":$("#smhjg").val(),
                    "emhjg":$("#emhjg").val(),
                    "mhzt":$("#mhzt").val(),
                    "page":obj?obj.curr:1,
                },
                success:function(data){
                    layui.use('laypage', function(){
                        layui.laypage.render({
                            elem: 'page',
                            limit: 9,
                            count: data.count,
                            curr: obj?obj.curr:1,
                            jump:function(obj,first){
                                if(!first){getlist(obj);}
                            }
                        });
                    });
                    var shtml="";
                    data.boxlist.forEach(function(value){
                        shtml+=getstring(value);
                    });
                    $("#content").html(shtml);
                },
            });
        }

        function getstring(row) {
            var rs="<div class=\"col-md-4\">";
                rs+="<div class=\"mhbox\">";
                rs+="  <div class=\"mhprice\">";
                rs+="    <h5>"+get_lan("blindboxlist.create.time")+"："+$.tmDate._toString(row.create_time,'yyyy-MM-dd HH:mm')+"</h5>&nbsp;<h5>"+get_lan("blindboxlist.sell.price")+"&nbsp;<span style='font-weight: bold;font-size: 16px;color: red'> "+row.price_rp+"</span>&nbsp;Rp&nbsp;"
                    +get_lan("movielist.issuenumb")+"&nbsp;<span style='font-weight: bold;font-size: 16px;color:lightseagreen'>"+row.circulation+"</span>&nbsp;"+get_lan("blindboxlist.surplus")+"&nbsp;<span style='font-weight: bold;font-size: 16px;color:lightseagreen'>"+row.surplus+"</span></h5>";
                if(row.status==0) {
                    rs += "    <a href=\"javascript:;\" onclick=\"grounding('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-arrow-up mr-xs\"></i>"+get_lan("blindboxlist.put.on")+"</a>";
                    rs += "    <a href=\"javascript:;\" onclick=\"editmould('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-pencil mr-xs\"></i>"+get_lan("button.modify")+"</a>";
                    rs += "    <a href=\"javascript:;\" onclick=\"delmould('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-close mr-xs\"></i>"+get_lan("button.delete")+"</a>";
                }
                else if(row.status==1){
                    rs += "    <a href=\"javascript:;\" onclick=\"undercarriage('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-arrow-down mr-xs\" ></i>"+get_lan("blindboxlist.put.down")+"</a>";
                }
                else{
                    rs += "    <a href=\"javascript:;\" onclick=\"grounding('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-arrow-up mr-xs\" ></i>"+get_lan("blindboxlist.reshelf")+"</a>";
                    rs += "    <a href=\"javascript:;\" onclick=\"editmould('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-pencil mr-xs\"></i>"+get_lan("button.modify")+"</a>";
                    rs += "    <a href=\"javascript:;\" onclick=\"delmould('"+row.blindbox_mould_id+"')\"><i class=\"fa fa-close mr-xs\"></i>"+get_lan("button.delete")+"</a>";
                }
                rs+="  </div>";
                rs+="  <div class=\"mhimg\">";
                rs+="    <div class=\"mhimages\"><img src=\"assets/img/mh.jpg\" /></div>";
                rs+="    <div class=\"mharticle\">";
                rs+="      <h4>"+get_lan("blindboxlist.goods")+row.goods_count+get_lan("blindboxlist.total")+"<span style='color: red'> "+row.goods_price+"</span>&nbsp;Rp</h4>";
                rs+="      <div class=\"mhatitle\">"+get_lan("blindboxlist.include.goods")+"："+row.goods_names+"</div>    ";
                rs+="      <div class=\"mhtbt\">TBT&nbsp;"+row.tbt_count+"</div>";
                //rs+="      <div class=\"mhatitle\">创建时间："+$.tmDate._toString(row.create_time,'yyyy-MM-dd HH:mm')+"</div>    ";
                rs+="    </div>";
                if(row.status==0){
                    rs+="<div class=\"mhzt border_orange\">"+get_lan("blindboxlist.temporary.storage")+"</div>";
                }
                else if(row.status==1){
                    rs+="<div class=\"mhzt border_success\">"+get_lan("blindboxlist.grounding")+"</div>";
                }
                else{
                    rs+="<div class=\"mhzt border_gary\">"+get_lan("blindboxlist.not.shelf")+"</div>";
                }
                rs+="</div></div></div>";
            return rs;
        }

        function editmould(mouldId){
            document.location.href="<%=ctxPath%>/blindbox/crtoreditbox?mouldId="+mouldId;
        }

        function delmould(mouldId){
            yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
                    if(ok){
                        $.ajax({
                            type:"post",
                            url:"<%=ctxPath%>/blindbox/delmould",
                            data:{"mouldId":mouldId},
                            success:function(data){
                                if(data.flag == true){
                                    document.location.reload();
                                }
                                else{
                                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("message.delete.fail")});
                                }
                            }
                        });
                    }
                }});
        }
        //上架
        function grounding (mouldId){
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/grounding",
                data:{"mouldId":mouldId},
                success:function(data){
                    if(data.flag == true){
                        document.location.reload();
                    }
                    else{
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("blindboxlist.put.on")+get_lan("blindboxlist.field")});
                    }
                }
            });
        }
        //下架
        function undercarriage(mouldId){
            //alert(mouldId);
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/blindbox/undercarriage",
                data:{"mouldId":mouldId},
                success:function(data){
                    if(data.flag == true){
                        document.location.reload();
                    }
                    else{
                        yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),style:"e",content:get_lan("blindboxlist.put.down")+get_lan("blindboxlist.field")});
                    }
                }
            });
        }

    </script>
</body>
</html>
