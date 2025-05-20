package es.iespuertodelacruz.mp.canarytrails.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/api/v1/imagenes/ruta/**")
                .addResourceLocations("file:./images/ruta/");

        registry.addResourceHandler("/api/v1/imagenes/usuario/**")
                .addResourceLocations("file:./images/usuario/");

        registry.addResourceHandler("/api/v1/imagenes/fauna/**")
                .addResourceLocations("file:./images/fauna/");

        registry.addResourceHandler("/api/v1/imagenes/flora/**")
                .addResourceLocations("file:./images/flora/");
    }
}
