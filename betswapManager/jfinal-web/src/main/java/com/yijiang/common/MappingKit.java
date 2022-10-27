package com.yijiang.common;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.yijiang.model.*;

public class MappingKit {
    public static void mapping(ActiveRecordPlugin arp) {
        arp.addMapping("sys_user", "id", SysuserModel.class);
        arp.addMapping("sys_log", "id", SysLogModel.class);
        arp.addMapping("sys_user_rankset", "id", SysUserRankModel.class);
        arp.addMapping("sys_role_bet", "id", RoleTbtModel.class);
        arp.addMapping("advertisement", "id", AdvertisementModel.class);
        arp.addMapping("sys_msg", "id", SysmsgModel.class);
        arp.addMapping("sys_article", "id", SysArticleModel.class);
        arp.addMapping("sys_protocol", "id", SysProtocolModel.class);

        arp.addMapping("dictionary", "id", DictionaryModel.class);

        arp.addMapping("user", "id", UserModel.class);
        arp.addMapping("user_wallet", "id", UserWalletModel.class);
        arp.addMapping("revenue_record", "id", RevenueRecordModel.class);
    }
}
