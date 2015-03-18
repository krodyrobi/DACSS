package EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static EventBus instance;
    private List<EventBundle> eventBundles;


    public void publish(Event event) {
        for(EventBundle bundle: eventBundles) {
            if( bundle.event.getClass().isInstance(event) )
                bundle.subscriber.inform(event);
        }
    }

    public void subscribe(Event event, Subscriber subscriber) {
        eventBundles.add(new EventBundle(event, subscriber));
    }

    public void unsubscribe(Event event, Subscriber subscriber) {
        eventBundles.remove(new EventBundle(event, subscriber));
    }


    private EventBus() {
        eventBundles = new ArrayList<EventBundle>();
    }

    public static EventBus instance() {
        if( instance == null )
            instance = new EventBus();

        return instance;
    }


    class EventBundle {
        Event event;
        Subscriber subscriber;

        public EventBundle(Event event, Subscriber subscriber) {
            this.event = event;
            this.subscriber = subscriber;
        }

        public boolean equals(Object object) {
            if( !(object instanceof EventBundle) )
                return false;

            EventBundle that = (EventBundle) object;
            return event.equals(that.event) && subscriber.equals(that.subscriber);
        }
    }
}
