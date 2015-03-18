package EventBus.participants;

import EventBus.Event;
import EventBus.EventBus;
import EventBus.Subscriber;
import EventBus.events.Arrived;
import EventBus.events.Done_B;
import EventBus.events.Done_C;
import EventBus.events.Done_F;

public class C implements Subscriber {

    public C() {
        Arrived f = new Arrived();
        EventBus.instance().subscribe(f, this);
    }

    public void inform(Event event) {
        Arrived e = (Arrived) event;

        //we know the payload
        String data = (String) event.getPayload();
        data += 'C';

        Done_C new_event = new Done_C(data);
        EventBus.instance().publish(new_event);
    }
}
