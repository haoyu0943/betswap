<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p v-html="langdata['xgyhm']">修改用户名</p>
      </div>
      <div class="content">
        <div class="username">
          <p v-html="langdata['yhm']">用户名</p>
          <input type="text" v-model="userName" v-bind:placeholder="langdata['qsryhm']">
        </div>
        <div class="ipt" v-bind:class="userName?'ipt':'ipt_h'" @click="submit">
          <p v-html="langdata['tjxg']">提交修改</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {langdata} from "@/utils/lang";

export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      userName:'',
      userData:[],
    }
  },
  mounted() {

  },
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.getuser();

  },
  methods: {
    // 获取个人信息
    getuser(){
      this.$api("user/userDetail").then(res=>{
        this.userData = res.data
        this.userName = res.data.userName
      })
    },
    submit(){
      this.$api("user/info/updateUserInfo", {
        userName:this.userName
      }).then(res=>{
        console.log(res);
        if(res.code==1000){
          alert('修改成功')
        }else{
          alert(res.message)
        }
      })
    }
  },
}
</script>

<style lang="less" scoped>
.wrap{
  background-color: #0A0A0A;
  height: calc(100vh - 50px);
}
.header{
  padding: 30px 30px 20px 20px;
  background-color: #0A0A0A;
  display: flex;
  align-items: center;
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
  .username{
    padding: 25px 20px;
    font-size: 14px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #FFFFFF;
    height: 20px;
    background: #2A2A2A;
    display: flex;
    align-items: center;
    input{
      width: 80%;
      padding-left: 20px;
    }
  }
  .ipt{
    width: 80vw;
    height: 35px;
    background: linear-gradient(0deg, #0075BA, #29D3FF);
    border-radius: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    margin-top: 50px;
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
  .ipt_h{
    background: #B2B2B2;
  }
}
</style>
