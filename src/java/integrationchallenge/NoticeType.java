package integrationchallenge;

public enum NoticeType {
    DEACTIVATED,
    REACTIVATED,
    CLOSED;
    
    public static NoticeType fromValue(String type) {
        return NoticeType.valueOf(type);
    }
}
