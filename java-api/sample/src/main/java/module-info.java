module ovh.vicart.messaging.sample {
    requires ovh.vicart.messaging;
    requires javafx.fxml;
    requires javafx.controls;
    opens ovh.vicart.messaging.sample.view to javafx.fxml;
    exports ovh.vicart.messaging.sample;
}