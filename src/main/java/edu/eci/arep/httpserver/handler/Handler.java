package edu.eci.arep.httpserver.handler;

import edu.eci.arep.httpserver.Request;
import edu.eci.arep.httpserver.Response;

public interface Handler {

    /**
     * Maneja la petición del cliente y construye una respuesta para ser enviada de vuelta
     *
     * @param prefix El prefijo de la ruta que se solicitó
     * @param req    La petición web que fué realizada
     * @return La respuesta construida para ser enviada al cliente
     */
    Response handle(String prefix, Request req);
}
