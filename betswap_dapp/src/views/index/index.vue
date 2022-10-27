<template>
  <div>

    <div class="navbar-wrap">
        <div class="search">
            <input type="text" v-bind:placeholder="langdata['ssss']" v-model="keyword">
        </div>
        <div class="screen" @click="screenshow=!screenshow">
            <p>
                {{screen_title}}
                <span>{{screen_title_num}}</span>
            </p>
            <van-icon :class="{'screen-icon':true,active:screenshow}" name="play"  color="#fff" size="8px"/>
        </div>
        <div class="search-more" @click="toolshow=true">
            <van-icon name="wap-nav" color="#fff" size="15px"/>
        </div>
    </div>

    <div class="page-wrap">
        <div class="banner">
            <van-swipe class="my-swipe" :autoplay="3000"  lazy-render>
                <van-swipe-item v-for="(item,index) in lbimg" :key="index">
                    <img v-bind:src="item.coverImage" alt="">
                </van-swipe-item>
            </van-swipe>
        </div>
        <template  v-if="screen_num==0 || screen_num==1 || screen_num==2 || screen_num==4 || screen_num==6 || screen_num==7 || screen_num==8 || screen_num==9 || screen_num==10">
<!--            <div class="datetime" >{{ langdata['jt'] }}  {{todaytext}}</div>-->
            <van-tabs @click="sxlist" v-model="datetabs" background="#141414" title-active-color="#1EBBED" title-inactive-color="#cacaca" v-if="screen_num==2 || screen_num==0">
                <van-tab :title="langdata['syrq']"></van-tab>
                <van-tab v-for="item in zptime" :title="item[0]" :name="item[1]"></van-tab>
            </van-tabs>
            <div class="jinri_wrap" v-for="(item,index) in list" :key="index">
                <div class="jinri_top">
                    <div class="left">
                        <img v-if="leagusMatchs[lang][item.leagueMatch]" v-bind:src="leagusMatchs[lang][item.leagueMatch]['logo']" alt="">
                        <p v-if="leagusMatchs[lang][item.leagueMatch]" v-html="leagusMatchs[lang][item.leagueMatch]['name']">英格兰超级联赛</p>
                    </div>
                    <div class="right">
                        <p v-html="langdata['qcdy']">全场独赢</p>
                        <p v-html="langdata['qcbf']">全场让球</p>
                        <p v-html="langdata['qcdx']">全场大小</p>
                    </div>
                </div>
                <div class="jinri_box" @click="$router.push('/bet?id='+item.id+'&leagueMatch='+item.leagueMatch)">
                    <div class="jinri-time">
                        <div class="left" v-html="sjgsh(item.matchTime)">05/07  20:30</div>
                        <div class="right" @click.stop="$router.push('/open?id='+item.id+'&leagueMatch='+item.leagueMatch)">
                            <img src="@img/index/VS.png" alt="">
                            <p v-html="langdata['kp']">开盘</p>
                        </div>
                    </div>
                    <div class="jinri">
                        <div class="left">
                            <div class="team">
                                <div class="team-name">
                                    <img v-bind:src="item.homeCover" alt="">
                                    <p class="ellipsis" v-html="item.homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                                    <p class="ellipsis" v-html="item.homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                                    <p class="ellipsis" v-html="item.homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                                </div>
                                <div class="team-score" v-html="item.homeScore">0</div>
                            </div>
                            <div class="team">
                                <div class="team-name">
                                    <img v-bind:src="item.guestCover" alt="">
                                    <p class="ellipsis" v-html="item.guestTeam" v-if="lang=='zh_CN'">伯恩利</p>
                                    <p class="ellipsis" v-html="item.guestTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                                    <p class="ellipsis" v-html="item.guestTeam_en" v-if="lang=='en_US'">伯恩利</p>
                                </div>
                                <div class="team-score" v-html="item.guestScore">1</div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="score-left">
                                <div class="score">
                                    <div class="score-fen" v-if="item.homeOdds" @click.stop="showopenshow(item.id);listkey=index;zstype=1;">
                                        <p v-html="langdata['zs']">主胜</p>
                                        <span v-html="item.homeOdds">3.15</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.homeOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.homeOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                                <div class="score">
                                    <div class="score-fen"  v-if="item.guestOdds" @click.stop="showopenshow(item.id);listkey=index;zstype=2;">
                                        <p v-html="langdata['ks']">客胜</p>
                                        <span v-html="item.guestOdds">2.45</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.guestOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.guestOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                                <div class="score">
                                    <div class="score-fen" v-if="item.tieOdds" @click.stop="showopenshow(item.id);listkey=index;zstype=3;">
                                        <p v-html="langdata['pj']">平局</p>
                                        <span v-html="item.tieOdds">3.30</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.tieOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.tieOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                            </div>
                            <div class="score-right">
                                <div class="score">
                                    <div class="score-fen">
                                        <p v-html="item.homeGivePoints">+0/0.5</p>
                                        <span v-html="item.rfHomeOdds">1.83</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.rfHomeOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.rfHomeOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                                <div class="score">
                                    <div class="score-fen">
                                        <p v-html="item.guestGivePoints">-0/0.5</p>
                                        <span v-html="item.rfGuestOdds">2.11</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.rfGuestOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.rfGuestOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                            </div>
                            <div class="score-right">
                                <div class="score">
                                    <div class="score-fen">
                                        <p v-html="item.homeSpecificSize">+0/0.5</p>
                                        <span v-html="item.dxHomeOdds">1.83</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.dxHomeOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.dxHomeOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                                <div class="score">
                                    <div class="score-fen">
                                        <p v-html="item.guestSpecificSize">-0/0.5</p>
                                        <span v-html="item.dxGuestOdds">2.11</span>
                                    </div>
                                    <img src="@img/index/suo.png" alt="" v-if="!item.dxGuestOdds && item.matchTime>ns">
                                    <div class="score-fen" v-if="!item.dxGuestOdds && item.matchTime<=ns">
                                      <p>—</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </template>
        <template  v-if="screen_num==3">
            <div class="champion-list">
                <div class="champion-item" v-for="(item,index) in list" :key="index">
                    <div class="champion-top">
                        <p>英格兰超级联赛 2021/2022</p>
                        <van-icon name="arrow-up" size="10" color="#DCDCDC" v-if="item.show" @click="item.show = !item.show"/>
                        <van-icon name="arrow-down" size="10" color="#DCDCDC"  v-if="!item.show" @click="item.show = !item.show"/>
                    </div>
                    <div class="champion"  v-if="item.show">
                        <div class="champion-title">
                            <div class="champion-title-left">
                                <img src="@img/index/guanun.png" alt="">
                                <p>冠军</p>
                            </div>
                            <div class="champion-title-right">2022年05月28日 17:00截止</div>
                        </div>
                        <div class="champion-box">
                            <div class="champion-dui" v-for="(item,index) in 5">
                                <div class="champion-dui-left">
                                    <img src="@img/index/premter_min.png" alt="">
                                    <p class="ellipsis">阿森纳阿森纳阿森纳阿森纳</p>
                                </div>
                                <div class="champion-dui-right">3.15</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </template>
        <template  v-if="screen_num==5">
            <div class="saiguo-title" v-html="langdata['wdtz']">我的投注</div>
            <div class="sgsx">
              <p class="sxls" v-if="lang=='zh_CN'" @click="lssxtype=true"><span v-html="sgleagueMatchname!=''?sgleagueMatchname:'联赛'">联赛</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
              <p class="sxls" v-if="lang=='zh_HK'" @click="lssxtype=true"><span v-html="sgleagueMatchname!=''?sgleagueMatchname:'聯賽'">聯賽</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
              <p class="sxls" v-if="lang=='en_US'" @click="lssxtype=true"><span v-html="sgleagueMatchname!=''?sgleagueMatchname:'league'">league</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
              <p class="sxrq" v-if="lang=='zh_CN'" @click="sgtime=true"><span v-html="xssgtime!=''?xssgtime:'日期'">日期</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
              <p class="sxrq" v-if="lang=='zh_HK'" @click="sgtime=true"><span v-html="xssgtime!=''?xssgtime:'日期'">日期</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
              <p class="sxrq" v-if="lang=='en_US'" @click="sgtime=true"><span v-html="xssgtime!=''?xssgtime:'date'">日期</span><img src="../../assets/img/betwap/xia.png" alt=""></p>
            </div>
            <div class="champion-list">
                <div class="champion-item" v-for="(item,index) in list" :key="index">
                    <div class="champion-top">
                        <p v-if="leagusMatchs[lang][item.leagueMatch]" v-html="leagusMatchs[lang][item.leagueMatch]['name']">英格兰超级联赛</p>
                    </div>
                    <div class="saiguo" >
                        <div class="saiguo-time" v-html="sjgsh(item.matchTime)">05/07  20:30</div>
                        <div class="saiguo-box">
                            <div class="saiguo-item">
                                <div class="saiguo-item-left">
                                    <img v-bind:src="item.homeCover" alt="">
                                    <p class="ellipsis" v-html="item.homeTeam" v-if="lang=='zh_CN'">阿森纳阿森纳阿森纳阿森纳</p>
                                    <p class="ellipsis" v-html="item.homeTeam_zht" v-if="lang=='zh_HK'">阿森纳阿森纳阿森纳阿森纳</p>
                                    <p class="ellipsis" v-html="item.homeTeam_en" v-if="lang=='en_US'">阿森纳阿森纳阿森纳阿森纳</p>
                                </div>
                                <div class="saiguo-item-right active" v-html="item.homeScore">
                                    1
                                </div>
                            </div>
                            <div class="saiguo-item">
                                <div class="saiguo-item-left">
                                    <img v-bind:src="item.guestCover" alt="">
                                    <p class="ellipsis" v-html="item.guestTeam" v-if="lang=='zh_CN'">阿森纳阿森纳阿森纳阿森纳</p>
                                    <p class="ellipsis" v-html="item.guestTeam_zht" v-if="lang=='zh_HK'">阿森纳阿森纳阿森纳阿森纳</p>
                                    <p class="ellipsis" v-html="item.guestTeam_en" v-if="lang=='en_US'">阿森纳阿森纳阿森纳阿森纳</p>
                                </div>
                                <div class="saiguo-item-right" v-html="item.guestScore">
                                    0
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </template>
    </div>
<!--    底部時間-->
    <van-popup v-model:show="sgtime" position="bottom" :style="{  width:'100%',height: '310px' }">
      <van-datetime-picker
          v-model="sgDate"
          type="date"
          title="选择日期"
          :min-date="minDate"
          :max-date="maxDate"
          @confirm="onConfirm2"
          @cancel="sgtime=false"
      />
    </van-popup>
<!--    底部联赛-->
    <van-popup v-model:show="lssxtype" position="bottom" :style="{ width:'100%',height: '230px' }">
      <van-picker
          :show-toolbar="true"
          :columns="lssxlist"
          @confirm="onConfirm1"
          @cancel="onCancel1"
          @change="onChange1"
      />
    </van-popup>
    <!-- 顶部 今日筛选 -->
    <van-popup
    v-model="screenshow"
    round
    position="top"
    >
        <div class="screen-wrap">
            <div class="screen-list">
                <div :class="{'screen-item':true, active:screen_num==0}" @click="screenClick(0)" v-html="langdata['qb']">全部 </div>
                <div :class="{'screen-item':true, active:screen_num==1}" @click="screenClick(1)">{{ langdata['jr'] }} {{mathList.todayCount}}</div>
                <div :class="{'screen-item':true, active:screen_num==2}" @click="screenClick(2)">{{ langdata['zp'] }} {{mathList.zpCount}}</div>
                <div :class="{'screen-item':true, active:screen_num==3}" @click="screenClick(3)">{{ langdata['gj'] }} {{mathList.winCount}}</div>
                <div :class="{'screen-item':true, active:screen_num==4}" @click="screenClick(4)">{{ langdata['rm'] }} {{mathList.hotCount}}</div>
                <div :class="{'screen-item':true, active:screen_num==5}" @click="screenClick(5)">{{ langdata['sg'] }} {{mathList.resultCount}}</div>
            </div>
          <div class="screen-list">
            <div style="width: 100%;" v-for="(value ,key) in leagusMatchs[lang]" @click="lxscreenClick(key)"  :class="{'screen-item':true, active:leagueMatch==key}" v-html="value['name']">全部 </div>
          </div>
        </div>
    </van-popup>
    <!-- 右侧  排序分类 -->
    <van-popup
    v-model="toolshow"
    closeable
    close-icon-position="top-right"
    position="right"
    z-index="99999"
    >
        <div class="tool-wrap">
            <div class="tool">
                <div class="tool-left">
                    <img src="@img/index/shaixuan/1.png" alt="">
                    <p>排序</p>
                </div>
                <div class="tool-right" v-on:click="setorderType()">
                    <p v-html="orderType==1?'按时间':'按热门'">按热门</p>
                </div>
            </div>
            <div class="tool">
                <div class="tool-left">
                    <img src="@img/index/shaixuan/2.png" alt="">
                    <p>盘口</p>
                </div>
                <div class="tool-right">
                    <p>香港盘</p>
                </div>
            </div>
            <div class="tool active">
                <div class="tool-left">
                    <img src="@img/index/shaixuan/3.png" alt="">
                    <p>赔率</p>
                </div>
                <div class="tool-right">
                    <p>任何赔率</p>
                </div>
            </div>
            <div class="tool active">
                <div class="tool-left ">
                    <img src="@img/index/shaixuan/4.png" alt="">
                    <p>规则说明</p>
                </div>
            </div>
<!--            <div class="tool">
                <div class="tool-left">
                    <img src="@img/index/shaixuan/5.png" alt="">
                    <p>换肤</p>
                </div>
                <div class="tool-right">
                    <img src="@img/index/shaixuan/7.png" alt="">
                    <img src="@img/index/shaixuan/6.png" alt="">
                </div>
            </div>-->
        </div>
    </van-popup>
    <!-- 开盘 投注 -->
    <van-popup
    v-model="openshow"
    round
    position="bottom"
    >
<!--      <div v-if="zjlong" style="margin:0 auto;width: 50px;position: absolute;left: 50%;top:50%;margin-left: -25px;">
        <van-loading type="spinner"/>
      </div>-->
        <div class="touzhu-wrap">
            <div class="touzhu-top">
                <div class="left" v-html="langdata['tzd']">投注单</div>
                <div class="right">
                    <div class="yue">
                        <p v-html="langdata['zhye']">账户余额</p>
                        <span v-html="udstbalance">500.00</span>
                    </div>
                    <div class="close" @click="openshow=false">
                        <img src="@img/index/bottom-close.png" alt="">
                    </div>
                </div>
            </div>
            <div class="touzhu">
                <div class="left">
                    <div class="left-img">
                        <img v-if="zstype==1" v-bind:src="tz.homeCover" alt="">
                        <img v-if="zstype==2" v-bind:src="tz.guestCover" alt="">
                    </div>
                    <div class="left-title">
                      <div class="left-h1" v-html="tz.homeTeam" v-if="zstype==1 && lang=='zh_CN'">阿纳森</div>
                      <div class="left-h1" v-html="tz.guestTeam" v-if="zstype==2 && lang=='zh_CN'">阿纳森</div>
                      <div class="left-h1" v-html="tz.homeTeam_en" v-if="zstype==1 && lang=='en_US'">阿纳森</div>
                      <div class="left-h1" v-html="tz.guestTeam_en" v-if="zstype==2 && lang=='en_US'">阿纳森</div>
                      <div class="left-h1" v-html="tz.homeTeam_zht" v-if="zstype==1 && lang=='zh_HK'">阿纳森</div>
                      <div class="left-h1" v-html="tz.guestTeam_zht" v-if="zstype==2 && lang=='zh_HK'">阿纳森</div>
                      <div class="left-h1" v-if="zstype==3" v-html="langdata['pj']">平局</div>
                        <div class="left-h2" v-html="langdata['qcdy']">全场独赢</div>
                        <div class="left-p" v-if="leagusMatchs[lang][tzleagueMatch]" v-html="leagusMatchs[lang][tzleagueMatch]['name']">英格兰超级联赛</div>
                      <div class="left-p">{{ tz.homeTeam }} V {{ tz.guestTeam }}( {{tz.homeScore}}-{{tz.guestScore}} )</div>
                    </div>
                </div>
                <div class="right">
                    <div class="right-h1" v-html="odds">3.15</div>
                    <div class="right-succ" v-if="tztype==1">
                        <img src="@img/index/success.png" alt="">
                        <p>投注成功</p>
                    </div>
                </div>
            </div>
            <div class="touzhu-success" v-if="tztype==1">
                <div class="left">
                    <div class="left-p">
                      {{ langdata['zgky'] }}<span v-html="queryBetPage.maxBetAmount">80.00</span>
                    </div>
                    <div class="left-p">
                      {{ langdata['fy'] }}<span  v-html="queryBetPage.fee">25.00</span>
                    </div>
                </div>
                <div class="right">
                  {{ langdata['tzje'] }}  {{touzhuMoney}}
                </div>
            </div>

            <div class="touzhu-input" v-if="tztype==0">
                <div class="title">单关</div>
                <div class="touzhu-input-box">
                    <div class="left">
                        <div class="left-p">
                          {{ langdata['zgky'] }}<span v-html="touzhuMoney-(queryBetPage.fee*touzhuMoney/100)">80.00</span>
                        </div>
                        <div class="left-p">
                          {{ langdata['fy'] }}<span v-html="queryBetPage.fee*touzhuMoney/100">25.00</span>
                        </div>
                    </div>
                    <div class="right">
                        <input type="number" v-bind:placeholder="langdata['zd']+queryBetPage.maxBetAmount" v-model="touzhuMoney">
                        <p v-if="touzhuMoney>udstbalance" v-html="langdata['yebz']">余额不足</p>
                    </div>
                </div>
            </div>

            <div :class="{'touzhu-bt':true,active:touzhuMoney}" v-on:click="send()" v-if="tztype==0" v-html="langdata['tz']">投注{{touzhuMoney}}</div>
            <div class="touzhu-bt active" v-on:click="tztype=0;openshow=false;" v-if="tztype==1" v-html="langdata['qr']">确认</div>
        </div>
    </van-popup>
    <van-popup :close-on-click-overlay="false" style="background-color:transparent;" v-model:show="zjlong" position="bottom">
      <!--        <div style="margin:0 auto;width: 100%;position: absolute;left: 50%">-->
      <van-loading style="margin-bottom: 9rem;" vertical>确认中，请您耐心等待！</van-loading>
      <!--        </div>-->
    </van-popup>
  </div>
</template>

<script>
/*import VConsole from "vconsole";
const vConsole = new VConsole();*/
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
              lssxtype:false,
              sgtime:false,
              sgDate:new Date(),
              xssgtime:"",
              minDate:new Date(1970, 0, 1),
              maxDate:new Date(2099, 12, 31),
              lang:localStorage.getItem("lang"),
              langdata:langdata(localStorage.getItem("lang")),
              zstype:1,
              zjlong:false,
                datetabs:0,
                screenshow:false,
                screen_num:0,

                screen_title:localStorage.getItem("lang")=='zh_HK'?'全部':'ALL',
                screen_title_num:0,

                toolshow:false,

                openshow:false,
                touzhuMoney:'',

                list:[
                    {id:1,show:true,homeScore:"",guestScore:"",leagueMatch:1},
                    {id:2,show:false,homeScore:"",guestScore:"",leagueMatch:1}
                ],

                tztype:0,
              orderType:1,

              mathList:[],
              todaytext:  this.getDateWeek(),
              zptime:[],
              startTime:new Date(new Date().setHours(0, 0, 0, 0)).getTime(),
              // endTime:new Date(new Date().setHours(0, 0, 0, 0)).getTime()+(60*60*24*1000)-1,
              // startTime:"",
              endTime:"",
              lbimg:[],
              keyword:"",
              pageNum: 0,
              trxbalance:0,
              udstbalance:0,
              listkey:0,
              txid:"",
              oid:"",
              tz:{guestCover:"",guestTeam:"",guestTeamId:"",guestTeam_en:"",guestTeam_zht:"",homeCover:"",homeTeam:"",homeTeamId:"",homeTeam_en:"",homeTeam_zht:"",homeScore:0,guestScore:0},
              odds:0,
              queryBetPage:{id:"",surplusBond:0,fee:0,unitAmount:0,maxBetAmount:0},
              ptaddress:"",
              tzleagueMatch:1,
              leagusMatchs:{
                "zh_CN":{},
                "zh_HK":{},
                "en_US":{},
              },
              lssxlist:[],
              sgleagueMatch:"",
              sgleagueMatchname:"",
              leagueMatch:"",
              ns:Date.parse(new Date())+ (7 * 24 * 60 * 60 * 1000),
              dq:Date.parse(new Date()),
              jynum:0,
            }
        },
        mounted() {
          window.addEventListener("scroll", this.handleScroll);
        },
        created() {
          if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
            this.lang="en_US";
          }
          this.init();
          this.lslist();
          this.indexlist();
          this.zptimelist();
          this.lunbo();
          // this.tzview();
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
          lunbo(){
            // console.log(this.ns)
            this.$api("user/Advertisement",{
            },"post").then(res=>{
              this.lbimg=res.data;
            })
          },
          lslist(){
            this.$api("system/findLeagueMatchS",{
            },"post").then(res=>{
              var leagusMatchs={"zh_CN":{},"zh_HK":{},"en_US":{}}
              for (var i=0;i<res.data.length;i++){
                leagusMatchs["zh_CN"][res.data[i]['leagueMatchId']]={name:res.data[i].name,logo:res.data[i].logo}
                leagusMatchs["zh_HK"][res.data[i]['leagueMatchId']]={name:res.data[i].name_zht,logo:res.data[i].logo}
                leagusMatchs["en_US"][res.data[i]['leagueMatchId']]={name:res.data[i].name_en,logo:res.data[i].logo}
              }
              this.leagusMatchs=leagusMatchs;
              console.log(this.leagusMatchs);
            })
          },
          init(){
            /*var date = new Date();
            var oneweekdate = new Date(date.getTime() + (7 * 24 * 60 * 60 * 1000));
            var y = oneweekdate.getFullYear() + '-';
            var m = (oneweekdate.getMonth() + 1 < 10 ? '0' + (oneweekdate.getMonth() + 1) : oneweekdate.getMonth() + 1) + '-';
            var d = (oneweekdate.getDate() < 10 ? '0' + (oneweekdate.getDate()) : oneweekdate.getDate());
            this.ns=y + m + d;*/
            this.$api('quotation/queryMathCount',{
              startTime:this.startTime
            },"post").then(res =>{
              this.mathList=res.data;
              this.screen_title_num = "";
            })
          },
          setorderType(){
            this.orderType=this.orderType==1?0:1;
            this.pageNum=0;
            this.indexlist();
          },
          indexlist(){
            if(this.pageNum==0){
              this.list=[];
            }
            var fontdata={
              startTime:this.startTime>0?this.startTime:"",
              endTime:this.endTime>0?this.endTime:"",
              ifHot:0,
              keyword:this.keyword,
              leagueMatch:this.leagueMatch,
              orderType:this.orderType,
              pageNum:this.pageNum,
              pageSize:10
            };
            if(fontdata.startTime=="" || fontdata.endTime==""){
              delete fontdata.startTime;
              delete fontdata.endTime;
            }
            if(this.keyword==''){
              delete fontdata.keyword;
            }
            if(this.screen_num==4){
              fontdata.leagueMatch=1;
            }else if(this.screen_num==6){
              fontdata.ifHot=1;
            }else if(this.screen_num==5){
              fontdata.leagueMatch=this.sgleagueMatch>0?this.sgleagueMatch:"";
            }else if(this.screen_num==10){
              fontdata.leagueMatch=2;
            }else if(this.screen_num==7){
              fontdata.leagueMatch=3;
            }else if(this.screen_num==9){
              fontdata.leagueMatch=4;
            }else if(this.screen_num==8){
              fontdata.leagueMatch=5;
            }
            var url="";
            if(this.screen_num==0){
              var myDate = new Date();
              var y=myDate.getFullYear();
              var yue=(myDate.getMonth() + 1);
              var ri =myDate.getDate();
              var nowDate =  y+'-'+(yue<10?('0'+yue):yue)+'-'+(ri<10?('0'+ri):ri);
              if(this.datetabs==0 || this.datetabs==nowDate){
                fontdata.startTime=new Date(new Date()).getTime()
              }
            }
            if(this.screen_num==0 || this.screen_num==1 || this.screen_num==2 || this.screen_num==4 || this.screen_num==10 || this.screen_num==9 || this.screen_num==8 || this.screen_num==7 || this.screen_num==6){
              url="quotation/queryMathByCondition";
            }else if(this.screen_num==3){
              url="quotation/queryWinMath";
            }else if(this.screen_num==5){
              url="quotation/queryMathFruit";
            }
            this.$api(url,fontdata,"post").then(res =>{
              if(this.screen_num==0 || this.screen_num==1 || this.screen_num==2 || this.screen_num==4 || this.screen_num==5 || this.screen_num==10 || this.screen_num==9 || this.screen_num==8 || this.screen_num==7 || this.screen_num==6){
                var list=this.list;
                this.list=list.concat(res.data.matchs);
              }else if(this.screen_num==3){
                var list=this.list;
                this.list=list.concat(res.data.winMatchs);
              }
              // console.log(this.list);
            })
          },
          lxscreenClick(key){
            this.leagueMatch=key;
            this.screenshow = false;
            this.pageNum=0;
            this.screen_title=this.leagusMatchs[this.lang][key]['name'];
            this.indexlist();
          },
          screenClick(num){
                this.screen_num = num;
                this.screenshow = false;
                this.startTime="";
                this.endTime="";
                if(num==1){
                    if(this.lang=='zh_CN'){
                      this.screen_title='今日'
                    }else if(this.lang=='zh_HK'){
                      this.screen_title='今日'
                    }else if(this.lang=='en_US'){
                      this.screen_title='today'
                    }else{
                      this.screen_title='today'
                    }
                    this.screen_title_num = this.mathList.todayCount;
                    this.startTime=new Date(new Date().setHours(0, 0, 0, 0)).getTime();
                    this.endTime=this.startTime+(60*60*24*1000)-1;
                }else if(num==0){
                  if(this.lang=='zh_CN'){
                    this.screen_title='全部'
                  }
                  if(this.lang=='zh_HK'){
                    this.screen_title='全部'
                  }
                  if(this.lang=='en_US'){
                    this.screen_title='ALL'
                  }
                  this.screen_title_num ="";
                }else if(num==2){
                  if(this.lang=='zh_CN'){
                    this.screen_title='早盘'
                  }
                  if(this.lang=='zh_HK'){
                    this.screen_title='早盤'
                  }
                  if(this.lang=='en_US'){
                    this.screen_title='morning'
                  }
                  this.screen_title_num = this.mathList.zpCount;
                }else if(num==3){
                  if(this.lang=='zh_CN'){
                    this.screen_title='冠军'
                  }
                  if(this.lang=='zh_HK'){
                    this.screen_title='冠軍'
                  }
                  if(this.lang=='en_US'){
                    this.screen_title='champion'
                  }
                  this.screen_title_num = this.mathList.winCount;
                }else if(num==4){
                  if(this.lang=='zh_CN'){
                    this.screen_title='热门'
                  }
                  if(this.lang=='zh_HK'){
                    this.screen_title='熱門'
                  }
                  if(this.lang=='en_US'){
                    this.screen_title='hot'
                  }
                    this.screen_title_num = this.mathList.hotCount;
                }else if(num==5){
                  var lssxlist=[];
                  if(this.lang=='zh_CN'){
                    this.screen_title='赛果'
                  }
                  if(this.lang=='zh_HK'){
                    this.screen_title='賽果'
                  }
                  if(this.lang=='en_US'){
                    this.screen_title='amidithion'
                  }
                  for (var key in this.leagusMatchs[this.lang]){
                    lssxlist[lssxlist.length]={id:key,text:this.leagusMatchs[this.lang][key]['name']};
                  }
                  this.lssxlist=lssxlist;
                  this.screen_title_num = this.mathList.resultCount;
                }else if(num==6){
                  this.screen_title='英超'
                  this.screen_title_num = this.mathList.leagueMatch1;
                }
                else if(num==10){
                  this.screen_title='法甲'
                  this.screen_title_num = this.mathList.leagueMatch2;
                }
                else if(num==7){
                  this.screen_title='西甲'
                  this.screen_title_num = this.mathList.leagueMatch3;
                }
                else if(num==9){
                  this.screen_title='德甲'
                  this.screen_title_num = this.mathList.leagueMatch4;
                }
                else if(num==8){
                  this.screen_title='意甲'
                  this.screen_title_num = this.mathList.leagueMatch5;
                }
                this.pageNum=0;
                this.indexlist();
            },
          zptimelist(){
            var sj=(new Date().getFullYear())+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate();
            for(var i=0;i<8;i++){
              this.zptime[i]=[this.getNextDate(sj,0+i,0,1,1,"/"),this.getNextDate(sj,0+i,1,1,1,"-")];
            }
          },
          sxlist(name,title){
            if(name==0){
              this.startTime=new Date(new Date().setHours(0, 0, 0, 0)).getTime();
              this.endTime="";
            }else{
              this.startTime=new Date(Date.parse(name.replace(/-/g, "/"))).getTime() ;
              this.endTime=this.startTime + (24 * 60 * 60 *1000 - 1);
            }
            this.pageNum=0;
            this.indexlist();
          },
          getNextDate(date, day,tY,tM,tD,symbol) {
            var dd = new Date(date.replace(/-/g, '/'));
            dd.setDate(dd.getDate() + day);
            var y = dd.getFullYear();
            var m = parseInt(dd.getMonth()) + 1 < 10 ? "0" + String(parseInt(dd.getMonth()) + 1) : String(parseInt(dd.getMonth()) + 1);
            var d = parseInt(dd.getDate()) < 10 ? "0" + String(dd.getDate()) : String(dd.getDate());
            var time="";
            if(tY==1){
              time = y;
            }
            if(tM==1){
              time += time?symbol+m:m;
            }
            if(tD==1){
              time += time?symbol+d:d;
            }
            return time;
          },
          getDateWeek(){
            var todayDate = new Date();
            var date = todayDate.getDate();
            var month= todayDate.getMonth() +1;
            var year= todayDate.getYear();
            var dateweek = "";
              if(navigator.appName == "Netscape")
              {
                dateweek = /*dateweek + (1900+year) + "年" +*/ month + "月" + date + "日 ";
              }
              if(navigator.appVersion.indexOf("MSIE") != -1)
              {
                dateweek = /*dateweek + year + "年" +*/ month + "月" + date + "日 ";
              }
              switch(todayDate.getDay())
              {
                case 0:dateweek += "星期日";break;
                case 1:dateweek += "星期一";break;
                case 2:dateweek += "星期二";break;
                case 3:dateweek += "星期三";break;
                case 4:dateweek += "星期四";break;
                case 5:dateweek += "星期五";break;
                case 6:dateweek += "星期六";break;
              }
            if(localStorage.getItem("lang")!='zh_HK'){
              if(navigator.appName == "Netscape")
              {
                dateweek = /*dateweek + (1900+year) + "年" +*/ month + "month" + date + "day ";
              }
              if(navigator.appVersion.indexOf("MSIE") != -1)
              {
                dateweek = /*dateweek + year + "年" +*/ month + "month" + date + "day ";
              }
              switch(todayDate.getDay())
              {
                case 0:dateweek += "Sunday";break;
                case 1:dateweek += "Monday";break;
                case 2:dateweek += "Tuesday";break;
                case 3:dateweek += "Wednesday";break;
                case 4:dateweek += "Thursday";break;
                case 5:dateweek += "Friday";break;
                case 6:dateweek += "Saturday";break;
              }
            }

            return dateweek;
          },
          handleScroll() {
            //这里使用个延时加载，不然滑动屏幕的时候会不断触发方法，去计算高度，浪费性能
            let that = this
            clearTimeout(this.timer)
            that.timer = setTimeout(function () {
              let scrollTop = document.documentElement.scrollTop || document.body.scrollTop; //元素超出上边界的值
              let windowHeight = document.documentElement.clientHeight || document.body.clientHeight; //窗口的高度
              let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight; //元素高度
              let scroll = parseInt(Math.round(scrollTop)) + parseInt(Math.round(windowHeight))
              //获取到关键属性的值后，判断即可
              // if (scroll >= scrollHeight) {
              that.load_more = 1;
              //在触底时获取当前页数和当前列表数组的长度
              that.pageNum++;
              that.indexlist();
            }, 500);
          },
          async getTronweb(){
            var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              let trxbalance = await tronWeb.trx.getBalance(address);
              let contract = await tronWeb.contract().at("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")
              let balance = await contract.balanceOf(address).call();
              let mybalance = balance.toNumber();//usdt通证余额
              this.udstbalance=mybalance>0?(mybalance/1000000):0;
              this.trxbalance=trxbalance;
            }
            this.tz.homeScore=this.list[this.listkey]['homeScore']
            this.tz.guestScore=this.list[this.listkey]['guestScore']
          },
          tzview(itemId){
            // console.log(this.list[this.listkey]['id'])
            this.$api("quotation/queryQuotationByMatchId",{
              matchId:itemId,
              // matchId:"M202205050100004768848",
              oddsType:1,
              quotationType:1
            },"post").then(res=>{
                var odds=0;
                if(this.zstype==1){
                  odds=res.data.qcList[0]['left']['odds']
                }
                if(this.zstype==2){
                  odds=res.data.qcList[0]['right']['odds']
                }
                if(this.zstype==3){
                  odds=res.data.qcList[0]['center']['odds']
                }
              this.tzleagueMatch=res.data.match.leagueMatch;
              this.tz.guestCover=res.data.match.guestCover;
              this.tz.guestTeam=res.data.match.guestTeam;
              this.tz.guestTeamId=res.data.match.guestTeamId;
              this.tz.guestTeam_zht=res.data.match.guestTeam_zht;
              this.tz.guestTeam_en=res.data.match.guestTeam_en;
              this.tz.homeCover=res.data.match.homeCover;
              this.tz.homeTeam=res.data.match.homeTeam;
              this.tz.homeTeamId=res.data.match.homeTeamId;
              this.tz.homeTeam_en=res.data.match.homeTeam_en;
              this.tz.homeTeam_zht=res.data.match.homeTeam_zht;
              this.tz.homeScore=res.data.match['homeScore']
              this.tz.guestScore=res.data.match['guestScore']
                this.odds=odds;
                var matchId=res.data.match["id"]
                this.$api("quotation/queryBetPage",{
                  matchId:matchId,
                  winTeamId:this.zstype==1?this.tz.homeTeamId:this.zstype==2?this.tz.guestTeamId:"",
                  odds:odds,
                  quotationType:1,
                },"post").then(ress=>{
                  if(ress.code!=1000){
                    this.openshow=false;
                    alert(ress.message);
                    return;
                  }
                  this.openshow=true;
                  // send(ress.data.id);
                  this.queryBetPage.id=ress.data.id
                  this.queryBetPage.surplusBond=ress.data.surplusBond;
                  this.queryBetPage.unitAmount=ress.data.unitAmount;
                  this.queryBetPage.fee=ress.data.fee;
                  this.queryBetPage.maxBetAmount=ress.data.maxBetAmount;
                })
            })
            /*this.$api("quotation/queryQuotationByMatchId",{
              matchId:this.list[this.listkey]['id'],
              oddsType:1,
              quotationType:1
            },"post").then(res=>{
              this.tz.guestCover=res.data.match.guestCover;
              this.tz.guestTeam=res.data.match.guestTeam;
              this.tz.homeCover=res.data.match.homeCover;
              this.tz.homeTeam=res.data.match.homeTeam;
            })*/
          },
          send(){
            if(this.touzhuMoney==0){
              alert("Please enter amount！");
              return;
            }
            if(10000000>this.trxbalance){
              alert("TRX Lack of balance");//trx余额不足
              return;
            }
            if(this.udstbalance<this.touzhuMoney){
              alert("Lack of balance");
              return;
            }
            if(parseFloat(this.queryBetPage.maxBetAmount)<parseFloat(this.touzhuMoney)){
              alert("Your balance exceeds the maximum limit");
              return;
            }
            this.$api("webPage/findAddressById",{
              matchId:this.list[this.listkey].id,
            },"post").then(ress=>{
              this.ptaddress=ress.data;
              this.zifu();
            })
            /*this.$api("webPage/betPayBefore",{
              betAmount:this.touzhuMoney,
              quotationId:this.queryBetPage.id,
            },"post").then(res=>{
              if(res.code!=1000){
                alert(res.message);
                return;
              }
              this.oid=res.data;
              this.$api("webPage/findAddressById",{
                eagueMatchId:this.list[this.listkey].leagueMatch,
              },"post").then(ress=>{
                this.ptaddress=ress.data;
                this.zifu(res.data);
              })
            })*/
          },
          async zifu(){
            /*var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              var tx = await tronWeb.transactionBuilder.sendTrx(this.ptaddress, this.touzhuMoney,address )
              var signedTx = await tronWeb.trx.sign(tx)
              await tronWeb.trx.sendRawTransaction(signedTx).then(res=>{
                this.txid=res.txid;
                var that=this;
                this.zjlong=true;
                setTimeout(function (){
                  that.betPayFinish();
                },3000)
              }).catch((err)=>{
                console.log(err,"err")
              })
            }*/
            var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              try {
                var pay_num_wei = parseInt(1 * 100000000);
                // var pay_num_wei = parseInt(1);
                let instance = await tronWeb.contract().at('TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t');
                var zfmoney=parseInt(this.touzhuMoney*1000000);
                let transfer = await instance.transfer(this.ptaddress, zfmoney);
                console.log(this.ptaddress, zfmoney);
                let res = await transfer.send({
                  feeLimit: pay_num_wei,//最高手续费额度
                  callValue: 0,
                  shouldPollResponse: false
                })
                this.txid=res;
                var that=this;
                this.zjlong=true;
                this.jynum=0;
                setTimeout(function (){
                  // that.dscx(res);
                  that.betPayFinish();
                },1000)
              } catch (error) {
                console.log("取消:",error);
              }
            }
          },
          async dscx(txid){
            var tronWeb = window.tronWeb
            var that=this;
            await tronWeb.trx.getTransaction(txid)
                .then(result => {
                  console.log(result)
                  if(result.ret[0].contractRet=='SUCCESS'){
                    that.jynum++;
                    if(that.jynum>=3){
                      that.betPayFinish();
                    }else{
                      setTimeout(function (){
                        that.dscx(txid);
                      },1000)
                    }
                  }else{
                    that.zjlong=false;
                    alert(result.ret[0].contractRet);
                  }
                });
          },
          betPayFinish(){
            this.$api("quotation/betPay",{
              betAmount:this.touzhuMoney,
              quotationId:this.queryBetPage.id,
              txId:this.txid,
            },"post").then(res=>{
              if(res.code!=1000){
                alert(res.message);
                return;
              }
              this.zjlong=false;
              this.tztype=1;
            })
            /*this.$api("webPage/betPayFinish",{
              betAmount:this.touzhuMoney,
              orderId:this.oid,
              quotationId:this.queryBetPage.id,
              txId:this.txid,
            },"post").then(res=>{
              if(res.code!=1000){
                alert(res.message);
                return;
              }
              this.zjlong=false;
              this.tztype=1;
            })*/
          },
          showopenshow(id){
            this.tzview(id);
            this.getTronweb();
          },
          onConfirm1:function (value){
            this.sgleagueMatch=value.id;
            this.sgleagueMatchname=value.text;
            this.pageNum=0;
            this.indexlist();
            this.lssxtype=false;
          },
          onCancel1:function (){
            this.lssxtype=false;
          },
          onChange1:function (){

          },
          onConfirm2:function (value){
            let dateStr = new Date(this.sgDate).format('yyyy-MM-dd')
            this.xssgtime=dateStr;
            this.startTime=new Date(Date.parse(dateStr.replace(/-/g, "/"))).getTime() ;
            this.endTime=new Date().setTime(Date.parse(dateStr.replace(/-/g, "/")) + 24 * 60 * 60 *1000 - 1);
            this.pageNum=0;
            this.indexlist();
            this.sgtime = false
          },
        },
      destroyed(){
        window.removeEventListener("scroll", this.handleScroll)

      },
      watch:{
        touzhuMoney:{
          handler:function (val){
            this.touzhuMoney=this.touzhuMoney>this.queryBetPage.maxBetAmount?this.queryBetPage.maxBetAmount:this.touzhuMoney;
          },
          deep: true
        },
        keyword:{
          handler:function (val){
            this.pageNum=0;
            this.indexlist();
          },
          deep: true
        },
        openshow:{
          handler:function (val){
            if(val==true){
              /*this.getTronweb();
              this.tzview();*/
            }
          },
          deep: true
        },
      }
    }
</script>

<style lang="less" scoped>
.tool-wrap{
    width: 66vw;
    height: 100vh;
    background: #252525;
    overflow-y: scroll;
    padding-top: 50px;
    .tool{
        padding: 19px 26px;
        border-bottom: 1px solid  #434343;
        display: flex;
        align-items: center;
        justify-content: space-between;
        &:last-of-type{
            border-bottom: none;
        }
        &.active{
            border-bottom: 3px solid #434343;
        }
        .tool-left{
            display: flex;
            align-items: center;
            img{
                width: 14px;
                height: auto;
            }
            p{
                font-size: 12px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #FFFFFF;
                margin-left: 5px;
            }
        }
        .tool-right{
            font-size: 12px;
            font-family: FZLanTingHei-DB-GBK;
            font-weight: 400;
            color: #26CDFA;
            display: flex;
            align-items: center;
            img{
                width: 15px;
                margin-left: 15px;
            }
        }
    }
}
/deep/ .van-tab{
font-size: 10PX;
}
/deep/.van-tabs__line{opacity: 0;}
    .navbar-wrap{
        width: 100%;
        height: 62px;
        padding: 0 19px;
        background: #0A0A0A;
        display: flex;
        align-items: center;
        justify-content: space-between;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 9999;
        .search{
            width: 238px;
            height: 32px;
            background: #373737;
            border-radius: 16px;
            input{
                width: 100%;
                height: 100%;
                padding: 0 7px;
                text-align: center;
                font-size: 12px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #FFFFFF;
            }
        }
        .screen{
            display: flex;
            align-items: center;
            p{
                font-size: 12px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #FFFFFF;
                position: relative;
                span{
                    min-width: 100%;
                    position: absolute;
                    bottom: -100%;
                    left: 50%;
                    transform: translateX(-50%);
                    text-align: center;
                }
            }
            .screen-icon{
                transform: rotate(90deg);
                margin-left: 2px;
                transition: all .365s;
                &.active{
                    transform: rotate(-90deg);
                }
            }
        }
        .search-more{}
    }
    .page-wrap{
        padding-top: 62px;
        .banner{
            margin: 12px 19px 0 19px;
            .van-swipe-item{
                width: 100%;
                height: 110px;
                img{
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    border-radius: 10px;
                }
            }

        }
    }
    .datetime{
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
        margin: 20px 19px;
    }
    .jinri_wrap{
        padding: 0 19px;
        margin-bottom: 10px;
        .jinri_top{
            height: 37px;
            background: #414141;
            border-radius: 4px 4px 0px 0px;
            padding: 0 15px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .left{
                display: flex;
                align-items: center;
                img{width: 12px;max-height: 100%;flex-shrink: 0;}
                p{
                    font-size: 12px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                    margin-left: 4px;
                }
            }
            .right{
                display: flex;
                align-items: center;
                p{
                    font-size: 10px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                    margin-right: 18px;
                    &:last-of-type{margin-right: 0;}
                }

            }
        }
        .jinri_box{
            margin: 5px 0;
            padding: 8px 15px 14px 15px;
            background: #2A2A2A;
            border-radius: 3px;
            .jinri-time{
                display: flex;
                align-items: center;
                justify-content: space-between;
                .left{
                    font-size: 11px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: rgba(255,255,255,.5);
                }
                .right{
                    display: flex;
                    align-items: center;
                    img{
                        width: 14px;
                    }
                    P{
                        font-size: 12px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #FFFFFF;
                        margin-left: 4px;
                    }
                }
            }
            .jinri{
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-top: 10px;
                .left{
                    width: 38%;
                    display: flex;
                    flex-direction: column;
                    .team{
                        width: 100%;
                        margin: 12px 0;
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        .team-name{
                            flex-shrink: 0;
                            display: flex;
                            align-items: center;
                            img{
                                width: 18px;
                                max-height: 25px;
                                flex-shrink: 0;
                            }
                            p{
                                width: 70px;
                                font-size: 12px;
                                font-family: FZLanTingHei-R-GBK;
                                font-weight: 400;
                                color: #FFFFFF;
                                margin-left: 7px;
                            }
                        }
                        .team-score{
                            font-size: 12px;
                            font-family: FZLanTingHei-R-GBK;
                            font-weight: 400;
                            color: #FFFFFF;
                        }
                    }
                }
                .right{
                    // width: 50%;
                    flex-shrink: 0;
                    display: flex;
                    .score-left{
                        .score{
                            width: 55px;
                            height: 27px;
                            margin: 1px 0;
                            background: #323232;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            .score-fen{
                                p{
                                    font-size: 8px;
                                    font-family: FZLanTingHei-R-GBK;
                                    font-weight: 400;
                                    color: rgba(255, 255, 255, .5);
                                    display: block;
                                    text-align: center;
                                }
                                span{
                                    font-size: 10px;
                                    font-family: FZLanTingHei-B-GBK;
                                    font-weight: 400;
                                    color: #FFFFFF;
                                    display: block;
                                    text-align: center;
                                    margin-top: 1px;
                                }
                            }
                            img{
                                width: 13px;
                                height: 13px;
                            }
                        }
                    }
                    .score-right{
                        margin-left: 1px;
                        .score{
                            width: 55px;
                            height: 41px;
                            margin: 1px 0;
                            background: #323232;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            .score-fen{
                                p{
                                    font-size: 8px;
                                    font-family: FZLanTingHei-R-GBK;
                                    font-weight: 400;
                                    color: rgba(255, 255, 255, .5);
                                    display: block;
                                    text-align: center;
                                }
                                span{
                                    font-size: 10px;
                                    font-family: FZLanTingHei-B-GBK;
                                    font-weight: 400;
                                    color: #FFFFFF;
                                    display: block;
                                    text-align: center;
                                    margin-top: 1px;
                                }
                            }
                            img{
                                width: 13px;
                                height: 13px;
                            }
                        }
                    }
                }
            }
        }
    }

    .touzhu-wrap{
        .touzhu-top{
            background: #D9EEFF;
            height: 50px;
            padding: 0 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .left{
                font-size: 12px;
                font-family: FZLanTingHei-DB-GBK;
                font-weight: 400;
                color: #1B1B1B;
            }
            .right{
                display: flex;
                align-items: center;
                .yue{
                    text-align: right;
                    p{
                        font-size: 9px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #1B1B1B;
                    }
                    span{
                        font-size: 12px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #2ABBF0;
                        display: block;
                        margin-top: 5px;
                    }
                }
                .close{
                    margin-left: 8px;
                    padding: 3px 0;
                    padding-left: 7px;
                    border-left: 1px solid #7D7D7D;
                    img{
                        width: 15px;
                        height: 15px;
                    }
                }
            }
        }
        .touzhu{
            padding: 25px 20px;
            padding-bottom: 0;
            display: flex;
            justify-content: space-between;
            .left{
                display: flex;
                .left-img{
                    width: 15px;
                    height: auto;
                    flex-shrink: 0;
                    margin-right: 10px;
                }
                .left-title{
                    .left-h1{
                        font-size: 16px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #1A1A1A;
                    }
                    .left-h2{
                        font-size: 13px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #1A1A1A;
                        margin-top: 5px;
                    }
                    .left-p{
                        font-size: 12px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #5B5B5B;
                        margin-top: 5px;
                    }
                }
            }
            .right{
                .right-h1{
                    font-size: 18px;
                    font-family: FZLanTingHei-DB-GBK;
                    font-weight: 400;
                    color: #1A1A1A;
                    text-align: right;
                }
                .right-succ{
                    display: flex;
                    align-items: center;
                    img{
                        width: 12px;
                        height: 12px;
                    }
                    p{
                        font-size: 10px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #66A719;
                        margin-left: 3px;
                    }

                }
            }
        }
        .touzhu-success{
            padding: 0 20px 0 45px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .left{
                .left-p{
                    font-size: 12px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #5B5B5B;
                    margin-top: 5px;
                    span{
                        color: #A01111;
                    }
                }
            }
            .right{
                font-size: 12px;
                font-family: FZLanTingHei-DB-GBK;
                font-weight: 400;
                color: #1A1A1A;
            }
        }
        .touzhu-input{
            margin-top: 25px;
            padding: 30px 20px;
            padding-bottom: 0;
            border-top: 6px solid #EBEBEB;
            .title{
                font-size: 13px;
                font-family: FZLanTingHei-DB-GBK;
                font-weight: 400;
                color: #1A1A1A;
            }
            .touzhu-input-box{
                margin-top: 4px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                .left{
                    flex-shrink: 0;
                    margin-right: 18px;
                    .left-p{
                        font-size: 10px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #5B5B5B;
                        &:last-of-type{margin-top: 4px;}
                        span{
                            color: #A01111;
                        }
                    }
                }
                .right{
                    flex: 1;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    background: #F7F7F7;
                    border: 1px solid #BFBFBF;
                    border-radius: 4px;
                    padding: 0 13px;
                    input{
                        width: 100%;
                        height: 31px;
                    }
                    p{
                        flex-shrink: 0;
                        margin-left: 10px;
                        font-size: 10px;
                        font-family: FZLanTingHei-DB-GBK;
                        font-weight: 400;
                        color: #FC2323;
                    }
                }
            }
        }
        .touzhu-bt{
            margin:55px 20px 34px 20px;
            height: 40px;
            background: #B2B2B2;
            border-radius: 20px;
            font-size: 16px;
            font-family: FZLanTingHei-DB-GBK;
            font-weight: 400;
            color: #F8F8F8;
            text-align: center;
            line-height: 40px;
            &.active{
                background: linear-gradient(0deg, #0075BA, #29D3FF);
            }
        }
    }

    .screen-wrap{
        background: #0A0A0A;
        padding: 20px;
        padding-top: 82px;
        .screen-list{
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            .screen-item{
                width: 159px;
                height: 37px;
                background: #C5C5C5;
                border-radius: 4px;
                margin-bottom: 9px;
                font-size: 14px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #131313;
                text-align: center;
                line-height: 37px;
                &.active{
                    color: #fff;
                    background: linear-gradient(0deg, #0075BA, #29D3FF);
                }
            }
        }
        .screen-title{
            font-size: 14px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
            margin-top: 28px;
            margin-bottom: 10px;
        }
    }

    .champion-list{
        .champion-item{
            padding: 0 18px;
            margin: 13px 0;
            .champion-top{
                height: 37px;
                background: #2A2A2A;
                border-radius: 4px 4px 0px 0px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 0 10px;
                p{
                    color: rgba(255, 255, 255, .5);
                    font-size: 10px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                }
            }
            .champion{
                margin-top: 2px;
                background: #2A2A2A;
                .champion-title{
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    padding: 8px 5px;
                    .champion-title-left{
                        display: flex;
                        align-items: center;
                        img{
                            width: 15px;
                            height: 15px;
                            margin-right: 5px;
                        }
                        p{
                            font-size: 12px;
                            font-family: FZLanTingHei-R-GBK;
                            font-weight: 400;
                            color: #FFFFFF;
                        }
                    }
                    .champion-title-right{
                        font-size: 10px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #FFFFFF;
                    }
                }
                .champion-box{
                    padding: 0 3px;
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-between;
                    padding-bottom: 8px;
                    .champion-dui{
                        background: #323232;
                        min-height: 35px;
                        padding: 0 13px;
                        width: calc(50% - 0.5px);
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        margin-bottom: 1px;
                        .champion-dui-left{
                            display: flex;
                            align-items: center;
                            img{
                                width: 15px;
                                height: auto;
                                flex-shrink: 0;
                            }
                            p{
                                font-size: 12px;
                                font-family: FZLanTingHei-R-GBK;
                                font-weight: 400;
                                color: #FFFFFF;
                                margin-left: 6px;
                            }
                        }
                        .champion-dui-right{
                            font-size: 10px;
                            font-family: FZLanTingHei-B-GBK;
                            font-weight: 400;
                            color: #FFFFFF;
                            flex-shrink: 0;
                            margin-left: 5px;
                        }
                    }
                }
            }
        }
    }
    .saiguo-title{
        margin: 15px 0;
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
        text-align: center;
    }

    .saiguo{
        background: #2A2A2A;
        margin-top: 2px;
        padding: 11px 0;
        .saiguo-time{
            padding: 0 10px;
            font-size: 11px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: rgba(255, 255, 255, .5);
        }
        .saiguo-box{
            padding: 0 10px;
            .saiguo-item{
                padding: 7px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                .saiguo-item-left{
                    display: flex;
                    align-items: center;
                    width: 60%;
                    img{
                        width: 15px;
                        height: auto;
                        flex-shrink: 0;
                    }
                    p{
                        flex: 1;
                        font-size: 12px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #FFFFFF;
                        margin-left: 6px;
                    }
                }
                .saiguo-item-right{
                    flex-shrink: 0;
                    width: 38%;
                    text-align: center;
                    font-size: 12px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                    &.active{
                        color: #40A6F1;
                    }
                }
            }
        }
    }
    .sgsx{
      display: flex;
      justify-content: space-around;
      color: #FFFFFF;
      .sxls{
        img{
          width: 20px;
        }
      }
      .sxrq{
        img{
          width: 20px;
        }
      }
    }


</style>
