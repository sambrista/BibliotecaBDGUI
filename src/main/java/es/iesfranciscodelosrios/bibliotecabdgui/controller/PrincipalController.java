package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.DAO.AutorDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.DAO.LibroDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.MainApplication;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PrincipalController {
    public Label tituloLbl;
    public Label isbnLbl;
    public Label autorLbl;
    public Button editarBtn;
    public Button borrarBtn;
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
            if (libroSeleccionado.getAutor() == null){
                Autor autor = AutorDAO.findByBookId(libroSeleccionado.getIdLibro());
                libroSeleccionado.setAutor(autor);
            }
            actualizarInterfazLibroSeleccionado(libroSeleccionado);
        }
    }

    private void actualizarInterfazLibroSeleccionado(Libro libro) {
        if (libro != null) {
            tituloLbl.setText(libro.getTitulo());
            isbnLbl.setText(libro.getIsbn());
            autorLbl.setText(libro.getAutor().getNombreAutor());
            editarBtn.setDisable(false);
            borrarBtn.setDisable(false);
        } else  {
            tituloLbl.setText("");
            isbnLbl.setText("");
            autorLbl.setText("");
            editarBtn.setDisable(true);
            borrarBtn.setDisable(true);
        }
    }

    public void borrarLibroSeleccionado(ActionEvent actionEvent) {
        Libro libroSeleccionado = librosLst.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            LibroDAO.deleteLibroById(libroSeleccionado);
            librosLst.getItems().remove(libroSeleccionado);
            librosLst.getSelectionModel().clearSelection();
            actualizarInterfazLibroSeleccionado(null);
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
        actualizarInterfazLibroSeleccionado(null);
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
        librosLst.refresh();
        actualizarInterfazLibroSeleccionado(librosLst.getSelectionModel().getSelectedItem());
    }

    public void cerrarAplicacion(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void mostrarDialogoAcercaDe(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("BibliotecaBD GUI 1.0");
        alert.setContentText("BibliotecaBD GUI es una aplicación que permite usar una interfaz gráfica para consultar la base de datos de una biblioteca. Ha sido desarrollada por Alfonso Jiménez Vílchez.");
        alert.show();
    }
}
