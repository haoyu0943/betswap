//package com.betswap.market;
//
//import com.betswap.market.app.nft.service.NFTService;
//import com.betswap.market.client.nft.converter.RawTransactionConverter;
//import com.betswap.market.infrastruture.common.transaction.TxReceipt;
//import com.betswap.market.infrastruture.nft.contract.NFTContract;
//import com.betswap.market.infrastruture.nft.contractHelper.NFTContractHelperModify;
//import com.betswap.market.infrastruture.nft.contractHelper.bo.NFTPublishInputBO;
//import com.betswap.market.infrastruture.nft.dao.RawTransactionDao;
//import com.betswap.market.infrastruture.nft.entity.RawTransactionEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.fisco.bcos.sdk.abi.ABICodec;
//import org.fisco.bcos.sdk.abi.ABICodecException;
//import org.fisco.bcos.sdk.crypto.CryptoSuite;
//import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
//import org.fisco.bcos.sdk.crypto.signature.ECDSASignatureResult;
//import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
//import org.fisco.bcos.sdk.model.CryptoType;
//import org.fisco.bcos.sdk.transaction.codec.encode.TransactionEncoderService;
//import org.fisco.bcos.sdk.transaction.model.gas.DefaultGasProvider;
//import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;
//import org.fisco.bcos.sdk.utils.Numeric;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigInteger;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//@SpringBootTest(classes = VirtualGarmentNFTBackendApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@Slf4j
//public class SendTransactionTest {
//    @Autowired
//    private NFTService nftService;
//    @Autowired
//    private NFTContractHelperModify helperModify;
//    @Value(value = "${fisco.groupId}")
//    private String chainId;
//    @Value(value = "${fisco.groupId}")
//    private Long groupId;
//    @Autowired
//    private RawTransactionDao rawTransactionDao;
//
//    @Test
//    public void testSendTxWithOptionalPrivateKey() throws ABICodecException {
//        //--------------- ????????????????????????keyPair
//        // ??????
//        String privateKey = "b442b59c3ab7a30697b8dd7516506d41101365840c114e8996f5508905c93e9a";
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        // ???????????????
//        CryptoKeyPair myKeyPair = cryptoSuite.createKeyPair(privateKey);
//        System.out.println("address:"+myKeyPair.getAddress());
//        //---------------- ????????????????????????
//        NFTPublishInputBO bo = NFTPublishInputBO.builder()
//                .nftContent("QmQ6JotoZY5y7mc4kwBvgMq5T6SsehUvxzUsrPjVhYTE2Y")
//                .nftIntroduce("??????")
//                .nftName("??????")
//                .nftTemplateId("QmQ6JotoZY5y7mc4kwBvgMq5T6SsehUvxzUsrPjVhYTE2Y")
//                .publishTime("2021-09-26 01:17:31")
//                .publishNum(BigInteger.valueOf(50))
//                .build();
//        //----------------
//        //??????????????????
//        //1?????????????????????????????????
//        String encodedTransaction = helperModify.generatePublishEncodedTransaction(bo);
//
//        //2?????????rawTransaction???????????????????????????????????????????????????????????????????????????????????????????????????
//        // randomId ????????????
//        BigInteger randomId = new BigInteger("1309160300493623609141836482992083756979515117160528982073928254523888398385");
//        RawTransaction rawTransaction = helperModify.buildRawTransaction(encodedTransaction,randomId);
//
//        //3???????????????????????????
//        String dataWithOutSign = helperModify.encodeBytesModify(rawTransaction);
//
//        //4?????????????????????????????????????????? ???????????????????????????????????????????????? ?????????????????????
//        String signedData = helperModify.sign(dataWithOutSign,privateKey);
//
//        //5???????????????????????????push????????? ??????verify?????????????????????????????????
//        TxReceipt txReceipt = helperModify.push(myKeyPair.getHexPublicKey(),signedData,rawTransaction);
//        printTxReceipt(txReceipt);//????????????
//    }
//
//    //???????????????????????????????????????
//    @Test
//    public void testSendTx() throws ABICodecException {
//        // -------?????????????????????--------
//        // ??????
//        String privateKey = "a46218750a0479a409b7ac995fa41c81e55e11690df7c67db71b7049616e507f";
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        // ???????????????
//        CryptoKeyPair myKeyPair = cryptoSuite.createKeyPair(privateKey);
//        System.out.println("address:"+myKeyPair.getAddress());
//        System.out.println("publicKey: "+myKeyPair.getHexPublicKey());
//        //----------------------------
//        // ?????????
//        //????????????????????????
//        NFTPublishInputBO bo = NFTPublishInputBO.builder()
//                .nftContent("hu111")
//                .nftIntroduce("intro111")
//                .nftName("name111")
//                .nftTemplateId("series1123")
//                .publishTime("2022")
//                .publishNum(BigInteger.valueOf(3))
//                .build();
//        //????????????????????????
//        String encodeTransaction = helperModify.generatePublishEncodedTransaction(bo);
//        //??????rawTransaction
//        BigInteger randomId = nftService.generateRandomId();
//        RawTransaction rawTransaction = helperModify.buildRawTransaction(encodeTransaction,randomId);
//        //?????????????????????
//        String dataWithOutSign = helperModify.getDataWithoutSign(rawTransaction);
//
//        //??????
//        String signedData = helperModify.sign(dataWithOutSign,privateKey);
//        //???????????????
//        TxReceipt txReceipt = helperModify.push(myKeyPair.getHexPublicKey(),signedData,rawTransaction);
//        //??????????????????
//        printTxReceipt(txReceipt);
//    }
//
//    @Test
//    public void sendTx(){
//        String privateKey = "04a5838f48a32b5278c2df8b69e546c845b65169b1cfe605350350141376553ce89b91db8acae55d395e7b58e7266ba430260b88dc4caf3a725880a361a7209685";
//        String signedData = "925907a4e27df22447ea0f6f4faccd40c95a453785fe186d2d2297cdd43d8e8344f4bd65f09f74c81a40f3438a3a8e6e6f515b81be9c62cd262e7b0574f98d3b01";
//        RawTransactionEntity entity = rawTransactionDao.findByDraftId("QmYBrRLWtEeH2smaLQs9Ln1SpF5GgQRsnhaRGUYYg93YeY");
//        RawTransaction rawTransaction = RawTransactionConverter.entity2dto(entity);
//        //???????????????
//        TxReceipt txReceipt = helperModify.push(privateKey,signedData,rawTransaction);
//    }
//
//    public void printTxReceipt(TxReceipt txReceipt){
//        System.out.println(txReceipt.getBlockHash());
//        System.out.println(txReceipt.getBlockNumber());
//        System.out.println(txReceipt.getTransactionHash());
//        System.out.println(txReceipt.getOutput());
//    }
//
//    @Test
//    //??????abi???function name???params????????????
//    public void testGenerateRawData() throws ABICodecException {
//        //generate params
//        String abi = NFTContract.ABI;
//        String functionName = "publish";
//        NFTPublishInputBO bo = NFTPublishInputBO.builder()
//                .nftContent("hu111")
//                .nftIntroduce("intro111")
//                .nftName("name111")
//                .nftTemplateId("series112")
//                .publishTime("2022")
//                .publishNum(BigInteger.valueOf(3))
//                .build();
//        List<Object> params = bo.toArgs();
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        ABICodec abiCodec = new ABICodec(cryptoSuite);
//        String data = abiCodec.encodeMethod(abi, functionName, params);
//        System.out.println("rawData:"+data);
//    }
//
//
//    @Test
//    public void testSignRawData(){
//        Date date = new Date();// ??????????????????
//        SimpleDateFormat sdf = new SimpleDateFormat();// ???????????????
//        // ???????????????????????????
//        String encodedTransaction = "0xba317eb300000000000000000000000000000000000000000000000000000000000000c0000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000180000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001c00000000000000000000000000000000000000000000000000000000000000009736572696573313132000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000076e616d65313131000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008696e74726f3131310000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000005687531313100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000043230323200000000000000000000000000000000000000000000000000000000";
//        // ??????
//        String privateKey = "19f6da8d14826e11fec826696f4511af623711ef88b3dec5f3225834d4f472ae";
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        //??????TransactionEncoderService
//        TransactionEncoderService encoderService = new TransactionEncoderService(cryptoSuite);
//        // ???????????????
//        CryptoKeyPair myKeyPair = cryptoSuite.createKeyPair(privateKey);
//        System.out.println("address:"+myKeyPair.getAddress());
//
//        BigInteger randomId = nftService.generateRandomId();
//        RawTransaction rawTransaction = helperModify.buildRawTransaction(encodedTransaction,randomId);
//
////        byte[] encodedTransaction = encoderService.encode(rawTransaction, null);
//        // ????????????????????????????????????
//        String hashMessageStr = cryptoSuite.hash(encodedTransaction);
//        System.out.println("hashMessageStr: " + hashMessageStr);
//        // ????????????????????????
//        SignatureResult signedTx = cryptoSuite.sign(hashMessageStr, myKeyPair);
//
//        //???????????? signedTx
//        String signedData = signedTx.convertToString();
//        System.out.println("signedData:"+signedData);
//        //??????
//        ECDSASignatureResult  signatureResult = new ECDSASignatureResult(signedData);
//        String signedData2 = signatureResult.convertToString();
//        System.out.println("signedData2:"+signedData2);
//        if(signedData.equals(signedData2)){
//            System.out.println("equal!!!");
//        }
////        new SignatureResult(signedData)
//        // ???????????????????????????????????????,?????????????????? rawTransaction ??? signedTx
//        byte[] signedTransaction = encoderService.encode(rawTransaction,signatureResult);
//        // ????????????????????????
//        String signedTransactionStr = Numeric.toHexString(signedTransaction);
//        System.out.println("signedTransactionStr: " + signedTransactionStr);
//        Date date2 = new Date();// ??????????????????
//        System.out.println(date2.getTime()-date.getTime());
//        //????????????
//
//        String res = createSignedTransaction(helperModify.getContractAddress(),encodedTransaction,myKeyPair);
//        System.out.println("res:"+res);
//    }
//
////    public RawTransaction getRawTransaction(String encodedTransaction){
////        // ???????????????
////        BigInteger randomId = new BigInteger(250, new SecureRandom());
////        BigInteger blockLimit = new BigInteger("1000");
////        //??????RawTransaction
////        RawTransaction rawTransaction =
////                RawTransaction.createTransaction(
////                        randomId,
////                        DefaultGasProvider.GAS_PRICE,
////                        DefaultGasProvider.GAS_LIMIT,
////                        blockLimit,
////                        helperModify.getContractAddress(),
////                        BigInteger.ZERO,
////                        encodedTransaction,
////                        new BigInteger(chainId),
////                        BigInteger.valueOf(groupId),
////                        "");
////        return rawTransaction;
////    }
//
//
//
//    public String createSignedTransaction(String to, String data, CryptoKeyPair cryptoKeyPair) {
//        // ???????????????
//        BigInteger randomId =   nftService.generateRandomId();
//        BigInteger blockLimit = new BigInteger("1000");
//        //??????RawTransaction
//        RawTransaction rawTransaction =
//                RawTransaction.createTransaction(
//                        randomId,
//                        DefaultGasProvider.GAS_PRICE,
//                        DefaultGasProvider.GAS_LIMIT,
//                        blockLimit,
//                        to,
//                        BigInteger.ZERO,
//                        data,
//                        new BigInteger(chainId),
//                        BigInteger.valueOf(groupId),
//                        "");
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        TransactionEncoderService encoderService = new TransactionEncoderService(cryptoSuite);
//        return encoderService.encodeAndSign(rawTransaction, cryptoKeyPair);
//    }
//
//    @Test
//    public void testMyTransactionEncoderService() throws ABICodecException {
//        //---------------
//        // ???????????????????????????
////        String encodedTransaction = "0xba317eb300000000000000000000000000000000000000000000000000000000000000c0000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000180000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001c00000000000000000000000000000000000000000000000000000000000000009736572696573313132000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000076e616d65313131000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008696e74726f3131310000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000005687531313100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000043230323200000000000000000000000000000000000000000000000000000000";
//        // ??????
//        String privateKey = "19f6da8d14826e11fec826696f4511af623711ef88b3dec5f3225834d4f472ae";
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        // ???????????????
//        CryptoKeyPair myKeyPair = cryptoSuite.createKeyPair(privateKey);
//        System.out.println("address:"+myKeyPair.getAddress());
//
//        NFTPublishInputBO bo = NFTPublishInputBO.builder()
//                .nftContent("hu111")
//                .nftIntroduce("intro111")
//                .nftName("name111")
//                .nftTemplateId("series112")
//                .publishTime("2022")
//                .publishNum(BigInteger.valueOf(3))
//                .build();
//        String encodedTransaction = helperModify.generatePublishEncodedTransaction(bo);
//        //----------------
//
////        RawTransaction rawTransaction = getRawTransaction(encodedTransaction);
//        BigInteger randomId = nftService.generateRandomId();
//        RawTransaction rawTransaction = helperModify.buildRawTransaction(encodedTransaction,randomId);
//        //-----ok-------
//        String dataWithOutSign = helperModify.encodeBytesModify(rawTransaction);
//
////        MyTransactionEncoderService myTransactionEncoderService = new MyTransactionEncoderService(cryptoSuite);
//        //??????encodeTransaction
//
//
////        byte[] hash = myTransactionEncoderService.encodeBytesModify(rawTransaction);
//        //todo
//        //???hash??????string?????????????????????????????????
////        String dataWithOutSign = Hex.toHexString(hash);
////        byte[] hash2 = Hex.decode(dataWithOutSign);
////        //??????????????????
////        if(Arrays.equals(hash,hash2)){
////            System.out.println("equal!!!");
////        }
//
//        //??????
////        SignatureResult signatureResult = helperModify.myTransactionEncoderService.sign(hash2,myKeyPair);
//        String signedData = helperModify.sign(dataWithOutSign,privateKey);
//        //???????????? signedTx
////        String signedData = signatureResult.convertToString();
//        System.out.println("signedData:"+signedData);
//
////        //??????????????????
////        if(helperModify.verify(myKeyPair.getHexPublicKey(),dataWithOutSign,signedData)){
////            System.out.println("verify pass!!!");
////        }
////
////        //????????????
////        ECDSASignatureResult signatureResult2 = new ECDSASignatureResult(signedData);
////        String encodeSignedStr = helperModify.myTransactionEncoderService.encodeSignedStr(rawTransaction,signatureResult2);//???????????????rawTransaction
////        System.out.println("encodeSignedStr:"+encodeSignedStr);
//
//
//        //????????????
////        sendToWebase(encodeSignedStr);
//
//        TxReceipt txReceipt = helperModify.push(myKeyPair.getHexPublicKey(),signedData,rawTransaction);
//        printTxReceipt(txReceipt);
//    }
//
//    public void sendToWebase(String encodeSignedStr){
//        TxReceipt txReceipt = helperModify.sendToWeBase(encodeSignedStr);
//        printTxReceipt(txReceipt);
//    }
//
//
//    @Test
//    public void testAddress(){
//        // ??????
//        String privateKey = "19f6da8d14826e11fec826696f4511af623711ef88b3dec5f3225834d4f472ae";
//        // ECDSA ????????????
//        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//        // ???????????????
//        CryptoKeyPair myKeyPair = cryptoSuite.createKeyPair(privateKey);
//        System.out.println("address:"+myKeyPair.getAddress());
//        System.out.println("publicKey: "+myKeyPair.getHexPublicKey());
//    }
//
//
//}
