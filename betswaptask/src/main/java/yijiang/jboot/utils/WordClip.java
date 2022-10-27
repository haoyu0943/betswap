package yijiang.jboot.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.jfinal.kit.PropKit;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.IWord;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.extractor.impl.TextRankKeyphraseExtractor;
import org.lionsoul.jcseg.extractor.impl.TextRankKeywordsExtractor;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;


public class WordClip {
    public static List<Term> HanLPWordClip(String inputstr){
        HanLpUtil hanLpUtil=new HanLpUtil();
        List<Term> termList= NLPTokenizer.segment(inputstr);
        return termList;
    }

    private static String getProperties(String keystr){
        InputStream inputStream = WordClip.class.getClassLoader().getResourceAsStream("jcseg.properties");
        Properties properties = new Properties();
        String rs="";
        try{
            properties.load(inputStream);
            if(properties.getProperty(keystr)!=null){
                rs=properties.getProperty(keystr);
            }
        }catch (IOException ioE){
            ioE.printStackTrace();
        }finally{
            try {
                inputStream.close();
            }
            catch(IOException ioE1){
                //
            }
        }
        return rs;
    }

    public static List<HashMap> JcsegWordClip(String inputstr){
        List<HashMap> keyphrases=new ArrayList<HashMap>();
        try {
            //创建SegmenterConfig分词配置实例，自动查找加载jcseg.properties配置项来初始化
            SegmenterConfig config = new SegmenterConfig(false);
            Properties properties = new Properties();
            //String dicroot= WordClip.class.getClassLoader().getResource("").getPath()+"lexicon";
            //System.out.println("dicroot="+dicroot);
            //PropKit.use("jcseg.properties");
            //String dicroot=PropKit.get("lexicon.path");
            //读取配置文件中的设置值
            String dicroot=getProperties("lexicon.path");
            System.out.println("dicroot="+dicroot);

            String[] lexPath = {dicroot};
            config.setLexiconPath(lexPath);
            config.setMaxLength(20);
            //config.setPollTime(60);                     //重新加载词库的时间
            config.setICnName(true);                      //是否识别中文名称
            //config.setPPT_MAX_LENGTH(15);               //成对标点文字的最大长度
            //config.setMaxCnLnadron(1);                  //中文姓氏最长
            config.setClearStopwords(false);              //设置不过滤停止词
            config.setAppendCJKSyn(false);                //设置关闭同义词追加
            config.setKeepUnregWords(true);               //是否保留不识别的词条
            //config.setKeepPunctuations("@#%.&+");       //保留在标记中的标点集（不是标记的结尾）
            //config.setAppendCJKPinyin(false);           //是否附加条目的拼音
            //config.setAppendCJKSyn(false);              //是否加载和附加条目的同义词
            //config.setDELIMITER();                      //设置为默认值或空白将使用默认空白作为分隔符

            //config.setEnWordSeg(false);                  //是否做英语分词 在激活此函数之前，jcseg.ensecondseg必须设置为true
            //config.setEnMaxLen(16);                    //英语最长单词
            //config.setEnSecondSeg(false);              //是否对复杂的英语单词进行二次切分
            //config.setEnSecondMinLen(1);               //二次要分段标记的最小长度

            config.setCnNumToArabic(false);              //是否将中文数字转换为阿拉伯数字
            config.setCnFactionToArabic(false);
            config.setLoadCJKPos(true);                 //是否加载词条的词性
            config.setLoadCJKPinyin(false);             //是否加载条目的拼音
            config.setLoadCJKSyn(false);                //是否加载条目的同义词
            config.setLoadEntity(false);                 //是否加载条目的实体
            //创建默认单例词库实现，并且按照config配置加载词库
            ADictionary dic = DictionaryFactory.createSingletonDictionary(config);
            //String preffix = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            //dic.load(dicroot + "/qyzz.lex");
            //dic.load(dicroot + "/qyzz2.lex");


            //依据给定的ADictionary和SegmenterConfig来创建ISegment
            //为了Api往后兼容，建议使用SegmentFactory来创建ISegment对象

            //简易模式：FMM算法，适合速度要求场合。
            //ISegment seg = ISegment.SIMPLE.factory.create(config, dic);
            //复杂模式：MMSEG四种过滤算法，具有较高的歧义去除，分词准确率达到了98.41%。
            //ISegment seg = ISegment.COMPLEX.factory.create(config, dic);
            //检测模式：只返回词库中已有的词条，很适合某些应用场合。
            //ISegment seg = ISegment.DETECT.factory.create(config, dic);
            //检索模式：细粒度切分，专为检索而生，除了中文处理外（不具备中文的人名，数字识别等智能功能）其他与复杂模式一致（英文，组合词等）
            //ISegment seg = ISegment.MOST.factory.create(config, dic);
            //分隔符模式：按照给定的字符切分词条，默认是空格，特定场合的应用
            //ISegment seg = ISegment.DELIMITER.factory.create(config, dic);
            //NLP模式：继承自复杂模式，更改了数字，单位等词条的组合方式，增加电子邮件，大陆手机号码，网址，人名，地名，货币等以及无限种自定义实体的识别与返回。
            ISegment seg = ISegment.NLP.factory.create(config, dic);
            //ISegment seg = ISegment.NGRAM.factory.create(config, dic);


            /*
            //备注：以下代码可以反复调用，seg为非线程安全
            //设置要被分词的文本
            String str = "研究生命起源";
            seg.reset(new StringReader(str));

            //获取分词结果
            IWord word = null;
            while ((word = seg.next()) != null) {
                System.out.println(word.getValue());
            }*/

            //2, 构建TextRankKeyphraseExtractor关键短语提取器
            //TextRankKeyphraseExtractor extractor = new TextRankKeyphraseExtractor(seg);
            //extractor.setMaxIterateNum(120);        //设置pagerank算法最大迭代词库，非必须，使用默认即可
            //extractor.setWindowSize(10);             //设置textRank窗口大小，非必须，使用默认即可
            //extractor.setKeywordsNum(5);           //设置最大返回的关键词个数，默认为10
            //extractor.setMaxWordsNum(10);            //设置最大短语词长，默认为5
            //List<String> keyphrases = extractor.getKeyphrase(new StringReader(str));

            //TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(seg);
            //extractor.setMaxIterateNum(100);        //设置pagerank算法最大迭代次数，非必须，使用默认即可
            //extractor.setWindowSize(5);             //设置textRank计算窗口大小，非必须，使用默认即可
            //extractor.setKeywordsNum(10);           //设置最大返回的关键词个数，默认为10
            //extractor.setAutoFilter(true);

            seg.reset(new StringReader(inputstr));
            IWord word = null;
            while ( (word = seg.next()) != null ) {
                HashMap map=new HashMap();
                map.put("word",word.getValue());
                map.put("type",word.getType());
                keyphrases.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyphrases;
    }
}
