package yijiang.jboot.Core;

import cn.dreampie.quartz.QuartzPlugin;
import com.jfinal.config.*;
import com.jfinal.core.ActionReporter;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import io.jboot.aop.jfinal.JfinalPlugins;
import io.jboot.db.datasource.DataSourceConfigManager;
import yijiang.jboot.TableBind.AutoTableBindPlugin;
import yijiang.jboot.TableBind.TableNameStyle;

import java.util.List;

public class Config extends JbootAppListenerBase {


    @Override
    public void onConstantConfig(Constants constants){

    }

    @Override
    public void onStartFinish() {
        //日志配置
        ActionReporter actionReporter = new ActionReporter();
        ActionToLog4j actionToLog4j = new ActionToLog4j();
        actionReporter.setWriter(actionToLog4j);
        super.onStartFinish();
    }



    @Override
    public void onPluginConfig(JfinalPlugins plugins) {
        //插入任务调度
        plugins.add(new Cron4jPlugin());
        String configFile="quarter.properties";
        plugins.add(new Cron4jPluginCom(configFile));
        //对表的控制
        List<IPlugin> pluginsList=plugins.getPluginList();
        int i=0;
        ActiveRecordPlugin arp=null;
        for(i=0;i<pluginsList.size();i++){
            String actname=pluginsList.get(i).getClass().getName();
            if(actname.equals("com.jfinal.plugin.activerecord.ActiveRecordPlugin")){
                //MappingKit.mapping((ActiveRecordPlugin)pluginsList.get(i));
                arp=(ActiveRecordPlugin)pluginsList.get(i);
                break;
            }
        }
        MappingKit.mapping(arp);

    }


}
