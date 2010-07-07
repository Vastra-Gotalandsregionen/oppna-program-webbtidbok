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
package se.vgregion.webbtidbok.flows;

import org.junit.Assert;
import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import se.vgregion.webbtidbok.LoginMessages;
import se.vgregion.webbtidbok.State;



public class LoginFlowTest extends AbstractXmlFlowExecutionTests{

    
//   constants
    private final static String LOGIN_FLOW_FILE_LOCATION = "src/main/resources/flows/login/login-flow.xml";

    // Used to control whether service calls should pass or fail.
    private boolean validationSuccess = true;
    private boolean lookupSuccess = true;
    private boolean loginSuccess = true;
    
    // Used to check if an action state has been visited.
    private boolean validationVisited = false;
    private boolean lookupVisited = false;
    private boolean loginVisited = false;
    private boolean logoutVisited = false;

    // This object mocks all services.
    private Object serviceMock = new Object() {

        @SuppressWarnings("unused")
        public boolean validateLogin(State st, LoginMessages lm) {
            validationVisited = true;
            return validationSuccess;
        }
        
        @SuppressWarnings("unused")
        public boolean lookup(State state) {
            lookupVisited = true;
            return lookupSuccess;
        }

        @SuppressWarnings("unused")
        public boolean login(State loginCredentials) throws Exception {
            loginVisited = true;
            return loginSuccess;
        }

        @SuppressWarnings("unused")
        public void logout(State loginCredentials) {
            logoutVisited = true;
        }

        @SuppressWarnings("unused")
        public void setFirstPlacesBoolean(boolean b) {
        }
    };


    // Get the the actual resource, i.e. grab the flow.xml file 
    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
        FlowDefinitionResource flowResource;
        flowResource = resourceFactory.createFileResource(LOGIN_FLOW_FILE_LOCATION);
        return flowResource;
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext){
        builderContext.registerBean("validationService", serviceMock);
        builderContext.registerBean("loginService", serviceMock);
        builderContext.registerBean("bookingService", serviceMock);
        
    }

    private Flow createMockDisplaySubflow() {
        return FlowTestHelper.createMockSubflow("display", "logout", new Mapper() {
            public MappingResults map(Object source, Object target) {
                Assert.assertTrue( (((AttributeMap) source).get("state")) instanceof State );
                return null;
            }
        });
    }

    private void startFlowWithState(String startState, String event) {
        MockExternalContext context = new MockExternalContext();
        setCurrentState(startState);

        getFlowScope().put("state", new State());
        getFlowScope().put("loginmessages", new LoginMessages());
        getFlowDefinitionRegistry().registerFlowDefinition(createMockDisplaySubflow());

        context.setEventId(event);
        resumeFlow(context);
    }

    private void assertStatesVisited(boolean validation, boolean lookup, boolean login, boolean logout) {
        Assert.assertEquals(validation, validationVisited);
        Assert.assertEquals(lookup, lookupVisited);
        Assert.assertEquals(login,  loginVisited);
        Assert.assertEquals(logout, logoutVisited);
    }

    
    // ----- Our tests go below -----

    public void testFlowStartup(){      
        startFlow(new MockExternalContext());      
        assertCurrentStateEquals("start");
    }
    
    public void testSuccessfulFlow(){
        startFlowWithState("start", "login");
        
        assertFlowExecutionEnded();
        assertFlowExecutionOutcomeEquals("loggedout");
        assertStatesVisited(true, true, true, true);
    }

    public void testFailingValidation(){
        validationSuccess = false;
        startFlowWithState("start", "login");
        
        assertCurrentStateEquals("start");
        assertStatesVisited(true, false, false, false);
    }

    public void testFailingLookup(){
        lookupSuccess = false;
        startFlowWithState("start", "login");
        
        assertCurrentStateEquals("start");
        assertStatesVisited(true, true, false, false);
    }

    public void testFailingLogin(){
        loginSuccess = false;
        startFlowWithState("start", "login");
        
        assertCurrentStateEquals("start");
        assertStatesVisited(true, true, true, false);
    }

}
