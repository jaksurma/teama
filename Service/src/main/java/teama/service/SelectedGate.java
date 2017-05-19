package teama.service;

/**
 * Class for POST/GET JSON request/response
 * @author MarcinPultyn
 */
public class SelectedGate {
    public SelectedGate(int gate) {
        this.gate = gate;
    }

    public int getGate() {
        return gate;
    }
    
    private final int gate;
}
