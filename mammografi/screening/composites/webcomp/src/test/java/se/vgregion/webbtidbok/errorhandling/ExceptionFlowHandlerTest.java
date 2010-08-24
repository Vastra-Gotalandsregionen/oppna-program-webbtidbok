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
package se.vgregion.webbtidbok.errorhandling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.webflow.conversation.ConversationException;
import org.springframework.webflow.conversation.NoSuchConversationException;
import org.springframework.webflow.conversation.impl.SimpleConversationId;
import org.springframework.webflow.core.FlowException;

public class ExceptionFlowHandlerTest {

  private ExceptionFlowHandler exceptionFlowHandler;
  private FlowExceptionMock flowExceptionMock;

  @Before
  public void setUp() throws Exception {
    
    exceptionFlowHandler = new ExceptionFlowHandler();
    flowExceptionMock = new FlowExceptionMock();
  }

  @Test
  public void testHandleExceptionFlowExceptionHttpServletRequestHttpServletResponseWithNoSuchConversationException() {
    flowExceptionMock.throwNoSuchConversationException  =true;
    String handleException = exceptionFlowHandler.handleException(flowExceptionMock, null, null);
    assertEquals("login", handleException);
  }
  
  @Test
  public void testHandleExceptionFlowExceptionHttpServletRequestHttpServletResponseWithNoSuchFlowDefinitionException() {
    String handleException = exceptionFlowHandler.handleException(flowExceptionMock, null, null);
    assertEquals("login", handleException);
  }
  
  @Test(expected=Error.class)
  public void testHandleExceptionFlowExceptionHttpServletRequestHttpServletResponseError() {
    flowExceptionMock.throwError = true;
    exceptionFlowHandler.handleException(flowExceptionMock, null, null);
  }
  
  class ThrowableMock extends ConversationException {

    public ThrowableMock(String message) {
      super(message);
      // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
  }
  
  class FlowExceptionMock extends FlowException {

    boolean throwNoSuchConversationException;
    boolean throwError;
    Throwable NoSuchConversationException = new NoSuchConversationException(new SimpleConversationId("id"));
    Throwable NoSuchFlowDefinitionException = new org.springframework.webflow.definition.registry.NoSuchFlowDefinitionException("id");
    
    public FlowExceptionMock() {
      super("test");
    }
    
    public FlowExceptionMock(String msg) {
      super(msg);
      // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Throwable getCause() {
      Throwable tmp = null;
      if(throwError){
        return new Throwable("error");
      }
      if (throwNoSuchConversationException) {
        tmp  = NoSuchConversationException;
        NoSuchConversationException = null;
      }else {
        tmp =  NoSuchFlowDefinitionException;
        NoSuchFlowDefinitionException = null;
      }
       return tmp;
    }
  }

}
