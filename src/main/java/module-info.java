module adrien.sae_201 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens adrien.sae_201 to javafx.fxml;
    exports adrien.sae_201;
}