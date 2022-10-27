<template>
    <div>
        <div class="header">
            <div class="header-left">
                <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" @click="$router.back()">
                <p v-html="langdata['tz']">投注</p>
            </div>
        </div>
        <div class="touzhu-box">
            <div class="touzhu-icon">
                <img src="@img/index/touzhu-t.png" alt="" v-if="!battleshow" @click="battleshow=true">
                <img src="@img/index/touzhu-b.png" alt="" v-if="battleshow" @click="battleshow=false">
            </div>
            <div class="touzhu-top">
                <div class="touzhu-title" v-html="matchTime">AM 20:30</div>
                <div class="touzhu-zhandui" v-if="!battleshow">
                    <div class="zhandui">
                        <img v-bind:src="homeCover" alt="">
                        <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                        <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                        <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                    </div>
                    <div class="zhandui-fen">
                        <img src="@img/index/VS.png" alt="">
                    </div>
                    <div class="zhandui">
                        <img v-bind:src="guestCover" alt="">
                        <p v-html="guestTeam" v-if="lang=='zh_CN'">南安普顿</p>
                        <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">南安普顿</p>
                        <p v-html="guestTeam_en" v-if="lang=='en_US'">南安普顿</p>
                    </div>
                </div>
            </div>
            <div class="touzhu" v-if="battleshow">
                <div class="touzhu-left">
                    <div class="zhandui">
                        <img  v-bind:src="homeCover" alt="">
                        <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                        <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                        <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                    </div>
                    <div class="zhandui">
                        <img v-bind:src="guestCover" alt="">
                        <p v-html="guestTeam" v-if="lang=='zh_CN'">南安普顿</p>
                        <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">南安普顿</p>
                        <p v-html="guestTeam_en" v-if="lang=='en_US'">南安普顿</p>
                    </div>
                </div>
                <div class="touzhu-right">
                    <div class="touzhu-p">
                        <p v-html="langdata['wf']">玩法</p>
                        <span v-if="quotationType==1" v-html="langdata['qc']">全场</span>
                        <span v-if="quotationType==2" v-html="langdata['rf']">让分</span>
                        <span v-if="quotationType==3" v-html="langdata['dx']">大小</span>
                    </div>
                    <div class="touzhu-p">
                        <p v-if="lang=='zh_CN'">队伍</p>
                        <p v-if="lang=='zh_HK'">隊伍</p>
                        <p v-if="lang=='en_US'">Team</p>
                        <span v-html="homeTeam" v-if="lang=='zh_CN' && (winTeamId==homeTeamId || winTeamId==1)">伯恩利</span>
                        <span v-html="homeTeam_zht" v-if="lang=='zh_HK' && (winTeamId==homeTeamId || winTeamId==1)">伯恩利</span>
                        <span v-html="homeTeam_en" v-if="lang=='en_US' && (winTeamId==homeTeamId || winTeamId==1)">伯恩利</span>
                        <span v-html="guestTeam" v-if="lang=='zh_CN' && (winTeamId==guestTeamId || winTeamId==0)">伯恩利</span>
                        <span v-html="guestTeam_zht" v-if="lang=='zh_HK' && (winTeamId==guestTeamId || winTeamId==0)">伯恩利</span>
                        <span v-html="guestTeam_en" v-if="lang=='en_US' && (winTeamId==guestTeamId || winTeamId==0)">伯恩利</span>
                    </div>
                    <div class="touzhu-p">
                        <p v-html="langdata['pl']">赔率</p>
                        <span v-html="odds">10.5</span>
                    </div>
                    <div class="touzhu-p">
                        <p v-html="langdata['jjc']">奖金池</p>
                        <span v-html="surplusBond+'/'+bond">9500/9500</span>
                    </div>
                    <div class="touzhu-p">
                        <p v-html="langdata['kpr']">开盘人</p>
                        <span v-html="userAddress">0x4524844Ca...C7874D78</span>
                        <img src="@img/betwap/deal/copy.png" alt="">
                    </div>
                </div>

            </div>
        </div>

        <div class="content">
            <div class="content-top">
                <p>{{ langdata['yzje'] }} （{{unitAmount}} USDT/Unit）</p>
                <p>{{ langdata['zd'] }} {{maxBetAmount}}</p>
            </div>
            <div class="number-box">
                <div class="numberbt" @click="money>0?money--:money">-</div>
<!--                <div class="number">{{money}}</div>-->
                <input type="number" class="number" v-model="money">
                <div class="numberbt" @click="money++">+</div>
            </div>
            <div class="balance">
                <div class="balance-item">
                    <p v-if="lang=='zh_CN'">预期收益</p>
                    <p v-if="lang=='zh_HK'">預期收益</p>
                    <p v-if="lang=='en_US'">EstimateProfit</p>
                    <span>{{surplusBond}} USDT</span>
                </div>
                <div class="balance-item">
                    <p v-html="langdata['fy']">手续费</p>
                    <span>{{ fee }} USDT</span>
                </div>
            </div>
        </div>
        <div class="bt" v-on:click="send()" v-html="langdata['tz']">投注</div>
<!--      <div v-if="zjlong" style="margin:0 auto;width: 50px;position: absolute;left: 50%;margin-left: -25px;">
        <van-loading type="spinner"/>
      </div>-->
      <van-overlay :show="showNo" @click="showNo = false">
        <div class="wrapper-box">
          <div class="wrapper-no" @click.stop>
            <div class="wrapper-no-img">
              <!--                        <img src="@img/index/no.png" alt="">-->
              <img src="@img/betwap/xz-ing.png" alt="">
            </div>
            <div class="wrapper-no-p" v-html="msg">GAS不足</div>
          </div>
        </div>
      </van-overlay>
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

function dateTOAMORPM(currentDateTime) {
  var hrs = currentDateTime.getHours();
  var mnts = currentDateTime.getMinutes();
  var AMPM = hrs >= 12 ? 'PM' : 'AM';
  hrs = hrs % 12;
  hrs = hrs ? hrs : 12;
  mnts = mnts < 10 ? '0' + mnts : mnts;
  var result = AMPM +' '+ hrs + ':' + mnts ;
  return result;
}
    export default {
        data() {
            return {
              showNo:false,
              msg:"",
              battleshow:false,
              money:0,
              bond:'',
              surplusBond:"",
              unitAmount:"",
              userAddress:"",
              fee:"",
              maxBetAmount:"",
              id:"",
              guestCover:"",
              guestTeam:"",
              guestTeamId:"",
              guestTeam_zht:"",
              guestTeam_en:"",
              homeCover:"",
              homeTeam:"",
              homeTeamId:"",
              homeTeam_en:"",
              homeTeam_zht:"",
              odds:this.$route.query.odds,
              ptaddress:"",
              zjaddress:"",
              trxbalance:0,
              usdtbalance:0,
              oid:"",
              txid:"",
              matchTime:"",
              Capital:0,
              clicktype:0,
              lang:localStorage.getItem("lang"),
              langdata:langdata(localStorage.getItem("lang")),
              zjlong:false,
              winTeamId:"",
              quotationType:"",
              jynum:0,
              matchId:this.$route.query.matchId,
            }
        },
        created() {
          if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
            this.lang="en_US";
          }
          this.getTronweb();
            this.init();
        },
        methods: {
          async getTronweb(){
            var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              let trxbalance = await tronWeb.trx.getBalance(address);
              let contract = await tronWeb.contract().at("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")
              let balance = await contract.balanceOf(address).call();
              let mybalance = balance.toNumber();//usdt通证余额
              this.usdtbalance=mybalance
              this.trxbalance=trxbalance
              this.Capital=mybalance
            }
          },
            init(){
              this.winTeamId=this.$route.query.winTeamId;
              this.quotationType=this.$route.query.quotationType,
              this.$api("quotation/queryBetPage",{
                matchId:this.matchId,
                winTeamId:!this.$route.query.winTeamId?"":this.$route.query.winTeamId,
                givePoints:!this.$route.query.givePoints?"":this.$route.query.givePoints,
                specificSize:!this.$route.query.specificSize?"":this.$route.query.specificSize,
                odds:this.$route.query.odds,
                quotationType:this.$route.query.quotationType,
              },"post").then(res=>{
                if(res.code!=1000){
                  alert(res.message);
                }
                this.id=res.data.id;
                this.bond=res.data.bond;
                this.surplusBond=res.data.surplusBond;
                this.unitAmount=res.data.unitAmount;
                this.userAddress=res.data.userAddress.slice(0,5)+"..."+res.data.userAddress.slice(res.data.userAddress.length-5);
                this.fee=res.data.fee;
                this.maxBetAmount=res.data.maxBetAmount;
                // console.log(res.data);
              })
              this.$api("quotation/queryQuotationByMatchId",{
                matchId:this.matchId,
                oddsType:1,
                quotationType:this.$route.query.quotationType,
              },"post").then(res=>{
                this.guestCover=res.data.match.guestCover;
                this.guestTeam=res.data.match.guestTeam;
                this.guestTeamId=res.data.match.guestTeamId;
                this.homeCover=res.data.match.homeCover;
                this.homeTeam=res.data.match.homeTeam;
                this.homeTeamId=res.data.match.homeTeamId;
                this.homeTeam_en=res.data.match.homeTeam_en
                this.homeTeam_zht=res.data.match.homeTeam_zht
                this.guestTeam_zht=res.data.match.guestTeam_zht
                this.guestTeam_en=res.data.match.guestTeam_en
                this.matchTime=dateTOAMORPM(new Date(res.data.match.matchTime));
                this.$api("webPage/findAddressById",{
                  matchId:this.matchId,
                },"post").then(ress=>{
                  this.ptaddress=ress.data;
                })
              })
            },
          send(){
            if(this.clicktype==1){
              return;
            }
            if(this.money==0){
              alert("Please enter amount！");
              return;
            }
            if(10000000>this.trxbalance){
              alert("TRX Lack of balance");//trx余额不足
              return;
            }
            if(this.usdtbalance<this.money*1000000){
              alert("Lack of balance");//余额不足
              return;
            }
            if(parseFloat(this.maxBetAmount)<parseFloat(this.money)){
              alert("Exceeding the maximum amount");
              return;
            }
            this.zifu();
            /*this.$api("webPage/betPayBefore",{
              betAmount:this.money,
              quotationId:this.id,
            },"post").then(res=>{
              this.clicktype=0;
              if(res.code!=1000){
                alert(res.message);
                return;
              }
              this.oid=res.data;
              this.zifu();
            })*/
          },
          async zifu(){
            /*var that = this;
            that.zjlong = true;
            that.jynum = 0;
            that.dscx("57106dcd4a88a7a4d4ec10cb92ba12324bc6b584f6494d6585dbd419e85da9c6");
            return;*/
            /*var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              var tx = await tronWeb.transactionBuilder.sendTrx(this.ptaddress, this.money,address )
              var signedTx = await tronWeb.trx.sign(tx)
              await tronWeb.trx.sendRawTransaction(signedTx).then(res=>{
                this.txid=res.txid;
                this.zjlong=true;
                var that=this;
                setTimeout(function (){
                  that.betPayFinish();
                },4000)
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
                var zfmoney=parseInt(this.money*1000000);
                let transfer = await instance.transfer(this.ptaddress,zfmoney);
                console.log(this.ptaddress,zfmoney);
                let res = await transfer.send({
                  feeLimit: pay_num_wei,//最高手续费额度
                  callValue: 0,
                  shouldPollResponse: false
                })
                var that = this;
                this.txid = res;
                this.zjlong = true;
                this.jynum = 0;
                setTimeout(function () {
                  that.txid = res;
                  // that.dscx(res);
                  that.betPayFinish();
                }, 1000);
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
              betAmount:this.money,
              quotationId:this.id,
              txId:this.txid,
            },"post").then(res=>{
              /*this.getTronweb();
              this.init();*/
              this.zjlong=false;
              this.msg=res.message
              this.showNo=true;
              this.$router.back();
            })
            /*this.$api("webPage/betPayFinish",{
              betAmount:this.money,
              orderId:this.oid,
              quotationId:this.id,
              txId:this.txid,
            },"post").then(res=>{
              this.zjlong=false;
              this.msg=res.message
              this.showNo=true;
            })*/
          }
        },

    }
</script>

<style lang="less" scoped>
.header{
  padding: 30px 30px 20px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  z-index: 2;
  .header-left{
      display: flex;
      align-items: center;
      flex-shrink: 0;
      margin-right: 24px;
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
  .header-right{
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

}
.touzhu-box{
    padding: 11px 25px;
    background: #2A2A2A;
    position: relative;
    .touzhu-icon{
        position: absolute;
        bottom: 10px;
        right: 17px;
        img{
            width: 15px;
            height: 15px;
        }
    }
    .touzhu-top{
        display: flex;
        align-items: center;
        justify-content: space-between;
        .touzhu-title{
            flex-shrink: 0;
            font-size: 12px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
        }
        .touzhu-zhandui{
            display: flex;
            align-items: center;
            justify-content: space-around;
            padding-right: 20px;
            .zhandui{
                display: flex;
                align-items: center;
                img{
                    width: 30px;
                }
                p{
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                    text-align: center;
                    margin-left: 5px;
                }
            }
            .zhandui-fen{
                display: flex;
                align-items: center;
                margin: 0 10px;
                img{
                    width: 24px;
                }
            }
        }
    }
    .touzhu{
        padding: 16px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .touzhu-left{
            flex-shrink: 0;
            .zhandui{
                display: flex;
                align-items: center;
                &:last-of-type{margin-top: 16px;}
                img{
                    width: 30px;
                }
                p{
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                    text-align: center;
                    margin-left: 8px;
                }
            }
        }
        .touzhu-right{
            .touzhu-p{
                display: flex;
                align-items: center;
                p{
                    width: 40px;
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: rgba(255, 255, 255, .7);
                }
                span{
                    margin-left: 10px;
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                }
                img{
                    width: 12px;
                    height: 13px;
                    margin-left: 8px;
                }
            }
        }
    }
}
.content{
    margin: 21px 18px;
    padding: 28px 15px;
    background: #2A2A2A;
    border-radius: 4px;
    .content-top{
        display: flex;
        justify-content: space-between;
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
        padding: 0 5px;
        p:first-of-type{
            color: rgba(255, 255, 255, .7);
        }
    }
    .number-box{
        margin: 13px 5px;
        height: 34px;
        padding: 0 23px;
        background: #0A0A0A;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .numberbt{
            width: 23px;
            height: 23px;
            background: #2A2A2A;
            border-radius: 3px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
        }
        .number{
            font-size: 14px;
            font-family: FZLanTingHei-B-GBK;
            font-weight: 400;
            color: #1CB6EA;
            text-align: center;
        }
    }
    .balance{
        margin: 0 5px;
        .balance-item{
            margin: 15px 0;
            display: flex;
            align-items: center;
            justify-content: space-between;
            p{
                font-size: 12px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #FFFFFF;
                opacity: 0.7;
            }
            span{
                font-size: 12px;
                font-family: FZLanTingHei-B-GBK;
                font-weight: 400;
                color: #fff;
            }
        }
    }

}
.bt{
    height: 37px;
    margin:24px 18px;
    background: linear-gradient(0deg, #0075BA, #29D3FF);
    border-radius: 4px;
    font-size: 12px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
    text-align: center;
    line-height: 37px;
}
.wrapper-box{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  .wrapper{
    width: 216px;
    background: #2A2A2A;
    border-radius: 10px;
    padding: 20px;
    position: relative;
    .wrapper-logo{
      position: absolute;
      left: 7px;
      top: 11px;
      img{width: 31px;}
    }
    .wrapper-title{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #F2F2F2;
      text-align: center;
      margin-bottom:21px;
    }
    .wrapper-form{
      .wrapper-item{
        display: flex;
        align-items: center;
        margin: 6px 0;
        .wrapper-text{
          width: 50px;
          font-size: 9px;
          font-family: FZLanTingHei-R-GBK;
          font-weight: 400;
          color: #F2F2F2;
          flex-shrink: 0;
        }
        .wrapper-input-box{
          display: flex;
          align-items: center;
          flex: 1;
          margin-left: 5px;
          .wrapper-input{
            width: 100%;
            height: 24px;
            background: #535353;
            border-radius: 2px;
            input{
              width: 100%;
              height: 100%;
              font-size: 12px;
              font-family: FZLanTingHei-R-GBK;
              font-weight: 400;
              color: #FFFFFF;
              padding: 0 10px;
            }
          }
          .wrapper-add{
            flex-shrink: 0;
            width: 24px;
            height: 24px;
            margin-left: 4px;
            background: #535353;
            border-radius: 2px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
          }
        }
      }

    }
    .wrapper-bt{
      width: 97px;
      height: 31px;
      background: linear-gradient(0deg, #0075BA, #29D3FF);
      border-radius: 4px;
      margin: 0 auto;
      margin-top: 30px;
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #FFFFFF;
      text-align: center;
      line-height: 31px;
    }
  }
  .wrapper-no{
    padding: 15px 30px ;
    background: #2A2A2A;
    border-radius: 10px;
    .wrapper-no-img{
      width: 40px;
      height: 40px;
      margin: 0 auto;
      img{
        width: 100%;
        height: 100%;
      }
    }
    .wrapper-no-p{
      font-size: 12px;
      font-family: FZLanTingHei-R-GBK;
      font-weight: 400;
      color: #F2F2F2;
      text-align: center;
      margin-top: 5px;
    }
  }
}
</style>
