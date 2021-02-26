# NanoSpark

[![danielrincon-m](https://circleci.com/gh/danielrincon-m/AREP_LAB3.svg?style=svg)](https://app.circleci.com/pipelines/github/danielrincon-m/AREP_LAB3)
[![Heroku](../../Parciales/Primer%20Parcial/img/heroku_long.png)](https://nanospark.herokuapp.com/Apps/register)

## Descripción 🎇

NanoSpark es un framework web liviano que nos permite desplegar páginas web de manera sencilla y con muy pocas lineas de código,
además nos brinda la posibilidad de ver cualquier tipo de archivo en el navegador gracias a su amplia base de datos de
códigos MimeType. 

### Prueba de concepto

La prueba de concepto del Framework se divide en dos fases:

#### Primera Fase

Esta fase trata de mostrar la capacidad del servidor web de devolver recursos estáticos de varios tipos al cliente, 
para esto, a continuación daremos varios ejemplos de archivos estáticos alojados en el servidor.

- **Imagen / ico**: [Imagen en formato .ico](https://nanospark.herokuapp.com/favicon.ico)
- **Imagen / png**: [Imagen en formato .png](https://nanospark.herokuapp.com/static/img/future.png)
- **Script / JavaScript**: [Archivo de texto en formato .js](https://nanospark.herokuapp.com/static/js/get.js)
- **Script / css**: [Archivo de texto en formato .css](https://nanospark.herokuapp.com/static/css/main.css)

Una vez confirmamos que nuestro servidor web era capaz de devolverle al cliente archivos de cualquier tipo al cliente, continuamos
con la segunda fase, en donde realizamos una aplicación web funcional utilizando nuestro Framework.

#### Segunda Fase

Nuestra aplicación web es un simple registro de usuarios, en donde ponen sus datos básicos (nombre, documento, dirección y teléfono),
estos registros se realizan en una base de datos PostgreSQL de manera asíncrona por medio de JavaScript. La aplicación consta de dos partes:

- Un formulario de inscripción de usuarios el cual se puede consultar [aquí.](https://nanospark.herokuapp.com/Apps/register)
- Una página web en donde se pueden consultar los usuarios registrados hasta el momento, la cual se puede consultar
[aquí](https://nanospark.herokuapp.com/Apps/get)

De esta forma finaliza nuestra prueba de concepto, todos los servicios web están corriendo sobre el mini framework "NanoSpark". Como pudimos
observar ya se encuentra en una etapa bastante funcional, y está preparado para correr aplicaciones más complejas.

### Cómo utilizar el programa

Al abrir el [sitio web de registro](https://nanospark.herokuapp.com/Apps/register) nos encontraremos con una pantalla 
como esta:

![Pantalla Registro](img/PantallaRegistro.jpg)

✔️ Esta pantalla contiene un formulario en donde el usuario que se quiera registrar en la aplicación debe ingresar sus
datos básicos: Nombre, Documento, Teléfono y Dirección.

✔️ Una vez ingresados los datos personales podremos registrarnos dándole click al botón de registrar 
(si no llenamos todos los campos, no nos permitirá registrarnos).

✔️ Luego de un breve periodo de tiempo recibiremos una notificación, y nos habremos registrado exitosamente
en la aplicación.

--

Luego de esto nos gustaría verificar si efectivamente quedamos registrados, para ello, podremos ir a la
[página de consulta](https://nanospark.herokuapp.com/Apps/get), en donde nos encontraremos una pantalla como esta:

![Pantalla Consulta](img/PantallaConsulta.jpg)

✔️ Se trata de una pantalla informativa en donde podremos ver todos los usuarios que se han registrado en la aplicación. 

✔️ Si todo salió bien, deberías poder ver tu nombre en esta pantalla.

## Cómo obtener el proyecto 📥

### Prerequisitos

Asegúrese de tener git instalado en su máquina, lo puede hacer desde la [página oficial][gitLink].

### Descarga del proyecto

Clone el proyecto utilizando el siguiente comando:

```
git clone https://github.com/danielrincon-m/AREP_LAB3.git
```

## Correr las pruebas unitarias 🧪

### Prerequisitos

Un IDE que soporte proyectos Java, o una instalación de Maven en su sistema, puebe obtenerlo desde
la [página oficial.][mvnLink]

### Ejecución de pruebas

Las pruebas pueden ser ejecutadas desde la sección de pruebas de su IDE o si tiene maven puede navegar a la carpeta
principal del proyecto y ejecutar el comando

```
mvn test
```

## Documentación del código fuente 🌎

La documentación del proyecto puede ser encontrada en la carpeta [docs](/docs).

También puede ser generada con Maven, clonando el proyecto y ejecutando el siguiente comando:

```
mvn javadoc:javadoc
```

## Documento de diseño 📄

El documento de diseño del programa puede ser encontrado [aquí](Lab3_AREP.pdf).

## Herramientas utilizadas 🛠️

* [IntelliJ IDE](https://www.jetbrains.com/es-es/idea/download/) - IDE de desarrollo
* [Maven](https://maven.apache.org/) - Manejo de Dependencias
* [JUnit](https://junit.org/junit4/) - Pruebas unitarias
* [GitHub](https://github.com/) - Repositorio de código
* [Mime-Types](https://github.com/jshttp/mime-types) - Herramienta de consulta de MimeTypes
* [PostgreSQL](https://www.postgresql.org/) - Base de datos

## Autor 🧔

**Daniel Felipe Rincón Muñoz:** *Planeación y desarrollo del proyecto* -
[Perfil de GitHub](https://github.com/danielrincon-m)

## Licencia 🚀

Este proyecto se encuentra licenciado bajo **GNU General Public License** - consulte el archivo [LICENSE.md](LICENSE.md)
para más detalles.

<!-- 
## Acknowledgments 

* Hat tip to anyone whose code was used
* Inspiration
* etc
-->

[gitLink]: https://git-scm.com/downloads
[mvnLink]: https://maven.apache.org/download.cgi
