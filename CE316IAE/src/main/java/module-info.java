module com.example.ce316iae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ce316iae to javafx.fxml;
    exports com.example.ce316iae;
}