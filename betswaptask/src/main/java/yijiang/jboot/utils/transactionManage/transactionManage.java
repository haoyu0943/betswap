package yijiang.jboot.utils.transactionManage;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.IAtom;

import java.sql.SQLException;

public class transactionManage {
    /**
     * 声明式事务示例
     * @param atom
     * @return
     */
    public static boolean tx2(IAtom atom) {
        return Db.tx(atom);
    }

    /**
     * 声明式事务示例
     * @param configName
     * @param atom
     * @return
     */
    public static boolean tx2(String configName, IAtom atom) {
        return Db.use(configName).tx(atom);
    }

    /**
     * 开始事务
     * @throws SQLException
     */
    public static void beginTran() throws SQLException {
        DbKit.getConfig().setThreadLocalConnection(DbKit.getConfig().getConnection());
        DbKit.getConfig().getThreadLocalConnection().setAutoCommit(false);
    }

    /**
     * 开始事务
     * @param configName
     * @throws SQLException
     */
    public static void beginTran(String configName) throws SQLException {
        DbKit.getConfig(configName).setThreadLocalConnection(DbKit.getConfig(configName).getConnection());
        DbKit.getConfig(configName).getThreadLocalConnection().setAutoCommit(false);
    }

    /**
     * 事务回滚
     * @throws SQLException
     */
    public static void rollback() throws SQLException {
        DbKit.getConfig().getThreadLocalConnection().rollback();
        DbKit.getConfig().getThreadLocalConnection().setAutoCommit(true);
//    DbKit.getConfig().removeThreadLocalConnection();
           //         DbKit.getConfig().close(DbKit.getConfig().getThreadLocalConnection());
//        DbKit.getConfig().close(DbKit.getConfig().getConnection());
        DbKit.getConfig().getThreadLocalConnection().close();
    }

    /**
     * 事务回滚
     * @param configName
     * @throws SQLException
     */
    public static void rollback(String configName) throws SQLException {
        DbKit.getConfig(configName).getThreadLocalConnection().rollback();
        DbKit.getConfig(configName).getThreadLocalConnection().setAutoCommit(true);
//    DbKit.getConfig().removeThreadLocalConnection();
           //         DbKit.getConfig().close(DbKit.getConfig().getThreadLocalConnection());
//        DbKit.getConfig().close(DbKit.getConfig().getConnection());
        DbKit.getConfig().getThreadLocalConnection().close();
    }

    /**
     * 事务提交
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        DbKit.getConfig().getThreadLocalConnection().commit();
        DbKit.getConfig().getThreadLocalConnection().setAutoCommit(true);
//        DbKit.getConfig().removeThreadLocalConnection();
//        DbKit.getConfig().close(DbKit.getConfig().getThreadLocalConnection());
//        DbKit.getConfig().close(DbKit.getConfig().getConnection());
        DbKit.getConfig().getThreadLocalConnection().close();
    }

    /**
     * 事务提交
     * @param configName
     * @throws SQLException
     */
    public static void commit(String configName) throws SQLException {
        DbKit.getConfig(configName).getThreadLocalConnection().commit();
        DbKit.getConfig(configName).getThreadLocalConnection().setAutoCommit(true);
        DbKit.getConfig().removeThreadLocalConnection();
           //         DbKit.getConfig().close(DbKit.getConfig().getThreadLocalConnection());
    }

}
