package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.DAO.LibroDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class PrincipalController {
    @FXML
    private ListView<Libro> librosLst;

    public void initialize() {
        librosLst.setCellFactory( lv -> new ListCell<Libro>() {
            protected void updateItem(Libro libro, boolean empty) {
                super.updateItem(libro, empty);
                if (empty || libro == null) {
                    setText(null);
                } else {
                    setText(libro.getTitulo());
                }
            }
        });

        List<Libro> libros = LibroDAO.findAll();
        librosLst.getItems().setAll(libros);
    }
}
