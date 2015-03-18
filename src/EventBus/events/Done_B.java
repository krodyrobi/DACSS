package EventBus.events;

import EventBus.Event;

public class Done_B extends Event {
    public Done_B(Object data) {
        super(data);
    }

    public Done_B() {
        super(null);
    }
}
