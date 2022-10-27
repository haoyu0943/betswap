<template>
  <div>
    <div class="wrap">
      <div class="header">
        <div class="header_top">
          <div>
            <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
            <p v-html="langdata['wdtd']">我的团队</p>
          </div>
          <div v-on:click="xstype=xstype==1?0:1;init();">
            <img v-bind:src="xstype==1?xianshi:yincang" alt="">
            <p v-html="xstype==1?langdata['ksyc']:langdata['ksxs']">亏损隐藏</p>
          </div>
        </div>
        <div class="header_title">
          <p v-html="langdata['zsy']">总收益</p>
          <p v-html="langdata['jrsy']">今日收益</p>
        </div>
        <div class="header_bank">
          <div class="header_bank_list">
            <div class="header_bank_list_zsy">
              <img src="../../assets/img/betwap/wallet/USDT.png" alt="USDT.png">
              <div class="header_bank_list_zsy_num">
                <p>USDT</p>
                <p v-html="totalRevenueUSDT">138231.1254</p>
              </div>
            </div>
            <div class="header_bank_list_sy">
              <p>USDT</p>
              <p v-html="todayProfitUSDT">5.5478</p>
            </div>
          </div>
          <div class="header_bank_list">
            <div class="header_bank_list_zsy">
              <img src="../../assets/img/betwap/wallet/BET.png" alt="BET.png">
              <div class="header_bank_list_zsy_num">
                <p>BET</p>
                <p v-html="totalRevenueBET">541475.10</p>
              </div>
            </div>
            <div class="header_bank_list_sy">
              <p>BET</p>
              <p v-html="todayProfitBET">3.4534</p>
            </div>
          </div>
        </div>
      </div>
      <div class="person">
        <ul class="person_xinzeng">
          <li><p v-html="subordinateCount">15</p><p v-html="langdata['ztrs']">直推人数</p></li>
          <li><p v-html="totalPerson">15</p><p v-html="langdata['tdrs']">团队人数</p></li>
          <li><p v-html="todayAddCount">15</p><p v-html="langdata['jrxz']">今日新增</p></li>
          <li v-on:click="$router.push('/yield?teamId='+teamId)"><!--<p>10024.24</p>--><p>{{ langdata['tdsy'] }} <img src="../../assets/img/betwap/lan-you.png" alt=""></p></li>
        </ul>
        <div class="person_ipt" v-on:click="$router.push('/invite')"><p v-html="langdata['yqhy']">邀请好友</p></div>
        <ul class="person_numlist">
          <li><div><p v-html="langdata['pm']">排名</p></div><div><p>ID</p></div><div><p v-html="langdata['jysy']">交易收益</p></div></li>
          <li v-for="(value ,key) in persons"><div><p v-html="key+1">1</p></div><div><p v-html="value.userId">3548745</p></div><div><p>{{value.usdtNumber}}(USDT)</p><p>{{value.betNumber}}(BET)</p></div></li>
        </ul>
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
      xstype:1,
      xianshi:require("../../assets/img/betwap/deal/xianshi.png"),
      yincang:require("../../assets/img/betwap/deal/yincang.png"),
      todayAddCount:0,
      todayProfitBET:0,
      todayProfitUSDT:0,
      totalRevenueBET:0,
      totalRevenueUSDT:0,
      totalPerson:0,
      persons:[],
      teamId:"",
      total:0,
      subordinateCount:0,
    }
  },
  mounted() {

  },
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.init();
  },
  methods: {
    init(){
      this.$api("team/teamMember",{
        ifLoss:this.xstype
      },"post").then(res=>{
        this.todayAddCount=res.data.todayAddCount
        this.todayProfitBET=res.data.todayProfitBET
        this.todayProfitUSDT=res.data.todayProfitUSDT
        this.totalRevenueBET=res.data.totalRevenueBET
        this.totalRevenueUSDT=res.data.totalRevenueUSDT
        this.totalPerson=res.data.totalPerson
        this.persons=res.data.persons;
        this.teamId=res.data.teamId
        this.subordinateCount=res.data.subordinateCount
        this.total=res.data.total
        console.log(res.data)
      })
    }
  },
}
</script>

<style lang="less" scoped>
.header{
  padding: 30px 20px 100px 20px;
  background: url("../../assets/img/betwap/me/team_bg.png") no-repeat top;
  background-size: cover;
  background-position-y: -30px;
  .header_top{
    display: flex;
    justify-content: space-between;
    align-items: center;
    div:nth-child(1){
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
    div:nth-child(2){
      display: flex;
      align-items: center;
      img{
        width: 18px;
      }
      p{
        font-size: 14px;
        font-family: FZLanTingHei-DB-GBK;
        font-weight: 400;
        color: #FFFFFF;
        margin-left: 5px;
      }
    }
  }
  .header_title{
    font-size: 14px;
    font-family: PingFang SC;
    font-weight: bold;
    color: #FFFFFF;
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    p{
      width: 120px;
    }
  }
  .header_bank{
    .header_bank_list{
      margin-top: 15px;
      display: flex;
      justify-content: space-between;
      .header_bank_list_zsy{
        display: flex;
        align-items: center;
        img{
          width: 27px;
          height: 27px;
        }
        .header_bank_list_zsy_num{
          p{
            margin-left: 10px;
          }
          p:nth-child(1){
            font-size: 10px;
            font-family: PingFang SC;
            font-weight: 400;
            color: #FFFFFF;
          }
          p:nth-child(2){
            font-size: 17px;
            font-family: PingFang SC;
            font-weight: bold;
            color: #1BA27A;
          }
        }
      }
      .header_bank_list_sy{
        p{
          width: 117px;
        }
        p:nth-child(1){
          font-size: 10px;
          font-family: PingFang SC;
          font-weight: 400;
          color: #FFFFFF;
        }
        p:nth-child(2){
          font-size: 17px;
          font-family: PingFang SC;
          font-weight: 500;
          color: #FFFFFF;
          opacity: 0.8;
        }
      }
    }
    .header_bank_list:nth-child(2) .header_bank_list_zsy_num p:nth-child(2){
      color: #047FC1;
    }
  }
}
.person{
  // width: 327px;
  background: #2A2A2A;
  border-radius: 4px;
  margin: 20px;
  margin-top: -60px;
  padding: 25px 20px;
  .person_xinzeng{
    display: flex;
    justify-content: space-between;
    text-align: center;
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: bold;
    color: #FFFFFF;
    img{
      width: 5px;
      height: 10px;
    }
    li p:nth-child(2){
      font-size: 12px;
      font-family: PingFang SC;
      font-weight: bold;
      color: #FFFFFF;
      opacity: 0.5;
      margin-top: 2px;
    }
    li:nth-child(4) p{
      color: #047FC1;
      opacity: 1;
    }
  }
  .person_ipt{
    //width: 328px;
    height: 40px;
    background: linear-gradient(0deg, #0075BA, #29D3FF);
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 30px;
    margin-bottom: 30px;
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
  .person_numlist{
    text-align: left;
    li{
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 9px;
      font-family: PingFang SC;
      font-weight: 400;
      color: #FFFFFF;
      padding: 13px 0;
      border-bottom: 1px solid #202020;
    }
    li:nth-child(1){
      font-size: 12px;
      font-family: PingFang SC;
      font-weight: bold;
      color: #FFFFFF;
      opacity: 0.5;
    }
    li div:nth-child(1){
      width: 25px;
      text-align: center;
    }
    li div:nth-child(2){
      width: 114px;
      text-align: center;
    }
    li div:nth-child(3){
      width: 80px;
    }
  }
}
</style>
