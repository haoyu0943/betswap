<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title set-lan="html:coupon.setcoupon">coupon set</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/laydate/need/laydate.css">
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>

        <div id="page-wrapper">
            <div class="pageheader" set-lan="html:coupon.setcoupon"><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="HBox">
                    <div class="hhBox">
                        <span class="wwmc" set-lan="html:status">状态</span>
                        <select id="status" class="form-control" style="width: 150px">
                            <option value=0 set-lan="html:all">全部</option>
                            <option value=1 set-lan="html:coupon.valid">生效中</option>
                            <option value=2 set-lan="html:coupon.expired">已过期</option>
                        </select>
                    </div>
                    <div class="hhBox">
                        <span class="wwmc"set-lan="html:timerange"></span>
                        <input class="form-control timeInput inputR" type="text" id="sdate1" onClick="laydate({format: 'YYYY-MM-DD',elem: '#sdate1'})">至
                        <input class="form-control timeInput inputL" type="text" id="edate1" onClick="laydate({format: 'YYYY-MM-DD',elem: '#edate1'})">
                    </div>
                    <button type="button" class="btn btn-sm btn-info" onclick="getCoupons(0)"   set-lan="html:button.query">查询</button>
                    <button type="button" class="btn btn-sm btn-success" id="new"               set-lan="html:button.new">新增</button>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="page-inner">
                <div class="row" id="rowbody">

                </div>
                <div id="laypage1"></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width: 499px;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close"><span>&times;</span></button>
                    <h4 class="modal-title" set-lan="html:coupon.setcoupon"><lan set-lan="html:menu.coupon">优惠券设置</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:ScoupeOfUse"></dt>&nbsp;
                            <dd>
                                <select id="restrict" class="form-control" style="width: 120px">
                                    <option value=999 set-lan="html:coupon.film">电影</option>
                                    <option value=1024 set-lan="html:coupon.global">商城通用</option>
                                    <c:forEach var="item" items="${diclist}" varStatus="index">
                                    <option value=${item.code}>${item.d}</option>
                                    </c:forEach>
                                </select>
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:coupon.threshold">使用门槛额度</dt>&nbsp;
                            <dd>
                                <input id="threshold" class="form-control" type="text"/>
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:coupon.value">优惠券面值</dt>&nbsp;
                            <dd><input id="value" class="form-control" type="text"/></dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:coupon.userUpperLimit">单个用户领取上限</dt>&nbsp;
                            <dd><input id="limit" class="form-control" type="text"/></dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:coupon.totalLimit">全平台发放上限</dt>&nbsp;
                            <dd><input id="maxGenerated" class="form-control" type="text"/></dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:timerange">有效日期</dt>
                            <dd><input class="form-control timeInput inputR" type="text" id="sdate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#sdate'})">-
                            <input class="form-control timeInput inputL" type="text" id="edate" onClick="laydate({format: 'YYYY-MM-DD',elem: '#edate'})"></dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger" set-lan="html:description">使用描述</dt>&nbsp;
                            <dd><textarea id="description" class="form-control" style="width: 90%;height: 80px;"></textarea></dd>
                        </dl>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" onclick="save()" set-lan="html:button.save">保存</button>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
    <script>

        initlan();
        var vlan = $.tmCookie.getCookie('lan');

        var thisid;
        var coupons;
        var currtime=(new Date()).format("yyyy-MM-dd HH:mm:ss");

        $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        });

        getCoupons(0);
        function getCoupons(page){
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/getCoupons",
                data: {
                    page:   page,
                    sdate:  $("#sdate1").val(),
                    edate:  $("#edate1").val(),
                    status: $("#status").val(),
                },
                success:function(data){
                    console.log(data);
                    coupons = data.coupons;
                    var html="";
                    for(var i=0;i<coupons.length;i++){
                        var coupon=coupons[i];
                        var valid=(currtime<coupon.end_time);
                        html+="<div class=\"col-md-4\">" +
                            "<div class=\"yhqbox boxbg3\">" +
                            "<div class='yhqboxleft boxleftbg"+ (valid?3:1) +"'>" +
                            "<h1>"+ coupon.coupon_value +"</h1>" +
                            ((vlan==='cn') ? ("<span>每满"+ coupon.threshold +"可减</span></div>"):
                                            ("<span>Orders over "+ coupon.threshold +"</span></div>")) +
                            "<div class=\"yhqboxright\">" +
                            ((vlan==='cn') ? ("<div class=\"yhqup\"><div class=\"yhqboxzt1\">类型 "+coupon.seriesname+" 已领"+coupon.ngenerated):
                                             ("<div class=\"yhqup\"><div class=\"yhqboxzt1\">type "+coupon.seriesname+" "+coupon.ngenerated+" claimed")) +
                            ((vlan==='cn') ?("</div><div class=\"yhqboxzt2 "+(valid?("text-color3\">生效中"):("text-color1\">已过期"))):
                                            ("</div><div class=\"yhqboxzt2 "+(valid?("text-color3\">Valid"):("text-color1\">Expired")))) +

                            ((vlan==='cn') ?("</div></div><div class=\"yhqtime\">有效期："+  coupon.start_time.substr(0,10) + "至"  +coupon.end_time.substr(0,10) +"</div>"):
                                ("</div></div><div class=\"yhqtime\">period of validity："+ coupon.start_time.substr(0,10) +" to "+ coupon.end_time.substr(0,10) +"</div>")) ;
                        if(strlen(coupon.description)>=80){
                            html+="<div class=\"yhqtext\"><span title=\""+coupon.description+"\">"+ coupon.description.substr(0,80) +"...</span></div>";
                        }
                        else{
                            html+="<div class=\"yhqtext\">"+ coupon.description+"</div>";
                        }
                        html+=
                            "<div class=\"yhqdelete\">" +
                            "<a onclick='modify("+ coupon.template_id +")'><i class=\"fa fa-pencil mr-xs\"></i>"+ get_lan("button.modify")+"</a>" +
                            "<a onclick='del("+ coupon.template_id +")'><i class=\"fa fa-close mr-xs\"></i>"+get_lan("button.delete")+"</a>" +
                            "</div></div></div></div>";
                    }
                    document.getElementById("rowbody").innerHTML=html;
                    layui.laypage.render({
                        elem: 'laypage1',
                        limit: 12,
                        count: data.total,
                        curr:page+1,
                        jump: function(obj, first){
                            if(!first){
                                getCoupons(obj.curr-1);
                            }
                        }
                    });
                }
            });
        }

        function strlen(str){
            var len = 0;
            for (var i=0; i<str.length; i++) {
                var c = str.charCodeAt(i);
                //单字节加1
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
                    len++;
                }
                else {
                    len+=2;
                }
            }
            return len;
        }

        function modify(template_id){
            $(".zhezhao2").show();
            for(var i=0;i<coupons.length;i++){
                var coupon=coupons[i];
                if(coupon.template_id===template_id){
                    console.log(template_id);
                    thisid=template_id;
                    if(coupon.restrict_type=="0") {
                        $("#restrict").val("1024");
                    }
                    else{
                        $("#restrict").val(coupon.restrict_type);
                    }
                    $("#threshold").val(coupon.threshold);
                    $("#value").val(coupon.coupon_value);
                    $("#limit").val(coupon.person_limit);
                    $("#maxGenerated").val(coupon.max_generated);
                    $("#sdate").val(coupon.start_time);
                    $("#edate").val(coupon.end_time);
                    $("#description").val(coupon.description);
                }
            }
        }

        $(function(){
            $("#new").on('click',function(){
                $("#restrict").val("");
                $("#threshold").val("");
                $("#value").val("");
                $("#limit").val("");
                $("#maxGenerated").val("");
                $("#sdate").val("");
                $("#edate").val("");
                $("#description").val("");
                $(".zhezhao2").show();
            });
            $(".close").on('click',function(){
                $(".zhezhao2").hide();
                thisid=null;
            });
        });



        function save() {
            var restrict = $("#restrict").val();
            var threshold = $("#threshold").val();
            var value = $("#value").val();
            var limit = $("#limit").val();
            var maxGenerated = $("#maxGenerated").val();
            var sdate = $("#sdate").val();
            var edate = $("#edate").val();
            var description = $("#description").val();

            if(!isNumber(value)||!isNumber(threshold)){
                alert("满减金额必须是数字");return;
            }
            if(!isNumber(limit)){
                alert("假如单个用户领取没有上限，请设置很大的数字，比如1万。假如不想任何人领取该优惠券，设为0");return;
            }
            if(!isNumber(maxGenerated)){
                alert("假如全平台发放上限不设限，请设置很大的数字，比如100万。设为0，则该模板不会产生优惠券");return;
            }
            if(sdate===""||edate===""){
                alert("假如优惠券可以随时使用永不过期，请设置非常大的时间跨度，比如到2077年");return;
            }

            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/saveCoupon",
                data: {
                    id:thisid,
                    restrict:restrict,
                    threshold:threshold,
                    value:value,
                    limit:limit,
                    maxGenerated:maxGenerated,
                    sdate:sdate,
                    edate:edate,
                    description:description,
                },
                success:function(data){
                    alert("保存成功");
                    $(".zhezhao2").hide();
                    location.reload();
                }
            });
        }

        function isNumber(value) {         //验证是否为数字
            var patrn = /^(-)?\d+(\.\d+)?$/;
            return value !== "" && patrn.exec(value) !== null;
        }

        function initselect(){
            if(vlan=="cn") {
                for (var i = 0; i < cn_advtype.length; i++) {
                    $("#type").append("<option value=\"" + cn_advtype[i].id + "\">" + cn_advtype[i].name + "</option>");
                }
            }
            else{
                for (var i = 0; i < en_advtype.length; i++) {
                    $("#type").append("<option value=\"" + en_advtype[i].id + "\">" + en_advtype[i].name + "</option>");
                }
            }
        }

    </script>
</body>
</html>
