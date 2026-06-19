package com.item_productos_ubicacion.item_productos_ubicacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Api Control Bodega inventario")
            .version("1.0")
            .description("Con esta API se puede llevar el control de inventario de la bodega")  
        );
    }
}
