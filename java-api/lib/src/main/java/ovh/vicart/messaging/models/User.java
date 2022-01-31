package ovh.vicart.messaging.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class User {

    private final int id;
    private final String username;
    private final LocalDateTime lastLogin;

    public User(final int id, final String username, final LocalDateTime lastLogin) {
        this.username = username;
        this.lastLogin = lastLogin;
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public int getId() {
        return this.id;
    }
}
