package yijiang.jboot.TableBind;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yijiang.jboot.utils.StringKit;

import javax.sql.DataSource;
import java.util.List;

public class AutoTableBindPlugin extends ActiveRecordPlugin {
    private TableNameStyle tableNameStyle;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public AutoTableBindPlugin(DataSource dataSource) {
        super(dataSource);
    }

    public AutoTableBindPlugin(IDataSourceProvider dataSourceProvider, TableNameStyle tableNameStyle) {
        super(dataSourceProvider);
        this.tableNameStyle = tableNameStyle;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean start() {
        try {
            List<Class> modelClasses = ClassSearcher.findClasses(Model.class);
            logger.debug("modelClasses.size  {}",modelClasses.size());
            TableBind tb = null;
            for (Class modelClass : modelClasses) {
                tb = (TableBind) modelClass.getAnnotation(TableBind.class);
                if (tb == null) {
                    this.addMapping(tableName(modelClass), modelClass);
                    logger.debug("auto bindTable: addMapping({}, {})", tableName(modelClass), modelClass.getName());
                } else {
                    //if (StringKit.notBlank(tb.pk())) {
                    if (tb.pk()!=null&&!tb.pk().equals("")) {
                        this.addMapping(tb.name(), tb.pk(), modelClass);
                        logger.debug("auto bindTable: addMapping({}, {},{})", new Object[]{tb.name(),tb.pk(), modelClass.getName()});
                    } else {
                        this.addMapping(tb.name(), modelClass);
                        logger.debug("auto bindTable: addMapping({}, {})", tb.name(), modelClass.getName());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return super.start();
    }

    @Override
    public boolean stop() {
        return super.stop();
    }

    private String tableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        if (tableNameStyle == TableNameStyle.UP) {
            tableName = tableName.toUpperCase();
        } else if (tableNameStyle == TableNameStyle.LOWER) {
            tableName = tableName.toLowerCase();
        } else {
            tableName = StringKit.firstCharToLowerCase(tableName);
        }
        return tableName;
    }
}
