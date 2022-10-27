<template>
  <div>
    <div class="wrap">
      <div class="header">
        <div class="header_top">
          <div class="header_top_me" v-html="langdata['wd']">
            我的
          </div>
          <div class="header_top_img">
<!--            <div class="header_top_img1" v-on:click="$router.push('/notice')">
              <img src="../../assets/img/betwap/me/gonggao.png" alt="gonggao.png">
              <p class="header_top_img_num">4</p>
            </div>-->
            <!--<div>
              <img src="../../assets/img/betwap/me/shezhi.png" alt="shezhi.png">
            </div>-->
          </div>
        </div>
        <div class="header_user">
          <div class="header_user_xinxi">
            <div class="header_user_xinxi_img">
              <img :src="data.userAvatar" alt="BET.png" v-if="data.userAvatar">
              <img src="../../assets/img/betwap/wallet/BET.png" alt="BET.png" v-else  style="width:70%;height:70%;border-radius: 0;">
            </div>
            <div class="header_user_xinxi_comm" v-if="isLogin">
              <p>{{data.userName==''?'我是球迷':data.userName}} <img src="../../assets/img/betwap/me/xiugai.png" alt="xiugai.png" v-on:click="$router.push('/message')"></p>
              <p>ID:{{data.userId}}</p>
              <p style="width: 200px;word-wrap:break-word;">Address:{{address}}</p>
            </div>
            <div class="header_user_xinxi_level">
              {{rank}}
            </div>
          </div>
        </div>
      </div>
      <div class="content">
        <ul class="content_list">
          <li v-on:click="$router.push('/level')"><div class="content_list_right"><img src="../../assets/img/betwap/me/zuanshi.png" alt=""><p v-html="langdata['dldj']">代理等级</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>
<!--          <li><div class="content_list_right"><img src="../../assets/img/betwap/me/qianbao.png" alt=""><p>钱包管理</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>-->
          <li v-on:click="$router.push('/invite')"><div class="content_list_right"><img src="../../assets/img/betwap/me/fenxiang.png" alt=""><p v-html="langdata['wdyqm']">我的邀请码</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>
          <li v-on:click="$router.push('/team')"><div class="content_list_right"><img src="../../assets/img/betwap/me/tuandui.png" alt=""><p v-html="langdata['wdtd']">我的团队</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>
          <li class="content_ipt" v-on:click="copyHandle"><div class="content_list_right"><img src="../../assets/img/betwap/me/kefu.png" alt=""><p v-html="langdata['zxkf']">在线客服</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>
          <li v-on:click="yuyan=true"><div class="content_list_right"><img src="../../assets/img/betwap/me/yuyan.png" alt=""><p v-html="langdata['yyqh']">语言选择</p></div><img src="../../assets/img/betwap/me/you.png" alt=""></li>
        </ul>
      </div>
      <van-popup v-model:show="yuyan" position="bottom" :style="{ width:'100%',height: '230px' }">
        <van-picker
            :show-toolbar="true"
            :columns="columns"
            @confirm="onConfirm3"
            @cancel="onCancel3"
            @change="onChange3"
        />
      </van-popup>
    </div>
  </div>
</template>

<script>
/*import VConsole from "vconsole";
const vConsole = new VConsole();*/
import { mapState, mapMutations, mapActions } from "vuex";
import {langdata} from "@/utils/lang";
import ClipboardJS from 'clipboard';
export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      rank:"LV1",
      data:[],
      address:"",
      yuyan:false,
      columns:['English',"日本語","Melayu",'繁體中文'],
      kf:"",
    }
  },
  computed: {
      ...mapState(["isLogin","baseInfo"])
  },
  mounted() {},
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.getuser();
    this.getTronweb();
  },
  methods: {
    async getTronweb(){
      var tronWeb = window.tronWeb
      var address = tronWeb.defaultAddress.base58
      if (tronWeb && address) {
        let trxbalance = await tronWeb.trx.getBalance(address);
        this.address=address;
      }
    },
    getuser(){
      this.$api("user/userDetail",{
      },"post").then(res=>{
        this.rank = res.data.rank;
        this.data = res.data
      })
      this.$api("system/papa/findSysParaEntityById",{
        id:26
      },"post").then(ress=>{
        this.kf=ress.data.paraValue
      })
    },
    copyHandle(){
      var that=this;
      let clipboard = new ClipboardJS(".content_ipt",{
        text: function () {
          return that.kf; /* 要复制的文本 */
        },
      });
      clipboard.on("success", function(e) {
        alert(that.langdata['fzcg']+':'+that.kf)
        e.clearSelection();
        clipboard.destroy()
      });
      clipboard.on("error", function(e) {
        alert("error！");
        e.clearSelection();
      });
    },
    onConfirm3:function (value, index){
      if(value=='简体中文'){
        this.lang="zh_CN";
      }
      if(value=='繁體中文'){
        this.lang="zh_HK";
      }
      if(value=='English'){
        this.lang="en_US";
      }
      if(value=='日本語'){
        this.lang="ja_JP";
      }
      if(value=='Melayu'){
        this.lang="ms_MY";
      }
      localStorage.setItem("lang",this.lang);
      this.yuyan=false;
      location.reload();
    },
    onCancel3:function (){
      this.yuyan=false;
    },
    onChange3:function (){

    }
  },
}
</script>

<style lang="less" scoped>
.wrap{
  background: #141414;
  height:calc(100vh - 50px);
}
.header{
  //background-color: #0A0A0A;
  padding: 30px 30px 80px 30px;
  background: url("../../assets/img/betwap/me/wode_top.png") no-repeat top;
  background-size: cover;
}
.header_top{
  display: flex;
  justify-content: space-between;
}
.header_top_me{
  //width: 37px;
  height: 18px;
  font-size: 19px;
  font-family: FZLanTingHei-DB-GBK;
  font-weight: 400;
  color: #FFFFFF;
}
.header_top_img{
  width: auto;
  display: flex;
  align-items: center;
}
.header_top_img img{
  margin: 0 10px;
  width: 19px;
  height: 19px;
}
.header_top_img1{
  position: relative;
}
.header_top_img_num{
  color: #FFFFFF;
  background-color: #FF3131;
  border-radius: 50%;
  width: 15px;
  height: 15px;
  text-align: center;
  line-height: 15px;
  font-size: 11px;
  position:absolute;
  top: 8px;
  left: 3px;
}
.header_user_xinxi{
  margin-top: 40px;
  display: flex;
  align-items: center;
}
.header_user_xinxi_img{
  width: 62px;
  height: 62px;
  background: #FFFFFF;
  border-radius: 50%;
  text-align: center;
  //line-height: 62px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  overflow: hidden;
  img{
    // width: 35px;
    // height: 45px;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
.header_user_xinxi_comm{
  font-size: 16px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #FFFFFF;
  margin-left: 10px;
  p:last-of-type{
    font-size: 14px;
  }
  img{
    width: 13px;
    height: 13px;
  }
}
.header_user_xinxi_level{
  font-size: 14px;
  font-family: FZLanTingHei-DB-GBK;
  font-weight: 400;
  color: #FFFFFF;
  position: absolute;
  right: 40px;
}
.content{
  //margin-top: 40px;
}
.content_list{
  height: 100%;
  li:nth-child(1){
    border-top: 2px solid #0A0A0A;
  }
  li{
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #0A0A0A;
    padding: 0 30px;
    height: 45px;
    img{
      width: 13px;
      height: 13px;
    }
    .content_list_right{
      display: flex;
      align-items: center;
      //border-bottom: 1px solid #353535;
      img{
        width: 20px;
        height: 20px;
      }
      p{
        font-size: 14px;
        font-family: PingFang SC;
        font-weight: 400;
        color: #FFFFFF;
        margin-left: 10px;
      }
    }
  }
}
</style>
