package ovh.vicart.messaging.sample;

import javafx.application.Application;
import ovh.vicart.messaging.MessagingAPI;
import ovh.vicart.messaging.utils.BackendConfiguration;

public class Main {

    public static void main(String... args) {
        MessagingAPI.configure(new BackendConfiguration.Builder().host("localhost").port(8081).build());
        Application.launch(App.class, args);
    }
}
