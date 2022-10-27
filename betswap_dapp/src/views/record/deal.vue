<template>
  <div>
      <div class="wrap">
          <div class="header">
            <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
            <p v-html="langdata['jyjl']">交易记录</p>
          </div>
          <div class="content">
            <div class="content_top">
                <div class="content_top_gj">
                  <p v-html="homeScore"></p>
                  <div>
                    <img v-bind:src="homeCover" alt="boenli.png">
                    <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                    <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                    <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                  </div>
                </div>
                <div class="content_top_time">
                  <img src="../../assets/img/betwap/VS.png" alt="VS.png">
                  <p>AM 20:30</p>
                </div>
                <div class="content_top_gj">
                  <div>
                    <img v-bind:src="guestCover" alt="nananpudun.png">
                    <p v-html="guestTeam" v-if="lang=='zh_CN'">伯恩利</p>
                    <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                    <p v-html="guestTeam_en" v-if="lang=='en_US'">伯恩利</p>
                  </div>
                  <p v-html="guestScore"></p>
                </div>
            </div>
            <div class="content_detail">
                <div class="content_detail_xx">
                    <div class="content_detail_xx_sj">
                        <p class="" v-html="langdata['zt']">状态</p>
                        <p class="" v-if="teamStatus==1" v-html="langdata['wks']">未开赛</p>
                        <p class="" v-if="teamStatus==2 || teamStatus==3 || teamStatus==4 || teamStatus==5 || teamStatus==7" v-html="langdata['jxz']">进行中</p>
                        <p class="" v-if="teamStatus==8" v-html="langdata['yws']">已完赛</p>
                        <p class="" v-if="teamStatus==12" v-html="langdata['yqx']">已取消</p>
                        <p class="" v-if="teamStatus==9" v-html="langdata['yyq']">已延期</p>
                    </div>
                    <div class="content_detail_xx_sj">
                      <p class="" v-html="langdata['wf']">玩法</p>
                      <p class="" v-if="quotationType==1"><span style="margin-right: 20px" v-html="givePointsLeft"></span><span v-html="langdata['qc']"></span></p>
                      <p class="" v-if="quotationType==2"><span style="margin-right: 20px" v-html="givePointsLeft"></span><span v-html="langdata['rf']"></span></p>
                      <p class="" v-if="quotationType==3"><span style="margin-right: 20px" v-html="givePointsLeft"></span><span v-html="langdata['dx']"></span></p>
                    </div>
                    <div class="content_detail_xx_sj">
                      <p class="" v-html="langdata['dw']">队伍</p>
                      <p class="" v-html="winTeamName" v-if="lang=='zh_CN'">南安普顿</p>
                      <p class="" v-html="winTeamName_zht" v-if="lang=='zh_HK'">南安普顿</p>
                      <p class="" v-html="winTeamName_en" v-if="lang=='en_US'">南安普顿</p>
                    </div>
                    <div class="content_detail_xx_sj">
                      <p class="" v-html="langdata['pl']">赔率</p>
                      <p class=""><span v-html="odds"></span></p>
                    </div>
<!--                    <div class="content_detail_xx_sj">
                      <p class="">开盘人</p>
                      <p class="">0xsdafa5454...SFAS845FA <img src="../../assets/img/betwap/deal/copy.png" alt="copy.png"></p>
                    </div>-->
                    <div class="content_detail_xx_sj" v-if="transactionType==2">
                      <p class="" v-html="langdata['tzrs']">投注人数</p>
                      <p class="" v-html="betCount">20</p>
                    </div>
                </div>
                <div class="content_detail_sxf">
                    <div class="content_detail_sxf_fl">
                        <p class="title titlea" v-html="langdata['fy']">手续费</p>
                        <p class="num numa" v-html="fee">500</p>
                        <p class="cz cza">USDT</p>
                    </div>
                    <div class="content_detail_sxf_fl">
                      <p class="title" v-html="langdata['tzzj']">投注资金</p>
                      <p class="num" v-html="betAmount" v-if="transactionType==1">0.02561152</p>
                      <p class="num" v-if="transactionType==2">
                        <span class="orangspan" v-html="betAmountSum"></span>/
                        <span class="lanse" v-html="bond"></span>
                        ({{langdata['tzje']}}/{{langdata['bzj']}})
                      </p>
                      <p class="cz">USDT</p>
                    </div>
                    <div class="content_detail_sxf_fl">
                      <p class="title" v-html="langdata['qkid']">区块ID</p>
                      <p class="num" v-html="blockHash">0X2Fe5801...9720Ace08</p>
                      <p class="cz" v-on:click="copyHandle(blockHash)"><img src="../../assets/img/betwap/deal/copy.png" alt="copy.png"></p>
                    </div>
                    <div class="content_detail_sxf_fl">
                      <p class="title" v-html="langdata['jyhx']">交易哈希</p>
                      <p class="num" v-html="transactionHash">0xc7076b5...3b99a5eb6</p>
                      <p class="cz" v-on:click="copyHandle(transactionHash)"><img src="../../assets/img/betwap/deal/copy.png" alt="copy.png"></p>
                    </div>
                    <div class="content_detail_sxf_fl">
                      <p class="title">时间</p>
                      <p class="num" v-html="sjgsh(shijian)">2021-02-25  18:26:27</p>
                      <p class="cz"> </p>
                    </div>
                </div>
<!--                <div class="content_detail_input">
                  <p v-html="langdata['fx']">分享</p>
                </div>-->
              <div v-on:click="cancelQuotations" class="content_detail_input" v-if="teamStatus==1 && transactionType==2 && (status==0 || status==1 && status==2)">
                <p v-html="langdata['qxkp']">取消开盘</p>
              </div>
            </div>
          </div>
      </div>
  </div>
</template>

<script>
import ClipboardJS from 'clipboard'
import {langdata} from "@/utils/lang";
export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      id:"",
      transactionType:"",
      homeTeamId:"",
      guestCover:"",
      guestTeam:"",
      guestTeam_zht:"",
      guestTeam_en:"",
      guestScore:0,
      homeCover:"",
      homeTeam:"",
      homeTeam_zht:"",
      homeTeam_en:"",
      homeScore:0,
      teamStatus:0,
      quotationType:0,
      winTeamName:"",
      winTeamName_en:"",
      winTeamName_zht:"",
      odds:"",
      betCount:0,
      fee:0,
      betAmountSum:0,
      betAmount:0,
      blockHash:"",
      transactionHash:"",
      shijian:"",
      bond:"",
      status:"",
      givePointsLeft:"",
      unitAmount:"",
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
    copyHandle(content){
      let clipboard = new ClipboardJS(".cz",{
        text: function () {
          return content; /* 要复制的文本 */
        },
      });
      clipboard.on("success", function(e) {
        alert('已复制到剪切板:'+content)
        e.clearSelection();
        clipboard.destroy()
      });
      clipboard.on("error", function(e) {
        alert("复制失败！");
        e.clearSelection();
      });
    },
    init(){
      this.id=this.$route.query.id;
      this.transactionType=this.$route.query.transactionType;
      this.homeTeamId=this.$route.query.homeTeamId;
      this.$api("flow/queryBetRecordDetail",{
        id:this.id,
        transactionType:this.transactionType,
      },"post").then(res=>{
        this.guestCover=res.data.guestCover
        this.homeCover=res.data.homeCover
        this.teamStatus=res.data.teamStatus
        this.quotationType=res.data.quotationType
        this.winTeamName=res.data.winTeamName
        this.winTeamName_en=res.data.winTeamName_en
        this.winTeamName_zht=res.data.winTeamName_zht
        this.odds=res.data.odds
        this.betCount=res.data.betCount
        this.fee=res.data.fee
        this.betAmountSum=res.data.betAmountSum
        this.betAmount=res.data.betAmount
        this.blockHash=res.data.blockHash
        this.transactionHash=res.data.transactionHash
        this.shijian=res.data.createTime
        this.guestTeam=res.data.guestTeam;
        this.guestTeam_zht=res.data.guestTeam_zht;
        this.guestTeam_en=res.data.guestTeam_en;
        this.homeTeam=res.data.homeTeam;
        this.homeTeam_en=res.data.homeTeam_en;
        this.homeTeam_zht=res.data.homeTeam_zht;
        this.homeScore=res.data.homeScore;
        this.guestScore=res.data.guestScore;
        this.bond=res.data.bond;
        this.status=res.data.status;
        this.givePointsLeft=res.data.givePointsLeft;
        console.log(res.data)
      })
    },
    cancelQuotations(){
      this.$api("quotation/cancelQuotations",{
        quotationId:this.id,
      },"post").then(res=>{
        alert(res.message);
        if(res.code==1000){
          this.init();
          // this.$router.back();
        }
      })
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
.content_top{
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 22px 0;
  border-bottom: 4px solid #0A0A0A;
  .content_top_gj{
    text-align: center;
    display: flex;
    align-items: center;
    p{
      margin: 0 4px;
    }
    img{
      width: 30px;
      height: 35px;
    }
    p{
      font-size: 9px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      opacity: 0.88;
      margin-top: 6px;
    }
  }
  .content_top_time{
    text-align: center;
    img{
      width: 24px;
      height: 25px;
    }
    p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      margin-top: 6px;
    }
  }
}
.content_detail{
  font-size: 11px;
  font-family: FZLanTingHei-R-GBK;
  font-weight: 400;
  color: #FFFFFF;
  opacity: 0.8;
  padding-bottom: 50px;
}
.content_detail_xx{
  .content_detail_xx_sj{
    display: flex;
    justify-content: space-between;
    padding: 8px 30px;
    p{
      img{
        width: 12px;
        height: 13px;
        margin-left: 11px;
      }
    }
  }
}
.content_detail_sxf{
  width: 340px;
  height: 177px;
  background: #2A2A2A;
  box-shadow: 0px 0px 7px 0px rgba(47, 48, 48, 0.26);
  border-radius: 10px;
  margin: 0 auto;
  margin-top: 13px;
  padding: 16px 0;
  .content_detail_sxf_fl{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 6px 12px;
    p{
      font-size: 11px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      opacity: 0.8;
      img{
        width: 12px;
        height: 13px;
      }
    }
    .title{
      width: 80px;
    }
    .titlea{
      opacity: 1;
    }
    .num{
      width: 173px;
      overflow: hidden;
      .orangspan{
        color: red;
      }
      .lanse{
        color: green;
      }
    }
    .numa{
      opacity: 1;
      font-size: 14px;
      font-family: FZLanTingHei-B-GBK;
      font-weight: 400;
      color: #0683C3;
    }
    .cz{
      width: 35px;
      text-align: right;
    }
    .cza{
      opacity: 1;
    }
  }
}
.content_detail_input{
  width: 336px;
  height: 35px;
  background: linear-gradient(0deg, #0075BA, #29D3FF);
  border-radius: 17px;
  margin: 0 auto;
  margin-top: 31px;
  display: flex;
  p{
    display: flex;
    align-items: center;
    font-size: 12px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin: 0 auto;
    height: 100%;
  }
}
</style>
