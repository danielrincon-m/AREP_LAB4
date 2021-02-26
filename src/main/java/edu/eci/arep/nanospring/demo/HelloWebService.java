package edu.eci.arep.nanospring.demo;

import edu.eci.arep.nanospring.RequestMapping;

public class HelloWebService {
    @RequestMapping("/hello")
    public static String index() {
        return "Greetings from NanoSpring!";
    }
}
