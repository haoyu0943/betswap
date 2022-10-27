<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.9.1.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/register/index.css?v=${randomNum}">
</head>
<body>
<div class="main">
    <div class="world">
        <h1 id="title1">NEW USER</h1>
        <h1 id="title2">REGISTRATION</h1>
        <div class="world_text">
            <input type="text" placeholder="Register" id="register">
        </div>
        <div class="world_text">
            <input type="password" placeholder="Password" id="password">
        </div>
        <div class="world_text">
            <input type="password" placeholder="Password again" id="password2">
        </div>
        <div class="world_text">
            <select id="qwhm" class="world_text_selects">

            </select>
        </div>
        <div class="world_text">
            <input type="number" placeholder="Phone number" id="phonenumber">
        </div>
        <div class="world_text">
            <input class="world_text_text" type="number" placeholder="Verification code" id="vcode">
            <input class="world_text_button" id="getcodebtn" type="button" value="Get Code" onclick="hqyzmpro()">
        </div>
        <div class="world_text">
            <input type="text" placeholder="Invitation code(optional)" value="${yqm}" readonly id="yqm">
        </div>
        <!--<p class="world_zhuyi">Agree to the privacy policy and terms of service </p>-->
        <input class="world_button" type="button" value="SIGN UP" id="retbtn" onclick="sendreg()">
    </div>
</div>
</body>
<%@ include file="../common/resource_mh.jsp"%>
<!--<script type="text/javascript" src="<%=ctxPath%>/register/index.js"></script>-->
<script type="text/javascript" src="<%=ctxPath%>/register/country.js"></script>
<script type="text/javascript">
    var vlan = "${lan}";
    initpagelan();

    function initpagelan(){
        $("#title1").html(get_lan("reg.title1"));
        $("#title2").html(get_lan("reg.title2"));
        $("#register").attr("placeholder",get_lan("reg.account"));
        $("#password").attr("placeholder",get_lan("reg.pwd"));
        $("#password2").attr("placeholder",get_lan("reg.pwd2"));
        $("#phonenumber").attr("placeholder",get_lan("reg.phone"));
        $("#vcode").attr("placeholder",get_lan("reg.vcode"));
        $("#getcodebtn").val(get_lan("reg.getcode"));
        $("#retbtn").val(get_lan("reg.su"));
        for(var i=0;i<countryjsion.length;i++){
            if(vlan=='cn') {
                $("#qwhm").append("<option value='" + countryjsion[i].code + "'>" + countryjsion[i].zh + "("+countryjsion[i].code+")</option>");
            }
            else if(vlan=='en') {
                $("#qwhm").append("<option value='" + countryjsion[i].code + "'>" + countryjsion[i].en + "("+countryjsion[i].code+")</option>");
            }
            else {
                $("#qwhm").append("<option value='" + countryjsion[i].code + "'>" + countryjsion[i].indonesia + "("+countryjsion[i].code+")</option>");
            }
        }

    }

    function get_lan(m) {
        switch(vlan){
            case 'cn':
                var t = cn[m];
                break;
            case 'en':
                var t = en[m];
                break;
            case 'in':
                var t = ind[m];
                break;
            default:
                var t = cn[m];
        }
        //if(t==undefined) t = cn[m];
        //if(t==undefined) t = en[m];
        //if(t==undefined) t = ind[m];
        //if(t==undefined) t = hk[m];
        //if(t==undefined) t = m;
        return t;
    }

    var dxyzmyxq=5;
    var yznum=dxyzmyxq*60;
    var yzint;
    function hqyzmpro(){
        if(isEmpty($("#phonenumber").val())){
            //yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("reg.ts")});
            alert(get_lan("reg.ts"));
            return false;
        }
        var phonenumber=$("#phonenumber").val();
        var qwh=$("#qwhm").val();
        $.ajax({
            url : serviceUrl+"/user/check/checkPhone",
            type: "post",
            data : {"phoneRegionNumber":qwh,"userPhone":phonenumber},
            success : function(data){
                if(data.code=="1000"){
                    /*
                    alert("phoneRegionNumber="+qwh);
                    alert("userPhone="+phonenumber);
                    $.ajax({
                        url : serviceUrl+"/user/login/BySms",
                        type: "post",
                        data : {"phoneRegionNumber":qwh,"userPhone":phonenumber},
                        success : function(data){
                            alert(data.code);
                            if(data.code=="1000"){
                                yzint=setInterval("yzclock()",1000);
                                $("#yzmjybtn").show();
                                $("#sjdtyzm").removeAttr("disabled");
                            }
                            else{
                                //yyAlert({title:"提示",style:"e",content:"获取验证码出现异常"});
                                //yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("reg.codeerr")});
                                alert(get_lan("reg.codeerr"));
                            }
                        }
                    });
                    */
                    yzint=setInterval("yzclock()",1000);
                }
                else{
                    //yyAlert({title:"提示",style:"e",content:"获取验证码出现异常"});
                    //yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("reg.codeerr")});
                    alert(get_lan("reg.havephone")+":"+data.message);
                    //alert(data.code+"="+data.message);
                }
            }
        });



    }

    function yzclock(){
        yznum--;
        if(yznum==0){
            clearInterval(yzint);
            $("#getcodebtn").val(get_lan("reg.getcode"));
            $("#getcodebtn").attr("onclick","hqyzmpro();");
            yznum=dxyzmyxq*60;
        }
        else{
            $("#getcodebtn").removeAttr("onclick");
            $("#getcodebtn").val(yznum);
            //$("#getcodebtn").attr("value",yznum);
        }
    }

    function sendreg(){
        var register=$("#register").val();
        var password=$("#password").val();
        var password2=$("#password2").val();
        var phonenumber=$("#phonenumber").val();
        var qwhm=$("#qwhm").val();
        var qwhmtext=$("#qwhm").find("option:selected").text();

        var vcode=$("#vcode").val();
        var yqm=$("#yqm").val();
        if(register==""){
            alert(get_lan("reg.account")+get_lan("message.blank"));
            return;
        }
        if(qwhm==""){
            alert(get_lan("reg.areacode")+get_lan("message.blank"));
            return;
        }
        if(password==""||password2==""){
            alert(get_lan("reg.pwd")+get_lan("message.blank"));
            return;
        }
        else{
            if(password!=password2){
                alert(get_lan("reg.lcsrbyz"));
                return;
            }
        }
        if(phonenumber==""){
            alert(get_lan("reg.phone")+get_lan("message.blank"));
            return;
        }
        if(vcode==""){
            alert(get_lan("reg.vcode")+get_lan("message.blank"));
            return;
        }
        var pdata={};
        //alert(qwhmtext.substr(0,qwhmtext.indexOf("(")));
        pdata["password"]=password;
        pdata["phoneRegion"]=qwhmtext.substr(0,qwhmtext.indexOf("("));
        pdata["phoneRegionNumber"]=qwhm;
        pdata["userName"]=register;
        pdata["userPhone"]=phonenumber;
        pdata["moviesInvitationCode"]=yqm;
        $.ajax({
            url : serviceUrl+"/user/check/checkName",
            type: "post",
            data : {"userName":register},
            success : function(data){
                if(data.code=="1000") {
                    $.ajax({
                        url: serviceUrl + "/user/check/verifyPhone",
                        type: "post",
                        data: {"messageContent": vcode, "phoneRegionNumber": qwhm, "userPhone": phonenumber},
                        success: function (data) {
                            if (data.code == "1000") {
                                $.ajax({
                                    url: serviceUrl + "/user/register/ByPassword",
                                    type: "post",
                                    data: pdata,
                                    success: function (res) {
                                        if (res.code == "1000") {
                                            alert(get_lan("reg.regsucess"));
                                            window.close();
                                        } else {
                                            alert(get_lan("reg.regerr")+":"+res.message);
                                            //alert(res.code+"="+res.message);
                                        }
                                    }
                                });
                            } else {
                                //alert(data.message);
                                alert(get_lan("reg.codefaild")+":"+data.message);
                            }
                        }
                    });
                }
                else{
                    alert(get_lan("reg.userhave")+":"+data.message);
                }
            }
        });
    }
</script>
</html>