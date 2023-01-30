module by.yms.ymsdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires retrofit;
    requires converter.jackson;
    requires gson;
    requires lombok;
    requires okhttp;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.commons.lang3;

    opens by.yms.ymsdemo to javafx.fxml;
    exports by.yms.ymsdemo;
    exports by.yms.ymsdemo.network;
    opens by.yms.ymsdemo.network to javafx.fxml;
}