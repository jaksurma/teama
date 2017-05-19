package teama.service;

import java.util.ArrayList;

/**
 * Class for GET gates JSON response
 * @author MarcinPultyn
 */
public class GateList {
    public GateList(ArrayList<Gate> gates) {
        this.gates = gates;
    }
    
    public ArrayList<Gate> getGates() {
        return gates;
    }
 
    private final ArrayList<Gate> gates;
}
