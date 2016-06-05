/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.model;

import java.util.Date;

/**
 *
 * @author esko02
 */
public class Trade
{
    private double price = 0.0;
    
    private Date timeStamp = null;
    private int shareQuantity = 0;
    private TradeIndicator tradeIndicator = TradeIndicator.BUY;
    
    private Stock stock = null;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getShareQuantity() {
        return shareQuantity;
    }

    public void setShareQuantity(int shareQuantity) {
        this.shareQuantity = shareQuantity;
    }

    public TradeIndicator getTradeIndicator() {
        return tradeIndicator;
    }

    public void setTradeIndicator(TradeIndicator tradeIndicator) {
        this.tradeIndicator = tradeIndicator;
    }
    
}
