package com.yijiang.core;

import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.yijiang.common.ConstantList;
import com.yijiang.common.MappingKit;
import com.jfinal.config.*;
import com.yijiang.controller.*;

public class Config extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        //JspRender.setSupportActiveRecord(true);
        loadPropertyFile("jdbc.properties");
        me.setViewType(ViewType.JSP);
        //默认10M,此处设置为最大1024M
        me.setMaxPostSize(1024*1024*1024);
        me.setBaseDownloadPath(PathKit.getWebRootPath()+"/");
        me.setError404View("/404.jsp");
        me.setError500View("/500.jsp");
        //PropKit.use("IpConfig.txt");
    }

    @Override
    public void configRoute(Routes me) {
       //me.add("/user", UserController.class,"/user");
        me.add("/login", LoginController.class, "/");
        me.add("/system", SystemController.class,"/system");
        me.add("/log",  SyslogController.class, "/system");
        me.add("/statistic", StatisticController.class,   "/stat");
        me.add("/sysset", SyssetController.class,   "/sysset");
        me.add("/chart", ChartController.class,   "/chart");
        me.add("/message", MessageController.class,   "/message");
        me.add("/export", ExportController.class,   "/export");
        me.add("/register", UserregController.class,   "/register");
    }

    @Override
    public void  configEngine(Engine me){
        me.addSharedObject("ctx", JFinal.me().getContextPath());
        me.setDevMode(true);
    }

    @Override
    public void configPlugin(Plugins me) {
        String url=getProperty("jdbcUrl");
        String user=getProperty("user");
        String pwd=getProperty("password").trim();
        //System.out.println("url="+url);
        //System.out.println("user="+user);
        //System.out.println("pwd="+pwd);
        C3p0Plugin c3p0Plugin = new C3p0Plugin(url,user,pwd);
                //PropAESKit.AESDncode(ConstantList.ENCODE_RULES,user),
                //PropAESKit.AESDncode(ConstantList.ENCODE_RULES,pwd));
        c3p0Plugin.setInitialPoolSize(4);
        c3p0Plugin.setMaxIdleTime(1800);
        //默认的为10--100
        c3p0Plugin.setMaxPoolSize(20);
        c3p0Plugin.setMinPoolSize(4);
        c3p0Plugin.setDriverClass(getProperty("driverClass"));

        me.add(c3p0Plugin);
        //QuartzPlugin quartzPlugin = new QuartzPlugin();//定时任务
        //me.add(quartzPlugin);//定时任务
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setTransactionLevel(2);
        arp.setContainerFactory(new CaseInsensitiveContainerFactory());// 配置大小写不敏感
        //配置数据库方言
        //arp.setDialect(new OracleDialect());
        arp.setDialect(new MysqlDialect());
        arp.setShowSql(true);
        me.add(arp);
        //me.add(new EhCachePlugin(PathKit.getRootClassPath()+"/ehcache.xml"));
        MappingKit.mapping(arp);//全部的映射在这里配置

    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new LoginInterceptor());
        me.add(new SessionInViewInterceptor(true));
        //me.add(new EInterceptorAuth());//鉴权
        //方法级别的事务管理,主要对插入和修改进行事务管理
        me.add(new TxByMethodRegex("(.*save*.*|.*Save*.*|.*update*.*|.*Update*.*|.*merge*.*|.*Merge*.*|.*delete*.*|.*Delete*.*|.*edit*.*|.*Edit*.*|.*insert*.*|.*Insert*.*|.*remove*.*|.*Remove*.*)"));

    }

   @Override
   public void configHandler(Handlers me) {
       me.add(new JspSkipHandler());//拦截Jsp
       //me.add(new ContextPathHandler("ctxPath"));
       //me.add(new WebSocketHandler());
       //me.add(new WsPropHandler());
       //me.add(new FileUploadWsHandler());
   }

    @Override
    public void afterJFinalStart() {
        /**
         * 用于指定在JSP/jstl中，对待合体后的Bean仍然采用老版本对待Model的方式输出数据，
         * 也即使用 Model.get(String attr)而非Bean的getter方法输出数据，有利于在关联查询时输出无 getter 方法的字段值
         */
        //ModelRecordElResolver.setResolveBeanAsModel(true);
        JFinal.me().getServletContext().setAttribute(ConstantList.USER_ON_LINE_COUNT, 0);
    }


    @Override
    public void beforeJFinalStop() {

        super.beforeJFinalStop();
    }
}
