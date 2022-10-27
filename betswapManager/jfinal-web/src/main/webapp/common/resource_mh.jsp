<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- js中引入多语言配置 -->
<script type="text/javascript" src="<%=ctxPath%>/js/sgutil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/language/cn.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/language/en.js?v=${randomNum}"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/language/in.js?v=${randomNum}"></script>
<script type="text/javascript">
    var serviceUrl="http://8.142.45.106:8080/api/v1";//后台路径
    // var serviceUrl="http://192.168.2.242:8080/api/v1";
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
                var t = cn[m];
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

    function initlan(lan) {
        $('[set-lan]').each(function () {
            var me = $(this);
            var a = me.attr('set-lan').split(':');
            var p = a[0];   //文字放置位置
            var m = a[1];   //文字的标识

            //用户选择语言后保存在cookie中，这里读取cookie中的语言版本
            //var lan = $.tmCookie.getCookie('lan');
            //alert(m);

            //alert(lan);
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
                        list = cn[m];
                }
                var shtml="<option value=\"\"></option>";
                for(var i=0;i<list.length;i++){
                    shtml+="<option value=\""+list[i].id+"\">"+list[i].name+"</option>";
                }
                document.getElementById(selectid).innerHTML=shtml;
            }
            else {
                //alert(lan);
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
                        var t = cn[m];
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
</script>
