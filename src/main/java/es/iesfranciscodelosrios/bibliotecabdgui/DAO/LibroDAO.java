package es.iesfranciscodelosrios.bibliotecabdgui.DAO;

import es.iesfranciscodelosrios.bibliotecabdgui.baseDatos.ConnectionBD;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private final static String SQL_SELECT_ALL = "SELECT * FROM libro";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Libro WHERE idLibro = ?";
    private final static String SQL_SELECT_BY_AUTOR = "SELECT * FROM libro WHERE idAutor = ?";
    private final static String SQL_DELETE = "DELETE FROM Libro WHERE idLibro = ?";

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

    /**
     * Método que elimina un libro de la base de datos si existe, buscándolo por su ID.
     * @param libro: objeto que contiene el ID del autor a eliminar
     * @return true si ha encontrado y eliminado correctamente el autor, false si no se ha podido eliminar
     */
    public static boolean deleteLibroById(Libro libro) {
        boolean deleted = false;
        if (libro != null && findById(libro.getIdLibro())!=null) {
            try(PreparedStatement pst= ConnectionBD.getConnection().prepareStatement(SQL_DELETE)){
                pst.setInt(1,libro.getIdLibro());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }


    /**
     * Método que devuelve un objeto libro si lo ha encontrado en la tabla libro de la bbdd, en función de su id
     * @param idLibro entero que contiene el id del libro para buscar por él en la BBDD
     * @return objeto libro si lo ha encontrado, null si no existe el libro con ese id en la tabla de la bbdd
     */
    private static Libro findById(int idLibro) {
        Libro libro = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idLibro);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                libro = new Libro();
                libro.setIdLibro(rs.getInt("idLibro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutor(null);
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return libro;
    }
}
