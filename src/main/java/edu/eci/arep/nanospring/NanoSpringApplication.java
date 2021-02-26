package edu.eci.arep.nanospring;

import edu.eci.arep.httpserver.HttpServer;
import edu.eci.arep.httpserver.Request;
import edu.eci.arep.httpserver.Response;
import edu.eci.arep.httpserver.handler.Handler;
import edu.eci.arep.httpserver.handler.StaticHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase contiene un micro-framework web, el cual funciona por medio de reflexión y anotaciones, de esta forma
 * detecta todas las funciones anotadas con @RequestMapping y mapea una url a una función en el servidor.
 */
public class NanoSpringApplication implements Handler {
    private static final NanoSpringApplication _instance = new NanoSpringApplication();

    private final Map<String, Method> componentsRoutes = new HashMap<>();

    private static String staticFiles = "";
    private boolean componentsLoaded = false;

    private NanoSpringApplication() {
    }

    /**
     * Función principal, inicia el framework
     * @param args las clases en donde se deben buscar anotaciones
     */
    public static void run(String[] args) {
        if (!_instance.componentsLoaded) {
            try {
                _instance.loadComponents(args);
                _instance.componentsLoaded = true;
                _instance.startServer();
            } catch (ClassNotFoundException e) {
                Logger.getLogger(NanoSpringApplication.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Inicia el servidor web que funciona en el back.end
     */
    private void startServer() {
        try {
            HttpServer hServer = new HttpServer();
            hServer.registerHandler(this, "/nspapp");
            hServer.startServer();
        } catch (IOException e) {
            Logger.getLogger(NanoSpringApplication.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Carga las clases en donde se deben buscar las anotaciones y asigna cada una de las rutas encontradas a su
     * correspondiente función a ejecutar
     * @param components Las clases en donde se deben buscar las anotaciones
     * @throws ClassNotFoundException Cuando la clase no existe o no se encuentra
     */
    private void loadComponents(String[] components) throws ClassNotFoundException {
        for (String classPath : components) {
            for (Method m : Class.forName(classPath).getMethods()) {
                if (m.isAnnotationPresent(RequestMapping.class)) {
                    componentsRoutes.put(m.getAnnotation(RequestMapping.class).value(), m);
                }
            }
        }
    }

    /**
     * Invoca un método de alguna de las clases registradas, esto dependiendo de la anotación declarada
     * @param staticMethod El método a ejecutar
     * @param args Los argumentos para brindarle al método
     * @return La respuesta que brindó el método
     */
    public static String invoke(Method staticMethod, Object... args) {
        String body = ".";
        try {
            body = (String) staticMethod.invoke(null, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Logger.getLogger(NanoSpringApplication.class.getName()).log(Level.SEVERE, null, e);
        }
        return body;
    }

    /**
     * Define la ruta de archivos estáticos para el servidor web
     * @param staticFiles La ruta de archivos estáticos
     */
    public static void staticFiles(String staticFiles) {
        NanoSpringApplication.staticFiles = staticFiles;
    }

    /**
     * Abre un archivo, lo lee y lo retorna como cadena
     * @param path La ruta para encontrar el archivo
     * @return El contenido del archivo en forma de cadena
     */
    public static String file2String(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        path = path.startsWith("/") ? path.substring(1) : path;
        try {
            File file = new File(URLDecoder.decode(HttpServer.class.getClassLoader().getResource(path).getPath(),
                    "UTF-8"));
            BufferedReader in = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str).append('\n');
            }
            in.close();
        } catch (NullPointerException | IOException e) {
            return path + " not found.";
        }
        return contentBuilder.toString();
    }

    @Override
    public Response handle(String prefix, Request req) {
        Response res = new Response();

        String path = req.getRequestURL().replaceFirst(prefix, "");
        if (componentsRoutes.containsKey(path)) {
            res.header("Content-Type", "text/html");
            res.status("200 OK");
            String body = invoke(componentsRoutes.get(path), req, res);
            res.body(body.getBytes(StandardCharsets.UTF_8));
        } else {
            req.removePrefix(prefix);
            return new StaticHandler().handle(staticFiles, req);
        }
        return res;
    }
}
