<template>
    <div>
        <div class="header">
            <div class="header-left">
                <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" @click="$router.back()">
                <p v-html="langdata['tz']">投注</p>
            </div>
            <div class="header-right">
                <input type="text" v-bind:placeholder="langdata['ssss']">
            </div>
        </div>
        <div class="bg">
            <img src="@img/betwap/me/wode_top.png" alt="">
        </div>

        <div class="qiudui-box">
            <div class="qiudui-img">
                <img v-if="leagusMatchs[lang][leagueMatch]" v-bind:src="leagusMatchs[lang][leagueMatch]['logo']" alt="">
            </div>
          <div class="qiudui-p" v-if="leagusMatchs[lang][leagueMatch]" v-html="leagusMatchs[lang][leagueMatch]['name']">英超</div>
            <div class="qiudui-time" v-html="sjgsh(matchTime)">2022-3-20</div>
        </div>
        <div class="nav_tab">
            <p :class="{active:nav_num==1}" @click="navClick(1)" v-html="langdata['qc']">全场</p>
            <p :class="{active:nav_num==2}" @click="navClick(2)" v-html="langdata['rf']">让分</p>
            <p :class="{active:nav_num==3}" @click="navClick(3)" v-html="langdata['dx']">大小</p>
        </div>
        <div class="contnt">
            <div class="zhandui-box">
                <div class="zhandui">
                    <img v-bind:src="homeCover" alt="">
                    <p v-html="homeTeam" v-if="lang=='zh_CN'">伯恩利</p>
                  <p v-html="homeTeam_zht" v-if="lang=='zh_HK'">伯恩利</p>
                  <p v-html="homeTeam_en" v-if="lang=='en_US'">伯恩利</p>
                </div>
                <div class="zhandui-fen">
                    <img src="@img/index/VS.png" alt="">
                    <p v-html="zhmatchTime">AM 20:30</p>
                </div>
                <div class="zhandui">
                    <img v-bind:src="guestCover" alt="">
                    <p v-html="guestTeam" v-if="lang=='zh_CN'">南安普顿</p>
                  <p v-html="guestTeam_zht" v-if="lang=='zh_HK'">南安普顿</p>
                  <p v-html="guestTeam_en" v-if="lang=='en_US'">南安普顿</p>
                </div>
            </div>
            <div class="bifen-box" v-if="nav_num==1">
                <div class="bifen-top">
                    <p>WIN</p>
                    <p>TIE</p>
                    <p>WIN</p>
                </div>
                <div class="bifen-item" v-for="(item,index) in qcList" :key="index">
                    <div class="bifen" v-if="item.left" @click="item.left.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+item.left.odds+'&quotationType='+nav_num+'&winTeamId='+homeTeamId)">
                        <p v-html="item.left.odds">1.28</p>
                        <span> <span class="orangspan">{{ item.left.surplusBond }}</span>/ <span class="lanse">{{ item.left.bond }}</span> USDT</span>
                    </div>
                    <div class="bifen" v-else>
                    </div>
                    <div class="bifen" v-if="item.center" @click="item.center.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+item.center.odds+'&quotationType='+nav_num)">
                        <p v-html="item.center.odds">1.28</p>
                        <span> <span class="orangspan">{{ item.center.surplusBond }}</span>/ <span class="lanse">{{ item.center.bond }}</span> USDT</span>
                    </div>
                    <div class="bifen" v-else>
                    </div>
                    <div class="bifen" v-if="item.right" @click="item.right.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+item.right.odds+'&quotationType='+nav_num+'&winTeamId='+guestTeamId)">
                        <p v-html="item.right.odds">1.28</p>
                      <span> <span class="orangspan">{{ item.right.surplusBond }}</span>/ <span class="lanse">{{ item.right.bond }}</span> USDT</span>
                    </div>
                    <div class="bifen" v-else>
                    </div>
                </div>
            </div>

            <div class="bifen-box" v-if="nav_num==2">
<!--                <div class="bifen-top">
                    <p>WIN</p>
                    <p>WIN</p>
                </div>-->
                <div class="bifen-item-box" v-for="(item,index) in rfList" :key="index">
                    <div :class="{'bifen-item':true,'bifen-item-active':item.qcDTO} " @click="item.childshow = !item.childshow">
                        <div class="bifen">
                          <p>{{item.oddsLeft}}</p>
                          <span>{{item.bondLeft>0?item.bondLeft:""}}</span>
                        </div>
                        <div class="bifen-xiaofen">
                          <p>{{item.givePointsLeft}}</p>
                          <p>{{item.givePointsRight}}</p>
                        </div>
                        <div class="bifen">
                          <p>{{item.oddsRight}}</p>
                          <span>{{item.bondRight>0?item.bondRight:""}}</span>
                        </div>
                        <div class="bifen-item-icon" v-if="item.qcDTO">
                            <van-icon name="arrow-down" color="#000" size="15"  v-if="item.childshow"/>
                            <van-icon name="arrow-up" color="#000" size="15"  v-if="!item.childshow"/>
                        </div>
                    </div>
                    <div class="bifen-item bifen-item-child" v-for="(v,k) in item.qcDTO" :key="k" v-if="!item.childshow">
<!--                      <div class="bifen" @click="v.left.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.left.odds+'&quotationType='+nav_num+'&winTeamId='+homeTeamId+'&givePoints='+(v.left['winTeamId']==homeTeamId?(item.givePointsLeft):item.givePointsLeft))">
                        <p>{{v.left.odds}}</p>
                        <span v-if="v.left.odds>0"> <span class="orangspan">{{v.left.surplusBond}}</span>/<span class="lanse">{{v.left.bond}}</span> USDT</span>
                      </div>-->
                      <div class="bifen" @click="v.left.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.left.odds+'&quotationType='+nav_num+'&winTeamId='+homeTeamId+'&givePoints='+(v.left['winTeamId']==homeTeamId?(item.givePointsLeft*-1):item.givePointsLeft))">
                        <p>{{v.left.odds}}</p>
                        <span v-if="v.left.odds>0"> <span class="orangspan">{{v.left.surplusUnitAmount}}</span>/<span class="lanse">{{v.left.bond}}</span> USDT</span>
                      </div>
                      <div class="bifen-xiaofen">
                        <p></p>
                        <p></p>
                      </div>
<!--                      <div class="bifen" @click="v.right.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.right.odds+'&quotationType='+nav_num+'&winTeamId='+guestTeamId+'&givePoints='+(v.right['winTeamId']==guestTeamId?(item.givePointsRight*-1):(item.givePointsRight>0?(item.givePointsRight*-1):item.givePointsRight)))">
                        <p>{{v.right.odds}}</p>
                        <span v-if="v.right.odds>0"><span class="orangspan">{{v.right.surplusBond}}</span>/<span class="lanse">{{v.right.bond}}</span> USDT</span>
                      </div>-->
                      <div class="bifen" @click="v.right.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.right.odds+'&quotationType='+nav_num+'&winTeamId='+guestTeamId+'&givePoints='+(v.right['winTeamId']==guestTeamId?(item.givePointsRight):(item.givePointsRight*-1)))">
                        <p>{{v.right.odds}}</p>
                        <span v-if="v.right.odds>0"><span class="orangspan">{{v.right.surplusUnitAmount}}</span>/<span class="lanse">{{v.right.bond}}</span> USDT</span>
                      </div>
                    </div>
                </div>

            </div>
            <div class="bifen-box" v-if="nav_num==3">
                <div class="bifen-top">
                    <p></p>
                    <p>TOTAL</p>
                    <p></p>
                </div>
                <div class="bifen-item-box" v-for="(item,index) in dxList" :key="index">
                    <div :class="{'bifen-item':true,'bifen-item-active':item.qcDTO} " @click="item.childshow = !item.childshow">
                        <div class="bifen">
                            <p>{{item.oddsLeft}}</p>
<!--                            <span> {{item.bondLeft}}</span>-->
                            <span>{{item.bondLeft>0?item.bondLeft:""}}</span>
                        </div>
                        <div class="bifen-xiaofen">
                            <p>{{item.givePointsLeft}}</p>
                            <p>{{item.givePointsRight}}</p>
                        </div>
                        <div class="bifen">
                            <p>{{item.oddsRight}}</p>
                            <span>{{item.bondRight>0?item.bondRight:""}}</span>
                        </div>
                        <div class="bifen-item-icon" v-if="item.qcDTO">
                            <van-icon name="arrow-down" color="#000" size="15"  v-if="item.childshow"/>
                            <van-icon name="arrow-up" color="#000" size="15"  v-if="!item.childshow"/>
                        </div>
                    </div>
                    <div class="bifen-item bifen-item-child" v-for="(v,k) in item.qcDTO" :key="k" v-if="!item.childshow">
                        <div class="bifen" @click="v.left.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.left.odds+'&quotationType='+nav_num+'&winTeamId=1&specificSize='+item.givePointsLeft)">
                            <p>{{v.left.odds}}</p>
                            <span v-if="v.left.odds>0"> <span class="orangspan">{{v.left.surplusBond}}</span>/<span class="lanse">{{v.left.bond}}</span> USDT</span>
                        </div>
                        <div class="bifen-xiaofen">
                            <p></p>
                            <p></p>
                        </div>
                        <div class="bifen" @click="v.right.surplusBond<=0?tsalert():$router.push('/bet1?matchId='+matchId+'&odds='+v.right.odds+'&quotationType='+nav_num+'&winTeamId=0&specificSize='+item.givePointsRight)">
                            <p>{{v.right.odds}}</p>
                            <span v-if="v.right.odds>0"><span class="orangspan">{{v.right.surplusBond}}</span>/<span class="lanse">{{v.right.bond}}</span> USDT</span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
      <div class="footer_right" @click.stop="$router.push('/open?id='+matchId+'&leagueMatch='+leagueMatch+'&navnum='+nav_num)">
        <img src="@img/index/VS.png" alt="">
        <p v-html="langdata['kp']">开盘</p>
      </div>
    </div>
</template>

<script>
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
                nav_num:1,
                list:[
                    {id:1,win1:2,win1usdt:"100 USDT",win1num:'+2',win2:1.9,win2usdt:"450/500 USDT",win2num:'+2'},
                    {id:2,win1:2,win1usdt:"100 USDT",win1num:'+2',win2:1.9,win2usdt:"450/500 USDT",win2num:'+2',childshow:false,
                        child:[
                            {id:1,win1:2,win1usdt:"100 USDT",win1num:'+2',win2:1.9,win2usdt:"450/500 USDT",win2num:'+2'},
                            {id:2,win1:2,win1usdt:"100 USDT",win1num:'+2',win2:1.9,win2usdt:"450/500 USDT",win2num:'+2'},
                        ]
                    },
                    {id:3,win1:'',win1usdt:"",win1num:'+2',win2:1.9,win2usdt:"450/500 USDT",win2num:''}
                ],
                list1:[
                  {id:1,win1:2,win1usdt:"100 USDT",win1num:'>2',win2:1.9,win2usdt:"450/500 USDT",win2num:'<2'},
                  {id:2,win1:2,win1usdt:"100 USDT",win1num:'>2',win2:1.9,win2usdt:"450/500 USDT",win2num:'<2',childshow:false,
                    child:[
                      {id:1,win1:2,win1usdt:"100 USDT",win1num:'>2',win2:1.9,win2usdt:"450/500 USDT",win2num:'<2'},
                      {id:2,win1:2,win1usdt:"100 USDT",win1num:'>2',win2:1.9,win2usdt:"450/500 USDT",win2num:'<2'},
                    ]
                  },
                  {id:3,win1:'',win1usdt:"",win1num:'>2',win2:1.9,win2usdt:"450/500 USDT",win2num:''}
                ],
              leagueMatch:0,
              matchTime:"",
              zhmatchTime:"",
              homeTeam:"",
              homeTeamId:"",
              homeTeam_en:"",
              homeTeam_zht:"",
              homeCover:"",
              guestTeam:"",
              guestTeamId:"",
              guestTeam_en:"",
              guestTeam_zht:"",
              guestCover:"",
              qcList:[],
              dxList:[],
              rfList:[],
              matchId:this.$route.query.id,
              leagusMatchs:{
                "zh_CN":{},
                "zh_HK":{},
                "en_US":{},
              },
              lang:localStorage.getItem("lang"),
              langdata:langdata(localStorage.getItem("lang")),
            }
        },
        created() {
          if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
            this.lang="en_US";
          }
          this.lslist();
          this.init();
        },
        methods: {
          tsalert(){
            alert(this.langdata['gpkyf']);
          },
            init(){
              this.$api("quotation/queryQuotationByMatchId",{
                matchId:this.matchId,
                oddsType:0,
                quotationType:this.nav_num
              },"post").then(res=>{
                this.leagueMatch=res.data.match.leagueMatch
                this.matchTime=res.data.match.matchTime
                this.zhmatchTime=dateTOAMORPM(new Date(res.data.match.matchTime))
                this.homeCover=res.data.match.homeCover
                this.homeTeam=res.data.match.homeTeam
                this.homeTeamId=res.data.match.homeTeamId
                this.homeTeam_en=res.data.match.homeTeam_en
                this.homeTeam_zht=res.data.match.homeTeam_zht
                this.guestTeam=res.data.match.guestTeam
                this.guestTeamId=res.data.match.guestTeamId
                this.guestTeam_zht=res.data.match.guestTeam_zht
                this.guestTeam_en=res.data.match.guestTeam_en
                this.guestCover=res.data.match.guestCover
                if(this.nav_num==1){
                  this.qcList=res.data.qcList
                }
                if(this.nav_num==2){
                  var rfList=res.data.rfList
                  for(var key in rfList){
                    rfList[key]['childshow']=false;
                    for(var k in rfList[key]['qcDTO']){
                      if(!rfList[key]['qcDTO'][k]['left']){
                        rfList[key]['qcDTO'][k]['left']={odds:"",surplusBond:""}
                      }
                      if(!rfList[key]['qcDTO'][k]['right']){
                        rfList[key]['qcDTO'][k]['right']={odds:"",surplusBond:""}
                      }
                    }
                  }
                  this.rfList=rfList;
                }
                if(this.nav_num==3){
                  var dxList=res.data.dxList
                  for(var key in dxList){
                    dxList[key]['childshow']=false;
                    for(var k in dxList[key]['qcDTO']){
                      if(!dxList[key]['qcDTO'][k]['left']){
                        dxList[key]['qcDTO'][k]['left']={odds:"",surplusBond:""}
                      }
                      if(!dxList[key]['qcDTO'][k]['right']){
                        dxList[key]['qcDTO'][k]['right']={odds:"",surplusBond:""}
                      }
                    }
                  }
                  this.dxList=dxList;
                }
                console.log(res.data)
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
              })
            },
            navClick(num){
                this.nav_num=num;
              this.init();
            },
          sjgsh(time) {
            var now = new Date(time),
                y = now.getFullYear(),
                m = now.getMonth() + 1,
                d = now.getDate(),
                h = now.getHours(),
                mm = now.getMinutes(),
                s = now.getSeconds();
            return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
          },

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
.bg{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1;
    img{
        width: 100%;
        margin-top: -50px;
    }
}
.qiudui-box{
    padding: 0 32px;
    display: flex;
    align-items: center;
    position: relative;
    z-index: 2;
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
.nav_tab{
    margin-top: 10px;
    padding: 10px 33px;
    display: flex;
    justify-content: space-between;
    position: relative;
    z-index: 2;
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
    z-index: 2;
    background: #141414;
    padding: 0 18px;
    .zhandui-box{
        display: flex;
        align-items: center;
        justify-content: space-around;
        padding: 20px 0 12px 0;
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
            padding: 20px 0;
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

                p{
                    font-size: 14px;
                    font-family: FZLanTingHei-B-GBK;
                    font-weight: 400;
                    color: #FFFFFF;
                }
                span{
                    margin-top: 9px;
                    font-size: 9px;
                    font-family: FZLanTingHei-R-GBK;
                    font-weight: 400;
                    color: rgba(255, 255, 255, .8);
                }
              .orangspan{
                color: red;
              }
              .lanse{
                color: green;
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
.footer_right{
  position: fixed;
  bottom: 70px;
  right: 10px;
  z-index: 10;
  display: flex;
  align-items: center;
  img{
    width: 20px;
  }
  p{
    font-size: 10px;
    font-family: FZLanTingHei-R-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin-right: 18px;
    &:last-of-type{margin-right: 0;}
    margin-left: 2px;
  }

}
</style>
