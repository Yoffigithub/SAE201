module adrien.s201 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens adrien.s201 to javafx.fxml;
    exports adrien.s201;
}