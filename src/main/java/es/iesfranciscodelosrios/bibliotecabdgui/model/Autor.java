package es.iesfranciscodelosrios.bibliotecabdgui.model;

import java.util.List;
import java.util.Objects;

public class Autor {
    private int idAutor;
    private String nombreAutor;
    private List<Libro> misLibros;

    public Autor() {}

    public Autor(int idAutor, String nombreAutor, List<Libro> misLibros) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.misLibros = misLibros;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public List<Libro> getMisLibros() {
        return misLibros;
    }

    public void setMisLibros(List<Libro> misLibros) {
        this.misLibros = misLibros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "idAutor=" + idAutor +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", misLibros=" + misLibros +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return idAutor == autor.idAutor && Objects.equals(nombreAutor, autor.nombreAutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutor, nombreAutor);
    }
}
