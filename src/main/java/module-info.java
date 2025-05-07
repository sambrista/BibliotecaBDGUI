module es.iesfranciscodelosrios.bibliotecabdgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;


    opens es.iesfranciscodelosrios.bibliotecabdgui to javafx.fxml;
    exports es.iesfranciscodelosrios.bibliotecabdgui;
    opens es.iesfranciscodelosrios.bibliotecabdgui.controller to javafx.fxml;
    exports es.iesfranciscodelosrios.bibliotecabdgui.controller to javafx.fxml;
    opens es.iesfranciscodelosrios.bibliotecabdgui.baseDatos to java.xml.bind;
}