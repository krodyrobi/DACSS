package EventBus.participants;

import EventBus.Event;
import EventBus.EventBus;
import EventBus.Subscriber;
import EventBus.events.Done_B;
import EventBus.events.Done_S;

public class S implements Subscriber {

    public S() {
        Done_B f = new Done_B();
        EventBus.instance().subscribe(f, this);
    }

    public void inform(Event event) {
        Done_B e = (Done_B) event;

        //we know the payload
        String data = (String) event.getPayload();
        data += 'S';

        Done_S new_event = new Done_S(data);
        EventBus.instance().publish(new_event);
    }
}
