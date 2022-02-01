package ovh.vicart.messagingkt.sample

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class App : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Messaging sample - Kotlin"
        primaryStage.width = 800.0
        primaryStage.height = 600.0

        val loader = FXMLLoader()
        loader.location = javaClass.classLoader.getResource("root.fxml")
        val pane = loader.load<AnchorPane>()
        val scene = Scene(pane)
        primaryStage.scene = scene

        primaryStage.show()
    }
}