package teama.service;

import static org.junit.Assert.*;

/**
 * Unit tests for Gate class form Service project
 * @author emusial
 */
public class GateTest {
    public GateTest() {
    }

    /**
     * Test of getNumber method, of class Gate.
     */
    @org.junit.Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Gate instance = new Gate(28, 54.38200, 18.46196);
        int expResult = 28;
        int result = instance.getNumber();
        assertEquals(expResult, result);
     }

    /**
     * Test of getLat method, of class Gate.
     */
    @org.junit.Test
    public void testGetLat() {
        System.out.println("getLat");
        Gate instance = new Gate(26, 54.38170, 18.46315);
        double expResult = 54.38170;
        double result = instance.getLat();
        assertEquals(expResult, result, 0.0);
        
    }
    /**
     * Test of getLon method, of class Gate.
     */
    @org.junit.Test
    public void testGetLon() {
        System.out.println("getLon");
        Gate instance = new Gate(24, 54.38142, 18.46431);
        double expResult = 18.46431;
        double result = instance.getLon();
        assertEquals(expResult, result, 0.0);
    }
}
