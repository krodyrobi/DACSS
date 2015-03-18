package EventBus.events;

import EventBus.Event;

public class Done_C extends Event {
    public Done_C(Object data) {
        super(data);
    }

    public Done_C() {
        super(null);
    }
}
