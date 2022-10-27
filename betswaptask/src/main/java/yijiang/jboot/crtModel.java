package yijiang.jboot;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class crtModel {
    public static void main(String[] args) {
        PropKit.use("jboot.properties");
        String url= PropKit.get("jboot.datasource.url");
        String user=PropKit.get("jboot.datasource.user");
        String pwd=PropKit.get("jboot.datasource.password").trim();
        String drive=PropKit.get("jboot.datasource.driverClassName");
        DruidPlugin druidPlugin = new DruidPlugin(url, user, pwd);
        druidPlugin.setDriverClass(drive);
        druidPlugin.start();
        /*
        Connection con;
        try {
           Class.forName(drive);
        }
        catch(ClassNotFoundException e){
           e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url,user,pwd);
            System.out.println("数据库连接成功");
         }
        catch (SQLException e) {
             e.printStackTrace();
        }
        */
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "yijiang.jboot.model";
        // base model 所使用的包名
        String baseModelPackageName = modelPackageName + ".base";
        // base model 文件保存路径
        String baseModelOutputDir = "e:/test/" + baseModelPackageName.replace('.', '/');
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";
        System.out.println("输出路径："+ baseModelOutputDir);
        Generator gen = new Generator(druidPlugin.getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        //Generator gen = new Generator((DataSource), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        gen.setGenerateDaoInModel(true);
        gen.setDialect(new MysqlDialect());
        gen.setGenerateRemarks(true);
        gen.generate();
    }
}
