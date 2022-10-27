import Vue from "vue";
import store from "../store/index";
import VueRouter from "vue-router";
import wxShare from "@/utils/wxShare";


Vue.use(VueRouter);

// 解决报错
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
// push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}

const metaInfo = {
    // title: "金山恒丰科技",
    // keywords: "金山恒丰科技",
    // description: "金山恒丰科技",
}

const routes = [
  /*{
    path: "/",
    name: "index",
    component: () =>import(/!* webpackChunkName: "about" *!/ "../views/index/index.vue"),
    meta: {
        title:'首页',
        showFoot:true,
    },
  },*/
  {
    path: "/index",
    name: "index",
    component: () => import(/* webpackChunkName: "about" */ "../views/index/index.vue"),
    meta: {
        title:'首页',
        showFoot:true,
        needLogin:true,
    },
  },
  {
    path: "/bet",
    name: "bet",
    component: () => import(/* webpackChunkName: "about" */ "../views/index/bet.vue"),
    meta: {
      title: '投注',
      showFoot: true,
      needLogin: true,
    },
  },
  {
    path: "/bet1",
    name: "bet1",
    component: () => import(/* webpackChunkName: "about" */ "../views/index/bet1.vue"),
    meta: {
      title: '投注',
      showFoot: true,
      needLogin:true,
    },
  },
  {
    path: "/open",
    name: "open",
    component: () => import(/* webpackChunkName: "about" */ "../views/index/open.vue"),
    meta: {
      title: '开盘',
      showFoot: true,
      needLogin:true,
    },
  },
  {
    path: "/record",
    name: "record",
    component: () =>import(/* webpackChunkName: "about" */ "../views/record/record.vue"),
    meta: {
        title:'记录',
        showFoot:true,
        needLogin:true,
    },
  },
    {
        path: "/deal",
        name: "deal",
        component: () =>import(/* webpackChunkName: "about" */ "../views/record/deal.vue"),
        meta: {
            title:'交易记录',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/profit",
        name: "profit",
        component: () =>import(/* webpackChunkName: "about" */ "../views/record/profit.vue"),
        meta: {
            title:'盈利情况',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/me",
        name: "me",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/me.vue"),
        meta: {
            title:'我的',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/message",
        name: "message",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/message.vue"),
        meta: {
            title:'个人信息',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/setname",
        name: "setname",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/setname.vue"),
        meta: {
            title:'修改名称',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/level",
        name: "profit",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/level.vue"),
        meta: {
            title:'代理等级',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/invite",
        name: "invite",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/invite.vue"),
        meta: {
            title:'邀请好友',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/team",
        name: "team",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/team.vue"),
        meta: {
            title:'我的团队',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/yield",
        name: "yield",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/yield.vue"),
        meta: {
            title:'团队收益',
            showFoot:true,
            needLogin:true,
        },
    },
    {
        path: "/notice",
        name: "notice",
        component: () =>import(/* webpackChunkName: "about" */ "../views/me/notice.vue"),
        meta: {
            title:'公告',
            showFoot:true,
            needLogin: true,
        },
    },
    {
        path: "/",
        name: "wallet",
        component: () =>import(/* webpackChunkName: "about" */ "../views/wallet.vue"),
        meta: {
            title:'链接钱包',
            showFoot:false,
        },
    },
];

const scrollBehavior = (to, from, savedPosition) => {
  // console.log('滚动行为', to, from, savedPosition)
    if (savedPosition) {
        return savedPosition
    } else {
        return { x: 0,  y: 0 }
    }
}
const router = new VueRouter({  
  routes,
  base: "/",
  // mode: "hash",
  mode: "history",
  scrollBehavior
});

router.beforeEach((to, from, next) => {
  // debugger
  let userToken = localStorage.getItem("userToken");
  let userId = localStorage.getItem("userId");

  let is_login = userId && userToken;
  // console.log(is_login)
  if (is_login) {
      store.commit('set_isLogin', true)
  }
  // console.log(to.meta.needLogin);
  if (to.meta.needLogin) {
      if (is_login) {
          next();
      } else {
          next({path:'/'});
      }
  } else {
      next();
  }
});

router.afterEach((to, from) => {
    // console.log(to, from);
    // console.log("router.js  切换路由,重新配置微信jssdk");

    // setWxShareLink(to, from);
    // wxShare.getSign();
})
function setWxShareLink(to, from) {
  // debugger
  let link = '';
  let query = to.query || {};
  let queryStrArr = [];


  if (to) {
    link = location.origin + to.path
    if (query) {
      for (var key in query) {
        console.log(query[key]);
        if (key != 'code' && key != 'state') {
          queryStrArr.push(`${key}=${query[key]}`)
        }
      }
    }

    queryStrArr.push('wxshare=wxshare')

    if (queryStrArr.length) {
      link = link + '?' + queryStrArr.join('&')
    }
  }

  // console.log('setWxShareLink 分享链接', link);

  sessionStorage.setItem('WxShareLink', link)
}


export default router;
