package com.pipe09.OnlineShop.Exception;

public class StockLackException extends RuntimeException{
    public StockLackException(){

    }

    public StockLackException(String msg){
        super(msg);
    }
}
