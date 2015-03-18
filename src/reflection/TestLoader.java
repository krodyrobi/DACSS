package reflection;

import blackboard.Blackboard;
import blackboard.KnowledgeBase;
import blackboard.Resource;

import java.util.ArrayList;
import java.util.List;

public class TestLoader implements Cloneable {
    private Blackboard blackboard;
    private List<KnowledgeBase> kbs;
    public String test = "10";

    public void run() {
        clearReservations();
        execute(reserve());
    }

    private void clearReservations() {
        List<Resource> res = blackboard.getResources();
        for(Resource r: res) {
            if (r.isReserved() && r.isIdle())
                r.unreserve();
        }
    }

    private List<Reservation> reserve() {
        List<Reservation> toExec = new ArrayList<Reservation>();
        List<Resource> resources = blackboard.getResources();

        for(KnowledgeBase k: kbs) {
            for (Resource r : resources) {
                if (r.isReserved() || !r.isIdle())
                    continue;

                if (k.execCondition(r)) {
                    toExec.add(new Reservation(k, r));
                    r.reserve();
                    r.toWorking();

                    break;
                }
            }
        }

        return toExec;
    }

    private void execute(List<Reservation> reservations) {
        for(Reservation res: reservations) {
            Resource r = res.resource;

            res.kb.execAction(r);
            r.toIdle();
            r.unreserve();
        }
    }


    public TestLoader(Blackboard b) {
        this.blackboard = b;
        this.kbs = new ArrayList<KnowledgeBase>();
    }

    public void addKB(KnowledgeBase kb) {
        kbs.add(kb);
    }

    public void removeKB(KnowledgeBase kb) {
        kbs.remove(kb);
    }


    class Reservation {
        KnowledgeBase kb;
        Resource resource;

        public Reservation(KnowledgeBase kb, Resource res) {
            this.resource = res;
            this.kb = kb;
        }
    }
}
