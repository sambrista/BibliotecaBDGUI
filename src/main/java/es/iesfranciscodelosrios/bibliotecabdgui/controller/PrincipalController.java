package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.DAO.AutorDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.DAO.LibroDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class PrincipalController {
    public Label tituloLbl;
    public Label isbnLbl;
    public Label autorLbl;
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

    public void mostrarLibroSeleccionado(MouseEvent mouseEvent) {
        Libro libroSeleccionado = librosLst.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            Autor autor = AutorDAO.findByBookId(libroSeleccionado.getIdLibro());
            libroSeleccionado.setAutor(autor);
            tituloLbl.setText(libroSeleccionado.getTitulo());
            isbnLbl.setText(libroSeleccionado.getIsbn());
            autorLbl.setText(libroSeleccionado.getAutor().getNombreAutor());
        }
    }
}
