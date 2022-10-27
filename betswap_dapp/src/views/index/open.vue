<template>
    <div>
        <div class="header">
            <div class="header-left">
                <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" @click="$router.back()">
                <p v-html="langdata['kp']">开盘</p>
            </div>
            <div class="header-right">
              <input type="text" v-bind:placeholder="langdata['ssss']">
            </div>
        </div>
        <!-- <div class="qiudui-box">
            <div class="qiudui-img">
                <img src="@img/index/premter_min.png" alt="">
            </div>
            <div class="qiudui-p">英超</div>
            <div class="qiudui-time">2022-3-20</div>
        </div> -->
        <div class="zhandui-box">
            <div class="zhandui">
                <img v-bind:src="homeCover" alt="">
                <p v-html="homeTeam" v-if="lang=='zh_CN'">南安普顿</p>
                <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">南安普顿</p>
                <p v-html="homeTeam_en" v-if="lang=='en_US'">南安普顿</p>
            </div>
            <div class="zhandui-fen">
                <img src="@img/index/VS.png" alt="">
                <p v-html="matchTime">AM 20:30</p>
            </div>
            <div class="zhandui">
                <img v-bind:src="guestCover" alt="">
                <p v-html="guestTeam" v-if="lang=='zh_CN'">伯恩利</p>
                <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                <p v-html="guestTeam_en" v-if="lang=='en_US'">伯恩利</p>
            </div>
        </div>

        <div class="contnt">
            <div class="nav_tab">
              <p :class="{active:nav_num==1}" @click="navClick(1)" v-html="langdata['qc']">全场</p>
              <p :class="{active:nav_num==2}" @click="navClick(2)" v-html="langdata['rf']">让分</p>
              <p :class="{active:nav_num==3}" @click="navClick(3)" v-html="langdata['dx']">大小</p>
            </div>

<!--            第一个-->
            <div class="bifen-box" v-if="nav_num==1">
                <div class="bifen-item">
                    <div class="bifen">
                        <div class="bifen-zhandui">
                          <img v-bind:src="homeCover" alt="">
                          <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                          <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                          <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                        </div>
                        <div class="bifen-p"></div>
                    </div>
                    <div class="bifen"></div>
                    <div class="bifen" @click="show = true;zk=1;" v-if="nav_num==1" v-html="qclist[1].odds>0?qclist[1].odds:'-'">-</div>
                </div>
                <div class="bifen-item" v-if="nav_num==1">
                  <div class="bifen">
                    <div class="bifen-zhandui">
<!--                      <img v-bind:src="homeCover" alt="">
                      <p v-html="homeTeam">伯恩利</p>-->
                    </div>
                    <div class="bifen-p" v-html="langdata['ps']">TIE</div>
                  </div>
                  <div class="bifen"></div>
                  <div class="bifen" @click="show = true;zk=0;" v-if="nav_num==1" v-html="qclist[0].odds>0?qclist[0].odds:'-'">-</div>
                </div>
                <div class="bifen-item">
                  <div class="bifen">
                    <div class="bifen-zhandui">
                      <img v-bind:src="guestCover" alt="">
                      <p v-html="guestTeam" v-if="lang=='zh_CN'">伯恩利</p>
                      <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                      <p v-html="guestTeam_en" v-if="lang=='en_US'">伯恩利</p>
                    </div>
                    <div class="bifen-p"></div>
                  </div>
                  <div class="bifen"></div>
                  <div class="bifen" @click="show = true;zk=2;" v-if="nav_num==1" v-html="qclist[2].odds>0?qclist[2].odds:'-'">-</div>
                </div>
            </div>

<!--          第二个-->
          <div class="bifen-box" v-if="nav_num==2">
            <div class="bifen-item">
              <div class="bifen">
                <div class="bifen-zhandui">
                  <img v-bind:src="homeCover" alt="">
                  <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                  <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                  <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                </div>
                <div class="bifen-p"></div>
              </div>
              <div class="bifen" v-on:click="rftype=true;zk=1;" v-html="rflist[1].givePoints?rflist[1].givePoints:'-'">0</div>
              <div class="bifen" @click="show = true;zk=1;" v-if="nav_num==2" v-html="rflist[1].odds>0?rflist[1].odds:'-'">-</div>
            </div>
            <div class="bifen-item">
              <div class="bifen">
                <div class="bifen-zhandui">
                  <img v-bind:src="guestCover" alt="">
                  <p v-html="guestTeam" v-if="lang=='zh_CN'">伯恩利</p>
                  <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                  <p v-html="guestTeam_en" v-if="lang=='en_US'">伯恩利</p>
                </div>
                <div class="bifen-p"></div>
              </div>
              <div class="bifen" v-on:click="rftype=true;zk=2;" v-html="rflist[2].givePoints?rflist[2].givePoints:'-'">0</div>
              <div class="bifen" @click="show = true;zk=2;" v-if="nav_num==2" v-html="rflist[2].odds>0?rflist[2].odds:'-'">-</div>
            </div>
          </div>

<!--          第三个-->
          <div class="bifen-box" v-if="nav_num==3">
            <div class="bifen-item">
              <div class="bifen" v-html="dxlist[0].compareFlog==1?'>':'<'" v-on:click="dxlist[0].compareFlog==1?(dxlist[0].compareFlog=0,dxcolumns=dxcolumns2):(dxlist[0].compareFlog=1,dxcolumns=dxcolumns1),dxlist[0].specificSize=dxlist[0].specificSize>0?dxlist[0].specificSize:dxlist[0].compareFlog==1?0:0.5">
                >
              </div>
              <div class="bifen" v-on:click="dxtype=true;zk=0;" v-html="dxlist[0].specificSize">0</div>
              <div class="bifen" @click="show = true;zk=0;" v-if="nav_num==3" v-html="dxlist[0].odds>0?dxlist[0].odds:'-'">-</div>
            </div>
          </div>

        </div>

        <div class="total">
            <div class="total-left" v-html="langdata['zyj']">Total Margin</div>
            <div class="total-right">
                <p v-html="zfmoney">28</p>
                <span>USDT</span>
            </div>
        </div>
        <div class="bt" v-on:click="send()" v-html="langdata['kp']">Apprve</div>

        <van-popup v-model:show="rftype" position="bottom" :style="{ height: '310px' }">
          <van-picker
              :show-toolbar="true"
              title="让分"
              :columns="rfcolumns"
              @confirm="rfonConfirm"
              @cancel="rfonCancel"
              @change="rfonChange"
          />
        </van-popup>

      <van-popup v-model:show="dxtype" position="bottom" :style="{ height: '310px' }">
        <van-picker
            :show-toolbar="true"
            title="大小"
            :columns="dxcolumns"
            @confirm="dxonConfirm"
            @cancel="dxonCancel"
            @change="dxonChange"
        />
      </van-popup>

        <van-overlay :show="show" @click="show = false">
            <div class="wrapper-box">
                <div class="wrapper" @click.stop>
                    <div class="wrapper-logo" v-if="zk>0">
                        <img v-bind:src="zk==1?homeCover:guestCover" alt="">
                    </div>
                    <div class="wrapper-title" v-if="nav_num==1" v-html="zk>0?langdata['ying']:langdata['ps']">WIN</div>
                    <div class="wrapper-title" v-if="nav_num==2" v-html="langdata['rf']">WIN</div>
                    <div class="wrapper-title" v-if="nav_num==3" v-html="langdata['dx']">WIN</div>
                    <div class="wrapper-form">
                      <div class="wrapper-item" v-if="nav_num==2">
                        <div class="wrapper-text ellipsis" v-html="langdata['rf']">WIN</div>
                        <div class="wrapper-input-box">
                          <div class="wrapper-input">
                            <input type="text" v-model="rfShow" disabled>
                          </div>
                        </div>
                      </div>
                      <div class="wrapper-item" v-if="nav_num==3">
                        <div class="wrapper-text ellipsis" v-html="langdata['dx']">WIN</div>
                        <div class="wrapper-input-box">
                          <div class="wrapper-input">
                            <input type="text" v-model="dxShow" disabled>
                          </div>
                        </div>
                      </div>
                        <div class="wrapper-item">
                            <div class="wrapper-text ellipsis" v-html="langdata['pl']">WIN</div>
                            <div class="wrapper-input-box">
                                <div class="wrapper-input">
                                    <input type="text" v-model="odds">
                                </div>
                            </div>
                        </div>
                        <div class="wrapper-item">
                            <div class="wrapper-text ellipsis" v-html="langdata['szsl']">Margin</div>
                            <div class="wrapper-input-box">
                                <div class="wrapper-input">
                                    <input class="orangespan" type="text" v-model="money">
                                </div>
                                <div class="wrapper-add" @click="money++">+</div>
                                <div class="wrapper-add" @click="money>0?money--:money">-</div>
                            </div>
                        </div>
                        <div class="wrapper-item">
                            <div class="wrapper-text ellipsis" v-html="langdata['bzj']">Profit/Bet</div>
                            <div class="wrapper-input-box">
                                <div class="wrapper-input">
                                    <input class="greenspan" type="text" v-model="profitBet" readonly>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="wrapper-bt" @click="queren();" v-html="langdata['qr']">Send</div>
                </div>
            </div>

        </van-overlay>

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
                rftype:false,
                rfcolumns:['+5','+4.5','+4','+3.5','+3','+2.5','+2','+1.5','+1','+0.5','-0.5','-1','-1.5','-2','-2.5','-3','-3.5','-4','-4.5','-5'],
                dxtype:false,
                dxcolumns:[0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.8,9,9.5,10,10.5],
                dxcolumns1:[0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.8,9,9.5,10,10.5],
                dxcolumns2:[0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.8,9,9.5,10,10.5],
                show:false,
                showNo:false,
                msg:"",
                nav_num:!this.$route.query.navnum?1:this.$route.query.navnum,
                money:0,
                matchId:this.$route.query.id,
                guestCover:"",
                guestTeam:"",
                guestTeam_en:"",
                guestTeam_zht:"",
                homeCover:"",
                homeTeam:"",
                homeTeam_en:"",
                homeTeam_zht:"",
                guestTeamId:"",
                homeTeamId:"",
                matchTime:"",
                odds:0,
                zk:0,
                dxShow:"",
                rfShow:"",
                profitBet:0,
                qclist:[{profitBet:0,odds:0},{profitBet:0,odds:0},{profitBet:0,odds:0}],
                rflist:[{profitBet:0,odds:0},{profitBet:0,odds:0},{profitBet:0,odds:0}],
                dxlist:[{profitBet:0,compareFlog:1,specificSize:0,odds:0}],
                leagueMatch:0,
                zfmoney:0,
                txid:"",
                zjlong:false,
                jynum:0,
              clicktype:0,
              lang:localStorage.getItem("lang"),
              langdata:langdata(localStorage.getItem("lang")),
            }
        },
        created() {
          if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
            this.lang="en_US";
          }
          this.init();
        },
        methods: {
          init(){
            this.$api("quotation/queryQuotationByMatchId",{
              matchId:this.matchId,
              oddsType:1,
              quotationType:1
            },"post").then(res=>{
              this.guestCover=res.data.match.guestCover
              this.guestTeam=res.data.match.guestTeam
              this.guestTeam_en=res.data.match.guestTeam_en
              this.guestTeam_zht=res.data.match.guestTeam_zht
              this.homeCover=res.data.match.homeCover
              this.homeTeam=res.data.match.homeTeam
              this.homeTeam_en=res.data.match.homeTeam_en
              this.homeTeam_zht=res.data.match.homeTeam_zht
              this.matchTime=dateTOAMORPM(new Date(res.data.match.matchTime))
              this.leagueMatch=res.data.match.leagueMatch
              this.guestTeamId=res.data.match.guestTeamId
              this.homeTeamId=res.data.match.homeTeamId
              this.$api("webPage/findAddressById",{
                matchId:this.matchId,
              },"post").then(ress=>{
                this.ptaddress=ress.data;
              })
            })
          },
          queren(){
            if(this.odds==0 || this.money==0){
              alert("Please fill in a number greater than 0");
              return;
            }
            var data={
              // id:1,
              leagueMatch:this.leagueMatch,
              matchId:this.matchId,
              quotationType:this.nav_num,
              givePoints:null,
              specificSize:null,
              compareFlog:null,
              winTeamId:null,
              odds:this.odds,
              unitAmount:this.money,
              profitBet:this.profitBet,
            };
            if(this.zk==1){
              data.winTeamId=this.homeTeamId;
            }
            if(this.zk==2){
              data.winTeamId=this.guestTeamId;
            }
            if(this.nav_num==1){
              this.qclist[this.zk]=data;
            }
            if(this.nav_num==2){
              data['givePoints']=this.rflist[this.zk]['givePoints'];
              this.rflist[this.zk]=data;
            }
            if(this.nav_num==3) {
              data['specificSize']=this.dxlist[this.zk]['specificSize'];
              data['compareFlog']=this.dxlist[this.zk]['compareFlog'];
              data['winTeamId']=data['compareFlog']==1?this.homeTeamId:this.guestTeamId;
              this.dxlist[this.zk]=data;
            }
            var zfmoney=0;
            for (var key in this.qclist){
              zfmoney = zfmoney+parseFloat(this.qclist[key]['profitBet']);
            }
            for (var key in this.rflist){
              zfmoney = zfmoney+parseFloat(this.rflist[key]['profitBet']);
            }
            for (var key in this.dxlist){
              zfmoney = zfmoney+parseFloat(this.dxlist[key]['profitBet']);
            }
            this.zfmoney=zfmoney.toFixed(2);
            this.odds=0;
            this.money=0;
            this.show = false;
          },
            navClick(num){
              this.nav_num=num;
            },
          async send(){
            for (var key in this.rflist){
              if(this.rflist[key].profitBet>0){
                if(this.rflist[key]['givePoints']=="" || this.rflist[key]['givePoints']==null){
                  alert(this.langdata['wskp']);
                  return;
                }
              }
            }
            if(this.zfmoney<=0){
              alert(this.langdata['wskp']);
              return;
            }
            // this.kaipan();
            var tronWeb = window.tronWeb
            var address = tronWeb.defaultAddress.base58
            if (tronWeb && address) {
              let contract = await tronWeb.contract().at("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")
              let balance = await contract.balanceOf(address).call();
              let mybalance = balance.toNumber();//usdt通证余额
              var trxbalance = await tronWeb.trx.getBalance(address);
              if(10000000>trxbalance){
                alert("TRX Lack of balance");//trx余额不足
                return;
              }
              if(this.zfmoney*1000000>mybalance){
                alert("Lack of balance");//余额不足
                return;
              }
              try {
                var pay_num_wei = parseInt(1 * 100000000);
                // var pay_num_wei = parseInt(1);
                let instance = await tronWeb.contract().at('TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t');
                var zfmoney=parseInt(this.zfmoney*1000000);
                let transfer=await instance.transfer(this.ptaddress,zfmoney);
                console.log(this.ptaddress,zfmoney);
                let res =await transfer.send({
                  feeLimit:pay_num_wei,//最高手续费额度
                  callValue:0,
                  shouldPollResponse:false
                })
                var that=this;
                this.zjlong=true;
                this.jynum=0;
                setTimeout(function (){
                  // that.dscx(res);
                  that.kaipan(res);
                },1000);
              } catch (error) {
                console.log("取消:");
              }
              /*var tx = await tronWeb.transactionBuilder.sendTrx(this.ptaddress, this.zfmoney,address )
              var signedTx = await tronWeb.trx.sign(tx)
              await tronWeb.trx.sendRawTransaction(signedTx).then(res=>{
                this.txid=res.txid;
                if(res.txid){
                  var that=this;
                  this.zjlong=true;
                  setTimeout(function (){
                    that.kaipan(res.txid);
                  },5000)
                }
              }).catch((err)=>{
                console.log(err,"err")
              })*/
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
                      that.kaipan(txid);
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
          kaipan(txid){
            var quotations=[];
            for (var key in this.qclist){
              if(this.qclist[key].profitBet>0){
                quotations[quotations.length] = this.qclist[key];
              }
            }
            for (var key in this.rflist){
              if(this.rflist[key].profitBet>0){
                var rflist=JSON.stringify(this.rflist[key]);
                rflist=JSON.parse(rflist);
                quotations[quotations.length] = rflist;
                quotations[quotations.length-1]['givePoints']=parseFloat(quotations[quotations.length-1]['givePoints']);
                if(this.homeTeamId==this.rflist[key]['winTeamId']){
                  quotations[quotations.length-1]['givePoints']=quotations[quotations.length-1]['givePoints']*-1;
                }
              }
            }
            for (var key in this.dxlist){
              if(this.dxlist[key].profitBet>0){
                quotations[quotations.length] = this.dxlist[key];
              }
            }
            quotations=JSON.stringify(quotations);
            console.log(quotations);
            this.quotations=quotations;
            this.txid=txid;
            this.$api("webPage/createQuotationsWeb",{
              quotations:quotations,
              txId:txid
            },"post").then(ress=>{
              console.log(ress)
              this.zjlong=false;
              if(ress.code!=1000){
                alert(ress.message);
                return;
              }else{
                this.msg=ress.message;
                this.showNo=true;
              }
              this.$router.push("/record?cla1="+this.nav_num+"&iptValue=2")
              // this.$router.back();
            })
          },
          rfonConfirm(value,index){
            this.rflist[this.zk].givePoints=value;
            this.rftype=false;
          },
          rfonCancel(){
            this.rftype=false;
          },
          rfonChange(){},
          dxonConfirm(value,index){
            this.dxlist[this.zk].specificSize=value;
            this.dxtype=false;
          },
          dxonCancel(){
            this.dxtype=false;
          },
          dxonChange(){},
        },
      watch:{
        money:{
          handler:function (val){
            if(val==""){
              val=0;
            }
            this.money=parseInt(val);
            this.profitBet=(this.money*this.odds).toFixed(2);
          },
          deep: true
        },
        odds:{
          handler:function (val){
            var dot = String(val).indexOf(".");
            if(dot != -1){
              var dotCnt = String(val).substring(dot+1,val.length);
              if(dotCnt.length > 2){
                val = parseFloat(val).toFixed(2);
              }
              this.odds=val;
            }
            this.profitBet=(this.money*this.odds).toFixed(2);
          },
          deep: true
        },
        qclist:{
          handler:function (val){
          },
          deep:true
        },
        rflist:{
          handler:function (val){
          },
          deep:true
        },
        dxlist:{
          handler:function (val){
          },
          deep:true
        },
        show:{
          handler:function (val){
            console.log(val,this.nav_num);
            if(val==true){
              if(this.nav_num==1){
                if(this.qclist[this.zk].profitBet>0){
                  this.odds=this.qclist[this.zk].odds;
                  this.money=this.qclist[this.zk].unitAmount;
                  this.profitBet=this.qclist[this.zk].profitBet;
                }
              }
              if(this.nav_num==2){
                if(this.rflist[this.zk].profitBet>0) {
                  this.odds = this.rflist[this.zk].odds;
                  this.money = this.rflist[this.zk].unitAmount;
                  this.profitBet = this.rflist[this.zk].profitBet;
                }
                this.rfShow=this.rflist[this.zk].givePoints?this.rflist[this.zk].givePoints:'-';
              }
              if(this.nav_num==3) {
                if(this.dxlist[this.zk].profitBet>0) {
                  this.odds = this.dxlist[this.zk].odds;
                  this.money = this.dxlist[this.zk].unitAmount;
                  this.profitBet = this.dxlist[this.zk].profitBet;
                }
                this.dxShow=(this.dxlist[this.zk].compareFlog==1?'>':'<')+(this.dxlist[0].specificSize=this.dxlist[0].specificSize>0?this.dxlist[0].specificSize:this.dxlist[0].compareFlog==1?0:0.5);
              }
            }
          },
          deep: true
        }
      }
    }
</script>

<style lang="less" scoped>
.header{
  padding: 30px 30px 20px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
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
.qiudui-box{
    padding: 0 32px;
    display: flex;
    align-items: center;
    position: relative;
    .qiudui-img{
        width: 23px;
        height: 30px;
        img{
            width: 100%;
            height: 100%;
        }
    }
    .qiudui-p{
        font-size: 16px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #F0F0F0;
        padding: 0 14px;
    }
    .qiudui-time{
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
    }
}
.zhandui-box{
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 17px 34px;
    background: #141414;
    .zhandui{
        display: flex;
        align-items: center;
        flex-direction: column;
        img{
            width: 30px;
        }
        p{
            font-size: 9px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
            margin-top: 7px;
            text-align: center;
        }
    }
    .zhandui-fen{
        display: flex;
        align-items: center;
        flex-direction: column;
        img{
            width: 24px;
        }
        p{
            font-size: 12px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #FFFFFF;
            margin-top: 7px;
        }
    }
}
.nav_tab{
    margin-top: 10px;
    padding: 10px 33px;
    display: flex;
    justify-content: space-between;
    position: relative;
    p{
        width: 89px;
        display: flex;
        justify-content: center;
        position: relative;
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: rgba(255, 255, 255, .4);
    }
    .active{
        color: #fff;
        &::after{
            content: '';
            position: absolute;
            bottom: -10px;
            width: 100%;
            height: 2px;
            background: #EB40AC;
        }
    }
}
.contnt{
    position: relative;
    background: #2A2A2A ;
    // padding: 0 18px;

    .bifen-box{
        border-radius: 8px;
        overflow: hidden;
        background: #2A2A2A;
        margin-bottom: 34px;
        .bifen-top{
            height: 42px;
            background: #0A0A0A;
            border-radius: 8px 8px 0 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            p{
                width: 33%;
                text-align: center;
                font-size: 15px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #098BCA;
            }
        }
        .bifen-item{
            padding: 10px 0;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 1px solid #3D3D3D;
            position: relative;
            &:last-of-type{
                border-bottom: 0;
            }
            .bifen{
                width: 33%;
                text-align: center;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 12px;
                font-family: FZLanTingHei-R-GBK;
                font-weight: 400;
                color: #FFFFFF;
                .bifen-zhandui{
                    display: flex;
                    align-items: center;
                    flex-direction: column;
                    img{
                        width: 20px;
                    }
                    p{
                        font-size: 9px;
                        font-family: FZLanTingHei-R-GBK;
                        font-weight: 400;
                        color: #FFFFFF;
                        margin-top: 3px;
                        text-align: center;
                    }
                }
                .bifen-p{
                    font-size: 12px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                }
            }
            .bifen-xiaofen{
                width: 33%;
                display: flex;
                justify-content: space-between;
                p{
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: #34BCFF;
                }
            }
            .bifen-item-icon{
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
            }
        }
        .bifen-item-active{
            background: #0C90CE;
            .bifen-xiaofen{
                p{color: #fff;}
            }
        }
        .bifen-item-child{
            background: #1E1E1E;
        }
    }
}
.total{
    margin: 19px 18px;
    background: #2A2A2A;
    border-radius: 4px;
    padding: 20px 18px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .total-left{
        font-size: 12px;
        font-family: FZLanTingHei-R-GBK;
        font-weight: 400;
        color: #FFFFFF;
    }
    .total-right{
        text-align: center;
        p{
            font-size: 18px;
            font-family: PingFang SC;
            font-weight: 800;
            color: #1CB6EA;
        }
        span{
            font-size: 9px;
            font-family: FZLanTingHei-R-GBK;
            font-weight: 400;
            color: #1CB6EA;
            display: block;
            margin-top: 3px;
        }
    }
}
.bt{
    height: 37px;
    margin:31px 18px;
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
                      .orangespan{
                        color: red;
                      }
                      .greenspan{
                        color: green;
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
