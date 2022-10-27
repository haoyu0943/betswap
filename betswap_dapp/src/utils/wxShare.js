import store from "@/store";
import axios from "axios";

export default {
    //微信授权登录相关
    /**
     *
     */


    //获取签名
    getSign() {

        let url = window.location.href.split("#")[0];
        // url= 'www.dongyinghengdeli.com';
        // console.log('=== 获取签名的网页地址 ===', url)
        // axios({
        //   url:
        //     process.env.NODE_ENV !== "production"
        //       ? "api/wxshare.php?a=wxshare"
        //       : "/wxshare.php?a=wxshare",
        //   method: "post",
        //   params: {
        //     url: url,
        //   },
        // }).then((res) => {
        //   this.initWxConfig(res);
        // });


        axios({
            url:
                process.env.NODE_ENV !== "production"
                    ? "api/service.php"
                    : "/service.php",

            method: "post",
            params: {
                com_id: 1279,
                action: 'jssdk_getSignPackage',
                url: url,
            },
        }).then((res) => {
            console.log('微信配置参数', res);

            // return 
            this.initWxConfig(res.data);
        });
    },

    //初始化微信配置
    initWxConfig(config) {
        let title = ' ';
        let desc = '';
        // let WxShareLink = sessionStorage.getItem('WxShareLink') || location.href
        let WxShareLink = window.location.origin + `/wxregister?fromId=${store.state.baseInfo.phone}`
        console.log(WxShareLink)
        console.log(config)
        let { appId, timestamp, nonceStr, signature } = config;

        //微信配置
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: appId, // 必填，公众号的唯一标识
            timestamp: timestamp, // 必填，生成签名的时间戳
            nonceStr: nonceStr, // 必填，生成签名的随机串
            signature: signature, // 必填，签名
            jsApiList: [
                // "chooseWXPay",
                'updateAppMessageShareData',
                'updateTimelineShareData'
            ], // 必填，需要使用的JS接口列表
        });

        setTimeout(() => {
            wx.ready(function () {
                //需在用户可能点击分享按钮前就先调用
                wx.updateAppMessageShareData({
                    title: title , // 分享标题
                    desc: desc, // 分享描述
                    link: WxShareLink, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: "", // 分享图标
                    success: function () {
                        console.log('已分享xxx');
                    },
                    fail(err) {
                        console.log('分享取消');
                    },
                });

                wx.updateTimelineShareData({
                    title: title, // 分享标题
                    link: WxShareLink, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: "", // 分享图标
                    success: function () {

                    },
                    fail(err) {

                    },
                });
            });
        }, 0);
    },
};
