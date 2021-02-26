package edu.eci.arep.nanospring.demo;

import edu.eci.arep.httpserver.Request;
import edu.eci.arep.httpserver.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.HashMap;

/**
 * En esta clase se manejan las conexiones a la base de datos y se encuentran los métodos específicos de
 * insertar y consultar desde la bd
 *
 * @author Daniel Rincón
 */
public class Database {
    private final String url = "jdbc:postgresql://ec2-54-225-190-241.compute-1.amazonaws.com:5432/d4tv4t2q7n7qe4";
    private final String user = "nvihbfttrdyefl";
    private final String password = "ed081ccc3813515ad6b9f5f21e9f6ea199ec4853cd69e04753d02fb0d27a5f1d";

    /**
     * Método encargado de registrar un usuario cuyos datos llegaron de una petición web
     *
     * @param req La petición realizada
     * @param res La respuesta del servidor
     * @return La respuesta a la inserción de datos
     */
    public static String registerUser(Request req, Response res) {
        StringBuilder debug = new StringBuilder();
        Database db = new Database();
        Connection conn = null;
//        req.getParams().forEach((k, v) -> System.out.println(k + ": " + v));
        try {
            conn = db.connect();
            debug.append("Se realizó exitosamente la conexión con la base de datos\n");
        } catch (SQLException e) {
            debug.append("No se ha podido establecer la conexión con la base de datos\n");
            return debug.toString();
        }

        String table = "persona";
        HashMap<String, String> data = new HashMap<>();
        data.put("doc", req.getParam("doc"));
        data.put("nombre", req.getParam("name"));
        data.put("telefono", req.getParam("tel"));
        data.put("direccion", req.getParam("dir"));
        boolean result = db.insert(table, data, conn);
        if (result) {
            debug.append("Se insertaron correctamente los datos\n");
        } else {
            debug.append("Ocurrió un problema al insertar los datos\n");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            debug.append("Error al cerrar la conexión con la base de datos\n");
        }
        return debug.toString();
    }

    /**
     * Obtiene todos los registros de la tabla de usuarios
     *
     * @param req La petición realizada
     * @param res La respuesta del servidor
     * @return Los datos obtenidos de la bd, codificados como json
     */
    public static String getUsers(Request req, Response res) {
        StringBuilder debug = new StringBuilder();
        Database db = new Database();
        Connection conn = null;
        try {
            conn = db.connect();
//            debug.append("Se realizó exitosamente la conexión con la base de datos\n");
        } catch (SQLException e) {
//            debug.append("No se ha podido establecer la conexión con la base de datos\n");
            return debug.toString();
        }

        String table = "persona";
        try {
            ResultSet rs = db.select(table, conn);
            JSONArray jsonArr = new JSONArray();
            while (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("Documento", rs.getString("doc"));
                json.put("Nombre", rs.getString("nombre"));
                json.put("Teléfono", rs.getString("telefono"));
                json.put("Dirección", rs.getString("direccion"));
                jsonArr.put(json);
            }
//            System.out.println(jsonArr);
            debug.append(jsonArr.toString());
        } catch (SQLException e) {
            e.printStackTrace();
//            debug.append("Ocurrió un problema en la consulta");
        }
        return debug.toString();
    }

    /**
     * Metodo auxiliar para insertar datos en la base de datos
     *
     * @param table La tabla a la cual se insertarán los datos
     * @param data  El mapa de datos junto con su columna respectiva
     * @param conn  La conexión a la bd
     * @return Si la inserción se realizó con éxito
     */
    private boolean insert(String table, HashMap<String, String> data, Connection conn) {
        StringBuilder SQLBuilder = new StringBuilder();
        String SQL = null;

        SQLBuilder.append("INSERT INTO ").append(table).append("(");
        StringBuilder rows = new StringBuilder();
        for (String row : data.keySet()) {
            rows.append(",").append(row);
        }
        SQLBuilder.append(rows.deleteCharAt(0)).append(") VALUES(");
        rows = new StringBuilder();
        for (String row : data.values()) {
            rows.append(",'").append(row).append("'");
        }
        SQLBuilder.append(rows.deleteCharAt(0)).append(");");
        SQL = SQLBuilder.toString();

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    /**
     * Función auxiliar para seleccionar todos los datos de una tabla
     *
     * @param table La tabla de la cual se desea seleccionar
     * @param conn  La conexión a la bd
     * @return El set de resultados obtenido de la bd
     * @throws SQLException .
     */
    private ResultSet select(String table, Connection conn) throws SQLException {
        StringBuilder SQLBuilder = new StringBuilder();
        String SQL = null;

        SQLBuilder.append("SELECT * FROM ").append(table).append(";");
        SQL = SQLBuilder.toString();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        stmt.close();
        return rs;
    }

    /**
     * Se conecta con la base de datos especificada
     *
     * @return La conexión con la bd
     * @throws SQLException .
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
