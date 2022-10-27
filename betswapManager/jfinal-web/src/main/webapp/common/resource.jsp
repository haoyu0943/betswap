<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/jquery.royalslider.min.js"></script>
<!-- 工具类 -->
<script type="text/javascript" src="<%=ctxPath%>/js/sgutil.js"></script>
<!-- alert，confirm -->
<script type="text/javascript" src="<%=ctxPath%>/js/alert/dialog-two.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/jquery.dataTables.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/datatables-init.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/util.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/myDataTable.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/laydate/laydate.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/ajaxfileupload/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/ajaxfileupload/myAjaxFileUpload.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/md5.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/decimal.js"></script>
<!-- js中引入contextPath -->
<script type="text/javascript">var ctxPath="<%=ctxPath%>";</script>
<!-- js中引入多语言配置 -->
<script type="text/javascript" src="<%=ctxPath%>/js/language/cn.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/language/en.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/language/in.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/layui/layui.js"></script>
<!-- css样式单 -->
<link rel="stylesheet" href="<%=ctxPath%>/css/buttons.css" type="text/css">
<link href="<%=ctxPath%>/assets/css/bootstrap.css?v=${randomNum}" rel="stylesheet" />
<link href="<%=ctxPath%>/assets/css/font-awesome.css" rel="stylesheet" />
<link href="<%=ctxPath%>/assets/css/basic.css?v=${randomNum}" rel="stylesheet" />
<link href="<%=ctxPath%>/js/alert/dialog-two.css" rel="stylesheet" type="text/css">
<link href="<%=ctxPath%>/assets/choose/ui-choose.css" rel="stylesheet" >
<link href="<%=ctxPath%>/css/jquery.dataTables.css" rel="stylesheet">
<link href="<%=ctxPath%>/assets/img/favicon.ico" type="image/x-icon" rel="shortcut icon" />
<link rel="stylesheet" href="<%=ctxPath%>/js/laydate/need/laydate.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/layui/css/layui.css">
<style>
    .compare-label{font-weight: bold}
</style>
<script>
    $(function(){
        $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
        $("#main-menu").height($(window).height()-$(".navbar").height()-10);
    });
</script>
<script type="text/javascript">
    var serviceUrl="http://8.142.45.106:8080/api/v1";//后台路径
    // var serviceUrl="http://192.168.2.242:8080/api/v1";
    var formcheckMode;
    layui.use('form', function(){
        formcheckMode=layui.form;
        //自定义验证规则
        formcheckMode.verify({
            normalLength: function(value){
                if(value.length > 80){
                    return '至多不超过80个字符啊';
                }
            },
            textLength: function(value){
                if(value.length > 400){
                    return '至多不超过400个字符啊';
                }
            },
            minNumber:function(value){
                // var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                var reg=/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                if(!reg .test(value)){
                    console.log(value);
                    return '请填写正确金额，小数点不超过两位';
                }
            }

        });
    });

    function get_lan(m) {
        //获取文字
        var lan = $.tmCookie.getCookie('lan');     //语言版本
        //选取语言文字
        switch(lan){
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
                var t = en[m];
        }
        //若是所选语言的json中没有此内容就选取其余语言显示
        if(t==undefined) t = cn[m];
        if(t==undefined) t = en[m];
        if(t==undefined) t = ind[m];
        //if(t==undefined) t = hk[m];
        if(t==undefined) t = m; //若是仍是没有就返回他的标识

        return t;
    }

    function getJsonById(list,id,key) {
        for(var i=0;i<list.length;i++){
            if(list[i].id==id)
                return list[i][key];
        }
        return  "";
    }

    function initlan() {
        var lan = $.tmCookie.getCookie('lan');
        //alert("init="+lan);
        $('[set-lan]').each(function () {
            var me = $(this);
            var a = me.attr('set-lan').split(':');
            var p = a[0];   //文字放置位置
            var m = a[1];   //文字的标识

            //用户选择语言后保存在cookie中，这里读取cookie中的语言版本

            //选取语言文字
            if(p=="select"){
                //alert(m);
                var pp=m.indexOf(".");
                //alert(m.length+","+pp);
                var selectid=m.substr(pp+1,m.length-pp);
                //alert(m);
                //alert(selectid);
                var list=[];
                switch (lan) {
                    case 'cn':
                        list = cn[m];
                        break;
                    case 'en':
                        list = en[m];
                        break;
                    case 'in':
                        list = ind[m];
                        break;
                    default:
                        list = en[m];
                }
                var shtml="<option value=\"\"></option>";
                for(var i=0;i<list.length;i++){
                    shtml+="<option value=\""+list[i].id+"\">"+list[i].name+"</option>";
                }
                document.getElementById(selectid).innerHTML=shtml;
            }
            else {
                switch (lan) {
                    case 'cn':
                        var t = cn[m];  //这里cn[m]中的cn是上面定义的json字符串的变量名，m是json中的键，用此方式读取到json中的值
                        break;
                    case 'en':
                        var t = en[m];
                        break;
                    case 'in':
                        var t = ind[m];
                        break;
                    default:
                        var t = en[m];
                }

                //若是所选语言的json中没有此内容就选取其余语言显示
                if (t == undefined) t = cn[m];
                if (t == undefined) t = en[m];
                if (t == undefined) t = ind[m];
                //if (t == undefined) t = hk[m];

                if (t == undefined) return true;   //若是仍是没有就跳出

                //文字放置位置有（html,val等，能够本身添加）
                switch (p) {
                    case 'html':
                        me.html(t);
                        break;
                    case 'val':
                    case 'value':
                        me.val(t);
                        break;
                    default:
                        me.html(t);
                }
            }

        });
    }

    function checkNumber(theObj) {
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(theObj)) {
            return true;
        }
        return false;
    }

    function initTable(div_id){
        /*

        $("#"+div_id).dataTable( {
            searching: false,
            ordering:  false,
            "lengthMenu": [[20, 30, 40, -1], [20, 30, 40, "All"]],
            "oLanguage": {//国际语言转化
                "oAria": {
                    "sSortAscending": " - click/return to sort ascending",
                    "sSortDescending": " - click/return to sort descending"
                },
                "sLengthMenu": "",//显示 _MENU_ 记录
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sEmptyTable": "未有相关数据",
                "sLoadingRecords": "正在加载数据-请等待...",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
                "sInfoEmpty": "当前显示0到0条，共0条记录",
                "sInfoFiltered": "（数据库中共为 _MAX_ 条记录）",
                "sProcessing": "<img src='../resources/user_share/row_details/select2-spinner.gif'/> 正在加载数据...",
                "sSearch": "模糊查询：",
                "sUrl": "",
                //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
                "oPaginate": {
                    "sFirst": "first page",
                    "sPrevious": "previous page",
                    "sNext": "next page",
                    "sLast": "end page"
                }
            },

            "fnDrawCallback": function (oSettings) {

            },
            "fnCreatedRow": function (nRow, aData, iDataIndex) {

            }
        });
         */
        var lan = $.tmCookie.getCookie('lan');
        $("#"+div_id).dataTable( {
            searching: false,
            ordering:  false,
            "bFilter": false,    //去掉搜索框方法
            "bLengthChange": false, //改变每页显示数据数量
            "lengthMenu": [[20, 30, 40, -1], [20, 30, 40, "All"]],
            "language": {
                url: '<%=ctxPath%>/js/datatable/'+lan+'.json'
            },
            "fnDrawCallback": function (oSettings) {

            },
            "fnCreatedRow": function (nRow, aData, iDataIndex) {

            }
        });
    }
</script>
