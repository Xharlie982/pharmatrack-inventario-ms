/*
  App principal de Inventario & Sucursales.
  Expone endpoints REST y configura Swagger (springdoc).
*/
package com.pharmatrack.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventarioApplication.class, args);
    }
}
