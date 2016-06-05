/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.service;

import org.apache.commons.collections.Predicate;

import com.jpmorgan.assgn.sss02mvn.model.Stock;
import com.jpmorgan.assgn.sss02mvn.model.Trade;
import com.jpmorgan.assgn.sss02mvn.repository.StockTradeRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
// import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author esko02
 */
public class StockTradeServiceImpl implements StockTradeService
{
    private static final Logger logger = Logger.getLogger(StockTradeServiceImpl.class);
    
    private StockTradeRepository stockTradeRepository = null;
    
    @Override
    public double calculateDividendYield(String pstocksymbol)
            throws Exception
    {
        // EMK 20160605
        double dividendYield = -1.0;
        // double dividendYield = 0.0;

        logger.debug("Calculating Dividend Yield for the stock symbol: "+pstocksymbol);

        Stock stock = stockTradeRepository.getStockBySymbol(pstocksymbol);

        if(stock == null)
        {
            // throw new Exception("The stock symbol ["+pstocksymbol+"] is not supported by the Super Simple Stock system.");

            logger.warn("The stock symbol ["+ pstocksymbol + "] is not supported");

            return dividendYield;
        }

        if(stock.getTickerPrice() <= 0.0)
        {
            // throw new Exception("The ticker price for the stock ["+pstocksymbol+"] should be greater than zero (0).");

            logger.warn("The ticker price for the stock [" + pstocksymbol + "] should be greater than zero");

            return dividendYield;
        }

        dividendYield = stock.getDividendYield();

        logger.debug("Dividend Yield calculated: "+dividendYield);

        
        return dividendYield;
    }
    
    @Override
    public double calculatePERatio(String pstocksymbol) 
            throws Exception
    {
        double peRatio = -1.0;

        logger.debug("Calculating P/E Ratio for the stock symbol: " + pstocksymbol);

        Stock stock = stockTradeRepository.getStockBySymbol(pstocksymbol);

        // If the stock is not supported the a exception is raised
        if(stock == null)
        {
            // throw new Exception("The stock symbol ["+stockSymbol+"] is not supported by the Super Simple Stock system.");

            logger.warn("The stock symbol [" + pstocksymbol + "] is not supported.");

            return peRatio;
        }

        // Ticker price with value zero does not make any sense and could produce a zero division
        if(stock.getTickerPrice() <= 0.0)
        {
            // throw new Exception("The ticker price for the stock ["+stockSymbol+"] should be greater than zero (0)");

            logger.warn("The ticker price for the stock [" + pstocksymbol + "] should be greater than zero");

            return peRatio;
        }

        peRatio = stock.getPeRatio();

        logger.debug(" P/E Ratiocalculated: " + peRatio);

        return peRatio;
    }
    
    @Override
    public double calculateStockPrice(String pstocksymbol, int minutes)
            throws Exception
    {
		double stockPrice = 0.0;

		logger.debug("Trades in the original collection: "+ getTradesNumber() );

		
		@SuppressWarnings("unchecked")
		Collection<Trade> trades = CollectionUtils.select(stockTradeRepository.getTrades(), new StockPredicate( pstocksymbol, minutes));

		logger.debug("Trades in the filtered collection by ["+ pstocksymbol+","+ minutes + "]: "+trades.size());

		// Calculate the summation
		double shareQuantityAcum = 0.0;
		double tradePriceAcum = 0.0;
		for(Trade trade : trades){
			// Calculate the summation of Trade Price x Quantity
			tradePriceAcum += (trade.getPrice() * trade.getShareQuantity());
			// Acumulate Quantity
			shareQuantityAcum += trade.getShareQuantity();
		}

		// calculate the stock price
		if(shareQuantityAcum > 0.0){
			stockPrice = tradePriceAcum / shareQuantityAcum;	
		}


		return stockPrice;
    }
    
    @Override
    public double calculateStockPriceLast15mins(String pstocksymbol) 
            throws Exception
    {
        double stockPrice = 0.0;

        logger.debug("Calculating Stock Price for the stock symbol: " + pstocksymbol);
        Stock stock = stockTradeRepository.getStockBySymbol(pstocksymbol);

        // If the stock is not supported the a exception is raised
        if(stock==null){
                throw new Exception("The stock symbol ["+ pstocksymbol + "] is not supported");
        } 

        stockPrice = calculateStockPrice(pstocksymbol, 15);

        logger.debug(" Stock Price for stock symbol '" + pstocksymbol + "' calculated: "+stockPrice);

        return stockPrice;
    }
    
    @Override
    public double calculateGBCEAllShareIndex() 
            throws Exception
    {
        double allShareIndex = 0.0;

        // Calculate stock price for all stock in the system
        Map<String, Stock> stocks = stockTradeRepository.getStocks();
        ArrayList<Double> stockPrices = new ArrayList<Double>();
        
        for(String stockSymbol: stocks.keySet())
        {
            double stockPrice = calculateStockPrice(stockSymbol, 0);
            
            if(stockPrice > 0)
            {
                    stockPrices.add(stockPrice);
            }
        }

        if(stockPrices.size()>=1)
        {
            double[] stockPricesArray = new double[stockPrices.size()];

            for(int i=0; i<=(stockPrices.size()-1); i++)
            {
                // stockPricesArray[i] = stockPrices.get(i).doubleValue();
                stockPricesArray[i] = stockPrices.get(i);
            }
            
            // Calculates the GBCE All Share Index
            allShareIndex = StatUtils.geometricMean(stockPricesArray);
        }
        
        return allShareIndex;
    }
    
    @Override
    public boolean recordTrade(Trade ptrade) 
            throws Exception
    {
        if (ptrade == null)
        {
            logger.info("Trade object was null");
            
            return false;
        }
        
        if (ptrade.getStock() == null)
        {
            logger.info("Stock in trade '" + ptrade + "' was null");
            
            return false;
        }
        
        if (ptrade.getPrice() <= 0)
        {
            logger.info("Trade '" + ptrade + "' price was <= 0");
            
            return false;
        }
        
        if (ptrade.getShareQuantity() <= 0)
        {
            logger.info("Trade '" + ptrade + "' shares quantity was <= 0");
            
            return false;
        }
        
        boolean retval = false;
        
        retval = this.stockTradeRepository.recordTrade(ptrade);

        // Update the ticker price for the stock
        if(retval)
        {
            ptrade.getStock().setTickerPrice(ptrade.getPrice());
        }
       
        return retval;
    }

    public void setStockTradeRepository(StockTradeRepository stockTradeRepository) {
        this.stockTradeRepository = stockTradeRepository;
    }
    
    private int getTradesNumber() throws Exception {
        return stockTradeRepository.getTrades().size();
    }

    private class StockPredicate implements Predicate
    {
        /**
         *
         */
        private final Logger logger = Logger.getLogger(StockPredicate.class);

        /**
         * 
         */
        private String stockSymbol = "";

        /**
         * 
         */
        private Calendar dateRange = null;

        /**
         * 
         * @param stockSymbol
         * @param minutesRange
         */
        public StockPredicate(String stockSymbol, int minutesRange)
        {
                this.stockSymbol = stockSymbol;
                
            if( minutesRange > 0 )
            {
                dateRange = Calendar.getInstance();
                dateRange.add(Calendar.MINUTE, -minutesRange);
                logger.debug(String.format("Filter date pivot: %tF %tT", dateRange.getTime(), dateRange.getTime()));
            }

        }

        /**
         * 
         */
        public boolean evaluate(Object tradeObject)
        {
            if (tradeObject instanceof Trade == false)
            {
                return false;
            }
            
            Trade trade = (Trade) tradeObject;
            
            boolean shouldBeIncluded = trade.getStock().getStockSymbol().equals(stockSymbol);
            
            if(shouldBeIncluded && dateRange != null)
            {
                shouldBeIncluded = dateRange.getTime().compareTo(trade.getTimeStamp()) <= 0;
            }
            
            return shouldBeIncluded;
        }

    }

}
