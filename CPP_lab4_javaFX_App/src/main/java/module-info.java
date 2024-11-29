module lab3.cpp_lab4_javafx_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.jsoup;
    requires java.sql;
    requires java.desktop;

    opens lab3.cpp_lab4_javafx_app to javafx.fxml;
    exports lab3.cpp_lab4_javafx_app;
}