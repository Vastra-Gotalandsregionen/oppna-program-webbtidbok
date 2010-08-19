package se.vgregion.webbtidbok.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SurgeryTest {

    private Surgery surgery;
    
    @Before
    public void setUp() throws Exception {
        surgery = new Surgery();
    }

    @Test
    public void testSetSurgeryId() {
        surgery.setSurgeryId("test");
        assertEquals("test", surgery.getSurgeryId());
        surgery.setSurgeryId("annat");
        assertEquals("annat", surgery.getSurgeryId());
    }

    @Test
    public void testSetSurgeryName() {
        surgery.setSurgeryName("Mammo");
        assertEquals("Mammo", surgery.getSurgeryName());
        surgery.setSurgeryName("Bukaorta");
        assertEquals("Bukaorta", surgery.getSurgeryName());
    }

    @Test
    public void testSetSurgeryAddress() {
        surgery.setSurgeryAddress("Mammogatan");
        assertEquals("Mammogatan", surgery.getSurgeryAddress());
        surgery.setSurgeryAddress("Bukaortagatan");
        assertEquals("Bukaortagatan", surgery.getSurgeryAddress());
    }

    @Test
    public void testGetFullAddress() {
        surgery.setSurgeryName("Mammo");
        surgery.setSurgeryAddress("Mammogatan");
        assertEquals("Mammo, Mammogatan", surgery.getFullAddress());
        surgery.setSurgeryAddress("Mammostigen");
        assertEquals("Mammo, Mammostigen", surgery.getFullAddress());
    }

}
