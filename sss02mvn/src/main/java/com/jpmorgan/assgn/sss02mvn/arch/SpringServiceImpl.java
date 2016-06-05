/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.arch;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author esko02
 */
public class SpringServiceImpl implements SpringService
{
    private static final Logger logger = Logger.getLogger(SpringServiceImpl.class);
    
    private static final String SPRING_CONTEXT_FILE_NAME = "classpath*:*stocks_sss02-*-context.xml";
    
    private AbstractApplicationContext springContext = null;
    
    /**
     * Constructor of the class. The main purpose of the constructor is to load the Spring context for the applicarion. 
     */
    private SpringServiceImpl()
    {
        logger.info("Loading Spring Context");
        springContext = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE_NAME);
        
        logger.info("Before springContext.registerShutdownHook()");
        springContext.registerShutdownHook();
        
        logger.info("Spring Context created **** Successfully ****");
    }

    public static SpringService getInstance()
    {
        SpringService theServ = SpringServiceHolder.INSTANCE;
        theServ.getClass();
        
        return theServ;
    }
    
    public <T> T getBean(String beanName, Class<T> objectClass)
    {
            return springContext.getBean(beanName, objectClass);
    }

    /**
     * Holder class for the singleton factory instance. {@link SpringServiceHolder} is 
     * loaded on the first execution of {@link SpringServiceImpl#getInstance()} or the first 
     * access to {@link SpringServiceHolder#INSTANCE}, not before.
     */
    private static class SpringServiceHolder
    {
        private static final SpringService INSTANCE = new SpringServiceImpl();
    }

}
