package integrationchallenge;

public enum EventType {
    
    SUBSCRIPTION_ORDER,
    SUBSCRIPTION_CANCEL,
    SUBSCRIPTION_CHANGE,
    SUBSCRIPTION_NOTICE,
    USER_ASSIGNMENT,
    USER_UNASSIGNMENT;
    
    public static EventType fromValue(String eventType) {
        return EventType.valueOf(eventType);
    }
}
