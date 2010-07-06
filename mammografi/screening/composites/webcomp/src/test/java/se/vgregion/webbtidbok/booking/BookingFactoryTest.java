package se.vgregion.webbtidbok.booking;


import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.servicedef.ServiceDefinition;

public class BookingFactoryTest {

    private class BookingFacadeDummy implements BookingFacade {

        public boolean login(State state) {
            return false;
        }
    }
    
    @Test
    public void testSettingServiceDefinition() {
        BookingFactory factory = new BookingFactory();

        ArrayList<ServiceDefinition> list = new ArrayList<ServiceDefinition>();
        ServiceDefinition sd1 = new ServiceDefinition();
        sd1.setServiceID("sd1");
        sd1.setBookingService(new BookingFacadeDummy());
        list.add(sd1);
        ServiceDefinition sd2 = new ServiceDefinition();
        sd2.setServiceID("sd2");
        sd2.setBookingService(new BookingFacadeDummy());
        list.add(sd2);
        ServiceDefinition sd3 = new ServiceDefinition();
        sd3.setServiceID("sd3");
        sd3.setBookingService(new BookingFacadeDummy());
        list.add(sd3);

        factory.setServiceDefinitions(list);

        State state = new State();

        state.setService(sd1.getServiceID());
        Assert.assertEquals(sd1.getBookingService(), factory.getService(state));

        state.setService("nonexisting");
        Assert.assertNull(factory.getService(state));
        
        state.setService(null);
        Assert.assertNull(factory.getService(state));
    }
}
