import store from "@/store";

export default {
    appid: '', // 微信公众号 ID
    code: "", // 授权获取的 code
    authMarker: 'isWxAuthorize',
    query: {

    },

    //授权后使用code 注册用户信息
    user_regist() {
        // store.dispatch("users_wxLogin", {
        //   code: this.code,
        // });
    },

    //获取 url 的参数
    getUrlParam2(name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        //返回参数
        if (r != null) {
            return (r[2]);
        } else {
            return null;
        }
    },

    //获取url搜索的参数
    getUrlParam(name) {
        var searchParams = new URLSearchParams(location.search);
        let val = searchParams.get(name) || ''
        console.log('============ getUrlParam 搜索参数数值 ============', val)
        return val
    },


    //处理邀请人问题
    handleInvite(link) {
        if (link.includes("fromId=")) {
            let fromId = this.getUrlParam('fromId');;
            localStorage.setItem("fromId", fromId);
            console.log("微信登录设置 邀请人id", fromId);
        }
        if (link.includes("hid=")) {
            let hid = this.getUrlParam('hid');;
            localStorage.setItem("hid", hid);
            console.log("微信登录设置 活动id", hid);
        }
    },

    //处理授权
    handleAuthorize(successCallback, type) {
        // debugger
        let link = decodeURIComponent(window.location.href);
        this.handleInvite(link);

        console.log('微信登录链接', link)
        link = link.replace('#/', '');
        console.log('微信登录链接 处理#/', link)


        // debugger
        if (link.indexOf(this.authMarker) !== -1) {
            this.code = this.getUrlParam('code');
            store.commit('set_wx_code', this.code)
        }

        // debugger
        //获取到了code  并且未过期
        if (this.code  && !type) { 
            successCallback({ code: this.code, })
        } else {
            let { appid, authMarker } = this;
            
            if(type) {//过期了
                link = location.origin
            }
            
            let redirect_uri = encodeURIComponent(link)

            let shouquan_url = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${redirect_uri}&response_type=code&scope=snsapi_userinfo&state=${authMarker}#wechat_redirect`
            console.log('授权链接', shouquan_url)

            // debugger
            window.location.href = shouquan_url;
        }
    }

};