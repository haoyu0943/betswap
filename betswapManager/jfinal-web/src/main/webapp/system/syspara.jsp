<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>sys para</title>
    <link href="<%=ctxPath%>/css/cropper.css?v=${randomNum}" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/ImgCropping.css">
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/jquery-hunterTimePicker/css/timePicker.css">
</head>
<style>
.imageupload{text-align:center;position:relative;display: inline-block;}
.cover-wrap{display:none;position:fixed;left:0;top:0;width:100%;height:100%;background: rgba(0, 0, 0, 0.4);z-index: 10000000;text-align:center;}
.coverimgage{width:900px;height:600px;margin:100px auto;background-color:#FFFFFF;overflow: hidden;border-radius:4px;}
#clipArea{margin:10px;height: 520px;}
.wrapImage{height:56px;line-height:36px;text-align: center;padding-top:8px;}
#clipBtn{width:100px;height: 36px;border-radius: 4px;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;text-align: center;line-height: 36px;outline: none;border: none}
.btnupload{width:70px;height:32px;border-radius: 4px;cursor: pointer;background-color:#ff8a00;color: #FFFFFF;font-size: 14px;    vertical-align: top;text-align:center;line-height:32px;outline:none;margin-left:5px;position:relative;    display: inline-block; cursor: pointer}
#file{cursor:pointer;opacity:0;filter:alpha(opacity=0);width:100%;height:100%;position:absolute;top:0;left:0;cursor: pointer;}
.xgdh{width: 50%; display: inline-block;float: left}
.ml-xs{margin-left: 5px}
.mr-ml{margin-right: 10px}
.ml-m{margin-left: 20px}
.pointer{cursor: pointer}
.shzhilc{height: 30px; line-height: 30px; background: #c5d0f3;border: 1px solid #92a2d6;padding: 0 5px;margin-bottom: 10px;}
.liucheg{margin-bottom: 5px; width: 33%;float: left}
.liucheg100{margin-bottom: 5px;}
.pnane{padding: 2px;background: #eaeaea;}
.nameshe{width: 75px; text-align: right;display: inline-block; color: #1763e0}
.cursor{cursor: pointer;}

#tabsF {
    float:left;
    width:100%;
    background:#efefef;
    font-size:100%;
    line-height:normal;
    border-bottom:1px solid #666;
}
#tabsF ul {
    margin:0;
    padding:10px 10px 0 50px;
    list-style:none;
}
#tabsF li {
    display:inline;
    margin:0;
    padding:0;
}
#tabsF a {
    float:left;
    background:url("<%=ctxPath%>/assets/img/tableftF.gif") no-repeat left top;
    margin:0;
    padding:0 0 0 4px;
    text-decoration:none;
}
#tabsF a span {
    float:left;
    display:block;
    background:url("<%=ctxPath%>/assets/img/tabrightF.gif") no-repeat right top;
    padding:7px 15px 4px 6px;
    color:#666;
}
/* Commented Backslash Hack hides rule from IE5-Mac \*/
#tabsF a span {float:none;}
/* End IE5-Mac hack */
#tabsF a:hover span {
    color:#FFF;
}
#tabsF a:hover {
    background-position:0% -42px;
}
#tabsF a:hover span {
    background-position:100% -42px;
}
</style>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:syspara.parameter.setting">系统参数设置</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="compare-label"><lan set-lan="html:movielist.order">序号</lan></th>
                            <th class="compare-label"><lan set-lan="html:syspara.parameter.meaning">参数含义</lan></th>
                            <th class="compare-label"><lan set-lan="html:syspara.parameter.value">参数值</lan></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="para" items="${paralist}" varStatus="item">
                        <tr>
                            <td >${item.index+1}</td>
                            <td >${para.para_desc}</td>
                            <td id="fld${para.id}">
                                <c:choose>
                                    <c:when test="${para.id=='10'}">
                                        <c:choose>
                                            <c:when test="${para.para_value=='0'}">
                                                <span>未启用</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${para.id}')"></i></a>
                                            </c:when>
                                            <c:otherwise>
                                                <span>已启用</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${para.id}')"></i></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${para.id=='8'||para.id=='9'}">
                                        <span id="t${para.id}showstr" style="display: block">${para.para_value}<a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${para.id}')"></i></a></span>
                                        <span id="t${para.id}inputtime" style="display: none"><input id="timeinput${para.id}" type="text" class="time-picker" value="${para.para_value}" readonly style="width: 90px;">
                                            <a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this,'${para.id}')"></i></a>
                                        </span>
                                    </c:when>
                                    <c:when test="${para.id=='5'||para.id=='6'||para.id=='7'}">
                                        <span>${para.para_value}</span>%
                                        <a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${para.id}')"></i></a>
                                    </c:when>
                                    <c:otherwise>
                                        <span>${para.para_value}</span>
                                        <a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,'${para.id}')"></i></a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>

    <%@ include file="../common/modpwd.jsp"%>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery-hunterTimePicker/js/jquery-timepicker.js"></script>
    <script>
        initlan();
        var vlan = $.tmCookie.getCookie('lan');
        $(function(){
            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        });
        $().ready(function(e) {
	     $(".time-picker").hunterTimePicker();
        });

        function edit(obj,id){
            var parent$=$(obj).parents("td");
            var text=$(parent$).find('span').text();
            //两个时间
            if(id=="8"||id=="9"){
                $("#t"+id+"showstr").hide();
                $("#t"+id+"inputtime").show();
            }
            //一个radio选择
            else if(id=="10"){
                if(text=="未启用") {
                    $(parent$).html('<input name="tzdysffk" type="radio" value="0" checked>不启用' +
                        '<input name="tzdysffk" type="radio" value="1">启用' +
                        '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this,\'' + id + '\')"></i>' +
                        '</a>');
                }
                else{
                    $(parent$).html('<input name="tzdysffk" type="radio" value="0">不启用' +
                        '<input name="tzdysffk" type="radio" value="1" checked>启用' +
                        '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this,\'' + id + '\')"></i>' +
                        '</a>');
                }
            }
            else {
                $(parent$).html('<input class="form-control d-ib" type="text"  ' +
                    'value="' + text + '" style="width: 220px"><a href="#">' +
                    '<i class="fa fa-check ml-xs color-red" onclick="save(this,\'' + id + '\')"></i>' +
                    '</a>');
            }

        }

        function save(obj,id){
            var updateVal="";
            if(id=='10'){
                updateVal=$('input:radio[name="tzdysffk"]:checked').val();
            }
            else if(id=='8'||id=='9'){
                updateVal=$('#timeinput'+id).val();
            }
            else {
                updateVal=$(obj).parents("td").find("input").val();
            }
            if(id=='3'||id=='4'||id=='5'||id=='6'||id=='7'||id=='12'||id=='13'||id=='15') {
                if(checkNumber(updateVal)==false){
                    yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),content:"设置值必须是数字"});
                    return false;
                }
            }
            var fld=$(obj).parents("td").attr("id");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/system/updateSysPara",
                data:{
                    "id":id,
                    "val":updateVal,
                },
                success:function(res) {
                    if(id=='10') {
                        if(updateVal=="0"){
                            $("#" + fld).html('<span>未启用</span> '+
                                '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this,\'' + id + '\')"></i>' +
                                '</a>');
                        }
                        else{
                            $("#" + fld).html('<span>已启用</span> '+
                                '<a href="#"><i class="fa fa-check ml-xs color-red" onclick="save(this,\'' + id + '\')"></i>' +
                                '</a>');
                        }
                    }
                    else if(id=='8'||id=='9'){
                        $("#t"+id+"showstr").html(updateVal+'<a href=""><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\''+id+'\')"></i></a>');
                        $("#t"+id+"showstr").show();
                        $("#t"+id+"inputtime").hide();

                    }
                    else if(id=='5'||id=='6'||id=='7'){
                        $("#" + fld).html('<span>' + updateVal + '</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\'' + id + '\')"></i></a>');
                    }
                    else{
                        $("#" + fld).html('<span>' + updateVal + '</span><a href="#"><i class="fa fa-pencil ml-xs color-gray" onclick="edit(this,\'' + id + '\')"></i></a>');
                    }
                }
            });
        }
    </script>
</body>
</html>
