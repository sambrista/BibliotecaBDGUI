package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.DAO.AutorDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.DAO.LibroDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.MainApplication;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
            mostrarInformacionLibro(libroSeleccionado);
        }
    }

    private void mostrarInformacionLibro(Libro libro) {
        if (libro != null) {
            tituloLbl.setText(libro.getTitulo());
            isbnLbl.setText(libro.getIsbn());
            autorLbl.setText(libro.getAutor().getNombreAutor());
        } else  {
            tituloLbl.setText("");
            isbnLbl.setText("");
            autorLbl.setText("");
        }
    }

    public void borrarLibroSeleccionado(ActionEvent actionEvent) {
        Libro libroSeleccionado = librosLst.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            LibroDAO.deleteLibroById(libroSeleccionado);
            librosLst.getItems().remove(libroSeleccionado);
            librosLst.getSelectionModel().clearSelection();
            mostrarInformacionLibro(null);
        }
    }

    public void anadirLibro(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("libroForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir Libro");
        stage.setResizable(false);
        stage.showAndWait();
        List<Libro> libros = LibroDAO.findAll();
        librosLst.getItems().setAll(libros);
    }

    public void editarLibro(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("libroForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LibroFormController controlador = fxmlLoader.getController();
        controlador.setLibro(librosLst.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Editar Libro");
        stage.setResizable(false);
        stage.showAndWait();
    }
}
