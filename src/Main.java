import EventBus.EventBus;
import EventBus.events.Arrived;
import EventBus.events.Done_P;
import EventBus.participants.*;
import EventBus.Subscriber;
import EventBus.Event;
import blackboard.Blackboard;
import blackboard.Controller;
import blackboard.Resource;
import blackboard.bases.*;
import reflection.Inspector;

import java.io.IOException;
import java.util.List;

public class Main {
    //C (cut seats) - F(assemble feet) - B (assemble backrest) - S (assemble stabilizer bar) - P (package)
    public static void main(String args[]) {
        //Main.doBlackboard();
        //Main.doEventBus();
        Main.doInspector(args);
    }

    private static void doInspector(String[] args) {
        if (args.length != 1) {
            System.out.println("Jar Path missing");
            return;
        }

        Inspector ins;
        try {
            ins = new Inspector(args[0]);
        } catch (IOException e) {
            System.out.println("Invalid jar at location: " + args[0]);
            return;
        }

        ins.report();
    }


    private static void doEventBus() {
        Object[] workers = {new B(), new C(), new F(), new P(), new S(), new END()};

        String chair = "Chair(1): ";
        Arrived event = new Arrived(chair);

        EventBus.instance().publish(event);

    }

    private static void doBlackboard() {
        Blackboard b = Blackboard.instance();
        List<Resource> res = b.getResources();
        res.add(new Resource(""));
        res.add(new Resource(""));
        res.add(new Resource(""));
        Controller c = new Controller(b);
        c.addKB(new Packager());
        c.addKB(new Stabilizer_asm());
        c.addKB(new Back_asm());
        c.addKB(new Feet_asm());
        c.addKB(new SeatCutter());
        c.addKB(new SeatCutter());

        c.run();
        c.run();
        c.run();
        c.run();
        c.run();
        System.out.println(res);
    }


    static class END implements Subscriber {


        public END() {
            Done_P f = new Done_P();
            EventBus.instance().subscribe(f, this);
        }

        public void inform(Event event) {
            Done_P e = (Done_P) event;

            //we know the payload
            String data = (String) event.getPayload();
            data += " finished";

            System.out.println(data);
        }
    }
}
