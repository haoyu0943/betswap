<template>
  <div>
    <div class="wrap">
      <div class="header">
        <div class="header_top">
          <div class="header_top_me" v-html="langdata['jyjl']">
            交易记录
          </div>
        </div>
        <div class="header_user">
          <div class="header_user_xinxi">
            <div class="header_user_xinxi_img">
              <p class="header_user_xinxi_img_sl"><span v-html="winning">80</span>%</p>
              <p class="header_user_xinxi_img_sl" v-html="langdata['sl']">胜率</p>
            </div>
          </div>
          <div class="header_user_xinxi_right">
            <div @click="zzshow=true"><img src="../../assets/img/betwap/me/shaixuan.png" alt="">
              <p v-html="langdata['sx']">筛选</p>
            </div>
            <div v-on:click="$router.push('/profit')"><img src="../../assets/img/betwap/me/yingli.png" alt="">
              <p v-html="langdata['ylqk']">盈利情况</p>
            </div>
          </div>
        </div>
      </div>
      <div class="content">
        <div class="content_shaixuan">
          <ul class="content_shaixuan_cla1">
            <li v-bind:class="cla1==1?'content_shaixuan_cla1_li':''" v-on:click="cla1=1" v-html="langdata['qc']">全场</li>
            <li v-bind:class="cla1==2?'content_shaixuan_cla1_li':''" v-on:click="cla1=2" v-html="langdata['rf']">比分</li>
            <li v-bind:class="cla1==3?'content_shaixuan_cla1_li':''" v-on:click="cla1=3" v-html="langdata['dx']">大小</li>
          </ul>
          <ul class="content_shaixuan_cla2">
            <li v-bind:class="cla2==0?'content_shaixuan_cla2_li':''" v-on:click="cla2=0" v-html="langdata['qb']">全部</li>
            <li v-bind:class="cla2==1?'content_shaixuan_cla2_li':''" v-on:click="cla2=1" v-html="langdata['wks']">未开赛</li>
            <li v-bind:class="cla2=='2,3,4,5,7'?'content_shaixuan_cla2_li':''" v-on:click="cla2='2,3,4,5,7'" v-html="langdata['jxz']">进行中</li>
            <li v-bind:class="cla2==8?'content_shaixuan_cla2_li':''" v-on:click="cla2=8" v-html="langdata['yws']">已完赛</li>
            <li v-bind:class="cla2==12?'content_shaixuan_cla2_li':''" v-on:click="cla2=12" v-html="langdata['yqx']">已取消</li>
            <li v-bind:class="cla2==9?'content_shaixuan_cla2_li':''" v-on:click="cla2=9" v-html="langdata['yyq']">已延期</li>
          </ul>
        </div>
        <div class="anniu">
          <div class="ipt" v-bind:class="ipt_value==1?'ipt_a':''" v-on:click="ipt_value=1;pageNum=0;init();">
            <p v-html="langdata['tz']">投注</p>
          </div>
          <div class="ipt" v-bind:class="ipt_value==2?'ipt_a':''" v-on:click="ipt_value=2;pageNum=0;init();">
            <p v-html="langdata['kp']">开盘</p>
          </div>
        </div>
        <div class="content_list">
          <div class="content_list_kuai" v-for="(value ,key) in list" v-on:click="$router.push('/deal?id='+value.id+'&homeTeamId='+value.homeTeamId+'&transactionType='+(ipt_value))">
            <div class="content_list_kuai_top">
              <p class="content_list_kuai_top_quan" v-if="value.quotationType==1" v-html="langdata['qc']">全场</p>
              <p class="content_list_kuai_top_quan" v-if="value.quotationType==2" v-html="langdata['rf']">比分</p>
              <p class="content_list_kuai_top_quan" v-if="value.quotationType==3" v-html="langdata['dx']">大小</p>
              <p class="content_list_kuai_top_pxtype_hs" v-if="value.status==0" v-html="langdata['ddjxz']">订单进行中</p>
              <p class="content_list_kuai_top_pxtype_cs" v-if="value.status==1" v-html="langdata['djz']">待结账</p>
              <p class="content_list_kuai_top_pxtype_ls" v-if="value.status==2" v-html="langdata['ddywc']">订单已完成</p>
              <p class="content_list_kuai_top_pxtype_hs" v-if="value.status==3" v-html="langdata['ddyqx']">订单已取消</p>
              <p class="content_list_kuai_top_pxtype_hs" v-if="value.status==4" v-html="langdata['jzsb']">结账失败</p>
              <p class="content_list_kuai_top_pxtype_hs" v-if="value.status==5" v-html="langdata['ddcsgb']">订单超时关闭</p>
              <p class="content_list_kuai_top_pxtype_hs" v-if="value.status==6" v-html="langdata['ddslz']">订单上链中</p>
              <p class="content_list_kuai_top_pxtype_ls" v-if="value.status==7" v-html="langdata['ddslsx']">订单上链失效</p>
              <p class="content_list_kuai_top_pxtype content_list_kuai_top_pxtype_hs" v-if="value.teamStatus==1">{{ langdata['wks'] }} <img src="../../assets/img/betwap/me/you.png" alt=""></p>
              <p class="content_list_kuai_top_pxtype content_list_kuai_top_pxtype_cs" v-if="value.teamStatus==2 || value.teamStatus==3 || value.teamStatus==4 || value.teamStatus==5 || value.teamStatus==7">
                {{ langdata['jxz'] }} <img src="../../assets/img/betwap/me/you.png" alt=""></p>
              <p class="content_list_kuai_top_pxtype content_list_kuai_top_pxtype_ls" v-if="value.teamStatus==8">{{ langdata['yws'] }} <img src="../../assets/img/betwap/me/you.png" alt=""></p>
              <p class="content_list_kuai_top_pxtype content_list_kuai_top_pxtype_hs" v-if="value.teamStatus==12">{{ langdata['yqx'] }} <img src="../../assets/img/betwap/me/you.png" alt=""></p>
              <p class="content_list_kuai_top_pxtype content_list_kuai_top_pxtype_hs" v-if="value.teamStatus==9">{{ langdata['yyq'] }} <img src="../../assets/img/betwap/me/you.png" alt=""></p>
            </div>
            <div class="content_list_kuai_but">
              <div class="content_list_kuai_left">
                <p class="content_list_kuai_left_time">{{sjgsh(value.matchTime)}}</p>
                <p class="content_list_kuai_left_pk" v-if="lang=='zh_CN'">{{value.homeTeam}}<img src="../../assets/img/betwap/VS.png" alt="">{{value.guestTeam}}</p>
                <p class="content_list_kuai_left_pk" v-if="lang=='zh_HK'">{{value.homeTeam_zht}}<img src="../../assets/img/betwap/VS.png" alt="">{{value.guestTeam_zht}}</p>
                <p class="content_list_kuai_left_pk" v-if="lang=='en_US'">{{value.homeTeam_en}}<img src="../../assets/img/betwap/VS.png" alt="">{{value.guestTeam_en}}</p>
              </div>
              <div class="content_list_kuai_conter">
                <p class="content_list_kuai_conter_title" v-html="langdata['ylye']">盈利余额</p>
                <p class="content_list_kuai_conter_num">{{value.profitAmount}} USDT</p>
              </div>
              <div class="content_list_kuai_right" v-if="ipt_value==1">
                <div class="content_list_kuai_right_img">
                  <img v-bind:src="value.winTeamCover" alt="">
                </div>
                <div class="content_list_kuai_right_text">
                  <p class="num">{{value.odds}}</p>
                  <p class="name" v-if="lang=='zh_CN'">{{value.winTeamName}}</p>
                  <p class="name" v-if="lang=='zh_HK'">{{value.winTeamName_zht}}</p>
                  <p class="name" v-if="lang=='en_US'">{{value.winTeamName_en}}</p>
                </div>
              </div>
              <div class="content_list_kuai_right" v-if="ipt_value==2">
                <div class="content_list_kuai_right_text">
                  <p class="num">{{value.odds}}</p>
                  <p class="name">
                    <span v-if="lang=='zh_CN'&&value.winTeamName">{{value.winTeamName}}</span>
                    <span v-if="lang=='zh_HK'&&value.winTeamName_zht">{{value.winTeamName_zht}}</span>
                    <span v-if="lang=='en_US'&&value.winTeamName_en">{{value.winTeamName_en}}</span>
                    <span v-if="value.givePointsLeft" v-html="value.givePointsLeft"></span>
                  </p>
                  <span class="xzusdt"><span class="orangspan">{{value.betAmountSum}}</span>/<span class="lanse">{{value.bond}}</span> USDT</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="morePage" v-if='load_more == 1'>
          <van-loading type="spinner" size="24px">正在加载</van-loading>
        </div>
        <div class="morePage" v-if='load_more == 2'>没有更多数据了</div>
        <van-popup v-model:show="dizhi" position="bottom" :style="{ width:'100%',height: '230px' }">
          <van-picker
              :show-toolbar="true"
              :title="langdata['sx']"
              :columns="columns"
              @confirm="onConfirm3"
              @cancel="onCancel3"
              @change="onChange3"
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
      cla1:this.$route.query.cla1?this.$route.query.cla1:1,
      cla2:0,
      cla3:0,
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
      ipt_value:this.$route.query.iptValue?this.$route.query.iptValue:1,
      endTime:"",
      startTime:"",
      winning:0,
      list:[],
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
  methods: {
    sjgsh(time) {
      var now = new Date(time),
          y = now.getFullYear(),
          m = now.getMonth() + 1,
          d = now.getDate(),
          h = now.getHours(),
          mm = now.getMinutes(),
          s = now.getSeconds();
      return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + (h < 10 ? "0" + h : h) + ":" + (mm < 10 ? "0" + mm : mm) + ":" + (s < 10 ? "0" + s : s);
    },
    init:function (){
      if(this.pageNum==0){
        this.list=[];
      }
      var fontdata={
        pageNum:this.pageNum,
        pageSize:10,
        matchStatus:this.cla2,
        quotationType:this.cla1,
        transactionType:this.ipt_value,
      };
      if(this.cla2==0){
        delete fontdata.matchStatus;
      }
      if((this.endTime!='' && this.startTime!='') && (this.endTime>this.startTime)){
        fontdata['startTime']=this.startTime;
        fontdata['endTime']=this.endTime;
      }
      this.$api("flow/queryBetRecordByCondition",fontdata,"post").then(res =>{
        this.winning=res.data.winning
        var list=this.list;
        var dtos=res.data.dtos;
        this.list=list.concat(dtos);
        this.load_more = 0;
        console.log(res)
      })
    },
    onConfirm3:function (value, index){
      this.ipt_value=value;
      this.pageNum=0;
      this.init();
      this.dizhi=false;
    },
    onChange3:function (value, index){
      // Toast(`当前值: ${value}, 当前索引: ${index}`);
    },
    onCancel3:function (){
      this.dizhi=false;
    },
    confirmFn1:function () {
      let endTime = new Date(this.currentDate1).format("yyyy-MM-dd")
      this.qitime = endTime
      this.cla3=0;
      this.showtime1 = false
    },
    confirmFn2:function () {
      let dateStr = new Date(this.currentDate2).format('yyyy-MM-dd')
      this.zhitime = dateStr
      this.cla3=0;
      this.showtime2 = false
    },
    onConfirm:function (value, index){
      this.ipt_value=value;
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
  watch:{
    cla1:{
      handler:function (val){
        this.pageNum=0;
        this.init();
      },
      deep: true
    },
    cla2:{
      handler:function (val){
        this.pageNum=0;
        this.init();
      },
      deep: true
    },
    qitime:{
      handler:function (val){
        this.startTime=Date.parse(this.qitime);
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
    cla3:{
      handler:function (val){
        if(val==1){
          this.startTime=new Date(new Date().toLocaleDateString()).getTime()
          this.endTime=(new Date(new Date().toLocaleDateString()).getTime()+(24*60*60*1000));
        }
        if(val==2){
          this.startTime=(new Date(new Date().toLocaleDateString()).getTime() - (new Date().getDay()+6) * 24 * 60 * 60 * 1000)
          this.endTime=this.startTime+60*60*24*7;
        }
        if(val==3){
          this.startTime=new Date(new Date().getFullYear(), new Date().getMonth(), 1).getTime()
          this.endTime=(new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getTime() + 24 * 60 * 60*1000)
        }
        if(val==4){
          this.startTime=new Date(new Date().getFullYear(), 0).getTime()
          this.endTime=(new Date(new Date().getFullYear(), 11, 31).getTime() + 24 * 60 * 60*1000)
        }
        // this.startTime=this.endTime="";
        this.qitime=this.lang=='zh_CN'?"起始时间":localStorage.getItem("lang")=='zh_HK'?"起始時間":"Start time";
        this.zhitime=this.lang=='zh_CN'?"終止时间":localStorage.getItem("lang")=='zh_HK'?"終止時間":"End time"
        this.pageNum=0;
        this.init();
      },
      deep: true
    }
  }
}
</script>

<style lang="less" scoped>
.wrap{

}
.header{
  //background-color: #0A0A0A;
  padding: 30px 30px 50px 30px;
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
  width: 76px;
  height: 76px;
  background: url("../../assets/img/betwap/deal/shenglv.png");
  background-size: 76px 76px;
  text-align: center;
  line-height: 64px;
}
.header_user_xinxi_img_sl{
  font-size: 9px;
  font-family: PingFang SC;
  font-weight: 400;
  color: #FFFFFF;
  height: 20px;
  span{
    font-size: 24px;
    font-weight: 800;
  }
}
.header_user_xinxi_right{
  font-size: 14px;
  font-family: FZLanTingHei-DB-GBK;
  font-weight: 400;
  color: #FFFFFF;
  position: absolute;
  right: 40px;
  display: flex;
  div{
    display: flex;
    align-items: center;
    img{
      width: 13px;
      height: 14px;
      margin: 0 5px 0 15px;
    }
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
}
.anniu{
  //padding: 10px 30px 50px 30px;
  display: flex;
  justify-content: flex-end;
  .ipt_a{
    background: linear-gradient(0deg, #0075BA, #29D3FF);
  }
  .ipt{
    width: 70px;
    height: 30px;
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
.content_list{
  padding-bottom: 20px;
}
.content_list_kuai{
  // width: 362px;
  // height: 95px;
  background: #2A2A2A;
  margin:5px 10px;
  padding: 0 0 15px 0;
}
.content_list_kuai_top{
  display: flex;
  justify-content: space-between;
  padding: 15px 10px;
}
.content_list_kuai_top_quan{
  color: #FFFFFF;
}
.content_list_kuai_top_pxtype{
  font-size: 10px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  img{
    width: 6px;
    height: 7px;
  }
}
.content_list_kuai_top_pxtype_cs{
  color: #FFA735;
}
.content_list_kuai_top_pxtype_hs{
  color: #BCBCBC;
  opacity: 0.4;
}
.content_list_kuai_top_pxtype_ls{
  color: #73BB0C;
}
.content_list_kuai_top_pxtype_red{
  color: #FF3636;
}
.content_list_kuai_but{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.content_list_kuai_left{
  padding-left: 10px;
  width: 106px;
}
.content_list_kuai_left_top{
  font-size: 9px;
  font-family: FZLanTingHei-DB-GBK;
  font-weight: 400;
  color: #FFFFFF;
}
.content_list_kuai_left_time{
  font-size: 10px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #FFFFFF;
  opacity: 0.4;
  width: 125px;
}
.content_list_kuai_left_pk{
  font-size: 9px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #BCBCBC;
  opacity: 0.88;
  margin-top: 3px;
  width: 150px;
  img{
    width: 15px;
    height: 15px;
    margin: 0 3px;
  }
}
.content_list_kuai_conter{
  text-align: center;
  //padding: 10px;
  width: 106px;
}
.content_list_kuai_conter_title{
  font-size: 9px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #FFFFFF;
}
.content_list_kuai_conter_num{
  font-size: 14px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #FFFFFF;
  opacity: 0.8;
  margin-top: 3px;
}
.content_list_kuai_right{
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
  width: 106px;
}
.content_list_kuai_right_img{
  width: 33px;
  height: 35px;
}
.content_list_kuai_right_text{
  margin-left: 3px;
  .num{
    font-size: 14px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #FFFFFF;
    text-align: center;
  }
  .name{
    font-size: 9px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin-top: 3px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .xzusdt{
    font-size: 9px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin-top: 3px;
    .orangspan{
      color: red;
    }
    .lanse{
      color: green;
    }
  }
}
.van-popup{
  width: 262px;
  //height: 505px !important;
  background: #1c1c1c;
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
