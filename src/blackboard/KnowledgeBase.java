package blackboard;

public abstract class KnowledgeBase {
    public abstract boolean execCondition(Resource resource);
    public abstract void execAction(Resource resource);
}
