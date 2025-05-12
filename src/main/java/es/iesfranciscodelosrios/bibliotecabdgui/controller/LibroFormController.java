package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.DAO.AutorDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.DAO.LibroDAO;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class LibroFormController {
    public Button guardarBtn;
    public Label cabeceraLbl;
    public ChoiceBox<Autor> autorChoicebox;
    public TextArea tituloTxt;
    public TextArea isbnTxt;
    private Libro libro;

    public LibroFormController() {
        libro = null;
    }

    public void initialize() {
        List<Autor> autores = AutorDAO.findAll();
        autorChoicebox.getItems().addAll(autores);

        autorChoicebox.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor autor) {
                return (autor != null) ? autor.getNombreAutor() : "";
            }

            @Override
            public Autor fromString(String string) {
                return null;
            }
        });

        if (libro == null) {
            cabeceraLbl.setText("Añadir libro");
            guardarBtn.setText("Añadir");
        }
    }

        public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        cabeceraLbl.setText("Editar libro");
        guardarBtn.setText("Editar");
        tituloTxt.setText(libro.getTitulo());
        isbnTxt.setText(libro.getIsbn());
        autorChoicebox.getSelectionModel().select(libro.getAutor());
    }

    public void cerrarFormularioLibro(ActionEvent actionEvent) {
        Stage stage = (Stage) guardarBtn.getScene().getWindow();
        stage.close();
    }

    public void guardarLibro(ActionEvent actionEvent) {
        if (libro == null) {
            Libro libro = new Libro();
            libro.setTitulo(tituloTxt.getText());
            libro.setIsbn(isbnTxt.getText());
            libro.setAutor(autorChoicebox.getValue());
            LibroDAO.insertLibro(libro);
        } else {
            libro.setTitulo(tituloTxt.getText());
            libro.setIsbn(isbnTxt.getText());
            libro.setAutor(autorChoicebox.getValue());
            LibroDAO.updateLibro(libro);
        }
        Stage stage = (Stage) guardarBtn.getScene().getWindow();
        stage.close();
    }
}
