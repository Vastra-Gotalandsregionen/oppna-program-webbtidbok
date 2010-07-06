package se.vgregion.webbtidbok.servicedef;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacadeDummy;

public class LoginLookupServiceTest {

    private class BookingFacadeDummyImpl extends BookingFacadeDummy {

        public boolean loginSuccessSwitch = false;
        
        public boolean login(State state) {
            return loginSuccessSwitch;
        }

    }
    
    private ServiceDefinition serviceDefinition;
    private LoginLookupService lookupService;
    private BookingFacadeDummyImpl booking;

    @Before
    public void setUp() throws Exception {
        booking = new BookingFacadeDummyImpl();
        
        serviceDefinition = new ServiceDefinition();
        serviceDefinition.setServiceID("test");
        serviceDefinition.setBookingService(booking);

        lookupService = new LoginLookupService();
        lookupService.setServiceDefinition(serviceDefinition);
    }

    @Test
    public void testFailingLogin() {
        booking.loginSuccessSwitch = false;
        State state = new State();
        Assert.assertNull(lookupService.lookup(state));
    }
    
    @Test
    public void testSucceedingLogin() {
        booking.loginSuccessSwitch = true;
        State state = new State();
        Assert.assertEquals(serviceDefinition, lookupService.lookup(state));
    }

}
