package teama.service;

import java.util.ArrayList;
import java.util.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import static spark.Spark.*;

/**
 * Main Service class
 *
 * @author jaksurma, MarcinPultyn
 */
public class Service {

    public static void main(String[] args) {
        Service service = new Service();
        Gson gson = new Gson();

        // sets server's port
        port(8080);

        // GET for gates list
        get("/gates", (req, res) -> {
            res.type("application/json");
            res.header("Access-Control-Allow-Origin", "*");
            return gson.toJson(new GateList(service.getGates()));
        });

        // GET for a selected gate
        get("/gates/selected", (req, res) -> {
            res.type("application/json");
            res.header("Access-Control-Allow-Origin", "*");
            if (service.getCurrentGate().isPresent()) {
                return gson.toJson(new SelectedGate(service.getCurrentGate().get().getNumber()));
            } else {
                res.status(400);
                return gson.toJson(new ErrorResponse("Captain did not select a gate"));
            }
        });

        // POST to select a gate
        post("/gates", (req, res) -> {
            res.type("application/json");
            res.header("Access-Control-Allow-Origin", "*");
            
            SelectedGate selected;
            try {
                selected = gson.fromJson(req.body(), SelectedGate.class);
            } catch (JsonSyntaxException e) {
                res.status(400);
                return gson.toJson(new ErrorResponse("Invalid Gate Number"));
            }

            Optional<Gate> current = service.getGateByNumber(selected.getGate());
            if (!current.isPresent()) {
                res.status(400);
                return gson.toJson(new ErrorResponse("Invalid Gate Number"));
            }

            service.setCurrentGate(current.get());
            res.status(200);
            return "{}";
        });

    }

    public Service() {
        gates.add(new Gate(28, 54.38200, 18.46196));
        gates.add(new Gate(26, 54.38170, 18.46315));
        gates.add(new Gate(24, 54.38142, 18.46431));
        gates.add(new Gate(22, 54.38113, 18.46548));
    }

    public ArrayList<Gate> getGates() {
        return gates;
    }

    public Optional<Gate> getGateByNumber(int number) {
        return gates.stream().filter(gate -> gate.getNumber() == number).findFirst();
    }

    public Optional<Gate> getCurrentGate() {
        return currentGate;
    }

    public void setCurrentGate(Gate gate) {
        currentGate = Optional.ofNullable(gate);
    }

    private final ArrayList<Gate> gates = new ArrayList<>();
    private Optional<Gate> currentGate = Optional.empty();
}
