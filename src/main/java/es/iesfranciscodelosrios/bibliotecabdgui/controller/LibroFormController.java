package es.iesfranciscodelosrios.bibliotecabdgui.controller;

import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LibroFormController {
    public Button GuardarBtn;
    public Label cabeceraLbl;
    private Libro libro;

    public LibroFormController() {
        libro = null;
    }

    public void initialize() {
        if (libro == null) {
            cabeceraLbl.setText("Añadir libro");
            GuardarBtn.setText("Añadir");
        }
    }

        public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
