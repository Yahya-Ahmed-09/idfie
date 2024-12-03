module com.example.idfie {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires itextpdf;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires mongo.java.driver;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    opens com.example.idfie to javafx.fxml;
    exports com.example.idfie;
}