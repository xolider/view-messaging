package ovh.vicart.messagingkt.sample.view

import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.control.TextInputDialog
import javafx.scene.control.TitledPane
import ovh.vicart.messagingkt.MessagingAPI
import ovh.vicart.messagingkt.sample.viewmodel.RootViewModel

class RootView {

    private val vm = RootViewModel()

    @FXML
    lateinit var list: ListView<String>

    @FXML
    lateinit var messagingTitle: TitledPane

    @FXML
    lateinit var messagesArea: TextArea

    fun initialize() {
        var username: String
        var isValid = false
        while(!isValid) {
            username = UsernameDialog().showAndWait().get()
            try {
                vm.loginInAPI(username)
                isValid = true
            }
            catch(e: Exception) {

            }
        }

        list.itemsProperty().bind(vm.onlineUsers)
        vm.selectedUserIndex.bind(list.selectionModel.selectedIndexProperty())
        messagingTitle.textProperty().bind(Bindings.concat("Conversation avec: ", list.selectionModel.selectedItemProperty()))
        messagesArea.textProperty().bind(vm.messages)
        vm.fetchOnlineUsers()
    }
}