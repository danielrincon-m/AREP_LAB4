package edu.eci.arep.httpserver;

import java.util.HashMap;

/**
 * Clase sencilla en donde se almacena la información de retorno para el cliente.
 *
 * @author Daniel Rincón
 */
public class Response {
    private final HashMap<String, String> header = new HashMap<>();
    private byte[] body = new byte[0];
    private String status;

    /**
     * Establece un encabezado para agregar a la respuesta
     *
     * @param key   La llave del encabezado
     * @param value El valor del encabezado
     */
    public void header(String key, String value) {
        header.put(key, value);
    }

    /**
     * @return El mapa de encabezados almacenado en el objeto
     */
    HashMap<String, String> header() {
        return header;
    }

    /**
     * Establece el cuerpo de la solicitud en forma de lista de bytes
     *
     * @param body Los datos del cuerpo de la solicitud
     */
    public void body(byte[] body) {
        this.body = body;
    }

    /**
     * @return El cuerpo de la respuesta almacenada en el objeto
     */
    public byte[] body() {
        return body;
    }

    /**
     * Establece el estado de respuesta
     *
     * @param status El código de respuesta
     */
    public void status(String status) {
        this.status = status;
    }

    /**
     * @return El código de respuesta etablecido en la clase
     */
    public String status() {
        return status;
    }
}
