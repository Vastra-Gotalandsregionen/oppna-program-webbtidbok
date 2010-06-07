/**
 * Copyright 2009 Vastra Gotalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */
package se.vgregion.webbtidbok.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAdvice {

  private static Logger sLogger = LoggerFactory.getLogger("se.vgregion.webbtidbok.logging");

  public Object around(ProceedingJoinPoint inAdvisedMethodInvocation) throws Throwable {

    String theClassName = inAdvisedMethodInvocation.getTarget().getClass().getName();
    String theMethodName = inAdvisedMethodInvocation.getSignature().getName();

    sLogger.debug(theClassName + ":" + theMethodName + " - ***** Entering method.");

    Object theResult = null;
    try {
      theResult = inAdvisedMethodInvocation.proceed();
    } catch (Throwable theException) {
      sLogger.error(theClassName + ":" + theMethodName + " - An exception was thrown", theException);
      throw theException;
    }

    sLogger.debug(theClassName + ":" + theMethodName + " - ***** Exiting method.");

    return theResult;
  }

}