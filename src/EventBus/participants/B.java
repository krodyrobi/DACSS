package EventBus.participants;

import EventBus.Event;
import EventBus.EventBus;
import EventBus.Subscriber;
import EventBus.events.Done_B;
import EventBus.events.Done_F;

public class B implements Subscriber {

    public B() {
        Done_F f = new Done_F();
        EventBus.instance().subscribe(f, this);
    }

    public void inform(Event event) {
        Done_F e = (Done_F) event;

        //we know the payload
        String data = (String) event.getPayload();
        data += 'B';

        Done_B new_event = new Done_B(data);
        EventBus.instance().publish(new_event);
    }
}
