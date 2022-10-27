<template>
  <div id="app">
	<div :class="{padbottom:$route.meta && $route.meta.showFoot}">
		<router-view></router-view>
	</div>

    <CommonFoot v-if="$route.meta && $route.meta.showFoot" />
  </div>
</template>
<script>
import { Toast } from "vant";
import wxLogin from "@/utils/wxLoginSimple";
import CommonFoot from "@/components/common/footerNav.vue";

import { mapState } from "vuex";
export default {
  components: {
    CommonFoot,
  },
  data() {
    return {};
  },
  computed: {},
  created() {},
  mounted() {},
  methods: {
    copyHandle(content){
      let copy = (e)=>{
        e.preventDefault()
        e.clipboardData.setData('text/plain',content)
        alert('复制成功')
        document.removeEventListener('copy',copy)
      }
      document.addEventListener('copy',copy)
      document.execCommand("Copy");
    }
  },
};
</script>

<style lang="less">
.padbottom{
	padding-bottom: 50px;
}
html,body{
  background: #141414;
}
* {
  box-sizing: border-box;
  list-style: none;
  padding: 0;
  margin: 0;
  -webkit-text-size-adjust: none;
  font-family: Microsoft YaHei;
  box-sizing: border-box;
}
input,
textarea {
  outline: none;
  // -webkit-appearance: none;
  -webkit-tap-highlight-color: transparent;
  border: none;
  background: transparent;
  resize: none;
}
button,
input,
textarea {
  color: inherit;
  font: inherit;
  outline: none;
  border: none;
}

img {
  vertical-align: middle;
  max-width: 100%;
}

/*自动隐藏文字*/
.ellipsis-1 {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.ellipsis {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  word-break: break-all;
  white-space: normal !important;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.ellipsis-2 {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  word-break: break-all;
  white-space: normal !important;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.ellipsis-3 {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  word-break: break-all;
  white-space: normal !important;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.ellipsis-4 {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  word-break: break-all;
  white-space: normal !important;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}

// 清除浮动
.clearfix:after {
  content: "";
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.morePage{
  display: flex;
  justify-content: center;
  margin: 15px 0;
  font-size: 14px;
  color: #999;
}
</style>
