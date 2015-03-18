package blackboard.bases;

import blackboard.KnowledgeBase;
import blackboard.Resource;


public class Packager extends KnowledgeBase {

    @Override
    public boolean execCondition(Resource r) {
        String data = r.getData();
        return (data.contains("C") &&
                data.contains("B") &&
                data.contains("S") &&
                data.contains("F") && !data.contains("P"));
    }

    @Override
    public void execAction(Resource r) {
        r.setData(r.getData() + "P");
    }
}
