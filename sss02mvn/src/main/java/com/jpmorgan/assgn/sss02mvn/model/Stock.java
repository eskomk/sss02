/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.model;

/**
 *
 * @author esko02
 */
public class Stock
{
    private String stockSymbol = null;
    
    private StockType stockType = StockType.COMMON;

    private double lastDividend = 0.0;

    private double fixedDividend = 0.0;

    private double parValue = 0.0;

    private double tickerPrice = 0.0;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    public double getTickerPrice() {
        return tickerPrice;
    }

    public void setTickerPrice(double tickerPrice) {
        this.tickerPrice = tickerPrice;
    }
    
    public double getDividendYield()
    {
        double dividendYield = -1.0;
        
        if(tickerPrice > 0.0)
        {
            if( stockType==StockType.COMMON)
            {
                dividendYield = lastDividend / tickerPrice;
            }
            else
            {
                // PREFERRED
                dividendYield = (fixedDividend * parValue ) / tickerPrice;
            }
        }
        return dividendYield;
    }
    
    public double getPeRatio()
    {
        double peRatio = -1.0;

        if(tickerPrice > 0.0)
        {
            peRatio = tickerPrice / getDividendYield();	
        }

        return peRatio;
    }

}
