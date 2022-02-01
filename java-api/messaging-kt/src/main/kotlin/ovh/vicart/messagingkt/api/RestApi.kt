package ovh.vicart.messagingkt.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import org.json.JSONObject

class RestApi {

    private val client = HttpClient(CIO)

    companion object {
        var BASE_URL = "http://localhost:8081"
    }

    suspend fun getOnlineUsers() : String {
        val response = getRequestFor("/users/online")
        return response.readText()
    }

    suspend fun postUsername(username: String) : String {
        val response = postRequestFor("/user", JSONObject().put("username", username))
        return response.readText()
    }

    suspend fun getMessages(userFromId: Int, userToId: Int) : String {
        val response = getRequestFor("/messages/$userFromId/$userToId")
        return response.readText()
    }

    suspend fun postMessage(fromUserId: Int, toUserId: Int, message: String) : String {
        val response = postRequestFor("/message/$toUserId", JSONObject().put("userid", fromUserId).put("msg", message))
        return response.readText()
    }

    private suspend fun getRequestFor(url: String) : HttpResponse {
        return client.get(BASE_URL + url)
    }

    private suspend fun postRequestFor(url: String, body: JSONObject) : HttpResponse {
        return client.post(BASE_URL + url) {
            this.body = body.toString()
            this.header("Content-Type", "application/json")
        }
    }
}