package ml.huytools.ycnanswer.Core.Game.Event;

public class Event {
    public enum EventType { Touch }
    private EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }
}
