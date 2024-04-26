module com.example.instaljavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.instaljavafx to javafx.fxml;
    exports com.example.instaljavafx;
}