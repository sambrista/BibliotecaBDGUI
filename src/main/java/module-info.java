module es.iesfranciscodelosrios.bibliotecabdgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.iesfranciscodelosrios.bibliotecabdgui to javafx.fxml;
    exports es.iesfranciscodelosrios.bibliotecabdgui;
}