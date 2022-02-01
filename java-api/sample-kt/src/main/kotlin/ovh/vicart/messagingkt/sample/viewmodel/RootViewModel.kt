package ovh.vicart.messagingkt.sample.viewmodel

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import kotlinx.coroutines.*
import ovh.vicart.messaging.models.User
import ovh.vicart.messagingkt.MessagingAPI

class RootViewModel {

    private lateinit var api: MessagingAPI

    private lateinit var users: List<User>
    private val observableOnlineUsers = FXCollections.observableArrayList<String>()
    val onlineUsers: SimpleListProperty<String> = SimpleListProperty(observableOnlineUsers)

    val selectedUserIndex = SimpleIntegerProperty(-1)
    val currentUsername = SimpleStringProperty()

    val messages = SimpleStringProperty("")

    init {
        selectedUserIndex.addListener { _, _, newValue ->
            messages.set("")
            CoroutineScope(Dispatchers.Default).launch {
                val msgs = api.getMessages(users[newValue.toInt()])
                println(msgs)
                messages.set(msgs.map { it.message }.joinToString("\n"))
            }
        }
    }

    fun loginInAPI(username: String) {
        api = MessagingAPI.getInstanceFor(username)
    }

    fun fetchOnlineUsers() {
        CoroutineScope(Dispatchers.Default).launch {
            users = api.getOnlineUsers()
            observableOnlineUsers.setAll(users.map { it.username })
        }
    }
}