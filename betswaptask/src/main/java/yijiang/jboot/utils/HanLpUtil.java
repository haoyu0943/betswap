package yijiang.jboot.utils;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import io.jboot.app.JbootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HanLpUtil {
    HashMap cxmap=new HashMap();

    public void HanLpUtil(){
        loadmap();
    }

    public  void loadmap(){
        cxmap.put("a","形容词");
        cxmap.put("ad","副形词");
        cxmap.put("ag","形容词性语素");
        cxmap.put("al","形容词性惯用语");
        cxmap.put("an","名形词");
        cxmap.put("b","区别词");
        cxmap.put("begin","仅用于始##始");
        cxmap.put("bg","区别语素");
        cxmap.put("bl","区别词性惯用语");
        cxmap.put("c","连词");
        cxmap.put("cc","并列连词");
        cxmap.put("d","副词");
        cxmap.put("dg","辄,俱,复之类的副词");
        cxmap.put("dl","连语");

        cxmap.put("e","叹词");
        cxmap.put("end","仅用于终##终");
        cxmap.put("f","方位词");
        cxmap.put("g","学术词汇");
        cxmap.put("gb","生物相关词汇");
        cxmap.put("gbc","生物类别");
        cxmap.put("gc","化学相关词汇");
        cxmap.put("gg","地理地质相关词汇");
        cxmap.put("gi","计算机相关词汇");
        cxmap.put("gm","数学相关词汇");
        cxmap.put("gp","物理相关词汇");
        cxmap.put("h","前缀");
        cxmap.put("i","成语");
        cxmap.put("j","简称略语");

        cxmap.put("k","后缀");
        cxmap.put("l","习用语");
        cxmap.put("m","数词");
        cxmap.put("mg","数语素");
        cxmap.put("Mg","甲乙丙丁之类的数词");
        cxmap.put("mq","数量词");
        cxmap.put("n","名词");

        cxmap.put("nb","生物名");
        cxmap.put("nba","动物名");
        cxmap.put("nbc","动物纲目");

        cxmap.put("nbp","植物名");
        cxmap.put("nf","食品");
        cxmap.put("ng","名词性语素");

        cxmap.put("nh","医药疾病等健康相关名词");
        cxmap.put("nhd","疾病");
        cxmap.put("nhm","药品");

        cxmap.put("ni","机构相关");
        cxmap.put("nic","下属机构");
        cxmap.put("nis","机构后缀");

        cxmap.put("nit","教育相关机构");
        cxmap.put("nl","名词性惯用语");
        cxmap.put("nm","物品名");

        cxmap.put("nmc","化学品名");
        cxmap.put("nn","工作相关名词");
        cxmap.put("nnd","职业");

        cxmap.put("nnt","职务职称");
        cxmap.put("nr","人名");
        cxmap.put("nr1","复姓");
        cxmap.put("nr2","蒙古姓名");

        cxmap.put("nrf","音译人名");
        cxmap.put("nrj","日语人名");
        cxmap.put("ns","地名");
        cxmap.put("nsf","音译地名");

        cxmap.put("nt","机构团体名");
        cxmap.put("ntc","公司名");
        cxmap.put("ntcb","银行");
        cxmap.put("ntcf","工厂");

        cxmap.put("ntch","酒店宾馆");
        cxmap.put("nth","医院");
        cxmap.put("nto","政府机构");
        cxmap.put("nts","中小学");

        cxmap.put("ntu","大学");
        cxmap.put("nx","字母专名");
        cxmap.put("nz","其他专名");
        cxmap.put("o","拟声词");

        cxmap.put("p","介词");
        cxmap.put("pba","介词把");
        cxmap.put("pbei","介词被");
        cxmap.put("q","量词");

        cxmap.put("qg","量词语素");
        cxmap.put("qt","时量词");
        cxmap.put("qv","动量词");
        cxmap.put("r","代词");

        cxmap.put("rg","代词性语素");
        cxmap.put("Rg","古汉语代词性语素");
        cxmap.put("rr","人称代词");
        cxmap.put("ry","疑问代词");

        cxmap.put("rys","处所疑问代词");
        cxmap.put("ryt","时间疑问代词");
        cxmap.put("ryv","谓词性疑问代词");
        cxmap.put("rz","指示代词");

        cxmap.put("rzs","处所指示代词");
        cxmap.put("rzt","时间指示代词");
        cxmap.put("rzv","谓词性指示代词");
        cxmap.put("s","处所词");

        cxmap.put("t","时间词");
        cxmap.put("tg","时间词性语素");
        cxmap.put("u","助词");
        cxmap.put("ud","助词");

        cxmap.put("ude1","的 底");
        cxmap.put("ude2","地");
        cxmap.put("ude3","得");
        cxmap.put("udeng","等等");

        cxmap.put("udh","的话");
        cxmap.put("ug","过");
        cxmap.put("uguo","过");
        cxmap.put("uj","助词");

        cxmap.put("ul","连词");
        cxmap.put("ule","了 喽");
        cxmap.put("ulian","连小学生都会");
        cxmap.put("uls","来讲 来说 而言 说来");

        cxmap.put("usuo","所");
        cxmap.put("uv","连词");
        cxmap.put("uyy","一样 一般 似的 般");
        cxmap.put("uz","着");

        cxmap.put("uuzhe","着");
        cxmap.put("uzhi","之");
        cxmap.put("v","动词");
        cxmap.put("vd","副动词");

        cxmap.put("vf","趋向动词");
        cxmap.put("vg","动词性语素");
        cxmap.put("vi","不及物动词");
        cxmap.put("vl","动词性惯用语");

        cxmap.put("vn","名动词");
        cxmap.put("vshi","动词'是'");
        cxmap.put("vx","形式动词");
        cxmap.put("vyou","动词'有'");

        cxmap.put("w","标点符号");
        cxmap.put("wb","百分号千分号，全角：％ ‰ 半角：%");
        cxmap.put("wd","逗号，全角：， 半角：");
        cxmap.put("wf","分号，全角：； 半角： ");

        cxmap.put("wh","单位符号，全角：￥ ＄ ￡ ° ℃ 半角：$");
        cxmap.put("wj","句号");
        cxmap.put("wky","右括号");
        cxmap.put("wkz","左括号");

        cxmap.put("wm","冒号");
        cxmap.put("wn","顿号");
        cxmap.put("wp","破折号");
        cxmap.put("ws","省略号");

        cxmap.put("wt","叹号");
        cxmap.put("ww","问号");
        cxmap.put("wyy","右引号");
        cxmap.put("wyz","左引号");

        cxmap.put("x","字符串");
        cxmap.put("xu","网址URL");
        cxmap.put("xx","非语素字");
        cxmap.put("y","语气词");

        cxmap.put("yg","语气语素");
        cxmap.put("z","状态词");
        cxmap.put("zg","状态词");

    }

     public String getPartofspeec(String nature){
         //System.out.println("mapsize="+cxmap.size());
         if(cxmap.size()==0){
             loadmap();
         }
         String cx="";
         if(cxmap.get(nature)!=null){
             cx=cxmap.get(nature).toString();
         }
         //System.out.println("nature="+nature+",cx="+cx);
         return cx;
     }

    /**
     * 判断是否是数字
     */
    public static boolean isNumeric(String inputstr){
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
        return pattern.matcher(inputstr).matches();
    }

    /**
     * 判断是否是手机号码
     */
    public static boolean isMobile(String inputstr){
        if(inputstr.startsWith("1")&&isNumeric(inputstr)&&inputstr.length()==11){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 判断是否是车牌号
     */
    public static boolean isCarid(String inputstr){
        if(inputstr.length()<4||inputstr.length()>10){
            return false;
        }
        String[] Proname={"京","津","沪","渝","蒙","新","藏","宁","桂","港","澳","黑","吉","辽","晋",
                "冀","青","鲁","豫","苏","皖","浙","闽","赣","湘","鄂","粤","琼","甘","陕",
                "黔","滇","川"};
        boolean haveMatch=false;
        for(int i=0;i<Proname.length;i++){
            if(inputstr.startsWith(Proname[i])){
                haveMatch=true;
                break;
            }
        }
        //车牌第二个必然是字母
        if(haveMatch&&isLetter(inputstr.charAt(1))){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 判断是否是字母
     */
    public static boolean isLetter(char c){
        int i=(int)c;
        if((i>=65&&i<=90)||(i>=97&&i<=122)){
            return   true;
        }
        else{
            return   false;
        }
    }

    /**
     * 判断是否是身份证号
     */
    public static boolean isPsnid(String inputstr){
        if(inputstr.length()==18&&isNumeric(inputstr.substring(0, 17))){
            if(isDatestr(inputstr.substring(6,14))){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    /**
     * 判断是否是yyyymmdd的时间字符串
     */
    public static boolean isDatestr(String inputstr){
        if (inputstr.length()==8&&isNumeric(inputstr)){
            int iYear=Integer.parseInt(inputstr.substring(0,4));
            int iMonth=Integer.parseInt(inputstr.substring(4,6));
            int iDay=Integer.parseInt(inputstr.substring(6,8));
            if (iYear>3000 ||iYear<1900||iMonth>12||iMonth<1||iDay>31||iDay<1){
                return false;
            }
            else{
                switch(iDay){
                    case 31:
                        switch(iMonth){
                            case 2:
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                return false;
                        }
                    case 30:
                        if(iMonth==2){
                            return false;
                        }
                    case 29:
                        if(iMonth==2&&(isLeapYear(iYear)==false)){
                            return false;
                        }
                }
                return true;
            }
        }
        else{
            return false;
        }

    }

    /**
     * 判断是否是是否为闰年
     */
    private static boolean isLeapYear(int iYear){
        if((iYear%4)!=0)
            return false;
        else{
            if (((iYear%100)==0)&&((iYear%400)!=0))
                return false;
            else
                return true;
        }
    }

    public static boolean isNetaddr(String ips) {
        if (ips != null &&!ips.equals("")) {
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            if (ips.matches(regex)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static boolean isPassport(String cardId){
       Pattern pattern1 = Pattern.compile("/^[a-zA-Z]{5,17}$/");
       Pattern pattern2= Pattern.compile("/^[a-zA-Z0-9]{5,17}$/");
       return pattern1.matcher(cardId).matches()||pattern2.matcher(cardId).matches();
    }
    //是否港澳台通行
    public static boolean isHKMacao(String cardId){
        Pattern pattern = Pattern.compile("/^[HMhm]{1}([0-9]{10}|[0-9]{8})$/");
        return pattern.matcher(cardId).matches();
    }

    public static void main(String[] args) {

        String strtxt="乙市B分公安局 延长拘留期限通知书 （存根） 乙公字（2021〕020号 案件名称小张盗窃 案件编号2021020 犯罪嫌疑人小张男女25岁 羁押处所B区看守所 执行拘留时间2021年2月7日 延长拘留期限1B 延长拘留期限原因案情复杂 批准人王里 批准时间2021年2月7日 办案人王一王二 办案单位B区分局 填发时间2021年2月7日,填发人小白,乙市B公安局 延长拘留期限通知书 （副本） 乙公字［2021）020号 乙市B区看守所: 因案情复杂, 根据《中华人民共和国刑事诉讼法》第九十+第」一款之规定，决定延长对犯罪嫌疑人小张（性别男，年龄25,于2021年1月28日被执行拘留）的拘留期限，时间从2021年2月7日至2021年之月10日。 （公安局印）2021年2月7日 本通知书已收到。本通知书已收向我宣布。 （看守所印）被拘留人 年月日年月日 此联向被拘留人宣布后附卷 乙市B公安局延长拘留期限通知书（副本） 乙公字〔2021〕020号 乙市B区看守所: 因案情复杂, 根据《中华人民共和国刑事诉讼法》第九十+第」一款之规定，决定延长对犯罪嫌疑人小张（性别男，年龄25,于2021年1月28日被执行拘留）的拘留期限，时间从2021年2月7日至2021年之月10日。 （公安局印） 2021年2月7日 此联交看守所 ";
        HanLpUtil hanLpUtil=new HanLpUtil();
        List<Term> termList= NLPTokenizer.segment(strtxt);
        for(int i=0;i<termList.size();i++){
            System.out.println(termList.get(i).word+","+hanLpUtil.getPartofspeec(termList.get(i).nature.toString())+"("+termList.get(i).nature.toString()+"),"+termList.get(i).offset);
        }

         /*
        System.out.println(hasLetterAndNum("周仁迪"));
        System.out.println(hasLetterAndNum("周仁迪12啊"));
        System.out.println(hasLetterAndNum("阿斯顿发大水z暗室逢灯"));
        System.out.println(hasLetterAndNum("啊撒旦士大夫"));

          */
    }






    /*
    jQuery.validator.addMethod("isHKMacao",function(value,
                                                    element, type) {
        if($(type).val()
                === '3')
        {
            varre
                    = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/;
            returnthis.optional(element)
                    || (re.test(value));
        }else{
            returntrue;
        }
    },"港澳通行证格式不正确");*/



    public static boolean isBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    //是否还有数字或者字母
    public static boolean hasLetterAndNum(String str){
        String Contain_digit=".*[0-9].*";
        String contain_letter=".*[a-zA-Z].*";
        boolean n_flg=str.matches(Contain_digit);
        boolean l_flg=str.matches(contain_letter);
        //System.out.println("n_flg="+n_flg+",l_flg="+l_flg);
        if((n_flg||l_flg)&&str.length()>=15){
            return true;
        }
        else{
            return false;
        }
    }
    //是否包含汉字
    public static boolean hasChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    //是否含有标点或括号等
    public static boolean hasPunctuate(String str) {
        String[] filterStr = new String[]{"，", "。", "、", "？", "！", ".", ",","(",")","（","）"};
        boolean find = false;
        for (String string : filterStr) {
            if (str.contains(string)) {
                find = true;
                break;
            }
        }
        return find;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
