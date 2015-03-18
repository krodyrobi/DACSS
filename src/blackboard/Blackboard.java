package blackboard;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {
    private static Blackboard ourInstance = new Blackboard();
    private Controller controller;
    private List<Resource> res;



    public void setController(Controller c) {
        this.controller = c;
    }
    public Controller getController() {
        return controller;
    }


    public static Blackboard instance() {
        return ourInstance;
    }
    private Blackboard() { res = new ArrayList<Resource>(); }

    public List<Resource> getResources() {
        return res;
    }
}
