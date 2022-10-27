<template>
  <div class="page">
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p v-html="langdata['tdsy']">团队收益</p>
      </div>
    </div>
    <div class="content">
      <ul class="content_cla">
        <li>
          <p v-html="langdata['jt']">今天</p>
          <img src="../../assets/img/betwap/xia.png" alt="">
        </li>
        <li :class="cla1==1?'li_action':''" @click="tabClick(1)"><p>USDT</p></li>
        <li :class="cla1==2?'li_action':''" @click="tabClick(2)"><p>BET</p></li>
      </ul>
      <div class="content_list">
        <div v-if="cla1==1">
          <div class="content_list_kuai" v-for="(value ,key) in list" :key="key">
            <div class="content_list_kuai_order">
              <p v-html="langdata['ddh']">订单号：{{value.orderNumber}}</p>
              <p v-html="value.createTime">2022-03-14 8:28 AM</p>
            </div>
            <div class="content_list_kuai_money">
              <p v-html="value.amount">+0.044800</p>
              <p>(USDT)</p>
            </div>
          </div>
        </div>
        <div v-if="cla1==2">
          <div class="content_list_kuai content_list_kuai1" v-for="(value ,key) in list" :key="key">
            <div class="content_list_kuai_order">
              <p v-html="langdata['ddh']">订单号：{{value.orderNumber}}</p>
              <p v-html="value.createTime">2022-03-14 8:28 AM</p>
            </div>
            <div class="content_list_kuai_money">
              <p v-html="value.amount">+0.044800</p>
              <p>(BET)</p>
            </div>
          </div>
        </div>

        <div class="morePage" v-if='load_more == 1'>
            <van-loading type="spinner" size="24px">正在加载</van-loading>
        </div>
        <div class="morePage" v-if='load_more == 2'>没有更多数据了</div>


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
      cla1:1,
      list:[],
      pagination: {
        pageNum:0,//当前页
        pageSize:10,//每页数量
      }, //分页器
      total:0,
      load_more:0,
    }
  },
  watch:{
    // cla1:{
    //   handler(val){
    //     this.init();
    //   },
    //   deep: true
    // },
  },
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.init()
  },
  mounted() {
      window.addEventListener("scroll", this.handleScroll);
  },
  methods: {
    init(){
      this.$api("team/findTeamRevenue",{
        ifLoss:1,
        payType:''+this.cla1,
        ...this.pagination
      },"post").then(res=>{
        if(res.code = 1000){
          let data = res.data.dtos;
          this.total = parseInt(res.data.total) || 0;
          if (!this.empty(data)) {
            this.list = this.list.concat(data)
            setTimeout(()=>{
              this.load_more = 0
            },1000)
          }
        }else{
          alert(res.message)
        }
        console.log(res.data)
      })
    },
    // 切换点击
    tabClick(cla1){
        if(cla1 == this.cla1){return false}
        this.cla1 = cla1

        this.list = []
        this.load_more = 0
        this.pagination.pageNum = 0
        this.init();
    },

    // 判断是否为空
    empty(str){
        if(str == '' || str == undefined || str == null){
            return true
        }
        return false
    },
    // 监听页面滑动
    handleScroll() {
        //这里使用个延时加载，不然滑动屏幕的时候会不断触发方法，去计算高度，浪费性能
        let that = this
        clearTimeout(this.timer)
        that.timer = setTimeout(function () {
            let scrollTop = document.documentElement.scrollTop || document.body.scrollTop; //元素超出上边界的值
            let windowHeight = document.documentElement.clientHeight || document.body.clientHeight; //窗口的高度
            let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight; //元素高度
            console.log('scrollTop + windowHeight',scrollTop + windowHeight);
            // let scroll = parseInt(Math.round(scrollTop)) + parseInt(Math.round(windowHeight))
            //获取到关键属性的值后，判断即可
            // if (scrollTop + windowHeight  >= scrollHeight) {
                console.log("触底");
                that.load_more = 1;
                //在触底时获取当前页数和当前列表数组的长度
                let pageNum = that.pagination.pageNum;
                let length = that.list.length;
                //对比当前列表长度和列表总数，如果相等，说明没有数据了，否则页数加一，再次调获取列表接口
                if (length == that.total) {
                    // 我也是有底线的
                    that.load_more = 2;
                } else {
                    pageNum++
                    that.pagination.pageNum = pageNum;
                    that.init();
                }
            // }
        }, 500);
    },
  },
  destroyed(){
    window.removeEventListener("scroll", this.handleScroll)

  }

}
</script>

<style lang="less" scoped>
.page{overflow: hidden;}
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
  .content_cla{
    display: flex;
    margin:24px  20px 20px;
    // margin-top: 24px;
    // margin-bottom: 20px;
    li{
      display: flex;
      align-items: center;
      margin-right: 23px;
      img{
        width: 10px;
        height: 10px;
      }
      font-size: 14px;
      font-family: FZLanTingHei-DB-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
    .li_action{
      color: #0684C5;
    }
  }
  .content_list{
    margin: 20px;
    .content_list_kuai{
      // width: 331px;
      // height: 70px;
      background: #2A2A2A;
      border-radius: 4px;
      margin: 0 auto;
      margin-top: 2px;
      background: url("../../assets/img/betwap/deal/Tbeijing.png") no-repeat top;
      background-size: cover;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .content_list_kuai_order{
        padding: 18px 13px;
        p:nth-child(1){
          font-size: 9px;
          font-family: PingFang SC;
          font-weight: 400;
          color: #FFFFFF;
          opacity: 0.5;
        }
        p:nth-child(2){
          font-size: 12px;
          font-family: PingFang SC;
          font-weight: 400;
          color: #FFFFFF;
          margin-top: 4px;
        }
      }
      .content_list_kuai_money{
        padding-right: 38px;
        p:nth-child(1){
          font-size: 12px;
          font-family: FZLanTingHei-DB-GBK;
          font-weight: 400;
          color: #1BA27A;
        }
        p:nth-child(2){
          font-size: 9px;
          font-family: PingFang SC;
          font-weight: 400;
          color: #FFFFFF;
          text-align: center;
        }
      }
    }
    .content_list_kuai1{
      background: url("../../assets/img/betwap/deal/Bbeijing.png") no-repeat top;
      background-size: cover;
      .content_list_kuai_money{
        p:nth-child(1){
          color: #0B8FCD;
        }
      }
    }
  }
}
</style>
