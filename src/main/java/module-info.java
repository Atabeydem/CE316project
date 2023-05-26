module com.example.ce316_project_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.ce316_project_test to javafx.fxml;
    exports com.example.ce316_project_test;
    //exports com.example.ce316_project_test.db;
    //opens com.example.ce316_project_test.db to javafx.fxml;
}