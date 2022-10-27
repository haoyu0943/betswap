<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p v-html="langdata['ylqk']">盈利情况</p>
      </div>
      <div class="content" id="content">
        <div class="content_top">
            <div class="content_top_sy">
                <p class="num" v-html="totalProfit">1000</p>
                <p class="name">{{ langdata['zsy'] }}(USDT)</p>
            </div>
            <div class="content_top_sy">
              <p class="num" v-html="totalOrder">20</p>
              <p class="name" v-html="langdata['zds']">总单数</p>
            </div>
        </div>
        <div class="content_shaixuan">
          <ul class="content_shaixuan_cla1">
            <li v-bind:class="cla1==1?'content_shaixuan_cla1_li':''" v-on:click="cla1=1" v-html="langdata['qc']">全场</li>
            <li v-bind:class="cla1==2?'content_shaixuan_cla1_li':''" v-on:click="cla1=2" v-html="langdata['rf']">比分</li>
            <li v-bind:class="cla1==3?'content_shaixuan_cla1_li':''" v-on:click="cla1=3" v-html="langdata['dx']">大小</li>
          </ul>
        </div>
        <div class="content_xuanze">
          <div class="shaixuan" @click="zzshow=true">
            <img src="../../assets/img/betwap/me/shaixuan.png" alt="">
            <p v-html="langdata['sx']">筛选</p>
          </div>
          <div class="ipt" v-bind:class="ipt_value==1?'ipt_a':''" v-on:click="ipt_value=1;pageNum=0;init();">
            <p v-html="langdata['tz']">投注</p>
          </div>
          <div class="ipt" v-bind:class="ipt_value==2?'ipt_a':''" v-on:click="ipt_value=2;pageNum=0;init();">
            <p v-html="langdata['kp']">开盘</p>
          </div>
        </div>
        <div class="content_list">
          <div class="content_list_yl" v-for="(value ,key) in list" v-on:click="list[key]['cek']=!list[key]['cek']">
            <div class="content_list_yl_xz">
              <img v-bind:src="value.cek?xz_ing:xz" alt="">
            </div>
            <div class="content_list_yl_xinxi">
              <div class="content_list_yl_xinxi_top">
                <p class="quanchang" v-if="value.quotationType==1" v-html="langdata['qc']">全场</p>
                <p class="quanchang" v-if="value.quotationType==2" v-html="langdata['rf']">比分</p>
                <p class="quanchang" v-if="value.quotationType==3" v-html="langdata['dx']">大小</p>
                <div class="time"><p class="sj" v-html="value.matchTime">2022-3-15  AM 20:30</p><p v-html="langdata['ylje']">盈利金额</p></div>
              </div>
              <div class="content_list_yl_xinxi_but">
                <div class="mingcheng">
                  <div class="duiwu">
                    <div class="fen"></div><p class="name" v-html="value.homeTeam">伯恩利</p><p class="num" v-html="value.odds" v-if="value.winTeamId==value.homeTeamId">10.5</p><p class="win" v-if="value.winTeamId==value.homeTeamId">WIN</p>
                  </div>
                  <div class="duiwu">
                    <div class="lv"></div><p class="name" v-html="value.guestTeam">南安普</p><p class="num" v-html="value.odds" v-if="value.winTeamId==value.guestTeamId">10.5</p><p class="win" v-if="value.winTeamId==value.guestTeamId">WIN</p>
                  </div>
                </div>
                <div class="huobi">
                  {{value.profitAmount}} USDT
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="morePage" v-if='load_more == 1'>
        <van-loading type="spinner" size="24px">正在加载</van-loading>
      </div>
      <div class="morePage" v-if='load_more == 2'>没有更多数据了</div>
      <div class="bottom" v-on:click="setqxtype()">
        <img v-bind:src="qxtype==0?xz:xz_ing" alt="">
        <p>{{ langdata['qbxz'] }} <span v-html="count">2</span>{{ langdata['dg'] }} <span v-html="USDTnum">1000</span> USDT</p>
      </div>
      <van-popup v-model:show="dizhi" position="bottom" :style="{width:'100%', height: '230px' }">
        <van-picker
            :show-toolbar="true"
            :title="langdata['sx']"
            :columns="columns"
            @confirm="onConfirm"
            @cancel="onCancel"
            @change="onChange"
        />
      </van-popup>
      <van-popup v-model:show="zzshow" position="right" :style="{ height: '100%' }">
        <div class="xz_wrap">
          <div class="xztitle" v-html="langdata['rqxz']">日期选择</div>
          <div class="riqi">
            <ul class="riqi_shaixuan_cla2">
              <li v-bind:class="cla3==1?'riqi_shaixuan_cla2_li':''" v-on:click="cla3=1" v-html="langdata['br']">本日</li>
              <li v-bind:class="cla3==2?'riqi_shaixuan_cla2_li':''" v-on:click="cla3=2" v-html="langdata['bz']">本周</li>
              <li v-bind:class="cla3==3?'riqi_shaixuan_cla2_li':''" v-on:click="cla3=3" v-html="langdata['by']">本月</li>
              <li v-bind:class="cla3==4?'riqi_shaixuan_cla2_li':''" v-on:click="cla3=4" v-html="langdata['bn']">本年</li>
            </ul>
            <div class="xzfwtitle" v-html="langdata['xzfw']">选择范围</div>
            <div class="timelist">
              <p v-on:click="showtime1=true" v-html="qitime">起始日期</p>
              <div></div>
              <p v-on:click="showtime2=true" v-html="zhitime">终止日期</p>
            </div>
            <van-popup v-model:show="showtime1" position="bottom" :style="{ height: '310px' }">
              <van-datetime-picker
                  v-model="currentDate1"
                  type="date"
                  :title="langdata['qsrq']"
                  :min-date="minDate"
                  :max-date="maxDate"
                  @confirm="confirmFn1"
                  @cancel="showtime1=false"
              />
            </van-popup>
            <van-popup v-model:show="showtime2" position="bottom" :style="{ height: '310px' }">
              <van-datetime-picker
                  v-model="currentDate2"
                  type="date"
                  :title="langdata['zzrq']"
                  :min-date="minDate"
                  :max-date="maxDate"
                  @confirm="confirmFn2"
                  @cancel="showtime2=false"
              />
            </van-popup>
          </div>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script>
import {langdata} from "@/utils/lang";

Date.prototype.format = function(fmt) {
  var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt)) {
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  }
  for(var k in o) {
    if(new RegExp("("+ k +")").test(fmt)){
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    }
  }
  return fmt;
}
export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      cla1:1,
      cla2:0,
      cla3:0,
      list:[],
      xz:require("../../assets/img/betwap/xz.png"),
      xz_ing:require("../../assets/img/betwap/xz-ing.png"),
      qxtype:0,
      count:0,
      zzshow:false,
      showtime1:false,
      showtime2:false,
      currentDate1:new Date(),
      currentDate2:new Date(),
      minDate:new Date(1970, 0, 1),
      maxDate:new Date(2099, 12, 31),
      qitime:langdata(localStorage.getItem("lang"))['qsrq'],
      zhitime:langdata(localStorage.getItem("lang"))['zzrq'],
      dizhi:false,
      columns:['开盘','投注'],
      ipt_value:1,
      endTime:"",
      startTime:"",
      totalOrder:0,
      totalProfit:0,
      USDTnum:0,
      load_more:0,
      pageNum:0,
    }
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll, true);
  },
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.init();
  },
  watch:{
    list:{
      handler:function (val){
        let list=this.list;
        let qxtype=1;
        let count =0;
        let USDTnum=0;
        for (let key in list){
          if(!list[key]['cek']){
            qxtype=0;
          }
          if(list[key]['cek']){
            count++;
            USDTnum = USDTnum+parseFloat(list[key].profitAmount)
          }
        }
        this.count=count;
        this.USDTnum=USDTnum;
        this.qxtype=qxtype;
      },
      deep: true
    },
    qitime:{
      handler:function (val){
        this.startTime=Date.parse(this.qitime)/1000;
        this.pageNum=0;
        this.init();
      },
      deep: true
    },
    zhitime:{
      handler:function (val){
        this.endTime=Date.parse(this.zhitime);
        this.pageNum=0;
        this.init();
      },
      deep: true
    },
    cla1:{
      handler:function (val){
        this.pageNum=0;
        this.init();
      },
      deep: true
    },
    cla2:{
      handler:function (val){
        if(val==1){
          this.startTime=new Date(new Date().toLocaleDateString()).getTime()
          this.endTime=(new Date(new Date().toLocaleDateString()).getTime()+(24*60*60*1000));
        }
        if(val==2){
          this.startTime=(new Date(new Date().toLocaleDateString()).getTime() - (new Date().getDay()+6) * 24 * 60 * 60 * 1000)
          this.endTime=this.startTime+60*60*24*7*1000;
        }
        if(val==3){
          this.startTime=new Date(new Date().getFullYear(), new Date().getMonth(), 1).getTime()
          this.endTime=(new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getTime() + 24 * 60 * 60*1000)
        }
        if(val==4){
          this.startTime=new Date(new Date().getFullYear(), 0).getTime()
          this.endTime=(new Date(new Date().getFullYear(), 11, 31).getTime() + 24 * 60 * 60*1000)
        }
        this.qitime=this.lang=='zh_CN'?"起始时间":localStorage.getItem("lang")=='zh_HK'?"起始時間":"Start time";
        this.zhitime=this.lang=='zh_CN'?"終止时间":localStorage.getItem("lang")=='zh_HK'?"終止時間":"End time"
        this.pageNum=0;
        this.init();
      },
      deep: true
    }
  },
  methods: {
    init:function (){
      if(this.pageNum==0){
        this.list=[];
      }
      var fontdata={
        pageNum:this.pageNum,
        pageSize:10,
        quotationType:this.cla1,
        transactionType:this.ipt_value,
      };
      if((this.endTime!='' && this.startTime!='') && (this.endTime>this.startTime)){
        fontdata['startTime']=this.startTime;
        fontdata['endTime']=this.endTime;
      }
      this.$api("flow/queryProfitByCondition",fontdata,"post").then(res =>{
        this.totalOrder=res.data.totalOrder;
        this.totalProfit=res.data.totalProfit;
        var dtos=res.data.dtos;
        for(var key in dtos){
          dtos[key]['cek']=false;
        }
        var list=this.list;
        this.list=list.concat(dtos);
        console.log(res)
      })
    },
    setqxtype:function (){
      this.qxtype=this.qxtype==1?0:1;
      let ck =this.qxtype==1?true:false;
      let list=this.list;
      for (let key in list){
        list[key]['cek']=ck;
      }
      this.list=list;
    },
    confirmFn1:function () {
      let endTime = new Date(this.currentDate1).format("yyyy-MM-dd")
      this.qitime = endTime
      this.cla2=0;
      this.showtime1 = false
    },
    confirmFn2:function () {
      let dateStr = new Date(this.currentDate2).format('yyyy-MM-dd')
      this.zhitime = dateStr
      this.cla2=0;
      this.showtime2 = false
    },
    onConfirm:function (value, index){
      this.ipt_value=value;
      this.pageNum=0;
      this.init();
      this.dizhi=false;
    },
    onChange:function (value, index){
      // Toast(`当前值: ${value}, 当前索引: ${index}`);
    },
    onCancel:function (){
      this.dizhi=false;
    },
    handleScroll() {
      //这里使用个延时加载，不然滑动屏幕的时候会不断触发方法，去计算高度，浪费性能
      let that = this
      clearTimeout(this.timer)
      that.timer = setTimeout(function () {
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop; //元素超出上边界的值
        let windowHeight = document.documentElement.clientHeight || document.body.clientHeight; //窗口的高度
        let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight; //元素高度
        console.log('scrollTop + windowHeight',scrollTop + windowHeight);
        let scroll = parseInt(Math.round(scrollTop)) + parseInt(Math.round(windowHeight))
        //获取到关键属性的值后，判断即可
        console.log('scrollTop + windowHeight',scroll);
        console.log('scrollHeight',scrollHeight);
        // if (scroll >= scrollHeight) {
        console.log("触底");
        that.load_more = 1;
        //在触底时获取当前页数和当前列表数组的长度
        that.pageNum++;
        that.init();
        // }
      }, 500);
    },
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
.content{
  padding-bottom: 65px;
}
.content_top{
  display: flex;
  justify-content: space-between;
  padding: 25px 10px;
  background: #0A0A0A;
}
.content_top_sy{
  width: 48%;
  //height: 69px;
  background: linear-gradient(0deg, #0075BA, #29D3FF);
  border-radius: 4px;
  text-align: center;
  padding: 14px 0;
  .num{
    font-size: 22px;
    font-family: PingFang SC;
    font-weight: bold;
    color: #FFFFFF;
  }
  .name{
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #FFFFFF;
  }
}
.content_shaixuan_cla1{
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  li{
    font-size: 14px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #9e9e9e;
    text-align: center;
    padding: 10px 30px;
  }
}
.content_shaixuan_cla1 .content_shaixuan_cla1_li{
  border-bottom: 3px solid #EB40AC;
  color: #FFFFFF;
}
.content_shaixuan_cla2{
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  li{
    font-size: 13px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #9e9e9e;
    opacity: 0.5;
    padding: 30px 0;
  }
}
.content_shaixuan_cla2 .content_shaixuan_cla2_li{
  color: #EB40AC;
}
.content_xuanze{
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: 20px 0;
  .shaixuan{
    margin-right: 10px;
    display: flex;
    img{
      width: 13px;
      height: 14px;
      margin-right: 3px;
    }
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
  .ipt_a{
    background: linear-gradient(0deg, #0075BA, #29D3FF);
  }
  .ipt{
    width: 70px;
    height: 30px;
    //background: linear-gradient(0deg, #0075BA, #29D3FF);
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px;
    border: 1px solid #F9F9F9;
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
    img{
      width: 15px;
    }
  }
  .ipt:nth-child(1){
    margin-right: 0;
  }
}
.content_list{
  padding: 0 10px;
}
.content_list_yl{
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 5px;
  .content_list_yl_xz{
    flex-shrink: 0;
    margin-right: 10px;
    img{
      wwidth: 16px;
      height: 16px;
      border-radius: 50%;
    }
  }
  .content_list_yl_xinxi{
    width: 100%;
    //height: 76px;
    background: #2A2A2A;
    border-radius: 8px;
    padding: 10px 7px;
    .content_list_yl_xinxi_top{
      .quanchang{
        font-size: 9px;
        font-family: FZLanTingHei-DB-GBK;
        font-weight: 400;
        color: #FFFFFF;
      }
      .time{
        display: flex;
        justify-content: space-between;
        margin-top: 8px;
        font-size: 9px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
        .sj{
          font-size: 10px;
          opacity: 0.4;
        }
      }
    }
    .content_list_yl_xinxi_but{
      font-size: 9px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      opacity: 0.88;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .mingcheng{
        .duiwu{
          display: flex;
          align-items: center;
          .fen{
            width: 3px;
            height: 3px;
            background: #EB40AC;
            border-radius: 50%;
          }
          .lv{
            width: 3px;
            height: 3px;
            background: #0888C8;
            border-radius: 50%;
          }
          .name{
            margin-left: 3px;
          }
          .num{
            font-size: 12px;
            font-family: FZLanTingHei-DB-GBK;
            font-weight: 400;
            color: #FFFFFF;
            margin-left: 10px;
          }
          .win{
            width: 40px;
            //padding: 2px 0;
            background: #EB40AC;
            border-radius: 6px;
            font-size: 9px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
            text-align: center;
            margin-left: 8px;
          }
        }
      }
      .huobi{
        font-size: 14px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
        opacity: 0.8;
      }
    }
  }
}
.bottom{
  width: 100%;
  height: 57px;
  background: #D0D0D0;
  position: fixed;
  z-index: 99;
  bottom: 50px;
  display: flex;
  align-items: center;
  img{
    width: 16px;
    height: 16px;
    background: #F6F6F6;
    border: 1px solid #AAAAAA;
    border-radius: 50%;
    margin-left: 10px;
  }
  p{
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: bold;
    color: #0C0C0C;
    margin-left: 10px;
    span{
      color: #EB40AC;
    }
  }
}
.van-popup{
  width: 262px;
  //height: 505px !important;
  background: #1c1c1c;
}
.van-popup--right{
  //top: 350px;
  //background: #4E4E4E;
}
.xz_wrap{
  padding: 40px 12px;
  .xztitle,.xzfwtitle{
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: 500;
    color: #FFFFFF;
  }
  .riqi{
    margin-top: 22px;
    .riqi_shaixuan_cla2{
      //padding: 0 30px;
      display: flex;
      justify-content: space-between;
      li{
        font-size: 12px;
        font-family: PingFang SC;
        font-weight: bold;
        color: #FFFFFF;
      }
    }
    .riqi_shaixuan_cla2 .riqi_shaixuan_cla2_li{
      color: #EB40AC;
    }
  }
  .xzfwtitle{
    margin-top: 32px;
  }
  .timelist{
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin-top: 16px;
    div{
      width: 30px;
      height: 1px;
      background: #C3C3C3;
    }
    p{
      width: 99px;
      padding: 4px 0;
      background: #9B9B9B;
      border-radius: 8px;
      font-size: 12px;
      font-family: PingFang SC;
      font-weight: 500;
      color: #FFFFFF;
      text-align: center;
    }
  }
}
</style>
