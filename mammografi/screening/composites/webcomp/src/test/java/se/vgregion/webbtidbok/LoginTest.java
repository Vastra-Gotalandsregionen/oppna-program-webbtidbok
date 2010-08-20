package se.vgregion.webbtidbok;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.booking.BookingFacade;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.servicedef.LookupService;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

public class LoginTest {

  private static final String PWD = "pwd";
  private static final String PNR = "20100820-2222";
  private Login login;
  private State state;
  private BookingFacadeMock bookingFacadeMock;
  private LookupServieMock lookupServieMock;

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
    boolean lookup = login.lookup(state);
    assertTrue(lookup);
    assertEquals("ServiceID", state.getService());
    assertEquals("MessageBundleBase", state.getMessageBundle());
  }

  @Test
  public void testLookupFalse() {
    lookupServieMock.serviceDefinition = null;
    boolean lookup = login.lookup(state);
    assertFalse(lookup);
    assertNull(state.getService());
    assertEquals("", state.getMessageBundle());
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
  }

  class BookingFacadeMock implements BookingFacade {

    private boolean acceptLogin;
    private State state;

    @Override
    public List<Surgery> getAvailableSurgeries(State state) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Booking getBookingInfo(State state) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public List<BookingTime> getBookingTime(State state) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public List<Calendar> getFreeDays(State state, String surgeryId, Calendar startDate, Calendar endDate) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean login(State state) {
      this.state = state;
      return acceptLogin;
    }

    @Override
    public void reschedule(BookingTime bookingTime, State state) {
      // TODO Auto-generated method stub

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
