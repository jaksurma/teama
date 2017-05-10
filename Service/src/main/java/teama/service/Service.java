package teama.service;

import java.util.ArrayList;
import com.google.gson.Gson;
import static spark.Spark.*;

public class Service 
{
    final static ArrayList<Gate> gates = new ArrayList<>();
    final static ArrayList<String> communicates = new ArrayList<>();
    private int currentGateNumber = 0;
    
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        
        gates.add(new Gate(28, 54.38200, 18.46196));
        gates.add(new Gate(26, 54.38170, 18.46315));
        gates.add(new Gate(24, 54.38142, 18.46431));
        gates.add(new Gate(22, 54.38113, 18.46548));
        
        // sets server's port
        port(8080);
             
        // GET for gates list
        get("/gates", (req, res) -> gson.toJson(new GateList(gates)));
        
    }
}
