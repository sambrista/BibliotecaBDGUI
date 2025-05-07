package es.iesfranciscodelosrios.bibliotecabdgui.DAO;

import es.iesfranciscodelosrios.bibliotecabdgui.baseDatos.ConnectionBD;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private final static String SQL_SELECT_ALL = "SELECT * FROM libro";
    private final static String SQL_SELECT_BY_AUTOR = "SELECT * FROM libro WHERE idAutor = ?";

    public static List<Libro> findAll() {
        List<Libro> libros = new ArrayList<Libro>();
        Connection con = ConnectionBD.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("idLibro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutor(null);
                libros.add(libro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }

    /**
     * Devuelve la lista de libros de un autor según su Id, en versión Lazy (no relleno el objeto autor de cada libro, lo dejo a null)
     * @param idAutorBuscado
     * @return
     */
    public static List<Libro> findLibrosByAutor(int idAutorBuscado) {
        List<Libro> libros = new ArrayList<Libro>();
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_SELECT_BY_AUTOR)) {
            pst.setInt(1, idAutorBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("idLibro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutor(null);
                libros.add(libro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }

}
