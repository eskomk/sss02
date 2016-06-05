/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.service;

import com.jpmorgan.assgn.sss02mvn.arch.SpringService;
import com.jpmorgan.assgn.sss02mvn.model.Trade;
import com.jpmorgan.assgn.sss02mvn.repository.StockTradeRepository;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esko02
 */
public class StockTradeServiceImplTest
{
    
    public static final Logger logger = Logger.getLogger(StockTradeServiceImplTest.class);
    
    public StockTradeServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        logger.info("Start setUp ...");
        
        recordTrades();
        
        logger.info("End setUp ...");
    }
    
    @After
    public void tearDown()
    {
        logger.info("Start  tearDown ...");

        emptyTrades();

        logger.info("End tearDown");
    }
    
    public void emptyTrades()
    {
        @SuppressWarnings("unchecked")
        List<Trade> tradeList = SpringService.INSTANCE.getBean("tradeList", ArrayList.class);

        logger.info("Trade List size: " + tradeList.size());

        try
        {
            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            
            int tradesNumber = stocksTradeRepository.getTrades().size();
            
            logger.info("Trades number before: "+tradesNumber);
            
            try
            {
                stocksTradeRepository.getTrades().clear();
            }
            catch (UnsupportedOperationException uoe)
            {
                logger.warn("Clear() operation not supported for this list: " + stocksTradeRepository.getClass());
                stocksTradeRepository.setTrades(new ArrayList<Trade>());
            }
            
            // After emptying trades, the number of trades should be equal to zero
            tradesNumber = stocksTradeRepository.getTrades().size();
            
            logger.info("Trades number after: " + tradesNumber);
        }
        catch(Exception exception)
        {
            logger.error(exception);
        }
    }

    public void recordTrades()
    {
        logger.info("Start recordTrades ...");

        // Create the stock service and verify it's not null object
        StockTradeService stockTradeService = SpringService.INSTANCE.getBean("stockTradesService", StockTradeService.class);

        // Recover the trades configured in spring for the unit test
        @SuppressWarnings("unchecked")
        List<Trade> tradeList = SpringService.INSTANCE.getBean("tradeList", ArrayList.class);

        logger.info("Trade List size: " + tradeList.size());

        try
        {
            // Initial trades are empty, means, trades number equls to zero
            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            
            int tradesNumber = stocksTradeRepository.getTrades().size();
            
            logger.info("Trades number: " + tradesNumber);
            
            // Insert many trades in the stock system
            for(Trade trade: tradeList)
            {
                boolean result = stockTradeService.recordTrade(trade);
                
                logger.info("Adding trade succeeded: " + result);
            }

            // After record trades, the number of trades should be equal to the trades list
            tradesNumber = stocksTradeRepository.getTrades().size();
            
            logger.info("Trades number: " + tradesNumber + ", trade list size: " + tradeList.size());
        }
        catch(Exception exception)
        {
            logger.error(exception);
        }

        logger.info("Finish recordTrades ...OK");
    }
    
    @Test
    public void testInstancesAlwaysSameSpringServiceObject()
    {
        logger.info("Starting testInstancesAlwaysSameSpringServiceObject");
        
        SpringService A = SpringService.INSTANCE;
        
        SpringService B = SpringService.INSTANCE;
        
	assertNotNull(A);
	assertNotNull(B);
        
        assertEquals(A, B);
    }
    
    
    /**
     * Test of calculateDividendYield method, of class StockTradeServiceImpl.
     */
    @Test
    public void testCalculateDividendYield()
    {
        logger.info("Start calculateDividendYieldTest ...");

        try
        {
            // Create the stock service and verify it's not null object
            StockTradeService stockTradeService = SpringService.INSTANCE.getBean("stockTradesService", StockTradeService.class);
            assertNotNull(stockTradeService);

            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            int tradesNumber = stocksTradeRepository.getTrades().size();
            logger.info("Trades number: " + tradesNumber);

            // Calculates the dividend yield for the stock symbol
            String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
            
            for(String stockSymbol: stockSymbols)
            {
                double dividendYield = stockTradeService.calculateDividendYield(stockSymbol);
                
                logger.info(stockSymbol + "\n\n*** DividendYield calculated: " + dividendYield + " ***\n");
                
                assertTrue(dividendYield >= 0.0);
            }

        }
        catch(Exception exception)
        {
            logger.error(exception);
            System.out.println("" + exception);
            
            assertTrue(false);
        }

        logger.info("Finish calculateDividendYieldTest ...OK");
    }

    /**
     * Test of calculatePERatio method, of class StockTradeServiceImpl.
     */
    @Test
    public void testCalculatePERatio()
    {
        logger.info("Start  calculatePERatioTest ...");

        try
        {
            StockTradeService stockTradeService = SpringService.INSTANCE.getBean("stockTradesService", StockTradeService.class);
            assertNotNull(stockTradeService);

            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            int tradesNumber = stocksTradeRepository.getTrades().size();
            logger.info("Trades number: " + tradesNumber);


            // Calculates the P/E Ratio for the stock Symbol
            String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};

            for(String stockSymbol: stockSymbols)
            {
                double peRatio = stockTradeService.calculatePERatio(stockSymbol);

                logger.info(stockSymbol+"\n\n*** P/E Ratio calculated: " + peRatio + " ***\n");

                assertTrue(peRatio >= 0.0);
            }
        }
        catch(Exception exception)
        {
            logger.error(exception);
            assertTrue(false);
        }

        logger.info("Finish calculatePERatioTest ...OK");
    }

    /**
     * Test of calculateStockPriceLast15mins method, of class StockTradeServiceImpl.
     */
    @Test
    public void testCalculateStockPriceLast15mins()
    {
        try
        {
            // Create the stock service and verify it's not null object
            StockTradeService stockTradeService = SpringService.INSTANCE.getBean("stockTradesService", StockTradeService.class);
            assertNotNull(stockTradeService);

            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            int tradesNumber = stocksTradeRepository.getTrades().size();
            logger.info("Trades number: " + tradesNumber);

            // Calculates the Stock Price for all stocks
            String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
            
            for(String stockSymbol: stockSymbols)
            {
                double stockPrice = stockTradeService.calculateStockPriceLast15mins(stockSymbol);

                logger.info(stockSymbol + "\n\n*** Stock Price calculated: " + stockPrice + " ***\n");

                assertTrue(stockPrice >= 0.0);
            }
        }
        catch(Exception exception)
        {
            logger.error(exception);
            assertTrue(false);
        }
    }

    /**
     * Test of calculateGBCEAllShareIndex method, of class StockTradeServiceImpl.
     */
    @Test
    public void testCalculateGBCEAllShareIndex()
    {
        try
        {
            // Create the stock service and verify it's not null object
            StockTradeService stockTradeService = SpringService.INSTANCE.getBean("stockTradesService", StockTradeService.class);
            assertNotNull(stockTradeService);

            StockTradeRepository stocksTradeRepository = SpringService.INSTANCE.getBean("stockTradeRepo", StockTradeRepository.class);
            int tradesNumber = stocksTradeRepository.getTrades().size();
            logger.info("Trades number: "+tradesNumber);

            double GBCEAllShareIndex = stockTradeService.calculateGBCEAllShareIndex();
            
            logger.info("\n\n*** GBCE All Share Index: " + GBCEAllShareIndex + " ***\n");
            
            assertTrue(GBCEAllShareIndex > 0.0);
        }
        catch(Exception exception)
        {
            logger.error(exception);
            assertTrue(false);
        }
    }
}
