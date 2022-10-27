package yijiang.jboot.Core;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import yijiang.jboot.model.*;

public class MappingKit {
    public static void mapping(ActiveRecordPlugin arp) {
        arp.addMapping("user_wallet", "id", UserWallet.class);
        arp.addMapping("revenue_record", "id", RevenueRecord.class);
    }
}
