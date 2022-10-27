<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p v-html="langdata['dldj']">代理等级</p>
      </div>
      <div class="level">
        <div class="level_ka" v-bind:class="'level_ka'+rank">
          <div class="level_ka_xx">
            <div>
              <div class="level_ka_dj"><p>LV{{rank}}</p><p class="dq" v-html="langdata['dqdj']">当前等级</p></div>
              <div class="level_ka_jl"><p>{{ langdata['jlsj'] }}LV{{rank+1}}{{ langdata['hc'] }}</p></div>
              <div class="level_ka_jdk"><div class="level_ka_jdt" :style="{width:todayAddCount==0?'0px':(todayAddCount/upgradePerson*178)+'px'}"></div></div>
              <div class="level_ka_num"><p>{{ langdata['czz'] }} {{todayAddCount}}/{{upgradePerson}}人</p><p>{{ totalRevenueUSDT }}/{{ upgradeUSDT }}(U)</p></div>
            </div>
            <img v-bind:src="v1" alt="" v-if="rank==1">
            <img v-bind:src="v2" alt="" v-if="rank==2">
            <img v-bind:src="v3" alt="" v-if="rank==3">
            <img v-bind:src="v4" alt="" v-if="rank==4">
            <img v-bind:src="v5" alt="" v-if="rank==5">
            <img v-bind:src="v6" alt="" v-if="rank==6">
            <img v-bind:src="v7" alt="" v-if="rank==7">
          </div>
          <div class="level_ka_shuju">
            <div>
              <p class="shouyi1">{{ langdata['jrsy'] }}(USDT/BET)</p>
              <p class="shouyi" v-html="'+'+todayProfitUSDT">+1234.00</p>
            </div>
            <div>
              <p class="zshouyi1">{{ langdata['zsy'] }}(USDT/BET)</p>
              <p class="zshouyi" v-html="'+'+totalRevenueUSDT">+64.000.00</p>
            </div>
            <div>
              <p class="gonglue1"></p>
              <p class="gonglue" v-html="langdata['sjgl']">升级攻略</p>
            </div>
          </div>
        </div>
      </div>
      <div class="person">
        <ul class="person_xinzeng">
          <li><p v-html="subordinateCunt">15</p><p v-html="langdata['ztrs']">直推人数</p></li>
          <li><p v-html="totalPerson">15</p><p v-html="langdata['tdrs']">团队人数</p></li>
          <li><p v-html="todayAddCount">15</p><p v-html="langdata['jrxz']">今日新增</p></li>
        </ul>
        <div class="person_ipt" v-on:click="$router.push('/invite')"><p v-html="langdata['yqhy']">邀请好友</p></div>
        <ul class="person_numlist">
          <li><div><p v-html="langdata['pm']">排名</p></div><div><p>ID</p></div><div><p v-html="langdata['wdtd']">团队</p></div><div><p v-html="langdata['jysy']">交易收益</p></div></li>
          <li v-for="(value ,key) in persons"><div><p v-html="key+1">1</p></div><div><p v-html="value.userId">3548745</p></div><div><p v-html="value.teamId">S4AD</p></div><div><p>{{value.usdtNumber}}(USDT)</p><p>{{value.betNumber}}(BET)</p></div></li>
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
      v1:require("../../assets/img/betwap/v1.png"),
      v2:require("../../assets/img/betwap/v2.png"),
      v3:require("../../assets/img/betwap/v3.png"),
      v4:require("../../assets/img/betwap/v5.png"),
      v5:require("../../assets/img/betwap/v5.png"),
      v6:require("../../assets/img/betwap/v6.png"),
      v7:require("../../assets/img/betwap/v7.png"),
      rank:1,
      persons:[],
      totalPerson:0,
      upgradePerson:0,
      todayAddCount:0,
      todayProfitUSDT:0,
      totalRevenueUSDT:0,
      upgradeUSDT:0,
      subordinateCunt:0,
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
      this.$api("team/myTeamMember",{
      },"post").then(res=>{
        this.rank=res.data.rank;
        this.persons=res.data.persons;
        this.totalPerson=res.data.totalPerson
        this.upgradePerson=res.data.upgradePerson
        this.todayAddCount=res.data.todayAddCount
        this.totalRevenueUSDT=res.data.totalRevenueUSDT
        this.todayProfitUSDT=res.data.todayProfitUSDT
        this.upgradeUSDT=res.data.upgradeUSDT;
        this.subordinateCunt=res.data.subordinateCunt;
        console.log(res.data)
      })
    }
  },
}
</script>

<style lang="less" scoped>
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
.level_ka{
  // width: 329px;
  height: 184px;
  //background: linear-gradient(90deg, #FAE5DA, #D99D8C);
  border-radius: 4px;
  margin: 0 20px;
  padding: 0 24px;
}
.level_ka1{
  background: url("../../assets/img/betwap/1bg.png") 100% 100%;
}
.level_ka2{
  background: url("../../assets/img/betwap/2bg.png") 100% 100%;
}
.level_ka3{
  background: url("../../assets/img/betwap/3bg.png") 100% 100%;
}
.level_ka4{
  background: url("../../assets/img/betwap/4bg.png") 100% 100%;
}
.level_ka5{
  background: url("../../assets/img/betwap/5bg.png") 100% 100%;
}
.level_ka6{
  background: url("../../assets/img/betwap/6bg.png") 100% 100%;
}
.level_ka7{
  background: url("../../assets/img/betwap/7bg.png") 100% 100%;
}
.level_ka_xx{
  display: flex;
  justify-content: space-between;
  .level_ka_dj{
    font-size: 19px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #2F2E2E;
    display: flex;
    align-items: flex-end;
    margin-top: 21px;
    .dq{
      width: 50px;
      height: 13px;
      background: #2F2E2E;
      border-radius: 2px;
      font-size: 7px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      text-align: center;
      margin-left: 10px;
    }
  }
  .level_ka_jl{
    font-size: 10px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #2F2E2E;
    margin-top: 10px;
  }
  .level_ka_jdk{
    width: 178px;
    height: 5px;
    background: #EDEEED;
    border-radius: 2px;
    margin-top: 13px;
    .level_ka_jdt{
      width: 108px;
      height: 5px;
      background: #dda594;
      border-radius: 2px;
    }
  }
  .level_ka_num{
    display: flex;
    align-items: center;
    font-size: 9px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #2F2E2E;
    margin-top: 13px;
    p{
      margin-right: 10px;
    }
  }
  img{
    width: 97px;
    height: 130px;
  }
}
.level_ka_shuju{
  margin-top: 13px;
  display: flex;
  justify-content: space-between;
  div{
    font-size: 9px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #2F2E2E;
    .shouyi,.zshouyi,.gonglue{
      font-size: 10px;
      //width: 150px;
      font-family: FZChaoCuHei-M10S;
      font-weight: 800;
      color: #2F2E2E;
    }
    .gonglue{
      font-size: 7px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #222222;
    }
    .gonglue1{
      width: 1px;
      height: 16px;
    }
  }
}
.person{
  // width: 327px;
  background: #2A2A2A;
  border-radius: 4px;
  margin: 20px;
  margin-top: 11px;
  padding: 25px 20px;
  .person_xinzeng{
    display: flex;
    justify-content: space-between;
    text-align: center;
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: bold;
    color: #FFFFFF;
    li p:nth-child(2){
      font-size: 12px;
      font-family: PingFang SC;
      font-weight: bold;
      color: #FFFFFF;
      opacity: 0.5;
      margin-top: 2px;
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
    li:nth-child(1) div:nth-child(4){
      text-align: center;
    }
    li div:nth-child(1){
      width: 25px;
      text-align: center;
    }
    li div:nth-child(2){
      width: 76px;
      text-align: center;
      word-wrap:break-word; word-break:normal;
    }
    li div:nth-child(3){
      width: 76px;
      text-align: center;
      word-wrap:break-word; word-break:normal;
    }
    li div:nth-child(4){
      width: 70px;
    }
  }
}
</style>
