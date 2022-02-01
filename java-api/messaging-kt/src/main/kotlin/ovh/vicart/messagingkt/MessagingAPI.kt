package ovh.vicart.messagingkt

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import ovh.vicart.messaging.models.Message
import ovh.vicart.messaging.models.User
import ovh.vicart.messaging.utils.BackendConfiguration
import ovh.vicart.messaging.utils.TimeFormatter
import ovh.vicart.messagingkt.api.RestApi
import java.time.LocalDateTime
import java.time.OffsetDateTime

class MessagingAPI private constructor() {

    private val api = RestApi()

    companion object {
        private lateinit var currentUser: User

        fun configure(config: BackendConfiguration) {
            RestApi.BASE_URL = "http://${config.host}:${config.port}"
        }

        fun getInstanceFor(username: String): MessagingAPI = runBlocking {
            val api = MessagingAPI()
            launch {
                api.sendCurrentUser(username)
            }
            api
        }
    }

    suspend fun getOnlineUsers() : List<User> {
        val json = api.getOnlineUsers()
        return JSONArray(json).map {
            it as JSONObject
            User(it.getInt("id"), it.getString("username"), OffsetDateTime.parse(it.getString("last_login")).toLocalDateTime())
        }
    }

    suspend fun getMessages(userFrom: User) : List<Message> {
        val json = api.getMessages(currentUser.id, userFrom.id)
        return JSONArray(json).map {
            it as JSONObject
            val fromUser = User(it.getInt("from_id"), it.getString("from_username"), OffsetDateTime.parse(it.getString("from_last_login")).toLocalDateTime())
            val toUser = User(it.getInt("to_id"), it.getString("to_username"), OffsetDateTime.parse(it.getString("to_last_login")).toLocalDateTime())
            Message(it.getInt("id"), fromUser, toUser, it.getString("msg"), OffsetDateTime.parse(it.getString("datetime")).toLocalDateTime())
        }
    }

    suspend fun sendMessage(toUser: User, message: String) {
        api.postMessage(currentUser.id, toUser.id, message)
    }

    fun getCurrentUser(): User = currentUser

    private suspend fun sendCurrentUser(username: String) {
        val json = api.postUsername(username)
        var obj = JSONObject(json)
        if(obj.has("error")) throw Exception("Cet utilisateur existe déjà")
        obj = obj.getJSONObject("insertedUser")
        currentUser = User(obj.getInt("id"), obj.getString("username"), LocalDateTime.parse(obj.getString("last_login"),
            TimeFormatter.MYSQL_DATETIME))
    }
}