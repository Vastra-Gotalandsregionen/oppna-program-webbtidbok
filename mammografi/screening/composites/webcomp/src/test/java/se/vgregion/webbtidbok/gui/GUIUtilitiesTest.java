package se.vgregion.webbtidbok.gui;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.booking.BookingFacadeDummy;
import se.vgregion.webbtidbok.domain.Booking;
import se.vgregion.webbtidbok.domain.Surgery;
import se.vgregion.webbtidbok.domain.sectra.BookingSectra;

public class GUIUtilitiesTest {
    
    private GUIUtilities guiUtility; 

    @Before
    public void setUp() throws Exception {
        guiUtility = new GUIUtilities();
    }

    @Test
    public void testGetIsTimeListEmpty() {
        List<Integer> list = new ArrayList<Integer>();
        assertTrue(guiUtility.getIsTimeListEmpty(list));
        list.add(new Integer(1));
        assertFalse(guiUtility.getIsTimeListEmpty(list));
        list.add(new Integer(2));
        assertFalse(guiUtility.getIsTimeListEmpty(list));
    }

    @Test
    public void testSetCentralTidbokIDfromLocation() {
        State state = new State();
        state.setCentralTidbokID(0);
        
        guiUtility.setCentralTidbokIDfromLocation(state, "4");
        assertEquals(4, state.getCentralTidbokID());

        // Not numeric - should not update tidbokID.
        guiUtility.setCentralTidbokIDfromLocation(state, "test 3");
        assertEquals(4, state.getCentralTidbokID());
    }

    @Test
    public void testSetupLocations() {
        guiUtility.setBookingFacade(new TestFacade());
        
        State state = new State();
        Booking booking = new BookingSectra();

        Surgery surgery = new Surgery();
        surgery.setSurgeryId("test 2");
        surgery.setSurgeryName("Testmammo 2");
        surgery.setSurgeryAddress("Testaddress 2");
        booking.setSurgery(surgery);
        
        LocationHolder holder = guiUtility.setupLocations(booking, state);
        
        assertEquals("test 2", holder.getLocationId());
        assertEquals("Testmammo 2", holder.getCurrentLocation().getSurgeryName());
        assertEquals("Testaddress 2", holder.getCurrentLocation().getSurgeryAddress());
        
        assertEquals(2, holder.getSelectItems().size());
    }

    private class TestFacade extends BookingFacadeDummy {

        @Override
        public List<Surgery> getAvailableSurgeries(State state) {
            List<Surgery> surgeries = new ArrayList<Surgery>();
            
            Surgery s1 = new Surgery();
            s1.setSurgeryId("test 1");
            s1.setSurgeryName("Testmammo 1");
            s1.setSurgeryAddress("Testaddress 1");
            surgeries.add(s1);
                
            Surgery s2 = new Surgery();
            s2.setSurgeryId("test 2");
            s2.setSurgeryName("Testmammo 2");
            s2.setSurgeryAddress("Testaddress 2");
            surgeries.add(s2);

            return surgeries;
        }
    }
    
}
