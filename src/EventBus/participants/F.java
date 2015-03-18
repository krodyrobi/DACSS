package EventBus.participants;

import EventBus.Event;
import EventBus.EventBus;
import EventBus.Subscriber;
import EventBus.events.Done_C;
import EventBus.events.Done_F;

public class F implements Subscriber {

    public F() {
        Done_C f = new Done_C();
        EventBus.instance().subscribe(f, this);
    }

    public void inform(Event event) {
        Done_C e = (Done_C) event;

        //we know the payload
        String data = (String) event.getPayload();
        data += 'F';

        Done_F new_event = new Done_F(data);
        EventBus.instance().publish(new_event);
    }
}
