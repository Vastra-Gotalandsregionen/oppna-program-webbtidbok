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

package se.vgregion.webbtidbok;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.booking.BookingFacadeDummy;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.servicedef.LookupService;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

public class LoginTest {

	private static final String PWD = "pwd";
	private static final String PNR = "20100820-2222";
	private Login login;
	private State state;
	private BookingFacadeMock bookingFacadeMock;
	private LookupServieMock lookupServieMock;
	static ServiceDefinition sd;

	@Before
	public void setUp() throws Exception {
		login = new Login();

		lookupServieMock = new LookupServieMock();
		login.setLookupService(lookupServieMock);
		state = new State();
		state.setPnr(PNR);
		state.setPasswd(PWD);
		BookingFactory bookingFactory = new BookingFactory();
		login.setBookingFactory(bookingFactory);
		bookingFacadeMock = new BookingFacadeMock();
		sd = new ServiceDefinition();
		sd.setMessageBundleBase("test");
	}

	@Test
	public void testLogout() {
		state.setLoggedIn(true);
		login.logout(state);
		assertEquals("", state.getPnr());
		assertEquals("", state.getPasswd());
		assertFalse(state.isLoggedIn());

	}

	@Test
	public void testLoginTrue() throws Exception {
		bookingFacadeMock.acceptLogin = true;
		boolean loginResult = login.login(state);
		assertTrue(loginResult);
		assertTrue(state.isLoggedIn());
	}

	@Test
	public void testLoginFalse() throws Exception {
		boolean loginResult = login.login(state);
		assertFalse(loginResult);
		assertFalse(state.isLoggedIn());
	}

	@Test
	public void testLookupTrue() {
		LoginMessages loginMessages = new LoginMessages();
		boolean lookup = login.lookup(state, loginMessages);
		assertTrue(lookup);
		assertEquals("ServiceID", state.getService());
		assertEquals("MessageBundleBase", state.getMessageBundle());
		assertNull(loginMessages.getErrorMessages());
	}

	@Test
	public void testLookupFalse() {
		lookupServieMock.serviceDefinition = null;
		ResourceBundle bundle = ResourceBundle.getBundle("messages/BaseMessages");
		String errorMessage = bundle.getString("loginpageErrorMessage");
		String[] errorMessageList = errorMessage.split("\\|");
		LoginMessages loginMessages = new LoginMessages();
		login.setResourceBundle(bundle);
		boolean lookup = login.lookup(state, loginMessages);
		assertFalse(lookup);
		assertNull(state.getService());
		assertEquals("", state.getMessageBundle());
		assertEquals(errorMessageList[0], loginMessages.getErrorMessages()[0]);
		assertEquals(errorMessageList[1], loginMessages.getErrorMessages()[1]);
	}

	class BookingFactory implements se.vgregion.webbtidbok.booking.BookingFactory {

		@Override
		public BookingFacade getService(State state) {
			return bookingFacadeMock;
		}

		@Override
		public BookingFacade getService(String serviceId) {
			return bookingFacadeMock;
		}

		@Override
		public Map<String, ServiceDefinition> getService() {
			return null;
		}
	}

	class BookingFacadeMock extends BookingFacadeDummy {

		private boolean acceptLogin;
		private State state;

		@Override
		public boolean login(State state) {
			this.state = state;
			return acceptLogin;
		}

		@Override
		public void reschedule(BookingTime bookingTime, State state) {

		}

		@Override
		public boolean cancelBooking(State state) {
			return false;
		}
	}

	class LookupServieMock implements LookupService {

		private ServiceDefinition serviceDefinition = new ServiceDefinitionMock();

		@Override
		public ServiceDefinition lookup(State state) {
			return serviceDefinition;
		}

	}

	class ServiceDefinitionMock extends ServiceDefinition {
		@Override
		public String getServiceID() {
			return "ServiceID";
		}

		@Override
		public String getMessageBundleBase() {
			return "MessageBundleBase";
		}
	}

}
