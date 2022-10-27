package com.betswap.market.app.system.service;

import com.betswap.market.infrastruture.common.response.ServerResponse;
import org.springframework.web.multipart.MultipartFile;


public interface SystemService {

    ServerResponse queryDic(String userId, String dicType);

    ServerResponse queryDicNameByCodeAndType(String code, String type);

    ServerResponse findSysParaEntityById(int id);

    ServerResponse findCustomerService(String typeFlg);

    ServerResponse saveArticle(String typeFlag, MultipartFile file, String id, String title, String subtitle, String webUrl, String content, String userId);

    ServerResponse findParaValueByTypeFlag(int typeFlag);

    ServerResponse findMatchStatus();

    ServerResponse findLeagueMatchS();

    ServerResponse findLatestVersion();
}
