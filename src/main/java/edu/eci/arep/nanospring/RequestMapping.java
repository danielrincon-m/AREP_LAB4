package edu.eci.arep.nanospring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que se mantendrá a lo largo del runtime
 * se utilizará para identificar las funciones que serán ejecutadas bajo cierta petición.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    public String value();
}
