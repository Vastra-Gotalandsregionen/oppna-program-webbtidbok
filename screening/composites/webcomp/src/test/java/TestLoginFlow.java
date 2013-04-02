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

import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import se.vgregion.webbtidbok.ValidationLogin;

public class TestLoginFlow extends AbstractXmlFlowExecutionTests {

	// constants
	private final static String LOGIN_FLOW_FILE_LOCATION = "src/main/resources/flows/login/login-flow.xml";
	private final static String VALIDATION_SERVICE = "validationService";
	// states
	private final static String LOGIN_FLOW_INITIAL_STATE = "start";
	private final static String LOGIN_FLOW_SECOND_STATE = "validate";
	private final static String LOGIN_FLOW_THIRD_STATE = "lookupService";
	// events
	private final static String LOGIN_FLOW_FIRST_TO_SECOND_STATE_EVENT = "login";
	private final static String LOGIN_FLOW_SECOND_TO_THIRD_STATE_EVENT = "lookupService";
	private final static String LOGIN_FLOW_SECOND_TO_START_STATE_EVENT = "start";
	// instance var's
	private MutableAttributeMap mFlowInputParams;
	private MockExternalContext mMockExternalContext;

	// Get the the actual resource, i.e. grab the flow.xml file
	@Override
	protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
		FlowDefinitionResource flowResource;
		flowResource = resourceFactory.createFileResource(LOGIN_FLOW_FILE_LOCATION);
		return flowResource;
	}

	private void createParamsMapAndMockContext() {
		mFlowInputParams = new LocalAttributeMap();
		mMockExternalContext = new MockExternalContext();
	}

	@Override
	protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
		builderContext.registerBean("validationService", new ValidationLogin());
	}

	public void testFlowStartup() {
		createParamsMapAndMockContext();
		startFlow(mFlowInputParams, mMockExternalContext);
		assertCurrentStateEquals(LOGIN_FLOW_INITIAL_STATE);
	}

	// public void testStartToValidateTransition(){
	// createParamsMapAndMockContext();
	//
	// getFlowScope().put("state", new State());
	// getFlowScope().put("loginmessages", new LoginMessages());
	// setCurrentState("start");
	//
	// mMockExternalContext.setEventId("login");
	// resumeFlow(mMockExternalContext);
	//
	// assertCurrentStateEquals("validate");
	// }
}