module andrei.pt_30424_pelle_andrei_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires org.postgresql.jdbc;

    opens andrei to javafx.fxml;
    exports andrei;
    exports andrei.model;
    opens andrei.model to javafx.fxml;
    exports andrei.connection;
    opens andrei.connection to javafx.fxml;
    exports andrei.data;
    opens andrei.data to javafx.fxml;
    exports andrei.Controller;
    opens andrei.Controller to javafx.fxml;
//    opens andrei.GUI to javafx.fxml;
}