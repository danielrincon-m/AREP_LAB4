package edu.eci.arep.httpserver;

import com.github.amr.mimetypes.MimeTypes;
import edu.eci.arep.httpserver.handler.Handler;
import edu.eci.arep.httpserver.handler.StaticHandler;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase principal del servidor Web, se encarga de abrir un socket en el puerto 36000 o en el especificado
 * por la variable de entorno "PORT".
 * <p>
 * Escucha las peticiones que se realizan a través del socket especificado y las maneja utilizando el handler
 * adecuado según la URL que se haya solicitado.
 * <p>
 * Luego se encarga de enviar la respuesta de vuelta en un formato entendible por los navegadores.
 */
public class HttpServer {

    /**
     * Intenta buscar un archivo en los recursos del servidor
     *
     * @param path La ruta al archivo buscado
     * @return Un arreglo de bytes con la información del archivo en binario
     * @throws IOException Cuando no existe el archivo buscado
     */
    public static byte[] getFile(String path) throws IOException {
        try {
            URL resource = HttpServer.class.getClassLoader().getResource(path);
            File file = new File(URLDecoder.decode(resource.getPath(), "UTF-8"));
            return Files.readAllBytes(file.toPath());
        } catch (NullPointerException e) {
            throw new IOException("No se ha encontrado el archivo");
        }
    }

    /**
     * Obtiene el MimeType de un archivo según su extensión, utiliza una librería que contiene
     * una gran base de datos de MimeTypes
     *
     * @param path La ruta al archivo
     * @return El MymeType del archivo
     */
    public static String getMimeType(String path) {
        String[] splPath = path.split("\\.");
        String ext = splPath[splPath.length - 1];
        return MimeTypes.getInstance().getByExtension(ext).getMimeType();
    }


    private final Map<String, Handler> handlers = new HashMap<>();

    /**
     * Permite registrar un nuevo handler, basándose en el prefijo especificado
     *
     * @param h      El handler que manejará la petición
     * @param prefix El prefijo buscado para utilizar ese handler
     */
    public void registerHandler(Handler h, String prefix) {
        handlers.put(prefix, h);
    }

    /**
     * Inicia el servidor y comienza a aceptar peticiones en el puerto especificado
     *
     * @throws IOException Cuando no se puede obtener el ocntrol del puerto de escucha
     */
    public void startServer() throws IOException {
        ServerSocket serverSocket = null;
        int port = HttpServer.getPort();
        handlers.put("/", new StaticHandler());
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }
        while (true) {
            acceptRequest(serverSocket);
        }
    }

    /**
     * Escucha en el socket de entrada a la espera de una petición web que pueda ser procesada
     *
     * @param serverSocket El socket del servidor en donde se está escuchando
     * @throws IOException cuando no se se puede establecer conexión con el cliente
     */
    private void acceptRequest(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = null;

        try {
            System.out.println("\nListo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        Request request = new Request(clientSocket.getInputStream());
        request.parseRequest();
        request.getParams().forEach((k, v) -> System.out.println(k + ": " + v));
        if (!request.getRequestURL().equals("")) {
            System.out.println("Requested: " + request.getRequestURL());
            Response response = useHandler(request);
            byte[] output = constructResponse(response);
            clientSocket.getOutputStream().write(output);
        }
        clientSocket.close();
    }

    /**
     * Utiliza los handlers registrados para tomar la decisión de cual utilizar según el prefijo
     * encontrado en la petición.
     * Si ningún candidato es encontrado utiliza el handler por defecto que busca la solicitud en
     * los archivos estáticos
     *
     * @param request La solicitud procesada por parte del cliente
     * @return La respuesta brindada por el handler
     */
    private Response useHandler(Request request) {
        String url = request.getRequestURL();
        String prefix = url.equals("/") ? "" : "/" + url.split("/")[1];
        if (handlers.containsKey(prefix)) {
            System.out.println(prefix + " handler");
            return handlers.get(prefix).handle(prefix, request);
        } else {
            System.out.println("Default handler");
            return handlers.get("/").handle("", request);
        }
    }

    /**
     * Utiliza el objeto de respuesta y lo convierte en una lista de bytes para ser enviada al cliente
     * de esta forma se puede enviar cualquier tipo de archivo, es importante definir correctamente el
     * MimeType, de esto se encargan los handlers.
     *
     * @param res La respuesta que provee el handler
     * @return Una lista de bytes con la información para enviar al cliente
     */
    private byte[] constructResponse(Response res) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1 ").append(res.status()).append("\r\n");
        for (Map.Entry<String, String> headerItem : res.header().entrySet()) {
            headerBuilder.append(headerItem.getKey()).append(": ").append(headerItem.getValue()).append("\r\n");
        }
        headerBuilder.append("\r\n");

        byte[] header = headerBuilder.toString().getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[res.body().length + header.length];

        for (int i = 0; i < output.length; ++i) {
            output[i] = i < header.length ? header[i] : res.body()[i - header.length];
        }
        return output;
    }

    /**
     * Intenta obtener un puerto de las variables de entorno, si no hay ninguna variable registrada
     * utiliza el puerto 36000 por defecto
     *
     * @return El puerto de escucha
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000; //returns default port if heroku-port isn't set
    }
}
