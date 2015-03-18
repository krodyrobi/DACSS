package EventBus.events;

import EventBus.Event;

public class Done_S extends Event {
    public Done_S(Object data) {
        super(data);
    }

    public Done_S() {
        super(null);
    }
}
