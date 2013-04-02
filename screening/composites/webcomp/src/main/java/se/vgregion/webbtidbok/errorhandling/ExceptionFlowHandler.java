/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.errorhandling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.conversation.ConversationException;
import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class ExceptionFlowHandler extends AbstractFlowHandler {
  private static Logger sLogger = LoggerFactory.getLogger("se.vgregion.webbtidbok.flow-exceptions");

  public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
    sLogger.error("An exception occurred during execution of the flow", e);

    Throwable theRootCause = e;
    while (theRootCause.getCause() != null) {
      theRootCause = theRootCause.getCause();
    }

    if (theRootCause instanceof ConversationException || theRootCause instanceof FlowException) {
      return "login";
    } else {
      throw new Error(e);
    }
  }
}
