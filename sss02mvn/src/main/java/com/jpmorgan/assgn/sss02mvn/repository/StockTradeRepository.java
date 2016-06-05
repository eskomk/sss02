/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.repository;

import com.jpmorgan.assgn.sss02mvn.model.Trade;
import com.jpmorgan.assgn.sss02mvn.model.Stock;
import java.util.List;
import java.util.Map;

/**
 *
 * @author esko02
 */
public interface StockTradeRepository
{
	/**
         * Record a trade <code>trade</code> to repository
	 * 
	 * @param trade trade object to record.
	 * 
	 * @return <code>true</true> when operation successful. If not successful, <code>false</code>.
	 * 
	 * @throws Exception if something went wrong in recording the trade.
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	
	/**
	 * Returns a list of all trades.
	 * 
	 * @return list of all trades.
	 */
	public List<Trade> getTrades();
	
	/**
	 * 
	 * @param stockSymbol
	 * @return
	 */
	public Stock getStockBySymbol(String stockSymbol);
	
	/**
	 * Gets all the stocks.
	 * 
	 * @return all stocks in a map.
	 */
	public Map<String, Stock> getStocks();

        /**
         * This method is a backup in tests if our trade list does not support clear()-operation.
         * @param trades 
         * @see com.jpmorgan.assgn.sss02mvn.service.StockTradeServiceImplTest#recordTrades()
         */
        public void setTrades(List<Trade> trades);
}
