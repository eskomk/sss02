/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.service;

import com.jpmorgan.assgn.sss02mvn.model.Trade;

/**
 *
 * @author esko02
 */
public interface StockTradeService
{
    public double calculateDividendYield(String pstocksymbol) throws Exception;
    public double calculatePERatio(String pstocksymbol) throws Exception;
    public double calculateStockPrice(String pstocksymbol, int minutes) throws Exception;
    public double calculateStockPriceLast15mins(String pstocksymbol) throws Exception;
    
    public double calculateGBCEAllShareIndex() throws Exception;
    
    public boolean recordTrade(Trade ptrade) throws Exception;

}
