//package com.betswap.market.infrastruture.common.transaction;
//
//
//import org.fisco.bcos.sdk.model.TransactionReceipt;
//
////兼容本地搭建fisco的交易回执TransactionReceipt和bsn的交易回执ResKeyEscrow
//public class TxReceipt {
//    public String BlockHash;
//    public String BlockNumber;
//    public String TransactionHash;
//    public String Output;
//
//    public TxReceipt(){
//        setBlockHash("");
//        setBlockNumber("");
//        setTransactionHash("");
//        setOutput("");
//    }
//
//
//    public TxReceipt(TransactionReceipt receipt){
//        setBlockHash(receipt.getBlockHash());
//        setBlockNumber(receipt.getBlockNumber());
//        setTransactionHash(receipt.getTransactionHash());
//        setOutput(receipt.getOutput());
//    }
//
//    public void setBlockHash(String blockHash) {
//        BlockHash = blockHash;
//    }
//
//    public void setBlockNumber(String blockNumber) {
//        BlockNumber = blockNumber;
//    }
//
//    public void setTransactionHash(String transactionHash) {
//        TransactionHash = transactionHash;
//    }
//
//    public void setOutput(String output) {
//        Output = output;
//    }
//
//    public String getBlockHash() {
//        return BlockHash;
//    }
//
//    public String getBlockNumber() {
//        return BlockNumber;
//    }
//
//    public String getTransactionHash() {
//        return TransactionHash;
//    }
//
//    public String getOutput() {
//        return Output;
//    }
//}
