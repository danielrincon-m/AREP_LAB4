package edu.eci.arep.nanospring.demo;

import edu.eci.arep.httpserver.Request;
import edu.eci.arep.httpserver.Response;
import edu.eci.arep.nanospring.NanoSpringApplication;
import edu.eci.arep.nanospring.RequestMapping;

/**
 * Aquí se define un servicio Web, para cada una de las rutas de la aplicación, definimos una función correspondiente
 * que retorna un string con la respuesta que se le debe dar al cliente.
 */
public class HelloWebService {
    @RequestMapping("/register")
    public static String register(Request req, Response res) {
        return NanoSpringApplication.file2String("/static/register.html");
    }

    @RequestMapping("/registerAction")
    public static String registerAction(Request req, Response res) {
        return Database.registerUser(req, res);
    }

    @RequestMapping("/get")
    public static String get(Request req, Response res) {
        return NanoSpringApplication.file2String("/static/get.html");
    }

    @RequestMapping("/getAction")
    public static String getAction(Request req, Response res) {
        return Database.getUsers(req, res);
    }
}
