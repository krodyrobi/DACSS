package EventBus;

public abstract class Event {
    protected Object data;

    public Event(Object data) {
        this.data = data;
    }

    public Object getPayload() {
        return data;
    }
}
