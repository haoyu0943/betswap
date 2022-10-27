import Vue from "vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";

// axios.defaults.baseURL = '/api';
//设置axios为form-data
// axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
// axios.defaults.headers.get["Content-Type"] = "application/x-www-form-urlencoded";
// axios.defaults.transformRequest = [
//     function (data) {
//         let ret = "";
//         for (let it in data) {
//             ret += encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
//         }
//         return ret;
//     },
// ];



// 添加请求拦截器
// 在发送请求之前做些什么("请求拦截器")
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if(token){
    // 请求头携带token
    config.headers.token = token || '';
    // console.log("----------请求header携带token------------");
  }

  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});




// 添加响应拦截器
//respone拦截器==>对响应做处理
axios.interceptors.response.use(
    function (res) {
        // console.log("-------------- axios拦截 success----------------", res);
        let data = res.data;

        // debugger
        // 对响应数据做点什么
        if (data && data.code == -2) { //登录过期  重新授权
            data.message = '登录过期或未登录，请您先登录';
            store.commit('clear_loginInfo')//清除登录数据

            router.push({
                path: "/login",
                query: {
                    mode: "noLogin",
                },
            });
            alert(data.message)
        }

        if(data && data.code == 0 && data.message.includes('user_id或token不能为空')) {
            data.message = '登录过期或未登录，请您先登录';
            // store.commit('clear_loginInfo')//清除登录数据

            // router.push({
            //     path: "/login",
            //     query: {
            //         mode: "noLogin",
            //     },
            // });
        }

        // debugger

        return res.data;
    },
    function (error) {
        console.log("-------------- axios拦截 error----------------");
    }
);

/**
 *
 * @param {*} url
 * @param {*} data
 * @param {*} method
 * @param {*} uploaderConfig   上传文件时需要的的配置信息
 * @returns
 */

function api(url, data, method, uploaderConfig) {
    // debugger

    let reqUrl = "api/" + url;//请求地址
    let reqMethod = method || "post";//请求方式
    var lang=localStorage.getItem("lang");
    if(lang==""){
        lang="zh_CN";
    }
    let reqData = {
        ...data,
        userToken:localStorage.getItem("userToken"),
        lang:lang
    };//请求数据
    if (process.env.NODE_ENV !== "production") {
        reqUrl = "api/" + url;
        // console.log('本地');
    } else {
        // console.log('线上');
        reqUrl = "https://app.betswap.space/api/v1/" + url;
    }

    // debugger
    //axios 配置信息
    //普通请求 使用 x-www 格式
    //上传文件时  使用 formdata
    let otherConfig = {
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded'
        },
        transformRequest: uploaderConfig ? [] : [
            function (data) {
                let ret = "";
                for (let it in data) {
                    ret += encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
                }
                return ret;
            },
        ]
    };
    if (reqMethod == "get") {
        return axios({
            url: reqUrl,
            method: reqMethod,
            params: reqData,
            ...otherConfig,
            ...uploaderConfig
        });
    } else if (reqMethod == "post") {
        return axios({
            url: reqUrl,
            method: reqMethod,
            data: uploaderConfig ? data : reqData,
            ...otherConfig,
            ...uploaderConfig
        });
    }
}


Vue.prototype.$axios = axios;
Vue.prototype.$api = api;


export default {
    api
}
