package blackboard.bases;

import blackboard.KnowledgeBase;
import blackboard.Resource;

public class Feet_asm extends KnowledgeBase {

    @Override
    public boolean execCondition(Resource r) {
        String data = r.getData();
        return (data.equals("C") && !data.contains("F"));
    }

    @Override
    public void execAction(Resource r) {
        r.setData(r.getData() + "F");
    }
}
