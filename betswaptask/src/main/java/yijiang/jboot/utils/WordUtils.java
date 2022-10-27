package yijiang.jboot.utils;

//import com.spire.doc.Document;
//import com.spire.doc.FileFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.http.util.TextUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

public class WordUtils {

    private static Configuration configuration = null;
    private static final String templateFolder = WordUtils.class.getClassLoader().getResource("").getPath()+"templates/word/";
    static {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  @Description:导出word，传入request，response，map就是值，title是导出问卷名，ftl是你要使用的模板名
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, Map map, String title, String ftlFile) throws Exception {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Template freemarkerTemplate = configuration.getTemplate(ftlFile);
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            file = createDocFile(map,freemarkerTemplate);
            fin = new FileInputStream(file);
            String fileName = title + ".doc";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename="
                    +java.net.URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            byte[] buffer = new byte[512];
            int bytesToRead = -1;
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        }finally {
            if(fin != null){
                fin.close();
            }
            if(out != null) {
                out.close();
            }
            if(file != null) {
                file.delete();
            }
        }
    }


    /**
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：test.ftl
     * @param filePath 文件生成的目标路径，例如：D:/wordFile/
     * @param fileName 生成的文件名称，例如：test.doc
     */
    public static void createWord(Map dataMap,String templateName,String filePath,String fileName) throws IOException, TemplateException {
        Writer out =null;
        try {
            //创建配置实例
             configuration = new Configuration();

            //设置编码
            configuration.setDefaultEncoding("utf-8");

            //ftl模板文件统一放至 com.lun.template 包下面
            try {
                configuration.setDirectoryForTemplateLoading(new File(templateFolder));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //获取模板
            Template template = configuration.getTemplate(templateName);

            //输出文件
            File outFile = new File(filePath+File.separator+fileName);

            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }

            //将模板和数据模型合并生成文件
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));


            //生成文件
            template.process(dataMap, out);


        } finally {
            if (out != null) {
                //关闭流
                out.flush();
                out.close();
            }
        }
    }


    /**
     *  @Description:创建doc文件
     */
    private static File createDocFile(Map<?, ?> dataMap, Template template) {
        File file = new File("init.doc");
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            template.process(dataMap, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 合并word 文件
     * @param fpaths 原始文件路径  如：E:\test\test2.doc 集合
     * @param resultPath 返回文件路径如：E:\test\out.doc
     * @param processPath 过程文件路径如：E:\test\out1.doc
     * @return
     */
//    public static boolean mergeFiles(List<String> fpaths, String resultPath,String processPath ) {
//        if (fpaths == null || fpaths.size() < 1 || TextUtils.isEmpty(resultPath)) {
//            return false;
//        }
//        if (fpaths.size() == 1) {
//            return new File(fpaths.get(0)).renameTo(new File(resultPath));
//        }
//
//        Document document = new Document(fpaths.get(0));
//
//        for (int i = 1; i < fpaths.size(); i++) {
//            if (TextUtils.isEmpty(fpaths.get(i))) {
//                return false;
//            }
//            document.insertTextFromFile(fpaths.get(i), FileFormat.Docx_2010);
//        }
//        //保存文档
//        document.saveToFile(processPath, FileFormat.Docx_2010);
//        try {
//            //重新读取生成的文档
//            InputStream is = new FileInputStream(processPath);
//            XWPFDocument doc = new XWPFDocument(is);
//            //以上Spire.Doc 生成的文件会自带警告信息，这里来删除Spire.Doc 的警告
//            doc.removeBodyElement(0);
//            //输出word内容文件流，新输出路径位置
//            OutputStream os=new FileOutputStream(resultPath);
//            doc.write(os);
//            //TODO  测试结束后放开
////            //删除过程文件
////            new File(processPath).delete();
////            //删除原文件
////            for (int i = 0; i < fpaths.size(); i++) {
////                new File(fpaths.get(i)).delete();
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    /**
     * word 合并不适用 ，txt 可以
     * @param fpaths
     * @param resultPath
     * @return
     */
    public static boolean mergeOtherFiles(List<String> fpaths, String resultPath) {
        if (fpaths == null || fpaths.size() < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.size() == 1) {
            return new File(fpaths.get(0)).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.size()];
        for (int i = 0; i < fpaths.size(); i++) {
            files[i] = new File(fpaths.get(i));
            if (TextUtils.isEmpty(fpaths.get(i)) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            int bufSize = 1024;
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile));
            byte[] buffer = new byte[bufSize];

            for (int i = 0; i < fpaths.size(); i++) {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files[i]));
                int readcount;
                while ((readcount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readcount);
                }
                inputStream.close();
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < fpaths.size(); i++) {
            files[i].delete();
        }

        return true;
    }

}
