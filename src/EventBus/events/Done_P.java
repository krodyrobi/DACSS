package EventBus.events;

import EventBus.Event;

public class Done_P extends Event {
    public Done_P(Object data) {
        super(data);
    }

    public Done_P() {
        super(null);
    }
}
