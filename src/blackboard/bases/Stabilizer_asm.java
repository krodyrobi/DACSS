package blackboard.bases;

import blackboard.KnowledgeBase;
import blackboard.Resource;

public class Stabilizer_asm extends KnowledgeBase {

    @Override
    public boolean execCondition(Resource r) {
        String data = r.getData();
        return (data.contains("F") && !data.contains("S"));
    }

    @Override
    public void execAction(Resource r) {
        r.setData(r.getData() + "S");
    }
}
