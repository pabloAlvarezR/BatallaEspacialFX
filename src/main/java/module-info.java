module com.example.batallaespacialpablo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlayer;
    requires javafx.media;


    opens com.example.batallaespacialpablo to javafx.fxml;
    exports com.example.batallaespacialpablo;
}