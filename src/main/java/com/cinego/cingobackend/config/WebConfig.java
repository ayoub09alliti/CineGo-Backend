package com.cinego.cingobackend.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cinego.cingobackend.entity.User;
import com.cinego.cingobackend.model.Customers;
import com.cinego.cingobackend.model.Film;
import com.cinego.cingobackend.model.Genre;
import com.cinego.cingobackend.model.Media;
import com.cinego.cingobackend.model.Nationalite;
import com.cinego.cingobackend.model.Personne;
import com.cinego.cingobackend.model.Salle;
import com.cinego.cingobackend.model.Seance;

@Configuration
public class WebConfig implements WebMvcConfigurer, RepositoryRestConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("uploads").toAbsolutePath().normalize() + File.separator;
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200", "http://localhost:*", "http://127.0.0.1:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(
                Film.class,
                Genre.class,
                Salle.class,
                Seance.class,
                Personne.class,
                Nationalite.class,
                Media.class,
                Customers.class,
                User.class
        );
        cors.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200", "http://localhost:*", "http://127.0.0.1:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}