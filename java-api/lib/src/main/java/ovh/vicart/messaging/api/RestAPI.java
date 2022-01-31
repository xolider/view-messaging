package ovh.vicart.messaging.api;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * API Class that we use for calling the back-end API, returning all
 * responses in {@link String}
 */
public class RestAPI {

    private final HttpClient client = HttpClient.newHttpClient();

    public static String BASE_URL = "http://localhost:8081"; //Base url of the backend

    /**
     * Get the online users from the backend API
     * <p>
     *     The order of the IDs passed to the function doesn't matter, as the backend will check for both orders.
     * </p>
     * @return The body of the HTTP response
     * @throws URISyntaxException X
     * @throws IOException X
     * @throws InterruptedException X
     */
    public String getOnlineUsers() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = getRequestFor("/users/online");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Tell the backend API that we are logging in with the provided username
     * @param username The username we are going to use
     * @return The body of the HTTP response
     * @throws URISyntaxException X
     * @throws IOException X
     * @throws InterruptedException X
     */
    public String postUsername(final String username) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = postRequestFor("/user", new JSONObject().put("username", username));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Get the messages between two users from the backend API
     * @param userFromId First user ID
     * @param userToId Second user ID
     * @return The body of the HTTP response
     * @throws URISyntaxException X
     * @throws IOException X
     * @throws InterruptedException X
     */
    public String getMessages(final int userFromId, final int userToId) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = getRequestFor("/messages/" + userFromId + "/" + userToId);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Send a message to a user through the backend API
     * @param fromUserId The current logged-in user ID
     * @param toUserId The user ID which we want to send the message to
     * @param message The message to be sent
     * @return The body of the HTTP response
     * @throws URISyntaxException X
     * @throws IOException X
     * @throws InterruptedException X
     */
    public String postMessage(final int fromUserId, final int toUserId, final String message) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = postRequestFor("/message/" + toUserId, new JSONObject().put("userid", fromUserId)
                .put("msg", message));
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private HttpRequest getRequestFor(final String url) throws URISyntaxException {
        return HttpRequest.newBuilder().uri(new URI(BASE_URL + url))
                .GET().build();
    }

    private HttpRequest postRequestFor(final String url, final JSONObject body) throws URISyntaxException {
        return HttpRequest.newBuilder().uri(new URI(BASE_URL + url))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json").build();
    }
}
