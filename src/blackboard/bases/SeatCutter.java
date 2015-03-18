package blackboard.bases;

import blackboard.KnowledgeBase;
import blackboard.Resource;


public class SeatCutter extends KnowledgeBase {

    @Override
    public boolean execCondition(Resource r) {
        String data = r.getData();
        return (data.contains("") && !data.contains("C"));
    }

    @Override
    public void execAction(Resource r) {
        r.setData(r.getData() + "C");
    }
}
