package ovh.vicart.messaging.sample.viewmodel;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ovh.vicart.messaging.MessagingAPI;
import ovh.vicart.messaging.models.User;

import java.util.stream.Collectors;

public class RootViewModel {

    private final ObservableList<String> observableOnlineUsers = FXCollections.observableArrayList();
    private final MessagingAPI api;

    public RootViewModel() {
        api = MessagingAPI.getInstanceFor("tessske");
        api.getOnlineUsers().thenAccept(list -> {
            observableOnlineUsers.addAll(list.stream().map(User::getUsername).collect(Collectors.toList()));
        });
    }

    public SimpleListProperty<String> getOnlineUsers() {
        return new SimpleListProperty<>(observableOnlineUsers);
    }
}
