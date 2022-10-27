package com.betswap.market.infrastruture.config;

public class BlockchainException extends Exception{

    //无参构造方法
    public BlockchainException(){

        super();
    }

    //有参的构造方法
    public BlockchainException(String message){
        super(message);

    }

    // 用指定的详细信息和原因构造一个新的异常
    public BlockchainException(String message, Throwable cause){

        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public BlockchainException(Throwable cause) {

        super(cause);
    }


}
