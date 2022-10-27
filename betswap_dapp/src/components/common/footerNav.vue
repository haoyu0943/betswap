<template>
  <div class="footer">
    <van-tabbar route active-color class="van-tabbar-item--active" :border="false">
      <van-tabbar-item
        replace
        to="/index"
        :icon="is_zhuye ? icon1_a : icon1"
        >{{ navlist[lang][0] }}</van-tabbar-item
      >
      <van-tabbar-item
        replace
        to="/record"
        :icon="is_record ? icon2_a : icon2"
        >{{ navlist[lang][1] }}</van-tabbar-item
      >
      <van-tabbar-item
        replace
        to="/me"
        :icon="is_me ? icon3_a : icon3"
        >{{ navlist[lang][2] }}</van-tabbar-item
      >
    </van-tabbar>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "CommonFooter",
  components: {},
  data() {
    return {
        lang:localStorage.getItem("lang"),
        navlist:{
          zh_CN:["首页","记录","我的"],
          zh_HK:["首頁","記錄","我的"],
          en_US:["Home","Record","My"],
          ja_JP:["トップページ","記録","私の"],
          ms_MY:["Home","Record","My"]
        },
        icon1:require('@tabbar/shouye.png'),
        icon2:require('@tabbar/jiaoyi.png'),
        icon3:require('@tabbar/wode.png'),
        icon1_a:require('@tabbar/shouye_lan.png'),
        icon2_a:require('@tabbar/jiaouoi_lan.png'),
        icon3_a:require('@tabbar/wode_lan.png'),
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      /*localStorage.setItem("userToken", null);
      localStorage.setItem("userId", null);*/
      var lang = localStorage.getItem("lang");
      if (lang == "" || lang == null) {
        this.lang = "zh_CN";
        localStorage.setItem("lang", "zh_CN");
      }
    },
  },
  computed: {
    is_zhuye() {
      return this.$route.name == "index";
    },
    is_record(){
      return this.$route.name == "record";
    },
    is_me() {
      return this.$route.name == "me";
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
.van-tabbar-item{
  color: #8492B0;
}
.active-color{
  color: #0A8DCC;
}
.van-tabbar-item--active {
  color: #0A8DCC;
  background-color: #0C0C0C;
}

.van-tabbar {
  color: #8492B0;
  z-index: 1000;
  background-color: #0C0C0C;
}
</style>


