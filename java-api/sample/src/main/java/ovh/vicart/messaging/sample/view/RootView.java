package ovh.vicart.messaging.sample.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import ovh.vicart.messaging.sample.viewmodel.RootViewModel;

public class RootView {

    @FXML
    public ListView<String> list;

    private final RootViewModel vm = new RootViewModel();

    public void initialize() {
        list.itemsProperty().bind(vm.getOnlineUsers());
    }
}
