//package com.betswap.market.infrastruture.utils.helper.encode;
//
//import org.fisco.bcos.sdk.crypto.CryptoSuite;
//import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
//import org.fisco.bcos.sdk.crypto.signature.Signature;
//import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
//import org.fisco.bcos.sdk.transaction.codec.encode.TransactionEncoderService;
//import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;
//import org.fisco.bcos.sdk.transaction.signer.TransactionSignerFactory;
//import org.fisco.bcos.sdk.transaction.signer.TransactionSignerInterface;
//import org.fisco.bcos.sdk.utils.Numeric;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class MyTransactionEncoderService extends TransactionEncoderService {
//
//    protected static Logger logger = LoggerFactory.getLogger(MyTransactionEncoderService.class);
//    private final Signature signature;
//    private final TransactionSignerInterface transactionSignerService;
//    private final CryptoSuite cryptoSuite;
//
//    public MyTransactionEncoderService(CryptoSuite cryptoSuite) {
//        super(cryptoSuite);
//        this.cryptoSuite = cryptoSuite;
//        this.signature = cryptoSuite.getSignatureImpl();
//        this.transactionSignerService = TransactionSignerFactory.createTransactionSigner(signature);
//    }
//
//    public byte[] encodeBytesModify(RawTransaction rawTransaction) {
////        byte[] hash = encodeAndHashBytes(rawTransaction, cryptoKeyPair);
//////        SignatureResult result = transactionSignerService.sign(hash, cryptoKeyPair);
////        return hash;
//        return cryptoSuite.hash(encode(rawTransaction, null));
//    }
//
//    public SignatureResult sign(byte[] hash,CryptoKeyPair cryptoKeyPair){
//        return transactionSignerService.sign(hash, cryptoKeyPair);
//    }
//
//
//
//    public String encodeSignedStr(RawTransaction rawTransaction,SignatureResult result){
//        byte[] data = encode(rawTransaction, result);
//        return Numeric.toHexString(data);
//    }
//
//
//}
