package ovh.vicart.messaging.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class Message {

    private final int id;
    private final User fromUser;
    private final User toUser;
    private final String message;
    private final LocalDateTime dateTime;

    public Message(final int id, final User fromUser, final User toUser, final String message,
                   final LocalDateTime dateTime) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
        this.id = id;
        this.dateTime = dateTime;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
}
