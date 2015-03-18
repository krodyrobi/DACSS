package blackboard.bases;

import blackboard.KnowledgeBase;
import blackboard.Resource;


public class Back_asm extends KnowledgeBase {

    @Override
    public boolean execCondition(Resource r) {
        String data = r.getData();
        return (data.contains("F") && !data.contains("B"));
    }

    @Override
    public void execAction(Resource r) {
        r.setData(r.getData() + "B");
    }
}
