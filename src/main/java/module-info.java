module com.jetbrains.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    //requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.fasterxml.jackson.databind;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires java.sql;

    opens com.jetbrains.demo1.controller to javafx.fxml;
    opens com.jetbrains.demo1.data to javafx.base;

    exports com.jetbrains.demo1;

}