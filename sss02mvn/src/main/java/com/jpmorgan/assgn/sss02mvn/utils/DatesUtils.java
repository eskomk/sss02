/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.assgn.sss02mvn.utils;

import java.util.Date;

/**
 *
 * @author esko02
 */
public interface DatesUtils
{
    /**
     * Get the current date an time with moved the number of minutes specified by the value of the parameter <i>minutes</i>.
     * 
     * @param minutes Number of minutes used to move the current date time. If used negative values, the time is moved back.
     * 
     * @return The current time moved by the number of minutes specified by the value of the parameter <i>minutes</i>.
     */
    public Date getNowMovedMinutes(int minutes);
}
