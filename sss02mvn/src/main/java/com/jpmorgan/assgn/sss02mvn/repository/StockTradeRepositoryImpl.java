/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.repository;

import com.jpmorgan.assgn.sss02mvn.model.Stock;
import com.jpmorgan.assgn.sss02mvn.model.Trade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * 
 * @author esko02
 */
public class StockTradeRepositoryImpl implements StockTradeRepository
{
    private static final Logger logger = Logger.getLogger(StockTradeRepositoryImpl.class);
    
    private Map<String, Stock> stocks = null;
    private List<Trade> trades = null;
    
    public StockTradeRepositoryImpl()
    {
        // EMK 20160604
        // Back to original (1.8 --> 1.6)
	trades = new ArrayList<Trade>();
	stocks = new HashMap<String, Stock>();
	// trades = new ArrayList<>();
	// stocks = new HashMap<>();
    }

    @Override
    public boolean recordTrade(Trade trade) throws Exception
    {
        boolean retval = false;

        try
        {
            retval = trades.add(trade);
        }
        catch(Exception e)
        {
            throw new Exception("Exception occurred recording a trade", e);
        }
        
        return retval;
    }

    @Override
    public List<Trade> getTrades()
    {
        return trades;
    }
    
    @Override
    public void setTrades(List<Trade> trades)
    {
        this.trades = trades;
    }

    @Override
    public Stock getStockBySymbol(String stockSymbol)
    {
        Stock stock = null;
        
        try
        {
            if(stockSymbol != null)
            {
                stock = stocks.get(stockSymbol);
            }
        }
        catch(Exception e)
        {
            logger.error("Exception occurred when retrieving stock: " + stockSymbol + ": ", e);
        }
        
        return stock;
    }

    @Override
    public Map<String, Stock> getStocks()
    {
        return stocks;
    }
    
    /**
     * 
     * @param stocks
     */
    public void setStocks(Map<String, Stock> stocks) {
            this.stocks = stocks;
    }


}
