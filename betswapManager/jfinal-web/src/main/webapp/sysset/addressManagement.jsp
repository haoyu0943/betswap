<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>address</title>
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
            <div class="pageheader" set-lan="html:log.log">平台地址管理<i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                <div class="hhBox">
                    <span class="wwmc">USDT地址：</span>
                    <input class="form-control " type="text" id="usdtShow" style="width: 150px" disabled>
                </div>
                <div class="hhBox">
                    <span class="wwmc">USDT私钥：</span>
                    <input class="form-control " type="text" id="usdtKeyShow" style="width: 150px;display: none" disabled>
                </div>
                <div class="hhBox">
                    <button type="button" class="btn btn-sm btn-info" onclick="addUsdt()">手动添加USDT</button>
                    <button type="button" class="btn btn-sm btn-info"  onclick="addUsdtAuto()" >系统生成USDT</button>
                </div>
                <div class="clear"></div>
            </div>

            <div class="page-inner">
                <div class="hhBox">
                    <span class="wwmc">TRX地址：</span>
                    <input class="form-control " type="text" id="trxShow" style="width: 150px" disabled>
                </div>
                <div class="hhBox">
                    <span class="wwmc">TRX私钥：</span>
                    <input class="form-control " type="text" id="trxKeyShow" style="width: 150px;display: none" disabled>
                </div>
                <div class="hhBox">
                    <button type="button" class="btn btn-sm btn-info" onclick="addTrx()" >手动添加TRX</button>
                    <button type="button" class="btn btn-sm btn-info" onclick="addTrxAuto()">系统生成TRX</button>
                </div>
                <div class="clear"></div>
            </div>

        </div>
    </div>

    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width:360px;margin: 8% auto;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close1"><span>&times;</span></button>
                    <h4 class="modal-title" id="addTitle"></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger">钱包地址：</dt>
                            <dd>
                                <input class="form-control" type="text" placeholder="钱包地址" style="width: 200px" id="dz"  >
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger">钱包私钥：</dt>
                            <dd>
                                <input class="form-control" type="text" placeholder="钱包私钥" style="width: 200px" id="sy"  >
                            </dd>
                        </dl>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type=hidden id="addressType">
                    <button type="button" class="btn btn-success" onclick="SaveYhxx()"><lan set-lan="html:message.sureText">确定</lan></button>
                    <button type="button" class="btn btn-default" onclick="closeYhck()"><lan set-lan="html:message.cancelText">取消</lan></button>
                </div>
            </div>
        </div>
    </div>




<%@ include file="../common/modpwd.jsp"%>
<script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js"></script>
<script>
    
    initData();
    
    function initData(){
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/sysset/qryAddress",
            data:{
                "addressType":1
            },
            success:function(data){
                var lst=data.addressLst;
                for(var i=0;i<lst.length;i++){
                    if(lst[i].type==1){
                        $("#usdtShow").val(lst[i].wallet_address);
                    }else if(lst[i].type==2){
                        $("#trxShow").val(lst[i].wallet_address);
                    }
                }

            }
        });
    }

    //手动添加USDT
    function addUsdt(){
        $("#dz").val("");
        $("#sy").val("");
        $("#addressType").val(1);
        $("#addTitle").text("新增USDT钱包");
        $(".zhezhao2").show();
    }
    //手动添加TRX
    function addTrx(){
        $("#dz").val("");
        $("#sy").val("");
        $("#addressType").val(2);
        $("#addTitle").text("新增TRX钱包");
        $(".zhezhao2").show();
    }
    //自动添加USDT
    function addUsdtAuto(){
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/sysset/saveDZA",
            data:{
                "addressType":1
            },
            success:function(data){
                if(data.flag == true){
                    $("#usdtShow").val(data.dz);
                    $("#usdtKeyShow").val(data.sy);
                    $("#usdtKeyShow").show();
                }
                else{
                    yyAlert({title:"提示",sureText:"确定",style:"e",content:data.message});
                }

            }
        });
    }

    //自动添加TRX
    function addTrxAuto(){
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/sysset/saveDZA",
            data:{
                "addressType":2
            },
            success:function(data){
                if(data.flag == true){
                    $("#trxShow").val(data.dz);
                    $("#trxKeyShow").val(data.sy);
                    $("#trxKeyShow").show();
                }
                else{
                    yyAlert({title:"提示",sureText:"确定",style:"e",content:data.message});
                }

            }
        });
    }


    function SaveYhxx(){
        var dz=$("#dz").val();
        var sy=$("#sy").val();
        var addressType=$("#addressType").val();

        if(addressType==""){
            yyAlert({title:"提示",sureText:"确定",content:"钱包类型不能为空"});
            return;
        }

        if(dz==""){
            yyAlert({title:"提示",sureText:"确定",content:"钱包地址不能为空"});
            return;
        }
        if(sy==""){
            yyAlert({title:"提示",sureText:"确定",content:"钱包私钥不能为空"});
            return;
        }
        $.ajax({
            type:"post",
            url:"<%=ctxPath%>/sysset/saveDZ",
            data:{
                "dz":dz,
                "sy":sy,
                "addressType":addressType
            },
            success:function(data){
                if(data.flag == true){
                    closeYhck();
                    document.location.reload();
                }
                else{
                    yyAlert({title:"提示",sureText:"确定",style:"e",content:data.message});
                }

            }
        });
    }

    function closeYhck(){
        $(".zhezhao2").hide();
    }


</script>
</body>
</html>
