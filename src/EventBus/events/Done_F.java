package EventBus.events;

import EventBus.Event;

public class Done_F extends Event {
    public Done_F(Object data) {
        super(data);
    }

    public Done_F() {
        super(null);
    }
}
