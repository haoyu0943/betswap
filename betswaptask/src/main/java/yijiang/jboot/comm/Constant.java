package yijiang.jboot.comm;


public class Constant {



    //定义确保线程单一运行的常量
    public static boolean ifReleasedBETTaskRun=false;//BET释放
    public static boolean ifUserLevelTaskRun=false;//用户等级回填
    public static boolean iCreateBETTaskRun=false;//链上分账并生成bet 待释放
    public static boolean ifMathCrawlingTaskRun=false;//拼团商品
    public static boolean ifOrderTimeOut=false;//订单超时
    public static boolean ifMatchClose=false;//比赛封盘
    public static boolean ifTeamTaskRun=false;//队伍信息
    public static boolean ifBackBoondTaskRun=false;//返还保证金
    public static boolean ifConfirmOrderTaskRun=false;//BET释放
    public static boolean ifSummaryWalletTaskRun=false;//归集钱包定时

    //支付类型
    public static int PAY_USDT=1;//USDT
    public static int PAY_BET=2;//BET

    //订单支付状态
    public static final Integer ORDER_TIME_OUT=20*60;  //订单超时关闭时间
    public static final Integer ORDER_WAIT_ORDER=1;  //待结账
    public static final Integer ORDER_CLOSE=5; //订单超时支付关闭
    public static final String ORDER_PAY_OUT="支付超时"; //支付超时

    //订单类型
    public static final Integer SHOP=0; //商城
    public static final Integer SHOP_GROUP=8; //商城团购
    public static final Integer MOVIES=1; //电影票
    public static final Integer FUND=2; //基金


    //团购状态
    public static String NOT_START="0";//未开始
    public static String START="1";//进行中
    public static String END="2";//结束


    public static final String tronUrl="http://8.142.45.106:8082/tron/";

    //**************************************************//

    //订单状态
    public static final Integer ONGOING=0; //订单进行中
    public static final Integer WAITPAY=1; //待结账-管理端录入比赛结果 发起支付请求
    public static final Integer FINISHED=2; //订单已完成
    public static final Integer CANCEL=3; //订单已取消
    public static final Integer PAYFILE=4; //结账失败
    public static final Integer ORDER_INCHAIN=6; //创建时默认是这个状态 根据hash txid 去查询 结果
    public static final Integer ORDER_INCHAIN_FAIL=7; //// 根据 txid 和次数判断 我这边定时 1分钟查一次 判断3次 差不多有3分钟 结果还是没有的话就是失败 成功后 状态 改为 ONGOING

    //收益类型
    public static String BET_PROFIT="1";//收益类型--投注收益
    public static String QUOTATION_PROFIT="2";//收益类型--开盘收益
    public static String AGENT_PROFIT="3";//收益类型--代理收益

    //开盘类型
    public static Integer QUOTATION_QC=1;//开盘类型--全场
    public static Integer QUOTATION_RF=2;//开盘类型--让分
    public static Integer QUOTATION_DX=3;//开盘类型--大小

    //盘口结果
    public static Integer QUOTATION_RESULT_SUCCESS=1;//盘口结果--押对
    public static Integer QUOTATION_RESULT_FAIL=2;//开盘类型--押错
    public static Integer QUOTATION_RESULT_FLOW=3;//开盘类型--流局

    //sys_para  配置
    public static Integer tronUrlVal=22; //钱包地址路径

    public static Integer summaryWalletTime=28;//归集时间

    public static Integer oneConsumeGAS=29;//一次转账消耗

    public static Integer checkCount=30;// 查询交易次数

    public static Integer fee_bet=25;//盈利手续费--投注
    public static Integer fee_kp=5;//盈利手续费--开盘

    public static Integer rate=2;//汇率BET对 USDT

    public static Integer return_rate_bet=3;//投注BET返还率

    public static Integer return_rate_kp=3;//开盘BET返还率

    //用户等级 类型

    public static final Integer RANK_DEFAULT = 1;//默认类型


    //比赛状态 类型

    public static final Integer MATCH_WKS = 1;//未开赛
    public static final Integer MATCH_JXZ = 2;//进行中
    public static final Integer MATCH_YWS = 8;//已完赛
    public static final Integer MATCH_YQX = 12;//已取消
    public static final Integer MATCH_ZT = 5;//已延期

    // 比赛 是否已封盘

    public static final Integer NO=0;  //否
    public static final Integer YES=1; //是

    // 比赛相关数据

    public static final String user="bjyj";  //名称
    public static final String secret="20fde75bb3831f669d763e2dcb80c410";  //秘钥
//    public static final String leagusMatchs="1";
    public static final String leagusMatchs="1,108,109,129,130,120,121,142,143,82,84,2906";
    //世界杯,意大利甲级联赛,意大利乙级联赛,德国甲级联赛,德国乙级联赛,西班牙足球甲级联赛,西班牙足球乙级联赛,法国甲级联赛,法国乙级联赛,英格兰超级联赛,英格兰甲级联赛

    //封盘时的match 状态
    public static final String closeMatchs="2,3,4,5,7,8";//上半场，中场，下半场，加时赛，点球决战，已完赛


    //交易类型
    public static final Integer TRANSACTION_BET=1; //投注

    public static final Integer QUOTATION_QUOTATION=2; //投注


    //系统钱包类型

    public static final Integer WALLET_TYPE_USDT=1; //USDT

    public static final Integer WALLET_TYPE_TRX=2; //TRX

}
