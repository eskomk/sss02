/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.arch;

/**
 *
 * @author esko02
 */
public interface SpringService
{
    public SpringService INSTANCE = SpringServiceImpl.getInstance();
    
    public <T extends Object> T getBean(String beanName, Class<T> objectClass);
}
