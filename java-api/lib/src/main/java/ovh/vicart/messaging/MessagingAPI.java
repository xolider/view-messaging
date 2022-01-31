package ovh.vicart.messaging;

import org.json.JSONArray;
import org.json.JSONObject;
import ovh.vicart.messaging.api.RestAPI;
import ovh.vicart.messaging.models.Message;
import ovh.vicart.messaging.models.User;
import ovh.vicart.messaging.utils.BackendConfiguration;
import ovh.vicart.messaging.utils.TimeFormatter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * The main class of the library.
 * <p>
 *     This class allows developers to use backend API to create a messaging system
 * </p>
 */
public class MessagingAPI {

    private final RestAPI api = new RestAPI();

    private static User currentUser;

    /**
     * Get online {@link User}s
     * @return An async task that executes the operation and wait asynchronously for it to be finished
     */
    public CompletableFuture<List<User>> getOnlineUsers() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> list = new ArrayList<>();
            try {
                String json = api.getOnlineUsers();
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String username = obj.getString("username");
                    LocalDateTime lastLogin = OffsetDateTime.parse(obj.getString("last_login")).toLocalDateTime();
                    int id = obj.getInt("id");
                    User user = new User(id, username, lastLogin);
                    list.add(user);
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    /**
     * Get the last 20 {@link Message}s between two users. The limit is defined by the backend API
     * @param fromUser User who we want to get the messages from (received and sent)
     * @return An async task that executes the operation and wait asynchronously for it to be finished
     */
    public CompletableFuture<List<Message>> getMessages(final User fromUser) {
        return CompletableFuture.supplyAsync(() -> {
           List<Message> list = new ArrayList<>();
            try {
                String json = api.getMessages(currentUser.getId(), fromUser.getId());
                JSONArray array = new JSONArray(json);
                for(int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    int id = obj.getInt("id");
                    String message = obj.getString("msg");
                    LocalDateTime dateTime = LocalDateTime.parse(obj.getString("datetime"), TimeFormatter.MYSQL_DATETIME);
                    int from_user_id = obj.getInt("from_id");
                    String from_username = obj.getString("from_username");
                    LocalDateTime from_last_login = LocalDateTime.parse(obj.getString("from_last_login"), TimeFormatter.MYSQL_DATETIME);
                    int to_user_id = obj.getInt("to_id");
                    String to_username = obj.getString("to_username");
                    LocalDateTime to_last_login = LocalDateTime.parse(obj.getString("to_last_login"), TimeFormatter.MYSQL_DATETIME);
                    User from_user = new User(from_user_id, from_username, from_last_login);
                    User to_user = new User(to_user_id, to_username, to_last_login);
                    Message m = new Message(id, from_user, to_user, message, dateTime);
                    list.add(m);
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    /**
     * Send a message to a {@link User}
     * @param toUser The user we want to send a message to
     * @param message The message we want to send
     * @return An async task that executes the operation and wait asynchronously for it to be finished
     */
    public CompletableFuture<Void> sendMessage(final User toUser, final String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                api.postMessage(currentUser.getId(), toUser.getId(), message);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Get the current logged-in user
     * @return The current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Log in the current user with the provided username
     * @param username The username we want to use for messaging
     * @return An async task that executes the operation and wait asynchronously for it to be finished
     */
    private CompletableFuture<Void> sendCurrentUser(final String username) {
        return CompletableFuture.runAsync(() -> {
            try {
                String json = api.postUsername(username);
                JSONObject obj = new JSONObject(json);
                if(obj.has("error")) throw new Exception("L'utilisateur existe déjà");
                obj = obj.getJSONObject("insertedUser");
                int id = obj.getInt("id");
                LocalDateTime lastLogin = LocalDateTime.parse(obj.getString("last_login"), TimeFormatter.MYSQL_DATETIME);
                currentUser = new User(id, username, lastLogin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void configure(final BackendConfiguration bc) {
        RestAPI.BASE_URL = "http://" + bc.getHost() + ":" + bc.getPort();
    }

    private MessagingAPI() {

    }

    /**
     * Get an instance of the {@link MessagingAPI} class by providing a username
     * @param username The username we want to use
     * @return An instance of {@link MessagingAPI}
     */
    public static MessagingAPI getInstanceFor(final String username) {
        MessagingAPI api = new MessagingAPI();
        api.sendCurrentUser(username);
        return api;
    }
}
