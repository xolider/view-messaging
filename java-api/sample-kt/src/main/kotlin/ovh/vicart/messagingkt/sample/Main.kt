package ovh.vicart.messagingkt.sample

import javafx.application.Application
import ovh.vicart.messaging.utils.BackendConfiguration
import ovh.vicart.messagingkt.MessagingAPI
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    MessagingAPI.configure(BackendConfiguration.Builder().host("localhost").port(8081).build())
    Application.launch(App::class.java, *args)
}