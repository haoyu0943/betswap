import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "lib-flexible"; //网页适配

import "@/utils/request.js"; //  axios 配置

import Vant from 'vant';
import { Lazyload, Toast } from 'vant'

import 'vant/lib/index.css';

Vue.use(Vant);
Vue.use(Lazyload);
Vue.use(Toast);

window.alert = function (msg) {
    Toast(msg);
};

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  
  render: (h) => h(App),
}).$mount("#app");
