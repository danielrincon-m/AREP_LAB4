package edu.eci.arep.nanospring.demo;

import static edu.eci.arep.nanospring.NanoSpringApplication.*;

public class App {
    public static void main(String[] args) {
//        String[] components = {"edu.eci.arep.nanospring.demo.HelloWebService"};
        staticFiles("/static");
        run(args);
    }
}
