/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author esko02
 */
public class DatesUtilsImpl implements DatesUtils
{
    /**
     * Constructor of the class.
     */
    public DatesUtilsImpl(){

    }

    @Override
    public Date getNowMovedMinutes(int minutes)
    {
        Calendar now = Calendar.getInstance();
        
        now.add(Calendar.MINUTE, minutes);
        
        return now.getTime();
    }
    
}
