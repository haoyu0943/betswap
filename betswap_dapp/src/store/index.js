import Vue from "vue";
import Vuex from "vuex";

import router from '@/router'


import ajax from "@/utils/request.js"; //导入 axios 配置
const api = ajax.api; //请求方法

// debugger
// console.log('========== ajax ==========', ajax);

Vue.use(Vuex);

export default new Vuex.Store({
  state: {    
	userToken:'',
	userId:'',
	baseInfo: {},//用户信息
	isLogin: false,//是否登录
  },
  
  getters: {},

  mutations: {    
    // 设置用户信息
		setUserInfo(state, data) {
			console.log("设置用户信息", {...data });
		  	let { userToken, userId } = data;

		  	state.userToken = userToken;
		  	state.userId = userId;
			state.baseInfo = data;
			state.isLogin = true;

			localStorage.setItem("userToken", userToken);
			localStorage.setItem("userId", userId);

			localStorage.setItem("baseInfo", JSON.stringify(data));
		},
	//设置登陆状态
		set_isLogin(state, value) {
			console.log('--------------- 用户是否登录 ---------------', value)
			state.isLogin = value;
		},
	//清空登录信息
        clear_loginInfo(state) {
			console.log("====清空登录信息====");
			state.userToken = "";
			state.userId = "";
			state.baseInfo = {};
            state.isLogin = false;
            localStorage.clear();
        },
  },
  actions: {
	    async appInit({ commit, state, dispatch }, data) {
            let token = localStorage.getItem("userToken");
            let user_id = localStorage.getItem("userId");
            if (token && user_id) {
                commit('set_isLogin', true)
                // dispatch("getUserInfo");
                console.log("======== 用户已登录 ========");
            } else {
                commit('set_isLogin', false)
                console.log("======== 用户未登录 ========");
            }
        },

		// 获取用户信息
		async getUserInfo({ commit, state, dispatch }) {
			api("users_userInfo").then(res => {
				console.log('动态获取用户信息', res)
				let { code, data, message } = res;
				// debugger
				if (code == 1) {
					commit('set_isLogin', true)
					commit('setUserInfo', data)
				} else {
					commit('set_isLogin', false)
				}
			})
		},

		//退出
		vuex_out(context) {
			//退出登陆
			context.commit('clear_loginInfo');
			router.replace({
				path: '/wallet'
			})
		},
  },
  modules: {},
});
