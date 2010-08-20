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
