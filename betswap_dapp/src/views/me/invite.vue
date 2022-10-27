<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p v-html="langdata['yqhy']">邀请好友</p>
      </div>
      <div class="content">
          <div class="yqbg">
            <div class="yqbg_top">
              <p v-html="langdata['yqm']"> 邀请码</p>
              <p>{{userData.invitationCode}}</p>
              <p class="bit" style="display: none;">{{urldata}}</p>
            </div>
<template>
            <div class="erweima"><span ref="qrCodeUrl" style="width: 135px;height: 135px;"></span></div>
</template>
<!--            <img src="../../assets/img/betwap/ewm.png" alt="">-->
          </div>
          <div class="content_ipt" v-on:click="copyHandle(userData.invitationCode)" v-html="langdata['yqhy']"><p>邀请好友</p></div>
      </div>
    </div>
  </div>
</template>

<script>
/*import VConsole from "vconsole";
const vConsole = new VConsole();*/
import QRCode from "qrcodejs2";
import ClipboardJS from 'clipboard'
import {langdata} from "@/utils/lang";

export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      userData:[],
      qrCode:null,
      urldata:"",
    }
  },
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.getuser()
  },
  mounted() {},
  methods: {
    /*copyHandle(content){
      var url = window.location.protocol+"//"+window.location.host+"?code="+content;
      let copy = (e)=>{
        e.preventDefault()
        e.clipboardData.setData('text/plain',url)
        alert('已复制到剪切板:'+url)
        document.removeEventListener('copy',copy)
      }
      document.addEventListener('copy',copy)
      document.execCommand("Copy");
    },*/
    copyHandle(content){
      var that=this;
      var url = window.location.protocol+"//"+window.location.host+"?code="+content;
      this.urldata=url;
      let clipboard = new ClipboardJS(".content_ipt",{
        text: function () {
          return url; /* 要复制的文本 */
        },
      });
      clipboard.on("success", function(e) {
        alert(that.langdata['fzcg']+':'+url)
        e.clearSelection();
        clipboard.destroy()
      });
      clipboard.on("error", function(e) {
        alert("error！");
        e.clearSelection();
      });
    },
    // 获取个人信息
    getuser(){

      this.$api("user/userDetail").then(res=>{
        this.userData = res.data
        this.creatQrCode(res.data.invitationCode);
      })
    },
    creatQrCode(code) {
       this.qrCode = new QRCode(this.$refs.qrCodeUrl, {
        text: code, // 需要转换为二维码的内容
        width: 165,
        height: 165,
        colorDark: "black", //#000000为黑色
        colorLight: "white",
      });
    },
  },
}
</script>

<style lang="less" scoped>
.header{
  padding: 30px 30px 250px 20px;
  //background-color: #0A0A0A;
  display: flex;
  align-items: center;
  background: url("../../assets/img/betwap/me/yaoqingma_top.png") no-repeat top;
  background-size: cover;
  background-position-y: -30px;
  img{
    width: 18px;
  }
  p{
    font-size: 19px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin-left: 10px;
  }
}
.content{
  padding-bottom: 50px;
}
.yqbg{
  width: 202px;
  height: 284px;
  background: url("../../assets/img/betwap/me/yaoqingma.png") no-repeat top;
  background-size: cover;
  margin: 0 auto;
  margin-top: -80px;
  .yqbg_top{
    width: 164px;
    height: 70px;
    border-bottom: 1px dashed #F9F9F9;
    margin: 0 auto;
    text-align: center;
    p:nth-child(1){
      font-size: 14px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      padding-top: 10px;
    }
    p:nth-child(2){
      font-size: 22px;
      font-family: FZLanTingHei-DB-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
  .erweima{
    width: 135px;
    height: 135px;
    background: #FFFFFF;
    border-radius: 10px;
    display: block;
    margin: 0 auto;
    margin-top: 40px;

  }
}
.content_ipt{
  width: 202px;
  height: 34px;
  background: linear-gradient(0deg, #0075BA, #29D3FF);
  border-radius: 5px;
  margin: 0 auto;
  margin-top: 27px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  p{
    font-size: 12px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
  }
}
.content_betswap{
  width: 186px;
  //height: 136px;
  display: block;
  margin: 0 auto;
  margin-top: 30px;
}
</style>
