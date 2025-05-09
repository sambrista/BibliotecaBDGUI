package es.iesfranciscodelosrios.bibliotecabdgui.DAO;

import es.iesfranciscodelosrios.bibliotecabdgui.baseDatos.ConnectionBD;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Autor;
import es.iesfranciscodelosrios.bibliotecabdgui.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private final static String SQL_ALL = "SELECT * FROM Autor";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Autor WHERE idAutor = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Autor WHERE nombreAutor = ?";
    private final static String SQL_FIND_BY_BOOK_ID = "SELECT a.* FROM Autor a, Libro l WHERE a.idAutor = l.idAutor AND l.idLibro = ?";
    private final static String SQL_INSERT = "INSERT INTO Autor (nombreAutor) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE Autor SET nombreAutor = ? WHERE nombreAutor = ?";
    private final static String SQL_DELETE = "DELETE FROM Autor WHERE idAutor = ?";
    private final static String SQL_DELETE_BY_NAME = "DELETE FROM Autor WHERE NombreAutor = ?";


    /**
     * Version Lazy de obtener todos los autores, esto quiere decir que me traigo de la BBDD
     * solo los autores sin sus libros, por eso relleno por cada autor la lista de libros como
     * una lista vacia.
     * @return la lista de todos los autores de la bbdd
     */
    public static List<Autor> findAll() {
        List<Autor> autores = new ArrayList<Autor>();
        Connection con = ConnectionBD.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("idAutor"));
                autor.setNombreAutor(rs.getString("nombreAutor"));
                //versión Lazy de obtener todos, NO RELLENO LA LISTA DE LIBROS DEL AUTOR
                autor.setMisLibros(new ArrayList<Libro>());
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    /**
     * Version EAGER de obtener todos los autores, esto quiere decir que me traigo de la BBDD
     * los autores con sus libros (la lista de libros de cada autor)
     * @return la lista de todos los autores de la bbdd
     */
    public static List<Autor> findAllEager() {
        List<Autor> autores = new ArrayList<Autor>();

        Connection con = ConnectionBD.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Autor autor = new Autor();
                int idAutor = rs.getInt("idAutor");
                autor.setIdAutor(idAutor);
                autor.setNombreAutor(rs.getString("nombreAutor"));
                //versión EAGER, relleno la lista de libros llamando al método de LIbroDAO que me da la lista de libros de un autor
                autor.setMisLibros(LibroDAO.findLibrosByAutor(idAutor));
                autores.add(autor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return autores;
    }

    /**
     * Método que insertqa un autor en la tabla autor de la bbdd biblioteca
     * @param autor objeto de clase Autor, que tiene los datos de un autor concreto
     * @return el autor insertado o null si no hay podido insertarlo (está repetido o no tiene datos correctos)
     */
   public static Autor insertAutor(Autor autor) {

        if((autor!=null)&&findByName(autor.getNombreAutor())==null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setString(1, autor.getNombreAutor());
                pst.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            autor=null;
        }
        return autor;
    }

    /**
     * Método que devuelve un objeto autor si lo ha encontrado en la tabla autor de la bbdd, en función de su nombre
     * @param nombreAutor cadena que contine el nombre del autor para buscar por él en la BBDD
     * @return objeto autor si lo ha encontrado, null si no existe el autor con ese nombre en la tabla de la bbdd
     */
    private static Autor findByName(String nombreAutor) {
        Autor autor = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            pst.setString(1, nombreAutor);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                autor = new Autor();
                autor.setIdAutor(rs.getInt("idAutor"));
                autor.setNombreAutor(rs.getString("nombreAutor"));
                autor.setMisLibros(new ArrayList<>());
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return autor;
    }

    /**
     * Método que devuelve un objeto autor si lo ha encontrado en la tabla autor de la bbdd, en función de que haya escrito un libro con el id aportado.
     * @param idLibro cadena que contine el id del libro para buscar por él en la BBDD
     * @return objeto autor si lo ha encontrado, null si no existe el libro o no tiene autor
     */
    public static Autor findByBookId(int idLibro) {
        Autor autor = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_BOOK_ID)) {
            pst.setInt(1, idLibro);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                autor = new Autor();
                autor.setIdAutor(rs.getInt("idAutor"));
                autor.setNombreAutor(rs.getString("nombreAutor"));
                autor.setMisLibros(new ArrayList<>());
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return autor;
    }

    /**
     * Método que devuelve un objeto autor si lo ha encontrado en la tabla autor de la bbdd, en función de su id
     * @param idAutor cadena que contine el nombre del autor para buscar por él en la BBDD
     * @return objeto autor si lo ha encontrado, null si no existe el autor con ese id en la tabla de la bbdd
     */
    private static Autor findById(int idAutor) {
        Autor autor = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idAutor);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                autor = new Autor();
                autor.setIdAutor(rs.getInt("idAutor"));
                autor.setNombreAutor(rs.getString("nombreAutor"));
                autor.setMisLibros(new ArrayList<>());
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return autor;
    }

    /**
     * Método que modifica el nombre de un autor si existe en la  bbdd
     * @param autorNuevo: objeto con los datos del nuevo autor
     * @param autorActual objeto con los datos del autor que se supone ya está en la bbdd
     * @return true si ha encontrado el autor por su nombre y lo ha modificado, false si no se ha podido modificar
     */
    public static boolean updateAutor(Autor autorNuevo, Autor autorActual) {
        boolean result = false;
        if((autorNuevo!=null)&&(autorNuevo!=null)&&findByName(autorActual.getNombreAutor())!=null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {

                pst.setString(1, autorNuevo.getNombreAutor());
                pst.setString(2, autorActual.getNombreAutor());
                pst.executeUpdate();
                result = true;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Método que elimina un autor de la base de datos si existe, buscándolo por su ID.
     * @param autor: objeto que contiene el ID del autor a eliminar
     * @return true si ha encontrado y eliminado correctamente el autor, false si no se ha podido eliminar
     */
    public static boolean deleteAutorByID(Autor autor) {
        boolean deleted = false;
        if (autor != null && findById(autor.getIdAutor())!=null) {
            try(PreparedStatement pst= ConnectionBD.getConnection().prepareStatement(SQL_DELETE)){
                pst.setInt(1,autor.getIdAutor());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

    /**
     * Método que elimina un autor de la base de datos si existe, buscándolo por su Nombre.
     * @param autor: objeto que contiene el Nombre del autor a eliminar
     * @return true si ha encontrado y eliminado correctamente el autor, false si no se ha podido eliminar
     */
    public static boolean deleteAutorByName(Autor autor) {
        boolean deleted = false;
        if (autor != null && findByName(autor.getNombreAutor())!=null) {
            try(PreparedStatement pst= ConnectionBD.getConnection().prepareStatement(SQL_DELETE_BY_NAME)){
                pst.setString(1,autor.getNombreAutor());
                pst.executeUpdate();
                deleted = true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

}
