package EventBus.events;

import EventBus.Event;

public class Arrived extends Event {
    public Arrived(Object data) {
        super(data);
    }

    public Arrived() {
        super(null);
    }
}
