//package com.betswap.market;
//
//import com.betswap.market.app.nft.service.NFTService;
//import com.betswap.market.infrastruture.common.transaction.TxReceipt;
//import com.betswap.market.infrastruture.nft.contractHelper.NFTContractHelper;
//import com.betswap.market.infrastruture.nft.contractHelper.bo.NFTPublishInputBO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigInteger;
//
//
//@SpringBootTest(classes = VirtualGarmentNFTBackendApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
////@ContextConfiguration(locations = {"classpath:application.properties", "classpath:application.yml",
////        "classpath:application-dev.properties","classpath:application-prod.properties","classpath:application-test.properties"
////}) // 这里因为没有把 applicationContext-quartz.xml 配置文件加载进去，所以导致了异常的发生
//public class NFTTest {
//    @Autowired
//    private NFTContractHelper helper;
//    @Autowired
//    private NFTService nftService;
//
//    @Test
//    public void test3() throws Exception{
//        NFTPublishInputBO bo = NFTPublishInputBO.builder()
//                .nftContent("hutest31")
//                .nftIntroduce("fasfas")
//                .nftName("fqfqfq")
//                .nftTemplateId("vsdsvvs")
//                .publishTime("122edwadfasds")
//                .publishNum(BigInteger.valueOf(12))
//                .build();
//        TxReceipt receipt = helper.publish(bo);
//        if (receipt!=null){
//            System.out.println(receipt.BlockHash);
//        }
//    }
//}
