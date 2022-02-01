package ovh.vicart.messagingkt.sample.view

import javafx.scene.control.TextInputDialog

class UsernameDialog : TextInputDialog() {

    init {
        this.headerText = "Entrez votre nom d'utilisateur"
    }
}