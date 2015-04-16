package integrationchallenge;

public enum EventType {
    
    SUBSCRIPTION_ORDER,
    SUBSCRIPTION_CANCEL,
    SUBSCRIPTION_CHANGE,
    SUBSCRIPTION_NOTICE;
    
    public static EventType fromValue(String eventType) {
        return EventType.valueOf(eventType);
    }
}
