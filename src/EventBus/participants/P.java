package EventBus.participants;

import EventBus.Event;
import EventBus.EventBus;
import EventBus.Subscriber;
import EventBus.events.Done_P;
import EventBus.events.Done_S;

public class P implements Subscriber {

    public P() {
        Done_S f = new Done_S();
        EventBus.instance().subscribe(f, this);
    }

    public void inform(Event event) {
        Done_S e = (Done_S) event;

        //we know the payload
        String data = (String) event.getPayload();
        data += 'P';

        Done_P new_event = new Done_P(data);
        EventBus.instance().publish(new_event);
    }
}
